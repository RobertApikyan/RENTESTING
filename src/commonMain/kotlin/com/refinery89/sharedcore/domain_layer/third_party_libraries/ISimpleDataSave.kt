package com.refinery89.sharedcore.domain_layer.third_party_libraries

/**
 * This imitates the SharedPreferences from android it's used to enable the sdk to save temporally data in a key-value format.
 *
 * Used to create OS Key-value storing libraries that we can use from the Domain
 * @constructor
 */
internal interface ISimpleDataSave
{
	/**
	 * Returns an editor to edit and make many operations at once
	 * @return IEditor
	 */
	fun edit(): IEditor
	
	/**
	 * Returns true or false depending if the value exists or not
	 * @param key String
	 * @return String?
	 */
	fun contains(key: String): Boolean
	
	/**
	 * returns the value of the [key] if it exists or the [defaultValue] value if it doesn't
	 * @param key String
	 * @param defaultValue Long
	 * @return Long
	 */
	fun getLong(key: String, defaultValue: Long): Long
	
	/**
	 * returns the value of the [key] if it exists or the [defaultValue] value if it doesn't
	 * @param key String
	 * @param defaultValue Long
	 * @return Long
	 */
	fun getInt(key: String, defaultValue: Int): Int
	
	/**
	 * returns the value of the [key] if it exists or the [defaultValue] value if it doesn't
	 * @param key String
	 * @param defaultValue String
	 * @return String
	 */
	fun getString(key: String, defaultValue: String?): String?
	
	/**
	 * returns the value of the [key] if it exists or the [defaultValue] value if it doesn't
	 * @param key String
	 * @param defaultValue Boolean
	 * @return Boolean
	 */
	fun getBoolean(key: String, defaultValue: Boolean): Boolean
	
	/**
	 * Editor Interface for [edit] function in the [ISimpleDataSave] interface.
	 * This allows to make many operations and editions in the file at once
	 */
	interface IEditor
	{
		
		/**
		 * puts a long value in the file
		 * @param key String
		 * @param value Long
		 * @return ISimpleDataSave.IEditor
		 */
		fun putLong(key: String, value: Long): IEditor
		
		/**
		 * puts an int value in the file
		 * @param key String
		 * @param value Int
		 * @return ISimpleDataSave.IEditor
		 */
		fun putInt(key: String, value: Int): IEditor
		
		/**
		 * puts a string value in the file
		 * @param key String
		 * @param value String
		 * @return ISimpleDataSave.IEditor
		 */
		fun putString(key: String, value: String): IEditor
		
		/**
		 * puts a boolean value in the file
		 * @param key String
		 * @param value Boolean
		 * @return ISimpleDataSave.IEditor
		 */
		fun putBoolean(key: String, value: Boolean): IEditor
		
		/**
		 * Applies all the changes made in the editor
		 * @return Unit
		 */
		fun apply()
	}
}