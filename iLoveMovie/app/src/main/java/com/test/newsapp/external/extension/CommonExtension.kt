package com.test.newsapp.external.extension

import android.app.Activity
import android.app.DownloadManager
import android.content.*
import android.content.res.Configuration
import android.database.Cursor
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.util.Base64
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.text.toSpannable
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.newsapp.BuildConfig
import com.test.newsapp.data.constants.AppConstant
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


fun debugMode(function: () -> Unit) {
    if (BuildConfig.DEBUG) {
        function()
    }
}

fun releaseMode(function: () -> Unit) {
    if (BuildConfig.BUILD_TYPE.equals("release", ignoreCase = true)) {
        function()
    }
}

fun roundOffDecimal(number: Double): Double {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(number).toDouble()
}

fun Int.divideToPercent(divideTo: Int): Int {
    return if (divideTo == 0) 0
    else (this / divideTo.toFloat() * 100).toInt()
}

fun Int.calculatePercentage(percent: Int): Int {
    return this * percent / 100
}

inline fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}

inline fun String?.notNullOrEmpty(f: (it: String) -> Unit): String? {
    return if (this != null && this.trim().isNotEmpty()) {
        f(this)
        this
    } else null
}

fun <T> List<T>?.toArrayList(): ArrayList<T> {
    val list = arrayListOf<T>()
    this?.forEach { list.add(it) }
    return list
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

fun File?.toBase64(): String {
    var base64String = ""

    try {
        val bytes = this?.readBytes()
        base64String = Base64.encodeToString(bytes, Base64.NO_WRAP)
    } catch (error: Exception) {

        error.printStackTrace()
    }

    return base64String
}

fun String?.decodeBase64Image(): Bitmap? {
    var bitmap: Bitmap? = null

    try {
        val decodedString: ByteArray = Base64.decode(this?.toByteArray(), Base64.NO_WRAP)
        bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    } catch (error: Exception) {
        error.printStackTrace()
    }

    return bitmap
}

fun Context?.copyText(text: String?) {
    val clipManager = this?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText(AppConstant.CLIPBOARD, text)
    clipManager.setPrimaryClip(clipData)
}

fun Context.isDarkModeEnabled(): Boolean {
    return when (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> true
        else -> false
    }
}

fun String?.toIndonesianPhoneNumber(): String {
    return "" + this?.mapIndexed { index, char ->
        if (index == 0 && char == '0') {
            "+62"
        } else if (index == 0 && char == '8') {
            "+62$char"
        } else if (index == 0 && char == '6') {
            "+$char"
        } else {
            char
        }
    }?.joinToString("")
}

fun String.isContainNumbers(): Boolean {
    return this.matches(".*\\d.*".toRegex())
}

fun Long.getTimerHours(): String {
    val hours = ((this / (1000 * 60 * 60)) % 24)
    return twoDigitTimeString(hours)
}

fun Long.getTimerMinutes(): String {
    val minutes = ((this / (1000 * 60)) % 60)
    return twoDigitTimeString(minutes)
}

fun Long.getTimerSeconds(): String {
    val seconds = (this / 1000) % 60
    return twoDigitTimeString(seconds)
}

internal fun twoDigitTimeString(number: Long): String {
    if (number == 0L) {
        return "00"
    }

    return if (number / 10 == 0L) {
        "0$number"
    } else number.toString()
}

private fun getRealPathFromURI(context: Context?, contentUri: Uri?): String? {
    var cursor: Cursor? = null
    return try {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        contentUri?.let {
            cursor = context?.contentResolver?.query(it, proj, null, null, null)
            val columnIndex: Int? = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToFirst()
            cursor?.getString(columnIndex ?: 0)
        }
    } finally {
        cursor?.close()
    }
}

fun Bitmap?.saveToMediaStoragePath(context: Context?): String {
    val filename = "${System.currentTimeMillis()}.jpeg"
    var outputStream: OutputStream? = null
    var imagePath: String? = ""

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context?.contentResolver?.also { resolver ->

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }

            val imageUri: Uri? =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            outputStream = imageUri?.let { resolver.openOutputStream(it) }
            imagePath = getRealPathFromURI(context, imageUri)
        }
    } else {
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        val image = File(imagesDir, filename)
        outputStream = FileOutputStream(image)
        imagePath = image.absolutePath
    }

    outputStream?.use { this?.compress(Bitmap.CompressFormat.JPEG, 100, it) }
    return imagePath ?: ""
}

fun String.toBitmap(): Bitmap? {
    val file = File(this)
    val photoPath = file.absolutePath
    var rotatedBitmap: Bitmap? = null

    try {
        val bitmap = BitmapFactory.decodeFile(photoPath)
        val exif = ExifInterface(photoPath)
        rotatedBitmap = when (exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)
            else -> bitmap
        }
    } catch (error: Exception) {
        error.printStackTrace()
    }

    return rotatedBitmap
}

fun rotateImage(bitmap: Bitmap, angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

fun String.toRupiahFormat(): String {
    return try {
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
        formatRupiah.format(this.toInt())
    } catch (e: Exception) {
        ""
    }
}

fun String.removeComma(): String {
    return try {
        if (this.contains(", "))
            this.replace(",", "")
        else
            this.replace(",", " ")
    } catch (e: Exception) {
        ""
    }
}

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun String.toDDMMYY(): String {
    val format = SimpleDateFormat("yyyy-MM-dd")
    val resultFormat = SimpleDateFormat("dd - MM - yyyy")
    val date = format.parse(this)
    return resultFormat.format(date)
}

fun String.downloadFile(context: Context, filename: String?, onDownloadError: () -> Unit) {
    try {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(this)
        val request = DownloadManager.Request(uri)

        request.apply {
            setTitle(filename)
            setDescription("Downloading")
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)
        }

        downloadManager.enqueue(request)
    } catch (error: Exception) {

        error.printStackTrace()
        onDownloadError()
    }
}

fun getDateTimeWithFormat(obj: Any?, format: String?): String? {
    val dateFormat = SimpleDateFormat(format, Locale("id"))
    dateFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
    return dateFormat.format(obj)
}

fun fromStringsXml(string: String, context: Context): String {
    val ss = string.lowercase(Locale.getDefault()).replace(" ", "_");
    val s = searchForString(ss, context)
    return s ?: "ERROR"
}

private fun searchForString(message: String, context: Context): String? {
    return try {
        val resId: Int = context.resources.getIdentifier(message, "string", context.packageName)
        context.resources.getString(resId)
    } catch (e: Exception) {
        null
    }
}

fun setColorText(context: Context, message: String, colorId: Int) =
    object : Spannable.Factory() {
        override fun newSpannable(source: CharSequence?): Spannable {
            source.notNull {
                val spannable = it.toSpannable()
                val lenStart = it.indexOf(message)
                val lenEnd = lenStart + message.length

                spannable.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context, colorId)),
                    lenStart,
                    lenEnd,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                return spannable
            }
            return newSpannable(source)
        }
    }

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.hideKeyboard(event: MotionEvent) {
    if (event.action == MotionEvent.ACTION_DOWN) {
        val view = currentFocus
        if (view != null) {
            view.clearFocus()
            val outRect = Rect()
            view.getGlobalVisibleRect(outRect)
            if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                val inputMethodManager =
                    getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

                inputMethodManager.hideSoftInputFromWindow(
                    view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    fun String?.decodeBase64Image(): Bitmap? {
        var bitmap: Bitmap? = null

        try {
            val base64Image = this?.split(",")
            val decodedString: ByteArray =
                Base64.decode(base64Image?.get(1)?.toByteArray(), Base64.DEFAULT)
            bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        } catch (error: Exception) {
            error.printStackTrace()
        }

        return bitmap
    }



}