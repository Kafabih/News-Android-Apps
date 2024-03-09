package com.test.newsapp.ui.domain

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder

class AppSession(val application: Context) {

    private val GLASSMORPHISMPREF = "NewsSession"
    var preferences: SharedPreferences =
        application.getSharedPreferences(GLASSMORPHISMPREF, Context.MODE_PRIVATE)

    /**
     * Saves object into the Preferences.
     * @param `object` Object of model class (of type [T]) to save
     * @param key Key with which Shared preferences to
     **/
    fun <T> put(key: String, `object`: T) {
        val jsonString = GsonBuilder().create().toJson(`object`)
        preferences.edit().putString(key, jsonString).apply()
    }

    /**
     * Used to retrieve object from the Preferences.
     * @param key Shared Preference key with which object was saved.
     **/
    inline fun <reified T> get(key: String): T? {
        val value = preferences.getString(key, null)
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

    /**
     * Used to remove object from the Preferences.
     * @param key Shared Preference key with which object was removed.
     **/
    fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }

    /**
     * SESSION SAVE
     */
//    fun saveLoginSession(loginDTO: LoginDTO.Response) {
//        this.put(SessionConstant.LOGIN_SESSION, loginDTO)
//    }


    /**
     * SESSION GET
     */
//    fun getLoginSession(): LoginDTO.Response? = this.get<LoginDTO.Response>(SessionConstant.LOGIN_SESSION)


    /**
     * SESSION REMOVE
     */
//    fun clearLoginSession() { this.remove(SessionConstant.LOGIN_SESSION) }

}