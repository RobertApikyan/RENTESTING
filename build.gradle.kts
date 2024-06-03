@file:Suppress("PropertyName")

import com.github.gmazzo.buildconfig.BuildConfigExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.io.ByteArrayOutputStream

typealias SourceSetContext = Action<NamedDomainObjectContainer<KotlinSourceSet>>

plugins {
	id(libs.plugins.android.library.get().pluginId) apply true
	id(libs.plugins.jetbrains.kotlin.multiplatform.get().pluginId) apply true
	id(libs.plugins.jetbrains.kotlin.serialization.get().pluginId)
	id(libs.plugins.kotlinCocoapods.get().pluginId) apply true
	alias(libs.plugins.jetbrains.dokka)
	alias(libs.plugins.buildconfig)
	`maven-publish`
	signing
}

val SDK_NAME = "R89SDK"
val PUBLISH_VERSION: String get() = project.propertyOrEmpty("versionName")

////// Build Variant Configuration

enum class FlavorType
{
	FLUTTER,
	DEFAULT;
}

val flavorType: FlavorType by lazy {
	val property = project.findProperty("flavor")
	FlavorType.values().firstOrNull { it.name == property } ?: throw IllegalArgumentException(
		"No flavor found with name $property specified " +
				"in the gradle.properties, available values are ${FlavorType.values()}"
	)
}

kotlin {
//	The line below is added to bypass following error while build
//	Cannot locate tasks that match ':sharedCore:testClasses' as task 'testClasses' not found in project ':sharedCore'.
	task("testClasses")
	
	androidTarget {
		compilations.all {
			kotlinOptions {
				jvmTarget = "1.8"
			}
		}
		publishLibraryVariants("release", "debug")
	}
	
	val xcf = XCFramework(SDK_NAME)
	listOf(
		iosX64(),
		iosArm64(),
		iosSimulatorArm64()
	).forEach {
		it.binaries.framework {
			baseName = SDK_NAME
			isStatic = true
			xcf.add(this)
		}
	}
	
	sourceSets {
		val commonMain by getting {
			dependencies {
				//put your multiplatform dependencies here
				implementation(libs.ktor.client.core)
				implementation(libs.ktor.client.content.negotiation)
				implementation(libs.ktor.client.logging)
				implementation(libs.ktor.serialization.kotlinx.json)
			}
		}
		
		//Android
		androidDependsOn(commonMain) {
			dependencies {
				implementation(libs.ktor.client.okhttp)
				implementation(libs.google.gmasdk)
				implementation(libs.consentmanagernet.androidlibrary)
				implementation(files("libs/PrebidMobile-fat.jar"))
				implementation(libs.google.material)
				implementation(libs.squareup.picasso)
			}
		}
		//iOS
		iosDependsOn(commonMain) {
			dependencies {
				// Add iOS dependencies here
				implementation(libs.ktor.client.darwin)
			}
		}
		cocoapods {
			name = SDK_NAME
			version = PUBLISH_VERSION
			summary = "A summary of SDK."
			// TODO(@Apikyan): Resolve where to put description
			homepage = "Link to a Kotlin/Native module homepage"
			license = "{ :type => 'MIT', :file => 'LICENSE' }"
			authors = "{ 'Your Name' => 'your.email@example.com' }"
			// TODO(@Apikyan): Add dynamic version to tag
			source = "{ :git => 'https://github.com/RobertApikyan/RENTESTING.git', :tag => '1.0.3' }"
			ios.deploymentTarget = "12.0"
			
			// Add the pod dependency
			pod("Google-Mobile-Ads-SDK") {
				moduleName = "GoogleMobileAds"
				version = "11.5.0"
			}
			pod("CmpSdk") {
				version = "2.0.1"
			}
			pod("PrebidMobile") {
				extraOpts += listOf("-compiler-option", "-fmodules")
				version = "2.2.2"
			}
		}
	}
	
	// To include public API comments in the generated header files.
	targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
		compilations["main"].compilerOptions.options.freeCompilerArgs.add("-Xexport-kdoc")
	}
}

buildConfig {
	fromPropertyToBuildConfig("PREBID_SERVER_ACCOUNT_ID")
	fromPropertyToBuildConfig("managerApiVersion", overrideName = "API_VERSION_STRING")
}

android {
	namespace = "com.refinery89.sharedcore"
	compileSdk = 34
	defaultConfig {
		minSdk = 19
	}
}

dependencies {

}

////// Source set setups for platform targets

fun NamedDomainObjectContainer<KotlinSourceSet>.androidDependsOn(
	commonMain: KotlinSourceSet,
	configure: KotlinSourceSet.() -> Unit,
)
{
	val androidMain by getting
	val androidFlutterSourceSet = "androidFlutterSdk"
	val androidDefaultSourceSet = "androidDefaultSdk"
	
	val intermediate = createIntermediateSourceSet(androidFlutterSourceSet, androidDefaultSourceSet)
	androidMain.dependsOn(intermediate)
	
	val androidInternal by creating {
		dependsOn(commonMain)
		intermediate.dependsOn(this)
		configure(this)
	}
}

fun NamedDomainObjectContainer<KotlinSourceSet>.iosDependsOn(
	commonMain: KotlinSourceSet,
	configure: KotlinSourceSet.() -> Unit,
)
{
	val iosX64Main by getting
	val iosArm64Main by getting
	val iosSimulatorArm64Main by getting
	val iosMain by creating {
		dependsOn(commonMain)
		iosX64Main.dependsOn(this)
		iosArm64Main.dependsOn(this)
		iosSimulatorArm64Main.dependsOn(this)
	}
	
	val iOSFlutterSourceSet = "iosFlutterSdk"
	val iOSDefaultSourceSet = "iosDefaultSdk"
	
	val intermediate = createIntermediateSourceSet(iOSFlutterSourceSet, iOSDefaultSourceSet)
	iosMain.dependsOn(intermediate)
	
	val iosInternal by creating {
		dependsOn(commonMain)
		intermediate.dependsOn(this)
		configure(this)
	}
}

fun NamedDomainObjectContainer<KotlinSourceSet>.createIntermediateSourceSet(
	vararg intermediateSourceSets: String,
): KotlinSourceSet
{
	return intermediateSourceSets.firstOrNull { it.contains(flavorType.name, ignoreCase = true) }
		?.run { create(this) } ?: throw IllegalStateException(
		"No sourceSet matches for flavor=${flavorType} in " +
				"provided sourceSets=$intermediateSourceSets\nNote: Source set name should comply to <prefix><flavor=${FlavorType.values()}><suffix> pattern."
	)
}

////// ENVIRONMENT VARIABLES

fun BuildConfigExtension.fromPropertyToBuildConfig(name: String, overrideName: String? = null) = buildConfigField("String", overrideName ?: name, buildConfigProperty(name))

fun Project.buildConfigProperty(name: String) = "\"${propertyOrEmpty(name)}\""

fun Project.propertyOrEmpty(name: String): String
{
	val property = findProperty(name) as String?
	return property ?: environmentVariable(name)
}

fun environmentVariable(name: String) = System.getenv(name) ?: ""

/////// PUBLICATION

val ANDROID_LIBRARY_PLUGIN = "com.android.library"
val SIGNING_KEY_NAME: String = "signing.gnupg.keyName"
val IS_PRODUCTION_KEY_NAME: String = "isProduction"

//Build artifact group
val PUBLISH_GROUP_ID = "com.refinery89.androidsdk"

val PUBLISH_DEFAULT_ARTIFACT_ID = when (flavorType)
{
	FlavorType.FLUTTER -> "mobile-flutter"
	FlavorType.DEFAULT -> "mobile"
}

val PUBLISH_DESCRIPTION = "Android SDK for Publishers of Refinery89"
val PUBLISH_DEVELOPER_ID = "Refinery89 Devs"
val PUBLISH_DEVELOPER_NAME = "Refinery 89 Devs"
val PUBLISH_DEVELOPER_EMAIL = "development@refinery89.com"
val PUBLISH_ORGANIZATION = "Refinery 89"
val PUBLISH_ORGANIZATION_URL = "https://refinery89.com"
val EMPTY_STRING = ""

tasks.register<Jar>("androidSourcesJarWithReadme") {
	
	mustRunAfter(":sharedCore:generateMetadataFileForAndroidReleasePublication")
	
	group = "publishing"
	archiveClassifier.set("sources")
	
	val readmeContent = "This is a fake Readme file for the Not Exposing Source files"
	val fakeSourcesDirectory = buildDir.resolve("fakeSources").apply {
		mkdirs()
		val readmeFile = resolve("Readme.md")
		
		readmeFile.writeText(readmeContent)
	}
	
	// Configure the Jar task to include the Readme.md from the fakeSources directory
	from(fakeSourcesDirectory)
	
	// Specify the output directory and the final jar name (optional if you want to override the default)
	destinationDirectory.set(buildDir.resolve("libs"))
	archiveFileName.set("r89SDK-sources.jar")
}

tasks.register<Jar>("dokkaJavadocJar") {
	archiveClassifier.set("javadoc")
	from(tasks["dokkaJavadoc"])
}

afterEvaluate {
	publishing {
		
		val hasSigningKey = project.hasProperty(SIGNING_KEY_NAME)
		val isProduction = project.propertyOrEmpty(IS_PRODUCTION_KEY_NAME).toBoolean()
		val publishVersion = PUBLISH_VERSION
		
		publications {
			components.forEach { component ->
				create<MavenPublication>(component.name) {
					from(component)
					groupId = PUBLISH_GROUP_ID
					artifactId = PUBLISH_DEFAULT_ARTIFACT_ID
					version = publishVersion
					
					artifact(tasks["androidSourcesJarWithReadme"])
					artifact(tasks["dokkaJavadocJar"])
					
					pom {
						name.set(artifactId)
						description.set(PUBLISH_DESCRIPTION)
						
						//TODO change this to the SDK product Page
						url.set(PUBLISH_ORGANIZATION_URL)
						organization {
							name.set(PUBLISH_ORGANIZATION)
							url.set(PUBLISH_ORGANIZATION_URL)
						}
						developers {
							developer {
								id.set(PUBLISH_DEVELOPER_ID)
								name.set(PUBLISH_DEVELOPER_NAME)
								email.set(PUBLISH_DEVELOPER_EMAIL)
							}
						}
						scm {
							connection.set(EMPTY_STRING)
							developerConnection.set(EMPTY_STRING)
							url.set(EMPTY_STRING)
						}
						licenses {
							license {
								name.set("No License")
								url.set(EMPTY_STRING)
							}
						}
						//inside the pom block
						this.withXml {
							val repositoriesNode = this.asNode().appendNode("repositories")
							val jitpackIoNode = repositoriesNode.appendNode("repository")
							
							jitpackIoNode.appendNode("releases").apply {
								appendNode("enabled", "true")
								appendNode("updatePolicy", "always")
								appendNode("checksumPolicy", "warn")
							}
							
							jitpackIoNode.appendNode("snapshots").apply {
								appendNode("enabled", "false")
								appendNode("updatePolicy", "never")
								appendNode("checksumPolicy", "fail")
							}
							
							jitpackIoNode.appendNode("name", "jitpack")
							jitpackIoNode.appendNode("id", "jitpack-repo")
							jitpackIoNode.appendNode("url", "https://jitpack.io")
							jitpackIoNode.appendNode("layout", "default")
						}
					}
				}
			}
		}
		
		// SIGNING
		// You need to have GpgPG installed i recommend installing Gpg4win and Importing the Secret key.
		// if you have no access to the private key (secret key) and you want to test publication:
		// comment signing block AND DON'T PUSH It to VCS
		if (isProduction)
		{
			if (hasSigningKey)
			{
				signing {
					useGpgCmd()
					sign(publishing.publications)
				}
			} else
			{
				println("No Signing Key found, skipping signing")
			}
		} else
		{
			println("Not going to sign, skipping signing")
		}
	}
}

//// IOS PUBLICATION
val ASSEMBLE_TASK_NAME = "assemble${SDK_NAME}XCFramework"
val PODSPEC_TASK_NAME = "podspec"
val IOS_PUBLICATION_TASK_NAME = "publishIosXcFrameworkToCocoapods"
val GENERATED_XC_FRAMEWORK_PATH = "build/XCFrameworks/release/${SDK_NAME}.xcframework"
val ORIGINAL_SPEC_LIST = setOf(
	"spec.name",
	"spec.version",
	"spec.summary",
	"spec.description",
	"spec.homepage",
	"spec.license",
	"spec.author",
	"spec.platform",
	"spec.ios.deployment_target",
	"spec.dependency"
)

tasks.register(IOS_PUBLICATION_TASK_NAME) {
	val frameworkName = "$SDK_NAME.framework"
	val xcFrameworkName = "$SDK_NAME.xcframework"
	val podspecName = "${SDK_NAME}.podspec"
	val remoteUrl = "https://github.com/RobertApikyan/RENTESTING.git"
	val xcFrameworkDirectory = "${project.projectDir}/build/XCFrameworks/release/"
	
	group = "publishing"
	description = "Pushes the $xcFrameworkName to remote repository and publishes to Cocoapods."

//	dependsOn(ASSEMBLE_TASK_NAME)
//	dependsOn(PODSPEC_TASK_NAME)

//	CREATE THE PODSPEC
	val generatedPodspecFile = File("${project.projectDir}/${podspecName}")
	val projectSpecs = generatedPodspecFile.readLines().filter { specLine -> ORIGINAL_SPEC_LIST.any(specLine::contains) }.toMutableList()
	projectSpecs.add(0, "Pod::Spec.new do |spec|")
	projectSpecs.add("spec.source = { :git => '$remoteUrl', :tag => '$PUBLISH_VERSION' }")
	projectSpecs.add("spec.vendored_frameworks = \'${xcFrameworkName}\'")
	projectSpecs.add("spec.source_files = '${xcFrameworkName}/ios-arm64/$frameworkName/Headers/*.h','${xcFrameworkName}/ios-arm64_x86_64-simulator/$frameworkName/Headers/*.h'")
	projectSpecs.add("spec.public_header_files = '${xcFrameworkName}/ios-arm64/$frameworkName/Headers/*.h','${xcFrameworkName}/ios-arm64_x86_64-simulator/$frameworkName/Headers/*.h'")
	projectSpecs.add("end")
	val modifiedPodspecFile = File("$xcFrameworkDirectory$podspecName")
	modifiedPodspecFile.writeText(projectSpecs.joinToString("\n"))
//	SIGN THE FRAMEWORK
//	TODO
	
	dependsOn(PUSH_TO_REPO_TASK_NAME)
	
	projectSpecs.forEach(::println)
}

//	PUSH TO REPO
val PUSH_TO_REPO_TASK_NAME = "pushToRepo"
task<Exec>(PUSH_TO_REPO_TASK_NAME) {
	val xcFrameworkDirectory = "${project.projectDir}/build/XCFrameworks/release/"
	val remoteUrl = "https://github.com/RobertApikyan/RENTESTING.git"
	val remoteName = "sdk"
	workingDir = File(xcFrameworkDirectory)
	
//	commandLine("sh","${project.projectDir}/publish.sh")
	
//	 Initialize Git repository
	println("Initializing Git repository...")
	commandLine("git", "init")
	
	println("Adding remote repository...")
	val output = ByteArrayOutputStream()
	exec {
		commandLine("git", "remote", "-v")
		isIgnoreExitValue = true
		standardOutput = output
	}
	val hasRemote = output.toString().contains(remoteName)
	output.close()
	// Add remote repository
	if(!hasRemote){
		println("Adding remote repository...")
		exec {
			commandLine("git", "remote", "add", remoteName, remoteUrl)
			isIgnoreExitValue = true
		}
	}
	// Add all files
	println("Adding all files...")
	exec {
		commandLine("git", "add", ".")
		isIgnoreExitValue = true
	}
	
	// Commit changes
	println("Committing changes...")
	exec {
		commandLine("git", "commit", "-m", "\"$PUBLISH_VERSION\"")
		isIgnoreExitValue = true
	}
	// Create tag
	println("Creating tag...")
	exec {
		commandLine("git", "tag", PUBLISH_VERSION)
		isIgnoreExitValue = true
	}
	
	// Push tag to remote repository
	println("Pushing tag to remote repository...")
	exec {
		commandLine("git", "push", "--force", "sdk", PUBLISH_VERSION)
		isIgnoreExitValue = true
	}
}