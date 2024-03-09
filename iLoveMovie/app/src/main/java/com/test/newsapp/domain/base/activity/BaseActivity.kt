package com.test.newsapp.domain.base.activity

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.test.newsapp.domain.router.RouterNavigation
import com.test.newsapp.domain.session.AppSession
import com.test.newsapp.external.utility.OSUtility
import org.koin.android.ext.android.inject


abstract class BaseActivity : AppCompatActivity() {

    val appSession: AppSession by inject()
    val osUtility: OSUtility by inject()
    val router: RouterNavigation by inject()
    val gson: Gson by inject()

    fun showMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showLongMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}