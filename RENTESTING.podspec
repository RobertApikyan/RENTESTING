Pod::Spec.new do |spec|
  spec.name         = 'RENTESTING'
  spec.version      = '1.0.1'
  spec.summary      = 'A summary of RENTESTING.'
  spec.description  = 'A more detailed description of RENTESTING.'
  spec.homepage     = 'https://example.com/RENTESTING'
  spec.license      = { :type => 'MIT', :file => 'LICENSE' }
  spec.author       = { 'Your Name' => 'your.email@example.com' }
  spec.source       = { :git => 'https://github.com/RobertApikyan/RENTESTING.git', :tag => '1.0.1' }
  spec.platform     = :ios, '12.0'

  # Vendored frameworks
  spec.vendored_frameworks = 'RENTESTING.xcframework'

  # Specify the dependency
  spec.dependency 'Google-Mobile-Ads-SDK'
  spec.dependency 'CmpSdk'
  spec.dependency 'PrebidMobile'

  # Source files
  # Specify source files and public headers (adjust paths as necessary)
  spec.source_files = 'RENTESTING.xcframework/ios-arm64/RENTESTING.framework/Headers/*.h',
                      'RENTESTING.xcframework/ios-arm64_x86_64-simulator/RENTESTING.framework/Headers/*.h'
  spec.public_header_files = 'RENTESTING.xcframework/ios-arm64/RENTESTING.framework/Headers/*.h',
                             'RENTESTING.xcframework/ios-arm64_x86_64-simulator/RENTESTING.framework/Headers/*.h'
end