package com.test.newsapp.domain.session

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder


class AppSession(val application: Application) {

    private val NEWS_PREF = "NEWS_PREF"
    var preferences: SharedPreferences = application.getSharedPreferences(NEWS_PREF, Context.MODE_PRIVATE)

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

    fun clear() {

        preferences.edit().clear()
    }

    //GET

    //BOOLEAN

    //SAVE


    //CLEAR
}
