Pod::Spec.new do |spec|
  spec.name         = 'RENTESTING'
  spec.version      = '1.0.2'
  spec.summary      = 'A summary of RENTESTING.'
  spec.description  = 'A more detailed description of RENTESTING.'
  spec.homepage     = 'https://github.com/RobertApikyan/RENTESTING'
  spec.license      = { :type => 'MIT', :file => 'LICENSE' }
  spec.author       = { 'Your Name' => 'your.email@example.com' }
  spec.source       = { :git => 'https://github.com/RobertApikyan/RENTESTING.git', :tag => '1.0.2' }
  spec.platform     = :ios, '12.0'
  spec.vendored_frameworks = 'RENTESTING.xcframework'
  spec.dependency 'Google-Mobile-Ads-SDK'
  spec.dependency 'CmpSdk'
  spec.dependency 'PrebidMobile'
  spec.source_files = 'RENTESTING.xcframework/ios-arm64/RENTESTING.framework/Headers/*.h',
                      'RENTESTING.xcframework/ios-arm64_x86_64-simulator/RENTESTING.framework/Headers/*.h'
  spec.public_header_files = 'RENTESTING.xcframework/ios-arm64/RENTESTING.framework/Headers/*.h',
                             'RENTESTING.xcframework/ios-arm64_x86_64-simulator/RENTESTING.framework/Headers/*.h'
end
``
