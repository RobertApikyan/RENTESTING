Pod::Spec.new do |spec|
    spec.name                     = 'R89SDK'
    spec.version                  = '0.6.6'
    spec.homepage                 = 'Link to a Kotlin/Native module homepage'
    spec.source                   = { :git => 'https://github.com/RobertApikyan/RENTESTING.git', :tag => '1.0.3' }
    spec.authors                  = { 'Your Name' => 'your.email@example.com' }
    spec.license                  = { :type => 'MIT', :file => 'LICENSE' }
    spec.summary                  = 'A summary of SDK.'
    spec.vendored_frameworks      = 'sharedCore.xcframework'
    spec.libraries                = 'c++'
    spec.ios.deployment_target = '12.0'
    spec.dependency 'CmpSdk', '2.0.1'
    spec.dependency 'Google-Mobile-Ads-SDK', '11.5.0'
    spec.dependency 'PrebidMobile', '2.2.2'
                
                
                
                
end