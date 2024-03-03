package com.svck.ilovemovie.external.utility

import android.annotation.SuppressLint
import com.svck.ilovemovie.data.constants.DateConstant
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale

class DateHelper {

    fun getCurrentDate(): String {
        val now = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(DateConstant.eeeddmmm, Locale.getDefault())
        return dateFormat.format(now.time)
    }

    fun getCurrentDateTimeStamp(): String {
        val now = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(DateConstant.yyyyMMddTHHmmssSSSZ, Locale.getDefault())
        return dateFormat.format(now.time)
    }

    @SuppressLint("SimpleDateFormat")
    fun toIndonesianDateFormat(dateString: String?, input: String, output:String): String {
        var formattedDate = ""
        try {
            val id = Locale("in", "ID")
            val inputFormat = SimpleDateFormat(input, id)
            val outputFormat = SimpleDateFormat(output,id)
            val formattedInput = inputFormat.parse(dateString ?: "")
            formattedDate = formattedInput?.let { outputFormat.format(it) } ?: ""
        } catch(error: Exception) {
            error.printStackTrace()
        }

        return formattedDate
    }


    fun fromDateTimeZoneToDate(dateString: String?) : Date?{
        var date :Date? = null
        try {
            val inputFormat = SimpleDateFormat(DateConstant.yyyyMMddTHHmmssSSSZ, Locale.getDefault())
            date =  inputFormat.parse(dateString ?: "")
        } catch(error: Exception) {
            error.printStackTrace()
        }
        return date
    }

    @SuppressLint("SimpleDateFormat", "NewApi")
    fun fromTimeStamp(dateString: String?, format: String? = DateConstant.ddMMMMyyyy): String {
        var formattedDate = ""
        try {
            val id = Locale("in", "ID")
            val inputFormat = SimpleDateFormat(DateConstant.yyyyMMdd, id)
            val outputFormat = SimpleDateFormat(format, id)
            val formattedInput = inputFormat.parse(dateString ?: "")
            formattedDate = formattedInput?.let { outputFormat.format(it) } ?: ""
        } catch(error: Exception) {
            error.printStackTrace()
        }

        return formattedDate
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @SuppressLint("SimpleDateFormat")
    fun getTimeLimitInMillis(expireTime: String?): Long {
        var timeInMillis: Long = 0

        try {
            val inputFormat = SimpleDateFormat(DateConstant.yyyyMMddHHmmss, Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
            val date = inputFormat.parse(expireTime ?: "")
            val startTime = Calendar.getInstance()
            startTime.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
            val endTime = startTime.clone() as Calendar
            endTime.apply { time = date }

            timeInMillis = endTime.timeInMillis - startTime.timeInMillis
        } catch (error: Exception) {
            error.printStackTrace()
        }

        return timeInMillis
    }

    @SuppressLint("SimpleDateFormat")
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun isMoreThanCurrentDateTime(compareDateTime: String?): Boolean {
        var isMoreThanComparedDate = false

        try {
            val currentFormat = SimpleDateFormat(DateConstant.yyyyMMddTHHmmssSSSZ)
            val current: Date = currentFormat.parse(getCurrentDateTimeStamp())
            val compareFormat = SimpleDateFormat(DateConstant.yyyyMMddHHmmss, Locale.getDefault())
            val compare: Date = compareFormat.parse(compareDateTime)
            isMoreThanComparedDate = (compare > current)
        } catch (error: Exception) {
            error.printStackTrace()
        }

        return isMoreThanComparedDate
    }

    fun fromDateTimeZone(dateString: String?, format: String? = DateConstant.ddMMyyyy) : String{
        var formattedDate = ""
        try {
            val inputFormat = SimpleDateFormat(DateConstant.yyyyMMddTHHmmssSSSZ_2, Locale.getDefault())
            val outputFormat = SimpleDateFormat(format, Locale.getDefault())
            val formattedInput = inputFormat.parse(dateString ?: "")
            formattedDate = formattedInput?.let { outputFormat.format(it) } ?: ""
        } catch(error: Exception) {
            error.printStackTrace()
        }

        return formattedDate
    }

}