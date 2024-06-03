#import <Foundation/NSArray.h>
#import <Foundation/NSDictionary.h>
#import <Foundation/NSError.h>
#import <Foundation/NSObject.h>
#import <Foundation/NSSet.h>
#import <Foundation/NSString.h>
#import <Foundation/NSValue.h>

@class SharedCoreR89SDK, SharedCoreInitializationEvents, SharedCoreConfigBuilder, SharedCoreLogLevels, SharedCoreRefineryAdFactory, UIView, SharedCoreBannerEventListener, SharedCoreInfiniteScrollEventListener, UIViewController, SharedCoreInterstitialEventListener, SharedCoreR89LoadError, SharedCoreRewardItem, SharedCoreConfigBuilderCompanion, SharedCoreKotlinPair<__covariant A, __covariant B>, SharedCoreWrapperPosition, SharedCoreKotlinIntRange, SharedCoreKotlinEnumCompanion, SharedCoreKotlinEnum<E>, SharedCoreKotlinArray<T>, SharedCoreUiExecutorIosImplCompanion, SharedCoreKotlinIntProgressionCompanion, SharedCoreKotlinIntIterator, SharedCoreKotlinIntProgression, SharedCoreKotlinIntRangeCompanion;

@protocol SharedCoreKotlinComparable, SharedCoreKotlinIterator, SharedCoreKotlinIterable, SharedCoreKotlinClosedRange, SharedCoreKotlinOpenEndRange;

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
@interface SharedCoreBase : NSObject
- (instancetype)init __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
+ (void)initialize __attribute__((objc_requires_super));
@end

@interface SharedCoreBase (SharedCoreBaseCopying) <NSCopying>
@end

__attribute__((swift_name("KotlinMutableSet")))
@interface SharedCoreMutableSet<ObjectType> : NSMutableSet<ObjectType>
@end

__attribute__((swift_name("KotlinMutableDictionary")))
@interface SharedCoreMutableDictionary<KeyType, ObjectType> : NSMutableDictionary<KeyType, ObjectType>
@end

@interface NSError (NSErrorSharedCoreKotlinException)
@property (readonly) id _Nullable kotlinException;
@end

__attribute__((swift_name("KotlinNumber")))
@interface SharedCoreNumber : NSNumber
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
@interface SharedCoreByte : SharedCoreNumber
- (instancetype)initWithChar:(char)value;
+ (instancetype)numberWithChar:(char)value;
@end

__attribute__((swift_name("KotlinUByte")))
@interface SharedCoreUByte : SharedCoreNumber
- (instancetype)initWithUnsignedChar:(unsigned char)value;
+ (instancetype)numberWithUnsignedChar:(unsigned char)value;
@end

__attribute__((swift_name("KotlinShort")))
@interface SharedCoreShort : SharedCoreNumber
- (instancetype)initWithShort:(short)value;
+ (instancetype)numberWithShort:(short)value;
@end

__attribute__((swift_name("KotlinUShort")))
@interface SharedCoreUShort : SharedCoreNumber
- (instancetype)initWithUnsignedShort:(unsigned short)value;
+ (instancetype)numberWithUnsignedShort:(unsigned short)value;
@end

__attribute__((swift_name("KotlinInt")))
@interface SharedCoreInt : SharedCoreNumber
- (instancetype)initWithInt:(int)value;
+ (instancetype)numberWithInt:(int)value;
@end

__attribute__((swift_name("KotlinUInt")))
@interface SharedCoreUInt : SharedCoreNumber
- (instancetype)initWithUnsignedInt:(unsigned int)value;
+ (instancetype)numberWithUnsignedInt:(unsigned int)value;
@end

__attribute__((swift_name("KotlinLong")))
@interface SharedCoreLong : SharedCoreNumber
- (instancetype)initWithLongLong:(long long)value;
+ (instancetype)numberWithLongLong:(long long)value;
@end

__attribute__((swift_name("KotlinULong")))
@interface SharedCoreULong : SharedCoreNumber
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value;
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value;
@end

__attribute__((swift_name("KotlinFloat")))
@interface SharedCoreFloat : SharedCoreNumber
- (instancetype)initWithFloat:(float)value;
+ (instancetype)numberWithFloat:(float)value;
@end

__attribute__((swift_name("KotlinDouble")))
@interface SharedCoreDouble : SharedCoreNumber
- (instancetype)initWithDouble:(double)value;
+ (instancetype)numberWithDouble:(double)value;
@end

__attribute__((swift_name("KotlinBoolean")))
@interface SharedCoreBoolean : SharedCoreNumber
- (instancetype)initWithBool:(BOOL)value;
+ (instancetype)numberWithBool:(BOOL)value;
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("R89SDK")))
@interface SharedCoreR89SDK : SharedCoreBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)r89SDK __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedCoreR89SDK *shared __attribute__((swift_name("shared")));
- (void)initializePublisherId:(NSString *)publisherId appId:(NSString *)appId singleLine:(BOOL)singleLine publisherInitializationEvents:(SharedCoreInitializationEvents * _Nullable)publisherInitializationEvents __attribute__((swift_name("initialize(publisherId:appId:singleLine:publisherInitializationEvents:)")));
- (void)initializeWithConfigBuilderPublisherId:(NSString *)publisherId appId:(NSString *)appId configBuilder:(SharedCoreConfigBuilder *)configBuilder publisherInitializationEvents:(SharedCoreInitializationEvents * _Nullable)publisherInitializationEvents __attribute__((swift_name("initializeWithConfigBuilder(publisherId:appId:configBuilder:publisherInitializationEvents:)")));
- (void)resetConsent __attribute__((swift_name("resetConsent()")));
- (void)setDebugGetLocalFakeData:(BOOL)getLocalFakeData forceCMP:(BOOL)forceCMP useProductionAuctionServer:(BOOL)useProductionAuctionServer __attribute__((swift_name("setDebug(getLocalFakeData:forceCMP:useProductionAuctionServer:)")));
- (void)setLogLevelLevel:(SharedCoreLogLevels *)level __attribute__((swift_name("setLogLevel(level:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("RefineryAdFactory")))
@interface SharedCoreRefineryAdFactory : SharedCoreBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)refineryAdFactory __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedCoreRefineryAdFactory *shared __attribute__((swift_name("shared")));
- (int32_t)createBannerConfigurationID:(NSString *)configurationID wrapper:(UIView *)wrapper lifecycleCallbacks:(SharedCoreBannerEventListener * _Nullable)lifecycleCallbacks __attribute__((swift_name("createBanner(configurationID:wrapper:lifecycleCallbacks:)")));
- (int32_t)createInfiniteScrollConfigurationID:(NSString *)configurationID scrollView:(UIView *)scrollView scrollItemAdWrapperTag:(NSString *)scrollItemAdWrapperTag lifecycleCallbacks:(SharedCoreInfiniteScrollEventListener * _Nullable)lifecycleCallbacks __attribute__((swift_name("createInfiniteScroll(configurationID:scrollView:scrollItemAdWrapperTag:lifecycleCallbacks:)")));
- (int32_t)createInterstitialConfigurationID:(NSString *)configurationID uiViewController:(UIViewController *)uiViewController afterInterstitial:(void (^)(void))afterInterstitial lifecycleCallbacks:(SharedCoreInterstitialEventListener * _Nullable)lifecycleCallbacks __attribute__((swift_name("createInterstitial(configurationID:uiViewController:afterInterstitial:lifecycleCallbacks:)")));
- (int32_t)createManualInfiniteScrollConfigurationID:(NSString *)configurationID lifecycleCallbacks:(SharedCoreInfiniteScrollEventListener * _Nullable)lifecycleCallbacks __attribute__((swift_name("createManualInfiniteScroll(configurationID:lifecycleCallbacks:)")));
- (int32_t)createVideoInterstitialConfigurationID:(NSString *)configurationID uiViewController:(UIViewController *)uiViewController afterInterstitial:(void (^)(void))afterInterstitial lifecycleCallbacks:(SharedCoreInterstitialEventListener * _Nullable)lifecycleCallbacks __attribute__((swift_name("createVideoInterstitial(configurationID:uiViewController:afterInterstitial:lifecycleCallbacks:)")));
- (int32_t)createVideoOutstreamBannerConfigurationID:(NSString *)configurationID wrapper:(UIView *)wrapper lifecycleCallbacks:(SharedCoreBannerEventListener * _Nullable)lifecycleCallbacks __attribute__((swift_name("createVideoOutstreamBanner(configurationID:wrapper:lifecycleCallbacks:)")));
- (BOOL)getInfiniteScrollAdForIndexInfiniteScrollId:(int32_t)infiniteScrollId itemIndex:(int32_t)itemIndex itemAdWrapper:(UIView *)itemAdWrapper childAdLifecycle:(SharedCoreBannerEventListener * _Nullable)childAdLifecycle __attribute__((swift_name("getInfiniteScrollAdForIndex(infiniteScrollId:itemIndex:itemAdWrapper:childAdLifecycle:)")));
- (void)showIndex:(int32_t)index __attribute__((swift_name("show(index:)")));
@end


/**
 * Banner life cycle goes as follows:
 * 1. [BasicEventListener.onLoaded]: Called when the banner is loaded and ready to be shown. If this goes wrong, the [BasicEventListener.onFailedToLoad] is called.
 * 2. [BasicEventListener.onImpression] Ad Is Shown and ready to be Clicked.
 * 3. [BasicEventListener.onClick] A Creative of the Ad is clicked. It can be a call to action creative or not.
 * 4. [OpenEventListener.onOpen] happens after [BasicEventListener.onClick], If it opens an Intent in the App to see the advertisement.
 * 5. [CloseEventListener.onClose] That Intent was closed and now we are back to your app.
 * - [BasicEventListener.onFailedToLoad] When this occurs, the factory marks the object for deletion and then invokes this event with an R89LoadError. You can use that to know
 * what happened.
 */
__attribute__((swift_name("BannerEventListener")))
@interface SharedCoreBannerEventListener : SharedCoreBase

/**
 * Banner life cycle goes as follows:
 * 1. [BasicEventListener.onLoaded]: Called when the banner is loaded and ready to be shown. If this goes wrong, the [BasicEventListener.onFailedToLoad] is called.
 * 2. [BasicEventListener.onImpression] Ad Is Shown and ready to be Clicked.
 * 3. [BasicEventListener.onClick] A Creative of the Ad is clicked. It can be a call to action creative or not.
 * 4. [OpenEventListener.onOpen] happens after [BasicEventListener.onClick], If it opens an Intent in the App to see the advertisement.
 * 5. [CloseEventListener.onClose] That Intent was closed and now we are back to your app.
 * - [BasicEventListener.onFailedToLoad] When this occurs, the factory marks the object for deletion and then invokes this event with an R89LoadError. You can use that to know
 * what happened.
 */
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));

/**
 * Banner life cycle goes as follows:
 * 1. [BasicEventListener.onLoaded]: Called when the banner is loaded and ready to be shown. If this goes wrong, the [BasicEventListener.onFailedToLoad] is called.
 * 2. [BasicEventListener.onImpression] Ad Is Shown and ready to be Clicked.
 * 3. [BasicEventListener.onClick] A Creative of the Ad is clicked. It can be a call to action creative or not.
 * 4. [OpenEventListener.onOpen] happens after [BasicEventListener.onClick], If it opens an Intent in the App to see the advertisement.
 * 5. [CloseEventListener.onClose] That Intent was closed and now we are back to your app.
 * - [BasicEventListener.onFailedToLoad] When this occurs, the factory marks the object for deletion and then invokes this event with an R89LoadError. You can use that to know
 * what happened.
 */
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)onClick __attribute__((swift_name("onClick()")));
- (void)onClose __attribute__((swift_name("onClose()")));
- (void)onFailedToLoadError:(SharedCoreR89LoadError *)error __attribute__((swift_name("onFailedToLoad(error:)")));
- (void)onImpression __attribute__((swift_name("onImpression()")));
- (void)onLayoutChangeWidth:(int32_t)width height:(int32_t)height __attribute__((swift_name("onLayoutChange(width:height:)")));
- (void)onLoaded __attribute__((swift_name("onLoaded()")));
- (void)onOpen __attribute__((swift_name("onOpen()")));
@end


/**
 * Infinite Scroll life cycle goes as follows:
 * 1. [InfiniteScrollEventListenerInternal.onRoll] Called when the infinite scroll is about to roll for an item, this is just an event that happens before the roll or
 * [InfiniteScrollEventListenerInternal.onRollFailed] Called when the infinite scroll roll for an item says there is no ad in that item.
 * 3. [InfiniteScrollEventListenerInternal.onAdItemCreated] Called when the infinite scroll roll succeeds, so we are going to create a view inside it or
 * [InfiniteScrollEventListenerInternal.onAdItemFailedToCreate] Called when the infinite scroll roll succeeds, but we failed to create a view inside it, this is an actual error not
 * like [InfiniteScrollEventListenerInternal.onRollFailed] which is just a thing that can happen due to the probability distribution of the infinite scroll configuration
 * 4. [InfiniteScrollEventListenerInternal.onAdItemLoaded] Called when the ad that was rolled successfully for an item, actually loads and is ready to be shown or
 * [InfiniteScrollEventListenerInternal.onAdItemFailedToLoad] Called when the ad that was rolled successfully for an item, failed to load, also an error
 * 5. [InfiniteScrollEventListenerInternal.onAdItemImpression] Called when the ad loaded in the item is shown for the first time
 * 6. [InfiniteScrollEventListenerInternal.onAdItemClick] Called when the ad loaded in the item is clicked
 * 7. [InfiniteScrollEventListenerInternal.onAdItemOpen] Called when the ad loaded in the item is opened
 * 8. [InfiniteScrollEventListenerInternal.onAdItemClose] Called when the ad loaded in the item is closed
 */
__attribute__((swift_name("InfiniteScrollEventListener")))
@interface SharedCoreInfiniteScrollEventListener : SharedCoreBase

/**
 * Infinite Scroll life cycle goes as follows:
 * 1. [InfiniteScrollEventListenerInternal.onRoll] Called when the infinite scroll is about to roll for an item, this is just an event that happens before the roll or
 * [InfiniteScrollEventListenerInternal.onRollFailed] Called when the infinite scroll roll for an item says there is no ad in that item.
 * 3. [InfiniteScrollEventListenerInternal.onAdItemCreated] Called when the infinite scroll roll succeeds, so we are going to create a view inside it or
 * [InfiniteScrollEventListenerInternal.onAdItemFailedToCreate] Called when the infinite scroll roll succeeds, but we failed to create a view inside it, this is an actual error not
 * like [InfiniteScrollEventListenerInternal.onRollFailed] which is just a thing that can happen due to the probability distribution of the infinite scroll configuration
 * 4. [InfiniteScrollEventListenerInternal.onAdItemLoaded] Called when the ad that was rolled successfully for an item, actually loads and is ready to be shown or
 * [InfiniteScrollEventListenerInternal.onAdItemFailedToLoad] Called when the ad that was rolled successfully for an item, failed to load, also an error
 * 5. [InfiniteScrollEventListenerInternal.onAdItemImpression] Called when the ad loaded in the item is shown for the first time
 * 6. [InfiniteScrollEventListenerInternal.onAdItemClick] Called when the ad loaded in the item is clicked
 * 7. [InfiniteScrollEventListenerInternal.onAdItemOpen] Called when the ad loaded in the item is opened
 * 8. [InfiniteScrollEventListenerInternal.onAdItemClose] Called when the ad loaded in the item is closed
 */
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));

/**
 * Infinite Scroll life cycle goes as follows:
 * 1. [InfiniteScrollEventListenerInternal.onRoll] Called when the infinite scroll is about to roll for an item, this is just an event that happens before the roll or
 * [InfiniteScrollEventListenerInternal.onRollFailed] Called when the infinite scroll roll for an item says there is no ad in that item.
 * 3. [InfiniteScrollEventListenerInternal.onAdItemCreated] Called when the infinite scroll roll succeeds, so we are going to create a view inside it or
 * [InfiniteScrollEventListenerInternal.onAdItemFailedToCreate] Called when the infinite scroll roll succeeds, but we failed to create a view inside it, this is an actual error not
 * like [InfiniteScrollEventListenerInternal.onRollFailed] which is just a thing that can happen due to the probability distribution of the infinite scroll configuration
 * 4. [InfiniteScrollEventListenerInternal.onAdItemLoaded] Called when the ad that was rolled successfully for an item, actually loads and is ready to be shown or
 * [InfiniteScrollEventListenerInternal.onAdItemFailedToLoad] Called when the ad that was rolled successfully for an item, failed to load, also an error
 * 5. [InfiniteScrollEventListenerInternal.onAdItemImpression] Called when the ad loaded in the item is shown for the first time
 * 6. [InfiniteScrollEventListenerInternal.onAdItemClick] Called when the ad loaded in the item is clicked
 * 7. [InfiniteScrollEventListenerInternal.onAdItemOpen] Called when the ad loaded in the item is opened
 * 8. [InfiniteScrollEventListenerInternal.onAdItemClose] Called when the ad loaded in the item is closed
 */
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)onAdItemClickItemIdInData:(int32_t)itemIdInData __attribute__((swift_name("onAdItemClick(itemIdInData:)")));
- (void)onAdItemCloseItemIdInData:(int32_t)itemIdInData __attribute__((swift_name("onAdItemClose(itemIdInData:)")));
- (void)onAdItemCreatedAdapterId:(int32_t)adapterId probability:(double)probability __attribute__((swift_name("onAdItemCreated(adapterId:probability:)")));
- (void)onAdItemFailedToCreateMessage:(NSString *)message __attribute__((swift_name("onAdItemFailedToCreate(message:)")));
- (void)onAdItemFailedToLoadItemIdInData:(int32_t)itemIdInData error:(SharedCoreR89LoadError *)error __attribute__((swift_name("onAdItemFailedToLoad(itemIdInData:error:)")));
- (void)onAdItemImpressionItemIdInData:(int32_t)itemIdInData __attribute__((swift_name("onAdItemImpression(itemIdInData:)")));
- (void)onAdItemLoadedItemIdInData:(int32_t)itemIdInData __attribute__((swift_name("onAdItemLoaded(itemIdInData:)")));
- (void)onAdItemOpenItemIdInData:(int32_t)itemIdInData __attribute__((swift_name("onAdItemOpen(itemIdInData:)")));
- (void)onRollItemIdInData:(int32_t)itemIdInData __attribute__((swift_name("onRoll(itemIdInData:)")));
- (void)onRollFailedItemIdInData:(int32_t)itemIdInData __attribute__((swift_name("onRollFailed(itemIdInData:)")));
@end


/**
 * Interstitial life cycle goes as follows:
 * 1. [onLoaded]: Called when the banner is loaded and ready to be shown. Or If this goes wrong, the [onFailedToLoad] when this happens the factory marks the object for
 * deletion, and calls [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.createInterstitial] afterInterstitial
 * 2. [onOpen]: Something happen in your app and you call the factory [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.show] so this is the event is invoked if
 * everything goes according to plan. Otherwise [onAdFailedToShowFullScreen] is called, you will receive and error message string with information about it.
 * 3. [onImpression]: This means it did finish loading the creatives in the screen and they can be clicked
 * 4. [onClick]: The ad opens another Intent with the advertiser link
 * 5. [onAdDismissedFullScreenContent]: Called when the interstitial is closed normally
 *
 * - [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.createInterstitial] **afterInterstitial**: Is called when some situations happen. So the app flow can be recovered.
 * Situations when called are:
 *      - Everything went right and the user just closed the fullscreen ad
 *      - The ad hasn't loaded yet and you tried to show it
 *      - The ad has been Invalidated and you tried to show it. Gets invalidated when:
 *      - Fails to load
 *      - Already shown
 *      - Too Long without showing it
 *      - The ad failed to show
 */
__attribute__((swift_name("InterstitialEventListener")))
@interface SharedCoreInterstitialEventListener : SharedCoreBase

/**
 * Interstitial life cycle goes as follows:
 * 1. [onLoaded]: Called when the banner is loaded and ready to be shown. Or If this goes wrong, the [onFailedToLoad] when this happens the factory marks the object for
 * deletion, and calls [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.createInterstitial] afterInterstitial
 * 2. [onOpen]: Something happen in your app and you call the factory [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.show] so this is the event is invoked if
 * everything goes according to plan. Otherwise [onAdFailedToShowFullScreen] is called, you will receive and error message string with information about it.
 * 3. [onImpression]: This means it did finish loading the creatives in the screen and they can be clicked
 * 4. [onClick]: The ad opens another Intent with the advertiser link
 * 5. [onAdDismissedFullScreenContent]: Called when the interstitial is closed normally
 *
 * - [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.createInterstitial] **afterInterstitial**: Is called when some situations happen. So the app flow can be recovered.
 * Situations when called are:
 *      - Everything went right and the user just closed the fullscreen ad
 *      - The ad hasn't loaded yet and you tried to show it
 *      - The ad has been Invalidated and you tried to show it. Gets invalidated when:
 *      - Fails to load
 *      - Already shown
 *      - Too Long without showing it
 *      - The ad failed to show
 */
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));

/**
 * Interstitial life cycle goes as follows:
 * 1. [onLoaded]: Called when the banner is loaded and ready to be shown. Or If this goes wrong, the [onFailedToLoad] when this happens the factory marks the object for
 * deletion, and calls [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.createInterstitial] afterInterstitial
 * 2. [onOpen]: Something happen in your app and you call the factory [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.show] so this is the event is invoked if
 * everything goes according to plan. Otherwise [onAdFailedToShowFullScreen] is called, you will receive and error message string with information about it.
 * 3. [onImpression]: This means it did finish loading the creatives in the screen and they can be clicked
 * 4. [onClick]: The ad opens another Intent with the advertiser link
 * 5. [onAdDismissedFullScreenContent]: Called when the interstitial is closed normally
 *
 * - [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.createInterstitial] **afterInterstitial**: Is called when some situations happen. So the app flow can be recovered.
 * Situations when called are:
 *      - Everything went right and the user just closed the fullscreen ad
 *      - The ad hasn't loaded yet and you tried to show it
 *      - The ad has been Invalidated and you tried to show it. Gets invalidated when:
 *      - Fails to load
 *      - Already shown
 *      - Too Long without showing it
 *      - The ad failed to show
 */
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)onAdDismissedFullScreenContent __attribute__((swift_name("onAdDismissedFullScreenContent()")));
- (void)onAdFailedToShowFullScreenErrorMsg:(NSString *)errorMsg __attribute__((swift_name("onAdFailedToShowFullScreen(errorMsg:)")));
- (void)onClick __attribute__((swift_name("onClick()")));
- (void)onFailedToLoadError:(SharedCoreR89LoadError *)error __attribute__((swift_name("onFailedToLoad(error:)")));
- (void)onImpression __attribute__((swift_name("onImpression()")));
- (void)onLoaded __attribute__((swift_name("onLoaded()")));
- (void)onOpen __attribute__((swift_name("onOpen()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("RewardItem")))
@interface SharedCoreRewardItem : SharedCoreBase
- (instancetype)initWithAmount:(int32_t)amount type:(NSString *)type __attribute__((swift_name("init(amount:type:)"))) __attribute__((objc_designated_initializer));
- (SharedCoreRewardItem *)doCopyAmount:(int32_t)amount type:(NSString *)type __attribute__((swift_name("doCopy(amount:type:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t amount __attribute__((swift_name("amount")));
@property (readonly) NSString *type __attribute__((swift_name("type")));
@end


/**
 * Rewarded life cycle goes as follows:
 * 1. [onLoaded]: Called when the banner is loaded and ready to be shown. Or If this goes wrong, the [onFailedToLoad] when this happens the factory marks the object for
 * deletion, and calls [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.createRewarded] afterRewarded
 * 2. [onOpen]: Something happen in your app and you call the factory [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.show] so this is the event is invoked if
 * everything goes according to plan. Otherwise [onAdFailedToShowFullScreen] is called, you will receive and error message string with information about it.
 * 3. [onImpression]: This means it did finish loading the creatives in the screen and they can be clicked
 * 4. [onClick]: The ad opens another Intent with the advertiser link
 * 5. [onRewardEarned]: Called when the user has earned a reward it's usually called  before [onAdDismissedFullScreenContent] but it can change
 * 6. [onAdDismissedFullScreenContent]: Called when the interstitial is closed normally
 *
 * - [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.createRewarded] **afterInterstitial**: Is called when some situations happen. So the app flow can be recovered.
 * Situations when called are:
 *      - Everything went right and the user just closed the fullscreen ad
 *      - The ad hasn't loaded yet and you tried to show it
 *      - The ad has been Invalidated and you tried to show it. Gets invalidated when:
 *      - Fails to load
 *      - Already shown
 *      - Too Long without showing it
 *      - The ad failed to show
 */
__attribute__((swift_name("RewardedInterstitialEventListener")))
@interface SharedCoreRewardedInterstitialEventListener : SharedCoreBase

/**
 * Rewarded life cycle goes as follows:
 * 1. [onLoaded]: Called when the banner is loaded and ready to be shown. Or If this goes wrong, the [onFailedToLoad] when this happens the factory marks the object for
 * deletion, and calls [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.createRewarded] afterRewarded
 * 2. [onOpen]: Something happen in your app and you call the factory [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.show] so this is the event is invoked if
 * everything goes according to plan. Otherwise [onAdFailedToShowFullScreen] is called, you will receive and error message string with information about it.
 * 3. [onImpression]: This means it did finish loading the creatives in the screen and they can be clicked
 * 4. [onClick]: The ad opens another Intent with the advertiser link
 * 5. [onRewardEarned]: Called when the user has earned a reward it's usually called  before [onAdDismissedFullScreenContent] but it can change
 * 6. [onAdDismissedFullScreenContent]: Called when the interstitial is closed normally
 *
 * - [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.createRewarded] **afterInterstitial**: Is called when some situations happen. So the app flow can be recovered.
 * Situations when called are:
 *      - Everything went right and the user just closed the fullscreen ad
 *      - The ad hasn't loaded yet and you tried to show it
 *      - The ad has been Invalidated and you tried to show it. Gets invalidated when:
 *      - Fails to load
 *      - Already shown
 *      - Too Long without showing it
 *      - The ad failed to show
 */
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));

/**
 * Rewarded life cycle goes as follows:
 * 1. [onLoaded]: Called when the banner is loaded and ready to be shown. Or If this goes wrong, the [onFailedToLoad] when this happens the factory marks the object for
 * deletion, and calls [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.createRewarded] afterRewarded
 * 2. [onOpen]: Something happen in your app and you call the factory [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.show] so this is the event is invoked if
 * everything goes according to plan. Otherwise [onAdFailedToShowFullScreen] is called, you will receive and error message string with information about it.
 * 3. [onImpression]: This means it did finish loading the creatives in the screen and they can be clicked
 * 4. [onClick]: The ad opens another Intent with the advertiser link
 * 5. [onRewardEarned]: Called when the user has earned a reward it's usually called  before [onAdDismissedFullScreenContent] but it can change
 * 6. [onAdDismissedFullScreenContent]: Called when the interstitial is closed normally
 *
 * - [com.refinery89.androidSdk.domain_layer.RefineryAdFactory.createRewarded] **afterInterstitial**: Is called when some situations happen. So the app flow can be recovered.
 * Situations when called are:
 *      - Everything went right and the user just closed the fullscreen ad
 *      - The ad hasn't loaded yet and you tried to show it
 *      - The ad has been Invalidated and you tried to show it. Gets invalidated when:
 *      - Fails to load
 *      - Already shown
 *      - Too Long without showing it
 *      - The ad failed to show
 */
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (void)onAdDismissedFullScreenContent __attribute__((swift_name("onAdDismissedFullScreenContent()")));
- (void)onAdFailedToShowFullScreenErrorMsg:(NSString *)errorMsg __attribute__((swift_name("onAdFailedToShowFullScreen(errorMsg:)")));
- (void)onClick __attribute__((swift_name("onClick()")));
- (void)onFailedToLoadError:(SharedCoreR89LoadError *)error __attribute__((swift_name("onFailedToLoad(error:)")));
- (void)onImpression __attribute__((swift_name("onImpression()")));
- (void)onLoaded __attribute__((swift_name("onLoaded()")));
- (void)onOpen __attribute__((swift_name("onOpen()")));
- (void)onRewardEarnedRewardAmount:(int32_t)rewardAmount rewardType:(NSString *)rewardType __attribute__((swift_name("onRewardEarned(rewardAmount:rewardType:)")));
@end


/**
 * The Error object that you get when an ad fails to load
 */
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("R89LoadError")))
@interface SharedCoreR89LoadError : SharedCoreBase

/**
 * gets a comprehensive error message with ALL the information
 * @return String
 */
- (NSString *)description __attribute__((swift_name("description()")));
@end


/**
 * Builder in charge of making easy to configure the SDK. All the methods are optional to call.
 * This Class can be Used either when manualConfig is ON or OFF.
 * If its off, only Unfetchable configs will be built into the configuration:
 * This configurations are:
 * - consent configurations.
 *
 * If its ON you can add unitConfigs and APP
 *   global target Configs.
 */
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ConfigBuilder")))
@interface SharedCoreConfigBuilder : SharedCoreBase
- (instancetype)initWithPublisherName:(NSString *)publisherName __attribute__((swift_name("init(publisherName:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) SharedCoreConfigBuilderCompanion *companion __attribute__((swift_name("companion")));

/**
 * Adds keywords for better ad targeting
 *
 * @param storeUrl Play store url of publishers application
 * @param domain Publishers domain
 * @param appContextKeywords set of keywords for better ad targeting
 */
- (void)addAppTargetingStoreUrl:(NSString *)storeUrl domain:(NSString *)domain appContextKeywords:(NSSet<NSString *> * _Nullable)appContextKeywords __attribute__((swift_name("addAppTargeting(storeUrl:domain:appContextKeywords:)")));

/**
 * Adds a banner configuration to the sdk
 *
 * @param configurationId Configuration ID
 * @param bannerUnitId unit id from the manager
 * @param bannerConfigId config id from the manager
 * @param sizeList list of pair indicating width and height
 * @param autoRefreshMillis Ad auto refresh timer (default = 30000 millis / 30 seconds)
 * @param isCapped If ad is capped (default = false)
 * @param adAmountPerTimeSlot Capping amount of ads per time slot (default = 1)
 * @param timeSlotSize Size of the time slot (default = 3600000 millis / 1 hour)
 * @param keywords this is for adding specific keywords to the unit
 * @param unitFPID this is for adding specific first party data to the unit
 * @param closeButtonIsActive Activate ad close button (default = false)
 * @param closeButtonImageURL Url of custom icon for ad close button
 * @param wrapperWrapContent Wrap r89Wrapper around the content (ad)
 * @param wrapperMatchParentHeight Match r89Wrapper height with publishers wrapper height
 * @param wrapperMatchParentWidth Match r89Wrapper width with publishers wrapper width
 * @param wrapperHeight Change r89Wrapper's height in dp
 * @param wrapperWidth Change r89Wrapper's width in dp
 * @param wrapperPosition Change r89Wrapper's position inside of publishers wrapper WrapperPosition.(CENTER/START/END/TOP/BOTTOM)
 * @return ConfigBuilder after caching this configuration
 */
- (SharedCoreConfigBuilder *)addBannerConfigurationConfigurationId:(NSString *)configurationId bannerUnitId:(NSString *)bannerUnitId bannerConfigId:(NSString *)bannerConfigId sizeList:(NSArray<SharedCoreKotlinPair<SharedCoreInt *, SharedCoreInt *> *> *)sizeList autoRefreshMillis:(int32_t)autoRefreshMillis isCapped:(BOOL)isCapped adAmountPerTimeSlot:(int32_t)adAmountPerTimeSlot timeSlotSize:(int64_t)timeSlotSize keywords:(NSArray<NSString *> * _Nullable)keywords unitFPID:(NSDictionary<NSString *, NSSet<NSString *> *> * _Nullable)unitFPID closeButtonIsActive:(BOOL)closeButtonIsActive closeButtonImageURL:(NSString *)closeButtonImageURL wrapperWrapContent:(BOOL)wrapperWrapContent wrapperMatchParentHeight:(BOOL)wrapperMatchParentHeight wrapperMatchParentWidth:(BOOL)wrapperMatchParentWidth wrapperHeight:(int32_t)wrapperHeight wrapperWidth:(int32_t)wrapperWidth wrapperPosition:(SharedCoreWrapperPosition *)wrapperPosition __attribute__((swift_name("addBannerConfiguration(configurationId:bannerUnitId:bannerConfigId:sizeList:autoRefreshMillis:isCapped:adAmountPerTimeSlot:timeSlotSize:keywords:unitFPID:closeButtonIsActive:closeButtonImageURL:wrapperWrapContent:wrapperMatchParentHeight:wrapperMatchParentWidth:wrapperHeight:wrapperWidth:wrapperPosition:)")));

/** caches the cmp needed data to be shown, */
- (void)addCMPDataId:(int32_t)id cmpCodeId:(NSString *)cmpCodeId __attribute__((swift_name("addCMPData(id:cmpCodeId:)")));

/**
 * Adds a infinite scroll configuration to the sdk
 *
 * @param configurationId Configuration ID
 * @param minItems Minimum number of items in the row that should be ad-free after last item with an ad shown
 * @param maxItems Maximum number of items in the row that must contain at least one ad
 * @param variableProbability Probability of an item containing an ad
 * @param configsOfAdsToUse List if configurations of ads which should be used in this infinite scroll
 * @param wrapperWrapContent Wrap r89Wrapper around the content (ad)
 * @param wrapperMatchParentHeight Match r89Wrapper height with publishers wrapper height
 * @param wrapperMatchParentWidth Match r89Wrapper width with publishers wrapper width
 * @param wrapperHeight Change r89Wrapper's height in dp
 * @param wrapperWidth Change r89Wrapper's width in dp
 * @param wrapperPosition Change r89Wrapper's position inside of publishers wrapper WrapperPosition.(CENTER/START/END/TOP/BOTTOM)
 * @return ConfigBuilder after caching this configuration
 */
- (SharedCoreConfigBuilder *)addInfiniteScrollConfigurationConfigurationId:(NSString *)configurationId minItems:(int32_t)minItems maxItems:(int32_t)maxItems variableProbability:(SharedCoreKotlinIntRange *)variableProbability configsOfAdsToUse:(NSArray<NSString *> *)configsOfAdsToUse wrapperWrapContent:(BOOL)wrapperWrapContent wrapperMatchParentHeight:(BOOL)wrapperMatchParentHeight wrapperMatchParentWidth:(BOOL)wrapperMatchParentWidth wrapperHeight:(int32_t)wrapperHeight wrapperWidth:(int32_t)wrapperWidth wrapperPosition:(SharedCoreWrapperPosition *)wrapperPosition __attribute__((swift_name("addInfiniteScrollConfiguration(configurationId:minItems:maxItems:variableProbability:configsOfAdsToUse:wrapperWrapContent:wrapperMatchParentHeight:wrapperMatchParentWidth:wrapperHeight:wrapperWidth:wrapperPosition:)")));

/**
 * Add an interstitial configuration to the sdk
 *
 * @param configurationId Configuration ID
 * @param interstitialUnitId unit id from the manager
 * @param interstitialConfigId config id from the manager
 * @param isCapped If ad is capped (default = false)
 * @param adAmountPerTimeSlot Capping amount of ads per time slot (default = 1)
 * @param timeSlotSize Size of the time slot (default = 3600000 millis / 1 hour)
 * @param keywords this is for adding specific keywords to the unit
 * @param unitFPID this is for adding specific first party data to the unit
 * @return ConfigBuilder after caching this configuration
 */
- (SharedCoreConfigBuilder *)addInterstitialConfigurationConfigurationId:(NSString *)configurationId interstitialUnitId:(NSString *)interstitialUnitId interstitialConfigId:(NSString *)interstitialConfigId isCapped:(BOOL)isCapped adAmountPerTimeSlot:(int32_t)adAmountPerTimeSlot timeSlotSize:(int64_t)timeSlotSize keywords:(NSArray<NSString *> * _Nullable)keywords unitFPID:(NSDictionary<NSString *, NSSet<NSString *> *> * _Nullable)unitFPID __attribute__((swift_name("addInterstitialConfiguration(configurationId:interstitialUnitId:interstitialConfigId:isCapped:adAmountPerTimeSlot:timeSlotSize:keywords:unitFPID:)")));
- (SharedCoreConfigBuilder *)addPrebidServerConfigUseRealAuctionServer:(BOOL)useRealAuctionServer __attribute__((swift_name("addPrebidServerConfig(useRealAuctionServer:)")));

/**
 * Add a Rewarded interstitial configuration to the sdk
 *
 * @param configurationId Configuration ID
 * @param rewardedInterstitialUnitId unit id from the manager
 * @param isCapped If ad is capped (default = false)
 * @param adAmountPerTimeSlot Capping amount of ads per time slot (default = 1)
 * @param timeSlotSize Size of the time slot (default = 3600000 millis / 1 hour)
 * @param keywords this is for adding specific keywords to the unit
 * @param unitFPID this is for adding specific first party data to the unit
 * @return ConfigBuilder
 */
- (SharedCoreConfigBuilder *)addRewardedInterstitialConfigurationConfigurationId:(NSString *)configurationId rewardedInterstitialUnitId:(NSString *)rewardedInterstitialUnitId isCapped:(BOOL)isCapped adAmountPerTimeSlot:(int32_t)adAmountPerTimeSlot timeSlotSize:(int64_t)timeSlotSize keywords:(NSArray<NSString *> * _Nullable)keywords unitFPID:(NSDictionary<NSString *, NSSet<NSString *> *> * _Nullable)unitFPID __attribute__((swift_name("addRewardedInterstitialConfiguration(configurationId:rewardedInterstitialUnitId:isCapped:adAmountPerTimeSlot:timeSlotSize:keywords:unitFPID:)")));

/**
 * Add an video interstitial configuration to the sdk
 *
 * @param isCapped If ad is capped (default = false)
 * @param adAmountPerTimeSlot Capping amount of ads per time slot (default = 1)
 * @param timeSlotSize Size of the time slot (default = 3600000 millis / 1 hour)
 * @param keywords this is for adding specific keywords to the unit
 * @param unitFPID this is for adding specific first party data to the unit
 * @return ConfigBuilder after caching this configuration
 */
- (SharedCoreConfigBuilder *)addVideoInterstitialConfigurationConfigurationId:(NSString *)configurationId videoInterstitialUnitId:(NSString *)videoInterstitialUnitId videoInterstitialConfigId:(NSString *)videoInterstitialConfigId isCapped:(BOOL)isCapped adAmountPerTimeSlot:(int32_t)adAmountPerTimeSlot timeSlotSize:(int64_t)timeSlotSize keywords:(NSArray<NSString *> * _Nullable)keywords unitFPID:(NSDictionary<NSString *, NSSet<NSString *> *> * _Nullable)unitFPID __attribute__((swift_name("addVideoInterstitialConfiguration(configurationId:videoInterstitialUnitId:videoInterstitialConfigId:isCapped:adAmountPerTimeSlot:timeSlotSize:keywords:unitFPID:)")));

/**
 * Adds a video banner configuration to the sdk
 *
 * @param configurationId Configuration ID
 * @param outstreamUnitId Unit id from the manager
 * @param outstreamConfigId Config id from the manager
 * @param size Size of the video outstream banner in px
 * @param autoRefreshMillis Ad auto refresh timer (default = 30000 millis / 30 seconds)
 * @param isCapped If ad is capped (default = false)
 * @param adAmountPerTimeSlot Capping amount of ads per time slot (default = 1)
 * @param timeSlotSize Size of the time slot (default = 3600000 millis / 1 hour)
 * @param keywords Advertisement keywords for better targeting
 * @param unitFPID This is for adding specific first party data to the unit
 * @param closeButtonIsActive Activate ad close button (default = false)
 * @param closeButtonImageURL Url of custom icon for ad close button
 * @param wrapperWrapContent Wrap r89Wrapper around the content (ad)
 * @param wrapperMatchParentHeight Match r89Wrapper height with publishers wrapper height
 * @param wrapperMatchParentWidth Match r89Wrapper width with publishers wrapper width
 * @param wrapperHeight Change r89Wrapper's height in dp
 * @param wrapperWidth Change r89Wrapper's width in dp
 * @param wrapperPosition Change r89Wrapper's position inside of publishers wrapper WrapperPosition.(CENTER/START/END/TOP/BOTTOM)
 * @return ConfigBuilder after caching this configuration
 */
- (SharedCoreConfigBuilder *)addVideoOutstreamBannerConfigurationConfigurationId:(NSString *)configurationId outstreamUnitId:(NSString *)outstreamUnitId outstreamConfigId:(NSString *)outstreamConfigId size:(SharedCoreKotlinPair<SharedCoreInt *, SharedCoreInt *> *)size autoRefreshMillis:(int32_t)autoRefreshMillis isCapped:(BOOL)isCapped adAmountPerTimeSlot:(int32_t)adAmountPerTimeSlot timeSlotSize:(int64_t)timeSlotSize keywords:(NSArray<NSString *> * _Nullable)keywords unitFPID:(NSDictionary<NSString *, NSSet<NSString *> *> * _Nullable)unitFPID closeButtonIsActive:(BOOL)closeButtonIsActive closeButtonImageURL:(NSString * _Nullable)closeButtonImageURL wrapperWrapContent:(BOOL)wrapperWrapContent wrapperMatchParentHeight:(BOOL)wrapperMatchParentHeight wrapperMatchParentWidth:(BOOL)wrapperMatchParentWidth wrapperHeight:(int32_t)wrapperHeight wrapperWidth:(int32_t)wrapperWidth wrapperPosition:(SharedCoreWrapperPosition *)wrapperPosition __attribute__((swift_name("addVideoOutstreamBannerConfiguration(configurationId:outstreamUnitId:outstreamConfigId:size:autoRefreshMillis:isCapped:adAmountPerTimeSlot:timeSlotSize:keywords:unitFPID:closeButtonIsActive:closeButtonImageURL:wrapperWrapContent:wrapperMatchParentHeight:wrapperMatchParentWidth:wrapperHeight:wrapperWidth:wrapperPosition:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ConfigBuilder.Companion")))
@interface SharedCoreConfigBuilderCompanion : SharedCoreBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedCoreConfigBuilderCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) NSString *BANNER_TEST_CONFIG_ID __attribute__((swift_name("BANNER_TEST_CONFIG_ID")));
@property (readonly) int32_t BANNER_TEST_HEIGHT __attribute__((swift_name("BANNER_TEST_HEIGHT")));
@property (readonly) NSString *BANNER_TEST_R89_CONFIG_ID __attribute__((swift_name("BANNER_TEST_R89_CONFIG_ID")));
@property (readonly) NSString *BANNER_TEST_UNIT_ID __attribute__((swift_name("BANNER_TEST_UNIT_ID")));
@property (readonly) int32_t BANNER_TEST_WIDTH __attribute__((swift_name("BANNER_TEST_WIDTH")));
@property (readonly) NSString *CMP_TEST_CODE_ID __attribute__((swift_name("CMP_TEST_CODE_ID")));
@property (readonly) int32_t CMP_TEST_ID __attribute__((swift_name("CMP_TEST_ID")));
@property (readonly) NSString *INFINITE_SCROLL_TEST_R89_CONFIG_ID __attribute__((swift_name("INFINITE_SCROLL_TEST_R89_CONFIG_ID")));
@property (readonly) SharedCoreKotlinIntRange *INFINITE_SCROLL_TEST_VARIABLE_PROBABILITY __attribute__((swift_name("INFINITE_SCROLL_TEST_VARIABLE_PROBABILITY")));
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
@interface SharedCoreInitializationEvents : SharedCoreBase
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
@protocol SharedCoreIR89Logger
@required

/**
 * @param logLevel LogLevels what level of log is this message
 * @param tag where is this log coming from
 * @param message information to be logged
 * @return Unit
 */
- (void)printLogLevel:(SharedCoreLogLevels *)logLevel tag:(NSString *)tag message:(NSString *)message __attribute__((swift_name("print(logLevel:tag:message:)")));
@end

__attribute__((swift_name("KotlinComparable")))
@protocol SharedCoreKotlinComparable
@required
- (int32_t)compareToOther:(id _Nullable)other __attribute__((swift_name("compareTo(other:)")));
@end

__attribute__((swift_name("KotlinEnum")))
@interface SharedCoreKotlinEnum<E> : SharedCoreBase <SharedCoreKotlinComparable>
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) SharedCoreKotlinEnumCompanion *companion __attribute__((swift_name("companion")));
- (int32_t)compareToOther:(E)other __attribute__((swift_name("compareTo(other:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) int32_t ordinal __attribute__((swift_name("ordinal")));
@end


/**
 * levels available use google definition of them [Google Definition of the levels](https://developer.android.com/studio/debug/am-logcat#level)
 */
__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("LogLevels")))
@interface SharedCoreLogLevels : SharedCoreKotlinEnum<SharedCoreLogLevels *>
+ (instancetype)alloc __attribute__((unavailable));

/**
 * levels available use google definition of them [Google Definition of the levels](https://developer.android.com/studio/debug/am-logcat#level)
 */
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) SharedCoreLogLevels *verbose __attribute__((swift_name("verbose")));
@property (class, readonly) SharedCoreLogLevels *debug __attribute__((swift_name("debug")));
@property (class, readonly) SharedCoreLogLevels *info __attribute__((swift_name("info")));
@property (class, readonly) SharedCoreLogLevels *warn __attribute__((swift_name("warn")));
@property (class, readonly) SharedCoreLogLevels *error __attribute__((swift_name("error")));
@property (class, readonly) SharedCoreLogLevels *assert __attribute__((swift_name("assert")));
+ (SharedCoreKotlinArray<SharedCoreLogLevels *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<SharedCoreLogLevels *> *entries __attribute__((swift_name("entries")));

/**
 * The lower the ordinal(order in which they are defined) the higher the priority so VERBOSE has the highest priority
 * @param limitPriority Log level you want to compare to current log level
 * @return Returns true if [limitPriority] log level ordinal is lower then the current log levels ordinal, aka returns true if priority is higher than [limitPriority]
 */
- (BOOL)hasEqualOrHigherPriorityLimitPriority:(SharedCoreLogLevels *)limitPriority __attribute__((swift_name("hasEqualOrHigherPriority(limitPriority:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("WrapperPosition")))
@interface SharedCoreWrapperPosition : SharedCoreKotlinEnum<SharedCoreWrapperPosition *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) SharedCoreWrapperPosition *top __attribute__((swift_name("top")));
@property (class, readonly) SharedCoreWrapperPosition *bottom __attribute__((swift_name("bottom")));
@property (class, readonly) SharedCoreWrapperPosition *start __attribute__((swift_name("start")));
@property (class, readonly) SharedCoreWrapperPosition *end __attribute__((swift_name("end")));
@property (class, readonly) SharedCoreWrapperPosition *center __attribute__((swift_name("center")));
+ (SharedCoreKotlinArray<SharedCoreWrapperPosition *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<SharedCoreWrapperPosition *> *entries __attribute__((swift_name("entries")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UUIDLibraryIosImpl")))
@interface SharedCoreUUIDLibraryIosImpl : SharedCoreBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (NSString *)generate __attribute__((swift_name("generate()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UiExecutorIosImpl")))
@interface SharedCoreUiExecutorIosImpl : SharedCoreBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
@property (class, readonly, getter=companion) SharedCoreUiExecutorIosImplCompanion *companion __attribute__((swift_name("companion")));
- (void)executeInMainThreadBlock:(void (^)(void))block __attribute__((swift_name("executeInMainThread(block:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("UiExecutorIosImpl.Companion")))
@interface SharedCoreUiExecutorIosImplCompanion : SharedCoreBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedCoreUiExecutorIosImplCompanion *shared __attribute__((swift_name("shared")));
- (void)executeInMainThreadBlock:(void (^)(void))block __attribute__((swift_name("executeInMainThread(block:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("CollectionExtensionsKt")))
@interface SharedCoreCollectionExtensionsKt : SharedCoreBase
+ (NSDictionary<NSString *, NSSet<NSString *> *> *)toStringSetMap:(NSDictionary<id, id> *)receiver __attribute__((swift_name("toStringSetMap(_:)")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinPair")))
@interface SharedCoreKotlinPair<__covariant A, __covariant B> : SharedCoreBase
- (instancetype)initWithFirst:(A _Nullable)first second:(B _Nullable)second __attribute__((swift_name("init(first:second:)"))) __attribute__((objc_designated_initializer));
- (SharedCoreKotlinPair<A, B> *)doCopyFirst:(A _Nullable)first second:(B _Nullable)second __attribute__((swift_name("doCopy(first:second:)")));
- (BOOL)equalsOther:(id _Nullable)other __attribute__((swift_name("equals(other:)")));
- (int32_t)hashCode __attribute__((swift_name("hashCode()")));
- (NSString *)toString __attribute__((swift_name("toString()")));
@property (readonly) A _Nullable first __attribute__((swift_name("first")));
@property (readonly) B _Nullable second __attribute__((swift_name("second")));
@end

__attribute__((swift_name("KotlinIterable")))
@protocol SharedCoreKotlinIterable
@required
- (id<SharedCoreKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
@end

__attribute__((swift_name("KotlinIntProgression")))
@interface SharedCoreKotlinIntProgression : SharedCoreBase <SharedCoreKotlinIterable>
@property (class, readonly, getter=companion) SharedCoreKotlinIntProgressionCompanion *companion __attribute__((swift_name("companion")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (BOOL)isEmpty __attribute__((swift_name("isEmpty()")));
- (SharedCoreKotlinIntIterator *)iterator __attribute__((swift_name("iterator()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) int32_t first __attribute__((swift_name("first")));
@property (readonly) int32_t last __attribute__((swift_name("last")));
@property (readonly) int32_t step __attribute__((swift_name("step")));
@end

__attribute__((swift_name("KotlinClosedRange")))
@protocol SharedCoreKotlinClosedRange
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
@protocol SharedCoreKotlinOpenEndRange
@required
- (BOOL)containsValue_:(id)value __attribute__((swift_name("contains(value_:)")));
- (BOOL)isEmpty __attribute__((swift_name("isEmpty()")));
@property (readonly) id endExclusive __attribute__((swift_name("endExclusive")));
@property (readonly) id start __attribute__((swift_name("start")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinIntRange")))
@interface SharedCoreKotlinIntRange : SharedCoreKotlinIntProgression <SharedCoreKotlinClosedRange, SharedCoreKotlinOpenEndRange>
- (instancetype)initWithStart:(int32_t)start endInclusive:(int32_t)endInclusive __attribute__((swift_name("init(start:endInclusive:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) SharedCoreKotlinIntRangeCompanion *companion __attribute__((swift_name("companion")));
- (BOOL)containsValue:(SharedCoreInt *)value __attribute__((swift_name("contains(value:)")));
- (BOOL)containsValue_:(SharedCoreInt *)value __attribute__((swift_name("contains(value_:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (BOOL)isEmpty __attribute__((swift_name("isEmpty()")));
- (NSString *)description __attribute__((swift_name("description()")));

/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.9")
*/
@property (readonly) SharedCoreInt *endExclusive __attribute__((swift_name("endExclusive"))) __attribute__((deprecated("Can throw an exception when it's impossible to represent the value with Int type, for example, when the range includes MAX_VALUE. It's recommended to use 'endInclusive' property that doesn't throw.")));
@property (readonly) SharedCoreInt *endInclusive __attribute__((swift_name("endInclusive")));
@property (readonly) SharedCoreInt *start __attribute__((swift_name("start")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinEnumCompanion")))
@interface SharedCoreKotlinEnumCompanion : SharedCoreBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedCoreKotlinEnumCompanion *shared __attribute__((swift_name("shared")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinArray")))
@interface SharedCoreKotlinArray<T> : SharedCoreBase
+ (instancetype)arrayWithSize:(int32_t)size init:(T _Nullable (^)(SharedCoreInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (T _Nullable)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (id<SharedCoreKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(T _Nullable)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end

__attribute__((swift_name("KotlinIterator")))
@protocol SharedCoreKotlinIterator
@required
- (BOOL)hasNext __attribute__((swift_name("hasNext()")));
- (id _Nullable)next __attribute__((swift_name("next()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinIntProgression.Companion")))
@interface SharedCoreKotlinIntProgressionCompanion : SharedCoreBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedCoreKotlinIntProgressionCompanion *shared __attribute__((swift_name("shared")));
- (SharedCoreKotlinIntProgression *)fromClosedRangeRangeStart:(int32_t)rangeStart rangeEnd:(int32_t)rangeEnd step:(int32_t)step __attribute__((swift_name("fromClosedRange(rangeStart:rangeEnd:step:)")));
@end

__attribute__((swift_name("KotlinIntIterator")))
@interface SharedCoreKotlinIntIterator : SharedCoreBase <SharedCoreKotlinIterator>
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (SharedCoreInt *)next __attribute__((swift_name("next()")));
- (int32_t)nextInt __attribute__((swift_name("nextInt()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinIntRange.Companion")))
@interface SharedCoreKotlinIntRangeCompanion : SharedCoreBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) SharedCoreKotlinIntRangeCompanion *shared __attribute__((swift_name("shared")));
@property (readonly) SharedCoreKotlinIntRange *EMPTY __attribute__((swift_name("EMPTY")));
@end

#pragma pop_macro("_Nullable_result")
#pragma clang diagnostic pop
NS_ASSUME_NONNULL_END
