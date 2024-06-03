package com.refinery89.sharedcore.os_layer.extensions

import android.annotation.SuppressLint
import android.view.View
import com.refinery89.sharedcore.domain_layer.log_tool.R89LogUtil
import java.lang.reflect.Field

//Code from https://stackoverflow.com/questions/11186960/getonclicklistener-in-android-views

/**
 * Returns the current View.OnClickListener for the given View
 * @return the View.OnClickListener attached to the view; null if it could not be retrieved
 */
internal fun View.getOnClickListener(): View.OnClickListener?
{
	return getOnClickListenerV14()
}

//Used for new ListenerInfo class structure used beginning with API 14 (ICS)
@SuppressLint("PrivateApi")
private fun View.getOnClickListenerV14(): View.OnClickListener?
{
	var retrievedListener: View.OnClickListener? = null
	val viewStr = "android.view.View"
	val lInfoStr = "android.view.View\$ListenerInfo"
	try
	{
		val listenerField: Field? = Class.forName(viewStr).getDeclaredField("mListenerInfo")
		var listenerInfo: Any? = null
		
		if (listenerField != null)
		{
			listenerField.isAccessible = true
			listenerInfo = listenerField.get(this)
		}
		val clickListenerField: Field? = Class.forName(lInfoStr).getDeclaredField("mOnClickListener")
		if (clickListenerField != null && listenerInfo != null)
		{
			retrievedListener = clickListenerField.get(listenerInfo) as View.OnClickListener
		}
		
	} catch (ex: NoSuchFieldException)
	{
		R89LogUtil.e("GetListener", "No Such Field.", false)
	} catch (ex: IllegalAccessException)
	{
		R89LogUtil.e("GetListener", "Illegal Access.", false)
	} catch (ex: ClassNotFoundException)
	{
		R89LogUtil.e("GetListener", "Class Not Found.", false)
	}
	return retrievedListener
}