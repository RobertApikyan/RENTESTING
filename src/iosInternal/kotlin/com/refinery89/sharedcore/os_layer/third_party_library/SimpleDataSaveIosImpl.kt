package com.refinery89.sharedcore.os_layer.third_party_library

import com.refinery89.sharedcore.domain_layer.third_party_libraries.ISimpleDataSave
import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue


internal class SimpleDataSaveIosImpl(suiteName:String) : ISimpleDataSave
{
	private val userDefaults = NSUserDefaults(suiteName = suiteName)
	
	override fun edit(): ISimpleDataSave.IEditor
	{
		return UserDefaultEditor(userDefaults);
	}
	
	override fun contains(key: String): Boolean
	{
		return userDefaults.objectForKey(key) != null
	}
	
	override fun getLong(key: String, defaultValue: Long): Long
	{
		return userDefaults.objectForKey(key)?.toString()?.toLongOrNull() ?: defaultValue
	}
	
	override fun getInt(key: String, defaultValue: Int): Int
	{
		return userDefaults.objectForKey(key)?.toString()?.toIntOrNull() ?: defaultValue
	}
	
	override fun getString(key: String, defaultValue: String?): String?
	{
		return userDefaults.stringForKey(key) ?: defaultValue
	}
	
	override fun getBoolean(key: String, defaultValue: Boolean): Boolean
	{
		return userDefaults.objectForKey(key)?.toString()?.toBoolean() ?: defaultValue
	}
	
	private class UserDefaultEditor(private val userDefaults: NSUserDefaults) : ISimpleDataSave.IEditor
	{
		override fun putLong(key: String, value: Long): ISimpleDataSave.IEditor
		{
			userDefaults.setInteger(value, forKey = key)
			return this
		}
		
		override fun putInt(key: String, value: Int): ISimpleDataSave.IEditor
		{
			putLong(key, value.toLong())
			return this
		}
		
		override fun putString(key: String, value: String): ISimpleDataSave.IEditor
		{
			userDefaults.setValue(value, forKey = key)
			return this
		}
		
		override fun putBoolean(key: String, value: Boolean): ISimpleDataSave.IEditor
		{
			userDefaults.setBool(value, forKey = key)
			return this
		}
		
		override fun apply()
		{
			/* In iOS, no need to call apply explicitly after making changes */
		}
	}
}