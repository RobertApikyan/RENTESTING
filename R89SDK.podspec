Pod::Spec.new do |spec|
    spec.name                     = 'R89SDK'
    spec.version                  = '0.7.8'
    spec.homepage                 = 'Link to a Kotlin/Native module homepage'
    spec.authors                  = { 'Your Name' => 'your.email@example.com' }
    spec.license                  = { :type => 'MIT', :file => 'LICENSE' }
    spec.summary                  = 'A summary of SDK.'
    spec.ios.deployment_target = '12.0'
    spec.dependency 'CmpSdk', '2.0.1'
    spec.dependency 'Google-Mobile-Ads-SDK', '11.5.0'
    spec.dependency 'PrebidMobile', '2.2.2'
spec.source = { :git => 'https://github.com/RobertApikyan/RENTESTING.git', :tag => '0.7.9' }
spec.vendored_frameworks = 'R89SDK.xcframework'
spec.source_files = 'R89SDK.xcframework/ios-arm64/R89SDK.framework/Headers/*.h','R89SDK.xcframework/ios-arm64_x86_64-simulator/R89SDK.framework/Headers/*.h'
spec.public_header_files = 'R89SDK.xcframework/ios-arm64/R89SDK.framework/Headers/*.h','R89SDK.xcframework/ios-arm64_x86_64-simulator/R89SDK.framework/Headers/*.h'
end