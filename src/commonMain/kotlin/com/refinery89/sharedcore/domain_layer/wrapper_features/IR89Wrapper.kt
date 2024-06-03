package com.refinery89.sharedcore.domain_layer.wrapper_features

/**
 * Used to create a wrapper OS communication layer btw domain and OS, this wrapper is meant to go inside the publisher's wrapper,
 * inside of this we are going to have different things like: more buttons, or other stuff. Things that go "around" the ad.
 *
 * Also how the wrapper behaves: if it hugs the content and is as small as it can or if it is a fixed size and the ad is in a specific place inside it
 *
 * All of this things need to be done interacting with the specifics of the OS but Data is provided from the Domain
 */
internal interface IR89Wrapper
{
	
	/**
	 * Call only after addAdView, preferably after ad View is loaded
	 *
	 * @param adViewId Int this is the adView which will be the anchor thing which we place things around
	 * @param adLargestWidth Int this is the largest width of the adView which it CAN have not that is going to have it
	 * @param imageUrl String the Image url for the close button
	 * @param onClick what happens when we click the close button
	 * @return Unit
	 */
	fun addAdCloseButton(adViewId: String, adLargestWidth: Int, imageUrl: String, onClick: () -> Unit)
	
	/**
	 * Call only after addAdView, this will add an small text, the label, around the ad either on top or bottom
	 * @return Unit
	 */
	fun addLabel()
	
	/**
	 * Shows the wrapper with everything inside it
	 * @param newPublisherWrapper Any?
	 * @return Unit
	 */
	fun show(newPublisherWrapper: Any?)
	
	/**
	 * Hides the wrapper with everything inside it
	 * @return Unit
	 */
	fun hide()
	
	/**
	 * Destroys the wrapper with everything inside it
	 * @return Unit
	 */
	fun destroy()
	
	/**
	 * Adds the adView to the wrapper, this view is the most important thing inside the wrapper and is used as anchor for everything.
	 * Everything adapts to this view
	 * @param adView Any
	 * @return Unit
	 */
	fun addAdView(adView: Any)
	
	/**
	 * Reserves spaces taking into account the largest width and height of the adView plus
	 * all of the other configurations that we have added before.
	 *
	 * This is done so we have the biggest space possible for the adView reserved until it loads and after it loads we call
	 * other function to change this, this is done so the user does not see any content sliding around while everything loads
	 * @param largestHeightDp Int
	 * @param largestWidthDp Int
	 * @return Unit
	 */
	fun reserveSpace(largestHeightDp: Int, largestWidthDp: Int)
	
	/**
	 * after reserving the space, after the ad has loaded we might want to make the wrapper as small as possible,
	 * so the app does not have ugly white spaces
	 * @return Unit
	 */
	fun wrapContent()
	
	/**
	 * This is used to show the close button
	 * @return Unit
	 */
	fun showAdCloseButton()
	
	/**
	 * This is used to hide the close button
	 * @return Unit
	 */
	fun hideAdCloseButton()
	
	/**
	 * This is used to show the label
	 * @return Unit
	 */
	fun showLabel()
	
	/**
	 * This is used to hide the label
	 * @return Unit
	 */
	fun hideLabel()
}

