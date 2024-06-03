Pod::Spec.new do |spec|
    spec.version                  = '1.2.2'
	spec.source = { :git => 'https://github.com/RobertApikyan/RENTESTING.git', :tag => '1.2.2' }
	spec.platform         = :ios, '12.0'
	spec.vendored_frameworks = 'R89SDK.xcframework'
	spec.source_files = 'R89SDK.xcframework/ios-arm64/R89SDK.framework/Headers/*.h','R89SDK.xcframework/ios-arm64_x86_64-simulator/R89SDK.framework/Headers/*.h'
	spec.public_header_files = 'R89SDK.xcframework/ios-arm64/R89SDK.framework/Headers/*.h','R89SDK.xcframework/ios-arm64_x86_64-simulator/R89SDK.framework/Headers/*.h'
end