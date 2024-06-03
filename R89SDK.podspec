Pod::Spec.new do |spec|
    spec.name                     = 'R89SDK'
    spec.version                  = '1.2.5'
    spec.homepage                 = 'https://refinery89.com/monetize-app-sdk/'
    spec.source                   = { :http=> ''}
    spec.authors                  = { 'Refinery89' => 'Refinery89 SDK authors' }
    spec.license                  = { :type => 'No License', :file => 'LICENSE' }
    spec.summary                  = 'Monetize App SDK.'
    spec.dependency 'CmpSdk', '2.0.1'
    spec.dependency 'Google-Mobile-Ads-SDK', '11.5.0'
    spec.dependency 'PrebidMobile', '2.2.2'
	spec.source = { :git => 'https://github.com/RobertApikyan/RENTESTING.git', :tag => '1.2.5' }
	spec.platform         = :ios, '12.0'
	spec.vendored_frameworks = 'R89SDK.xcframework'
	spec.source_files = 'R89SDK.xcframework/ios-arm64/R89SDK.framework/Headers/*.h','R89SDK.xcframework/ios-arm64_x86_64-simulator/R89SDK.framework/Headers/*.h'
	spec.public_header_files = 'R89SDK.xcframework/ios-arm64/R89SDK.framework/Headers/*.h','R89SDK.xcframework/ios-arm64_x86_64-simulator/R89SDK.framework/Headers/*.h'
end