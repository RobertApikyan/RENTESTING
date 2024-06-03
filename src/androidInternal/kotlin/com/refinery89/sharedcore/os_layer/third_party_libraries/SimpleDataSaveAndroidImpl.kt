package com.refinery89.sharedcore.os_layer.third_party_libraries

import android.content.SharedPreferences
import com.refinery89.sharedcore.domain_layer.third_party_libraries.ISimpleDataSave

/**
 * Uses The OS SharedPreferences Library to comply with [ISimpleDataSave] interface contract
 * this imitates the [SharedPreferences] class
 * @property prefs SharedPreferences
 * @constructor
 */
internal class SimpleDataSaveAndroidImpl(private val prefs: SharedPreferences) : ISimpleDataSave
{
	
	/**
	 * returns true if the key exists in the shared preferences
	 * @param key String
	 * @return Boolean
	 */
	override fun contains(key: String): Boolean
	{
		return prefs.contains(key)
	}
	
	/**
	 * returns the value of the key if it exists or the default value if it doesn't
	 * @param key String
	 * @param defaultValue Long
	 * @return Long
	 */
	override fun getLong(key: String, defaultValue: Long): Long
	{
		return prefs.getLong(key, defaultValue)
	}
	
	/**
	 * returns the value of the key if it exists or the default value if it doesn't
	 * @param key String
	 * @param defaultValue Int
	 * @return Int
	 */
	override fun getInt(key: String, defaultValue: Int): Int
	{
		return prefs.getInt(key, defaultValue)
	}
	
	override fun getString(key: String, defaultValue: String?): String?
	{
		return prefs.getString(key, defaultValue)
	}
	
	override fun getBoolean(key: String, defaultValue: Boolean): Boolean
	{
		return prefs.getBoolean(key, defaultValue)
	}
	
	/**
	 * returns an editor to edit the shared preferences
	 * @return ISimpleDataSave.IEditor
	 */
	override fun edit(): ISimpleDataSave.IEditor
	{
		return EditorImpl(prefs.edit())
	}
	
	/**
	 * Editor Implementation imitating the [SharedPreferences.Editor] class
	 * @property editor Editor
	 * @constructor
	 */
	private class EditorImpl(private val editor: SharedPreferences.Editor) : ISimpleDataSave.IEditor
	{
		/**
		 * puts a long value in the editor
		 * @param key String
		 * @param value Long
		 * @return ISimpleDataSave.IEditor
		 */
		override fun putLong(key: String, value: Long): ISimpleDataSave.IEditor
		{
			editor.putLong(key, value)
			return this
		}
		
		/**
		 * puts an int value in the editor
		 * @param key String
		 * @param value Int
		 * @return ISimpleDataSave.IEditor
		 */
		override fun putInt(key: String, value: Int): ISimpleDataSave.IEditor
		{
			editor.putInt(key, value)
			return this
		}
		
		override fun putString(key: String, value: String): ISimpleDataSave.IEditor
		{
			editor.putString(key, value)
			return this
		}
		
		override fun putBoolean(key: String, value: Boolean): ISimpleDataSave.IEditor
		{
			editor.putBoolean(key, value)
			return this
		}
		
		/**
		 * applies the changes to the shared preferences
		 * @return Unit
		 */
		override fun apply()
		{
			editor.apply()
		}
	}
	
}