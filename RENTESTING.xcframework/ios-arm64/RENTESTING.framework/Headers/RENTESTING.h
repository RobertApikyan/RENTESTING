#import <Foundation/NSArray.h>
#import <Foundation/NSDictionary.h>
#import <Foundation/NSError.h>
#import <Foundation/NSObject.h>
#import <Foundation/NSSet.h>
#import <Foundation/NSString.h>
#import <Foundation/NSValue.h>

@class RENTESTINGR89SDK, RENTESTINGInitializationEvents, RENTESTINGConfigBuilder, RENTESTINGLogLevels, RENTESTINGRefineryAdFactory, UIView, RENTESTINGBannerEventListener, RENTESTINGInfiniteScrollEventListener, UIViewController, RENTESTINGInterstitialEventListener, RENTESTINGR89LoadError, RENTESTINGRewardItem, RENTESTINGConfigBuilderCompanion, RENTESTINGKotlinPair<__covariant A, __covariant B>, RENTESTINGWrapperPosition, RENTESTINGKotlinIntRange, RENTESTINGKotlinEnumCompanion, RENTESTINGKotlinEnum<E>, RENTESTINGKotlinArray<T>, RENTESTINGUiExecutorIosImplCompanion, RENTESTINGKotlinIntProgressionCompanion, RENTESTINGKotlinIntIterator, RENTESTINGKotlinIntProgression, RENTESTINGKotlinIntRangeCompanion;

@protocol RENTESTINGKotlinComparable, RENTESTINGKotlinIterator, RENTESTINGKotlinIterable, RENTESTINGKotlinClosedRange, RENTESTINGKotlinOpenEndRange;

NS_ASSUME_NONNULL_BEGIN
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wunknown-warning-option"
#pragma clang diagnostic ignored "-Wincompatible-property-type"
#pragma clang diagnostic ignored "-Wnullability"

#pragma push_macro("_Nullable_result")
#if !__has_feature(nullability_nullable_result)
#undef _Nullable_result
#define _Nullable_result _Nullable
#endif

__attribute__((swift_name("KotlinBase")))
@interface RENTESTINGBase : NSObject
- (instancetype)init __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
+ (void)initialize __attribute__((objc_requires_super));
@end

@interface RENTESTINGBase (RENTESTINGBaseCopying) <NSCopying>
@end

__attribute__((swift_name("KotlinMutableSet")))
@interface RENTESTINGMutableSet<ObjectType> : NSMutableSet<ObjectType>
@end

__attribute__((swift_name("KotlinMutableDictionary")))
@interface RENTESTINGMutableDictionary<KeyType, ObjectType> : NSMutableDictionary<KeyType, ObjectType>
@end

@interface NSError (NSErrorRENTESTINGKotlinException)
@property (readonly) id _Nullable kotlinException;
@end

__attribute__((swift_name("KotlinNumber")))
@interface RENTESTINGNumber : NSNumber
- (instancetype)initWithChar:(char)value __attribute__((unavailable));
- (instancetype)initWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
- (instancetype)initWithShort:(short)value __attribute__((unavailable));
- (instancetype)initWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
- (instancetype)initWithInt:(int)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
- (instancetype)initWithLong:(long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
- (instancetype)initWithLongLong:(long long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
- (instancetype)initWithFloat:(float)value __attribute__((unavailable));
- (instancetype)initWithDouble:(double)value __attribute__((unavailable));
- (instancetype)initWithBool:(BOOL)value __attribute__((unavailable));
- (instancetype)initWithInteger:(NSInteger)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
+ (instancetype)numberWithChar:(char)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
+ (instancetype)numberWithShort:(short)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
+ (instancetype)numberWithInt:(int)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
+ (instancetype)numberWithLong:(long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
+ (instancetype)numberWithLongLong:(long long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
+ (instancetype)numberWithFloat:(float)value __attribute__((unavailable));
+ (instancetype)numberWithDouble:(double)value __attribute__((unavailable));
+ (instancetype)numberWithBool:(BOOL)value __attribute__((unavailable));
+ (instancetype)numberWithInteger:(NSInteger)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
@end

__attribute__((swift_name("KotlinByte")))
@interface RENTESTINGByte : RENTESTINGNumber
- (instancetype)initWithChar:(char)value;
+ (instancetype)numberWithChar:(char)value;
@end

__attribute__((swift_name("KotlinUByte")))
@interface RENTESTINGUByte : RENTESTINGNumber
- (instancetype)initWithUnsignedChar:(unsigned char)value;
+ (instancetype)numberWithUnsignedChar:(unsigned char)value;
@end

__attribute__((swift_name("KotlinShort")))
@interface RENTESTINGShort : RENTESTINGNumber
- (instancetype)initWithShort:(short)value;
+ (instancetype)numberWithShort:(short)value;
@end

__attribute__((swift_name("KotlinUShort")))
@interface RENTESTINGUShort : RENTESTINGNumber
- (instancetype)initWithUnsignedShort:(unsigned short)value;
+ (instancetype)numberWithUnsignedShort:(unsigned short)value;
@end

__attribute__((swift_name("KotlinInt")))
@interface RENTESTINGInt : RENTESTINGNumber
- (instancetype)initWithInt:(int)value;
+ (instancetype)numberWithInt:(int)value;
@end

__attribute__((swift_name("KotlinUInt")))
@interface RENTESTINGUInt : RENTESTINGNumber
- (instancetype)initWithUnsignedInt:(unsigned int)value;
+ (instancetype)numberWithUnsignedInt:(unsigned int)value;
@end

__attribute__((swift_name("KotlinLong")))
@interface RENTESTINGLong : RENTESTINGNumber
- (instancetype)initWithLongLong:(long long)value;
+ (instancetype)numberWithLongLong:(long long)value;
@end

__attribute__((swift_name("KotlinULong")))
@interface RENTESTINGULong : RENTESTINGNumber
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value;
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value;
@end

__attribute__((swift_name("KotlinFloat")))
@interface RENTESTINGFloat : RENTESTINGNumber
- (instancetype)initWithFloat:(float)value;
+ (instancetype)numberWithFloat:(float)value;
@end

__attribute__((swift_name("KotlinDouble")))
@interface RENTESTINGDouble : RENTESTINGNumber
- (instancetype)initWithDouble:(double)value;
+ (instancetype)numberWithDouble:(double)value;
@end

__attribute__((swift_name("KotlinBoolean")))
@interface RENTESTINGBoolean : RENTESTINGNumber
- (instancetype)initWithBool:(BOOL)value;
+ (instancetype)numberWithBool:(BOOL)value;
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("R89SDK")))
@interface RENTESTINGR89SDK : RENTESTINGBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)r89SDK __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) RENTESTINGR89SDK *shared __attribute__((swift_name("shared")));
- (void)initializePublisherId:(NSString *)publisherId appId:(NSString *)appId singleLine:(BOOL)singleLine publisherInitializationEvents:(RENTESTINGInitializationEvents * _Nullable)publisherInitializationEvents __attribute__((swift_name("initialize(publisherId:appId:singleLine:publisherInitializationEvents:)")));
- (void)initializeWithConfigBuilderPublisherId:(NSString *)publisherId appId:(NSString *)appId configBuilder:(RENTESTINGConfigBuilder *)configBuilder publisherInitializationEvents:(RENTESTINGInitializationEvents * _Nullable)publisherInitializationEvents __attribute__((swift_name("initializeWithConfigBuilder(publisherId:appId:configBuilder:publisherInitializationEvents:)")));
- (void)resetConsent __attribute__((swift_name("resetConsent()")));
- (void)setDebugGetLocalFakeData:(BOOL)getLocalFakeData forceCMP:(BOOL)forceCMP useProductionAuctionServer:(BOOL)useProductionAuctionServer __attribute__((swift_name("setDebug(getLocalFakeData:forceCMP:useProductionAuctionServer:)")));
- (void)setLogLevelLevel:(RENTESTINGLogLevels *)level __attribute__((swift_name("setLogLevel(level:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("RefineryAdFactory")))
@interface RENTESTINGRefineryAdFactory : RENTESTINGBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)refineryAdFactory __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) RENTESTINGRefineryAdFactory *shared __attribute__((swift_name("shared")));
- (int32_t)createBannerConfigurationID:(NSString *)configurationID wrapper:(UIView *)wrapper lifecycleCallbacks:(RENTESTINGBannerEventListener * _Nullable)lifecycleCallbacks __attribute__((swift_name("createBanner(configurationID:wrapper:lifecycleCallbacks:)")));
- (int32_t)createInfiniteScrollConfigurationID:(NSString *)configurationID scrollView:(UIView *)scrollView scrollItemAdWrapperTag:(NSString *)scrollItemAdWrapperTag lifecycleCallbacks:(RENTESTINGInfiniteScrollEventListener * _Nullable)lifecycleCallbacks __attribute__((swift_name("createInfiniteScroll(configurationID:scrollView:scrollItemAdWrapperTag:lifecycleCallbacks:)")));
- (int32_t)createInterstitialConfigurationID:(NSString *)configurationID uiViewController:(UIViewController *)uiViewController afterInterstitial:(void (^)(void))afterInterstitial lifecycleCallbacks:(RENTESTINGInterstitialEventListener * _Nullable)lifecycleCallbacks __attribute__((swift_name("createInterstitial(configurationID:uiViewController:afterInterstitial:lifecycleCallbacks:)")));
- (int32_t)createManualInfiniteScrollConfigurationID:(NSString *)configurationID lifecycleCallbacks:(RENTESTINGInfiniteScrollEventListener * _Nullable)lifecycleCallbacks __attribute__((swift_name("createManualInfiniteScroll(configurationID:lifecycleCallbacks:)")));
- (int32_t)createVideoInterstitialConfigurationID:(NSString *)configurationID uiViewController:(UIViewController *)uiViewController afterInterstitial:(void (^)(void))afterInterstitial lifecycleCallbacks:(RENTESTINGInterstitialEventListener * _Nullable)lifecycleCallbacks __attribute__((swift_name("createVideoInterstitial(configurationID:uiViewController:afterInterstitial:lifecycleCallbacks:)")));
- (int32_t)createVideoOutstreamBannerConfigurationID:(NSString *)configurationID wrapper:(UIView *)wrapper lifecycleCallbacks:(RENTESTINGBannerEventListener * _Nullable)lifecycleCallbacks __attribute__((swift_name("createVideoOutstreamBanner(configurationID:wrapper:lifecycleCallbacks:)")));
- (BOOL)getInfiniteScrollAdForIndexInfiniteScrollId:(int32_t)infiniteScrollId itemIndex:(int32_t)itemIndex itemAdWrapper:(UIView *)itemAdWrapper childAdLifecycle:(RENTESTINGBannerEventListener * _Nullable)childAdLifecycle __attribute__((swift_name("getInfiniteScrollAdForIndex(infiniteScrollId:itemIndex:itemAdWrapper:childAdLifecycle:)")));
- (void)showIndex:(int32_t)index __attribute__((swift_name("show(index:)")));
@end

__attribute__((swift_name("BannerEventListener")))
@interface RENTESTINGBannerEventListener : RENTESTINGBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)onClick __attribute__((swift_name("onClick()")));
- (void)onClose __attribute__((swift_name("onClose()")));
- (void)onFailedToLoadError:(RENTESTINGR89LoadError *)error __attribute__((swift_name("onFailedToLoad(error:)")));
- (void)onImpression __attribute__((swift_name("onImpression()")));
- (void)onLayoutChangeWidth:(int32_t)width height:(int32_t)height __attribute__((swift_name("onLayoutChange(width:height:)")));
- (void)onLoaded __attribute__((swift_name("onLoaded()")));
- (void)onOpen __attribute__((swift_name("onOpen()")));
@end

__attribute__((swift_name("InfiniteScrollEventListener")))
@interface RENTESTINGInfiniteScrollEventListener : RENTESTINGBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)onAdItemClickItemIdInData:(int32_t)itemIdInData __attribute__((swift_name("onAdItemClick(itemIdInData:)")));
- (void)onAdItemCloseItemIdInData:(int32_t)itemIdInData __attribute__((swift_name("onAdItemClose(itemIdInData:)")));
- (void)onAdItemCreatedAdapterId:(int32_t)adapterId probability:(double)probability __attribute__((swift_name("onAdItemCreated(adapterId:probability:)")));
- (void)onAdItemFailedToCreateMessage:(NSString *)message __attribute__((swift_name("onAdItemFailedToCreate(message:)")));
- (void)onAdItemFailedToLoadItemIdInData:(int32_t)itemIdInData error:(RENTESTINGR89LoadError *)error __attribute__((swift_name("onAdItemFailedToLoad(itemIdInData:error:)")));
- (void)onAdItemImpressionItemIdInData:(int32_t)itemIdInData __attribute__((swift_name("onAdItemImpression(itemIdInData:)")));
- (void)onAdItemLoadedItemIdInData:(int32_t)itemIdInData __attribute__((swift_name("onAdItemLoaded(itemIdInData:)")));
- (void)onAdItemOpenItemIdInData:(int32_t)itemIdInData __attribute__((swift_name("onAdItemOpen(itemIdInData:)")));
- (void)onRollItemIdInData:(int32_t)itemIdInData __attribute__((swift_name("onRoll(itemIdInData:)")));
- (void)onRollFailedItemIdInData:(int32_t)itemIdInData __attribute__((swift_name("onRollFailed(itemIdInData:)")));
@end

__attribute__((swift_name("InterstitialEventListener")))
@interface RENTESTINGInterstitialEventListener : RENTESTINGBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)onAdDismissedFullScreenContent __attribute__((swift_name("onAdDismissedFullScreenContent()")));
- (void)onAdFailedToShowFullScreenErrorMsg:(NSString *)errorMsg __attribute__((swift_name("onAdFailedToShowFullScreen(errorMsg:)")));
- (void)onClick __attribute__((swift_name("onClick()")));
- (void)onFailedToLoadError:(RENTESTINGR89LoadError *)error __attribute__((swift_name("onFailedToLoad(error:)")));
- (void)onImpression __attribute__((swift_name("onImpression()")));
- (void)onLoaded __attribute__((swift_name("onLoaded()")));
- (void)onOpen __attribute__((swift_name("onOpen()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("RewardItem")))
@interface RENTESTINGRewardItem : RENTESTINGBase
- (instancetype)initWithAmount:(int32_t)amount type:(NSString *)type __attribute__((swift_name("init(amount:type:)"))) __attribute__((objc_designated_initializer));
- (RENTESTINGRewardItem *)doCopyAmount:(int32_t)amount type:(NSString *)type __attribute__((swift_name("doCopy(amount:type:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t amount __attribute__((swift_name("amount")));
@property (readonly) NSString *type __attribute__((swift_name("type")));
@end

__attribute__((swift_name("RewardedInterstitialEventListener")))
@interface RENTESTINGRewardedInterstitialEventListener : RENTESTINGBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)onAdDismissedFullScreenContent __attribute__((swift_name("onAdDismissedFullScreenContent()")));
- (void)onAdFailedToShowFullScreenErrorMsg:(NSString *)errorMsg __attribute__((swift_name("onAdFailedToShowFullScreen(errorMsg:)")));
- (void)onClick __attribute__((swift_name("onClick()")));
- (void)onFailedToLoadError:(RENTESTINGR89LoadError *)error __attribute__((swift_name("onFailedToLoad(error:)")));
- (void)onImpression __attribute__((swift_name("onImpression()")));
- (void)onLoaded __attribute__((swift_name("onLoaded()")));
- (void)onOpen __attribute__((swift_name("onOpen()")));
- (void)onRewardEarnedRewardAmount:(int32_t)rewardAmount rewardType:(NSString *)rewardType __attribute__((swift_name("onRewardEarned(rewardAmount:rewardType:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("R89LoadError")))
@interface RENTESTINGR89LoadError : RENTESTINGBase
- (NSString *)description __attribute__((swift_name("description()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ConfigBuilder")))
@interface RENTESTINGConfigBuilder : RENTESTINGBase
- (instancetype)initWithPublisherName:(NSString *)publisherName __attribute__((swift_name("init(publisherName:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) RENTESTINGConfigBuilderCompanion *companion __attribute__((swift_name("companion")));
- (void)addAppTargetingStoreUrl:(NSString *)storeUrl domain:(NSString *)domain appContextKeywords:(NSSet<NSString *> * _Nullable)appContextKeywords __attribute__((swift_name("addAppTargeting(storeUrl:domain:appContextKeywords:)")));
- (RENTESTINGConfigBuilder *)addBannerConfigurationConfigurationId:(NSString *)configurationId bannerUnitId:(NSString *)bannerUnitId bannerConfigId:(NSString *)bannerConfigId sizeList:(NSArray<RENTESTINGKotlinPair<RENTESTINGInt *, RENTESTINGInt *> *> *)sizeList autoRefreshMillis:(int32_t)autoRefreshMillis isCapped:(BOOL)isCapped adAmountPerTimeSlot:(int32_t)adAmountPerTimeSlot timeSlotSize:(int64_t)timeSlotSize keywords:(NSArray<NSString *> * _Nullable)keywords unitFPID:(NSDictionary<NSString *, NSSet<NSString *> *> * _Nullable)unitFPID closeButtonIsActive:(BOOL)closeButtonIsActive closeButtonImageURL:(NSString *)closeButtonImageURL wrapperWrapContent:(BOOL)wrapperWrapContent wrapperMatchParentHeight:(BOOL)wrapperMatchParentHeight wrapperMatchParentWidth:(BOOL)wrapperMatchParentWidth wrapperHeight:(int32_t)wrapperHeight wrapperWidth:(int32_t)wrapperWidth wrapperPosition:(RENTESTINGWrapperPosition *)wrapperPosition __attribute__((swift_name("addBannerConfiguration(configurationId:bannerUnitId:bannerConfigId:sizeList:autoRefreshMillis:isCapped:adAmountPerTimeSlot:timeSlotSize:keywords:unitFPID:closeButtonIsActive:closeButtonImageURL:wrapperWrapContent:wrapperMatchParentHeight:wrapperMatchParentWidth:wrapperHeight:wrapperWidth:wrapperPosition:)")));
- (void)addCMPDataId:(int32_t)id cmpCodeId:(NSString *)cmpCodeId __attribute__((swift_name("addCMPData(id:cmpCodeId:)")));
- (RENTESTINGConfigBuilder *)addInfiniteScrollConfigurationConfigurationId:(NSString *)configurationId minItems:(int32_t)minItems maxItems:(int32_t)maxItems variableProbability:(RENTESTINGKotlinIntRange *)variableProbability configsOfAdsToUse:(NSArray<NSString *> *)configsOfAdsToUse wrapperWrapContent:(BOOL)wrapperWrapContent wrapperMatchParentHeight:(BOOL)wrapperMatchParentHeight wrapperMatchParentWidth:(BOOL)wrapperMatchParentWidth wrapperHeight:(int32_t)wrapperHeight wrapperWidth:(int32_t)wrapperWidth wrapperPosition:(RENTESTINGWrapperPosition *)wrapperPosition __attribute__((swift_name("addInfiniteScrollConfiguration(configurationId:minItems:maxItems:variableProbability:configsOfAdsToUse:wrapperWrapContent:wrapperMatchParentHeight:wrapperMatchParentWidth:wrapperHeight:wrapperWidth:wrapperPosition:)")));
- (RENTESTINGConfigBuilder *)addInterstitialConfigurationConfigurationId:(NSString *)configurationId interstitialUnitId:(NSString *)interstitialUnitId interstitialConfigId:(NSString *)interstitialConfigId isCapped:(BOOL)isCapped adAmountPerTimeSlot:(int32_t)adAmountPerTimeSlot timeSlotSize:(int64_t)timeSlotSize keywords:(NSArray<NSString *> * _Nullable)keywords unitFPID:(NSDictionary<NSString *, NSSet<NSString *> *> * _Nullable)unitFPID __attribute__((swift_name("addInterstitialConfiguration(configurationId:interstitialUnitId:interstitialConfigId:isCapped:adAmountPerTimeSlot:timeSlotSize:keywords:unitFPID:)")));
- (RENTESTINGConfigBuilder *)addPrebidServerConfigUseRealAuctionServer:(BOOL)useRealAuctionServer __attribute__((swift_name("addPrebidServerConfig(useRealAuctionServer:)")));
- (RENTESTINGConfigBuilder *)addRewardedInterstitialConfigurationConfigurationId:(NSString *)configurationId rewardedInterstitialUnitId:(NSString *)rewardedInterstitialUnitId isCapped:(BOOL)isCapped adAmountPerTimeSlot:(int32_t)adAmountPerTimeSlot timeSlotSize:(int64_t)timeSlotSize keywords:(NSArray<NSString *> * _Nullable)keywords unitFPID:(NSDictionary<NSString *, NSSet<NSString *> *> * _Nullable)unitFPID __attribute__((swift_name("addRewardedInterstitialConfiguration(configurationId:rewardedInterstitialUnitId:isCapped:adAmountPerTimeSlot:timeSlotSize:keywords:unitFPID:)")));
- (RENTESTINGConfigBuilder *)addVideoInterstitialConfigurationConfigurationId:(NSString *)configurationId videoInterstitialUnitId:(NSString *)videoInterstitialUnitId videoInterstitialConfigId:(NSString *)videoInterstitialConfigId isCapped:(BOOL)isCapped adAmountPerTimeSlot:(int32_t)adAmountPerTimeSlot timeSlotSize:(int64_t)timeSlotSize keywords:(NSArray<NSString *> * _Nullable)keywords unitFPID:(NSDictionary<NSString *, NSSet<NSString *> *> * _Nullable)unitFPID __attribute__((swift_name("addVideoInterstitialConfiguration(configurationId:videoInterstitialUnitId:videoInterstitialConfigId:isCapped:adAmountPerTimeSlot:timeSlotSize:keywords:unitFPID:)")));
- (RENTESTINGConfigBuilder *)addVideoOutstreamBannerConfigurationConfigurationId:(NSString *)configurationId outstreamUnitId:(NSString *)outstreamUnitId outstreamConfigId:(NSString *)outstreamConfigId size:(RENTESTINGKotlinPair<RENTESTINGInt *, RENTESTINGInt *> *)size autoRefreshMillis:(int32_t)autoRefreshMillis isCapped:(BOOL)isCapped adAmountPerTimeSlot:(int32_t)adAmountPerTimeSlot timeSlotSize:(int64_t)timeSlotSize keywords:(NSArray<NSString *> * _Nullable)keywords unitFPID:(NSDictionary<NSString *, NSSet<NSString *> *> * _Nullable)unitFPID closeButtonIsActive:(BOOL)closeButtonIsActive closeButtonImageURL:(NSString * _Nullable)closeButtonImageURL wrapperWrapContent:(BOOL)wrapperWrapContent wrapperMatchParentHeight:(BOOL)wrapperMatchParentHeight wrapperMatchParentWidth:(BOOL)wrapperMatchParentWidth wrapperHeight:(int32_t)wrapperHeight wrapperWidth:(int32_t)wrapperWidth wrapperPosition:(RENTESTINGWrapperPosition *)wrapperPosition __attribute__((swift_name("addVideoOutstreamBannerConfiguration(configurationId:outstreamUnitId:outstreamConfigId:size:autoRefreshMillis:isCapped:adAmountPerTimeSlot:timeSlotSize:keywords:unitFPID:closeButtonIsActive:closeButtonImageURL:wrapperWrapContent:wrapperMatchParentHeight:wrapperMatchParentWidth:wrapperHeight:wrapperWidth:wrapperPosition:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ConfigBuilder.Companion")))
@interface RENTESTINGConfigBuilderCompanion : RENTESTINGBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) RENTESTINGConfigBuilderCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *BANNER_TEST_CONFIG_ID __attribute__((swift_name("BANNER_TEST_CONFIG_ID")));
@property (readonly) int32_t BANNER_TEST_HEIGHT __attribute__((swift_name("BANNER_TEST_HEIGHT")));
@property (readonly) NSString *BANNER_TEST_R89_CONFIG_ID __attribute__((swift_name("BANNER_TEST_R89_CONFIG_ID")));
@property (readonly) NSString *BANNER_TEST_UNIT_ID __attribute__((swift_name("BANNER_TEST_UNIT_ID")));
@property (readonly) int32_t BANNER_TEST_WIDTH __attribute__((swift_name("BANNER_TEST_WIDTH")));
@property (readonly) NSString *CMP_TEST_CODE_ID __attribute__((swift_name("CMP_TEST_CODE_ID")));
@property (readonly) int32_t CMP_TEST_ID __attribute__((swift_name("CMP_TEST_ID")));
@property (readonly) NSString *INFINITE_SCROLL_TEST_R89_CONFIG_ID __attribute__((swift_name("INFINITE_SCROLL_TEST_R89_CONFIG_ID")));
@property (readonly) RENTESTINGKotlinIntRange *INFINITE_SCROLL_TEST_VARIABLE_PROBABILITY __attribute__((swift_name("INFINITE_SCROLL_TEST_VARIABLE_PROBABILITY")));
@property (readonly) NSString *INTERSTITIAL_TEST_CONFIG_ID __attribute__((swift_name("INTERSTITIAL_TEST_CONFIG_ID")));
@property (readonly) NSString *INTERSTITIAL_TEST_R89_CONFIG_ID __attribute__((swift_name("INTERSTITIAL_TEST_R89_CONFIG_ID")));
@property (readonly) NSString *INTERSTITIAL_TEST_UNIT_ID __attribute__((swift_name("INTERSTITIAL_TEST_UNIT_ID")));
@property (readonly) NSString *REWARDED_INTERSTITIAL_TEST_R89_CONFIG_ID __attribute__((swift_name("REWARDED_INTERSTITIAL_TEST_R89_CONFIG_ID")));
@property (readonly) NSString *REWARDED_INTERSTITIAL_TEST_UNIT_ID __attribute__((swift_name("REWARDED_INTERSTITIAL_TEST_UNIT_ID")));
@property (readonly) NSString *VIDEO_INTERSTITIAL_TEST_CONFIG_ID __attribute__((swift_name("VIDEO_INTERSTITIAL_TEST_CONFIG_ID")));
@property (readonly) NSString *VIDEO_INTERSTITIAL_TEST_R89_CONFIG_ID __attribute__((swift_name("VIDEO_INTERSTITIAL_TEST_R89_CONFIG_ID")));
@property (readonly) NSString *VIDEO_INTERSTITIAL_TEST_UNIT_ID __attribute__((swift_name("VIDEO_INTERSTITIAL_TEST_UNIT_ID")));
@property (readonly) NSString *VIDEO_OUTSTREAM_TEST_CONFIG_ID __attribute__((swift_name("VIDEO_OUTSTREAM_TEST_CONFIG_ID")));
@property (readonly) int32_t VIDEO_OUTSTREAM_TEST_HEIGHT __attribute__((swift_name("VIDEO_OUTSTREAM_TEST_HEIGHT")));
@property (readonly) NSString *VIDEO_OUTSTREAM_TEST_R89_CONFIG_ID __attribute__((swift_name("VIDEO_OUTSTREAM_TEST_R89_CONFIG_ID")));
@property (readonly) NSString *VIDEO_OUTSTREAM_TEST_UNIT_ID __attribute__((swift_name("VIDEO_OUTSTREAM_TEST_UNIT_ID")));
@property (readonly) int32_t VIDEO_OUTSTREAM_TEST_WIDTH __attribute__((swift_name("VIDEO_OUTSTREAM_TEST_WIDTH")));
@end

__attribute__((swift_name("InitializationEvents")))
@interface RENTESTINGInitializationEvents : RENTESTINGBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)cmpFinished __attribute__((swift_name("cmpFinished()")));
- (void)dataFetchFailed __attribute__((swift_name("dataFetchFailed()")));
- (void)dataFetchSuccess __attribute__((swift_name("dataFetchSuccess()")));
- (void)initializationFinished __attribute__((swift_name("initializationFinished()")));
- (void)startedCMP __attribute__((swift_name("startedCMP()")));
- (void)startedDataFetch __attribute__((swift_name("startedDataFetch()")));
- (void)startedInitialization __attribute__((swift_name("startedInitialization()")));
@end

__attribute__((swift_name("IR89Logger")))
@protocol RENTESTINGIR89Logger
@required
- (void)printLogLevel:(RENTESTINGLogLevels *)logLevel tag:(NSString *)tag message:(NSString *)message __attribute__((swift_name("print(logLevel:tag:message:)")));
@end

__attribute__((swift_name("KotlinComparable")))
@protocol RENTESTINGKotlinComparable
@required
- (int32_t)compareToOther:(id _Nullable)other __attribute__((swift_name("compareTo(other:)")));
@end

__attribute__((swift_name("KotlinEnum")))
@interface RENTESTINGKotlinEnum<E> : RENTESTINGBase <RENTESTINGKotlinComparable>
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) RENTESTINGKotlinEnumCompanion *companion __attribute__((swift_name("companion")));
- (int32_t)compareToOther:(E)other __attribute__((swift_name("compareTo(other:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) int32_t ordinal __attribute__((swift_name("ordinal")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("LogLevels")))
@interface RENTESTINGLogLevels : RENTESTINGKotlinEnum<RENTESTINGLogLevels *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) RENTESTINGLogLevels *verbose __attribute__((swift_name("verbose")));
@property (class, readonly) RENTESTINGLogLevels *debug __attribute__((swift_name("debug")));
@property (class, readonly) RENTESTINGLogLevels *info __attribute__((swift_name("info")));
@property (class, readonly) RENTESTINGLogLevels *warn __attribute__((swift_name("warn")));
@property (class, readonly) RENTESTINGLogLevels *error __attribute__((swift_name("error")));
@property (class, readonly) RENTESTINGLogLevels *assert __attribute__((swift_name("assert")));
+ (RENTESTINGKotlinArray<RENTESTINGLogLevels *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<RENTESTINGLogLevels *> *entries __attribute__((swift_name("entries")));
- (BOOL)hasEqualOrHigherPriorityLimitPriority:(RENTESTINGLogLevels *)limitPriority __attribute__((swift_name("hasEqualOrHigherPriority(limitPriority:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("WrapperPosition")))
@interface RENTESTINGWrapperPosition : RENTESTINGKotlinEnum<RENTESTINGWrapperPosition *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) RENTESTINGWrapperPosition *top __attribute__((swift_name("top")));
@property (class, readonly) RENTESTINGWrapperPosition *bottom __attribute__((swift_name("bottom")));
@property (class, readonly) RENTESTINGWrapperPosition *start __attribute__((swift_name("start")));
@property (class, readonly) RENTESTINGWrapperPosition *end __attribute__((swift_name("end")));
@property (class, readonly) RENTESTINGWrapperPosition *center __attribute__((swift_name("center")));
+ (RENTESTINGKotlinArray<RENTESTINGWrapperPosition *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<RENTESTINGWrapperPosition *> *entries __attribute__((swift_name("entries")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UUIDLibraryIosImpl")))
@interface RENTESTINGUUIDLibraryIosImpl : RENTESTINGBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (NSString *)generate __attribute__((swift_name("generate()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UiExecutorIosImpl")))
@interface RENTESTINGUiExecutorIosImpl : RENTESTINGBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) RENTESTINGUiExecutorIosImplCompanion *companion __attribute__((swift_name("companion")));
- (void)executeInMainThreadBlock:(void (^)(void))block __attribute__((swift_name("executeInMainThread(block:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UiExecutorIosImpl.Companion")))
@interface RENTESTINGUiExecutorIosImplCompanion : RENTESTINGBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) RENTESTINGUiExecutorIosImplCompanion *shared __attribute__((swift_name("shared")));
- (void)executeInMainThreadBlock:(void (^)(void))block __attribute__((swift_name("executeInMainThread(block:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("CollectionExtensionsKt")))
@interface RENTESTINGCollectionExtensionsKt : RENTESTINGBase
+ (NSDictionary<NSString *, NSSet<NSString *> *> *)toStringSetMap:(NSDictionary<id, id> *)receiver __attribute__((swift_name("toStringSetMap(_:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UIViewExtKt")))
@interface RENTESTINGUIViewExtKt : RENTESTINGBase
+ (UIView * _Nullable)findViewByLabel:(UIView *)receiver accessoryLabel:(NSString *)accessoryLabel __attribute__((swift_name("findViewByLabel(_:accessoryLabel:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinPair")))
@interface RENTESTINGKotlinPair<__covariant A, __covariant B> : RENTESTINGBase
- (instancetype)initWithFirst:(A _Nullable)first second:(B _Nullable)second __attribute__((swift_name("init(first:second:)"))) __attribute__((objc_designated_initializer));
- (RENTESTINGKotlinPair<A, B> *)doCopyFirst:(A _Nullable)first second:(B _Nullable)second __attribute__((swift_name("doCopy(first:second:)")));
- (BOOL)equalsOther:(id _Nullable)other __attribute__((swift_name("equals(other:)")));
- (int32_t)hashCode __attribute__((swift_name("hashCode()")));
- (NSString *)toString __attribute__((swift_name("toString()")));
@property (readonly) A _Nullable first __attribute__((swift_name("first")));
@property (readonly) B _Nullable second __attribute__((swift_name("second")));
@end

__attribute__((swift_name("KotlinIterable")))
@protocol RENTESTINGKotlinIterable
@required
- (id<RENTESTINGKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
@end

__attribute__((swift_name("KotlinIntProgression")))
@interface RENTESTINGKotlinIntProgression : RENTESTINGBase <RENTESTINGKotlinIterable>
@property (class, readonly, getter=companion) RENTESTINGKotlinIntProgressionCompanion *companion __attribute__((swift_name("companion")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (BOOL)isEmpty __attribute__((swift_name("isEmpty()")));
- (RENTESTINGKotlinIntIterator *)iterator __attribute__((swift_name("iterator()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t first __attribute__((swift_name("first")));
@property (readonly) int32_t last __attribute__((swift_name("last")));
@property (readonly) int32_t step __attribute__((swift_name("step")));
@end

__attribute__((swift_name("KotlinClosedRange")))
@protocol RENTESTINGKotlinClosedRange
@required
- (BOOL)containsValue:(id)value __attribute__((swift_name("contains(value:)")));
- (BOOL)isEmpty __attribute__((swift_name("isEmpty()")));
@property (readonly) id endInclusive __attribute__((swift_name("endInclusive")));
@property (readonly) id start __attribute__((swift_name("start")));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.9")
*/
__attribute__((swift_name("KotlinOpenEndRange")))
@protocol RENTESTINGKotlinOpenEndRange
@required
- (BOOL)containsValue_:(id)value __attribute__((swift_name("contains(value_:)")));
- (BOOL)isEmpty __attribute__((swift_name("isEmpty()")));
@property (readonly) id endExclusive __attribute__((swift_name("endExclusive")));
@property (readonly) id start __attribute__((swift_name("start")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinIntRange")))
@interface RENTESTINGKotlinIntRange : RENTESTINGKotlinIntProgression <RENTESTINGKotlinClosedRange, RENTESTINGKotlinOpenEndRange>
- (instancetype)initWithStart:(int32_t)start endInclusive:(int32_t)endInclusive __attribute__((swift_name("init(start:endInclusive:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) RENTESTINGKotlinIntRangeCompanion *companion __attribute__((swift_name("companion")));
- (BOOL)containsValue:(RENTESTINGInt *)value __attribute__((swift_name("contains(value:)")));
- (BOOL)containsValue_:(RENTESTINGInt *)value __attribute__((swift_name("contains(value_:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (BOOL)isEmpty __attribute__((swift_name("isEmpty()")));
- (NSString *)description __attribute__((swift_name("description()")));

/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.9")
*/
@property (readonly) RENTESTINGInt *endExclusive __attribute__((swift_name("endExclusive"))) __attribute__((deprecated("Can throw an exception when it's impossible to represent the value with Int type, for example, when the range includes MAX_VALUE. It's recommended to use 'endInclusive' property that doesn't throw.")));
@property (readonly) RENTESTINGInt *endInclusive __attribute__((swift_name("endInclusive")));
@property (readonly) RENTESTINGInt *start __attribute__((swift_name("start")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinEnumCompanion")))
@interface RENTESTINGKotlinEnumCompanion : RENTESTINGBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) RENTESTINGKotlinEnumCompanion *shared __attribute__((swift_name("shared")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinArray")))
@interface RENTESTINGKotlinArray<T> : RENTESTINGBase
+ (instancetype)arrayWithSize:(int32_t)size init:(T _Nullable (^)(RENTESTINGInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (T _Nullable)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (id<RENTESTINGKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(T _Nullable)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end

__attribute__((swift_name("KotlinIterator")))
@protocol RENTESTINGKotlinIterator
@required
- (BOOL)hasNext __attribute__((swift_name("hasNext()")));
- (id _Nullable)next __attribute__((swift_name("next()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinIntProgression.Companion")))
@interface RENTESTINGKotlinIntProgressionCompanion : RENTESTINGBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) RENTESTINGKotlinIntProgressionCompanion *shared __attribute__((swift_name("shared")));
- (RENTESTINGKotlinIntProgression *)fromClosedRangeRangeStart:(int32_t)rangeStart rangeEnd:(int32_t)rangeEnd step:(int32_t)step __attribute__((swift_name("fromClosedRange(rangeStart:rangeEnd:step:)")));
@end

__attribute__((swift_name("KotlinIntIterator")))
@interface RENTESTINGKotlinIntIterator : RENTESTINGBase <RENTESTINGKotlinIterator>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (RENTESTINGInt *)next __attribute__((swift_name("next()")));
- (int32_t)nextInt __attribute__((swift_name("nextInt()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinIntRange.Companion")))
@interface RENTESTINGKotlinIntRangeCompanion : RENTESTINGBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) RENTESTINGKotlinIntRangeCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) RENTESTINGKotlinIntRange *EMPTY __attribute__((swift_name("EMPTY")));
@end

#pragma pop_macro("_Nullable_result")
#pragma clang diagnostic pop
NS_ASSUME_NONNULL_END
