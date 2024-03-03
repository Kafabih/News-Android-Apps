package com.svck.ilovemovie.domain.base.dialogfragment

import android.widget.Toast
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment : DialogFragment() {

    fun dismissDialog() {
        dismissAllowingStateLoss()
    }

    fun showMessage(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showLongMessage(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}