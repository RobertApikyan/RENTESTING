Pod::Spec.new do |spec|
    spec.name                     = 'R89SDK'
    spec.version                  = '1.2.0'
    spec.homepage                 = 'Link to a Kotlin/Native module homepage'
    spec.source                   = { :http=> ''}
    spec.authors                  = { 'Refinery89' => 'Refinery89 SDK authors' }
    spec.license                  = { :type => 'No License', :file => 'LICENSE' }
    spec.summary                  = 'A summary of SDK.'
    spec.ios.deployment_target = '12.0'
    spec.dependency 'CmpSdk', '2.0.1'
    spec.dependency 'Google-Mobile-Ads-SDK', '11.5.0'
    spec.dependency 'PrebidMobile', '2.2.2'
	spec.public_header_files = 'R89SDK.xcframework/ios-arm64/R89SDK.framework/Headers/*.h','R89SDK.xcframework/ios-arm64_x86_64-simulator/R89SDK.framework/Headers/*.h'
end