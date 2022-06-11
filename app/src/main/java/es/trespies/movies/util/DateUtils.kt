package es.trespies.movies.util

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

enum class DateFormarEnum(val value: String) {
    YYYYMMDD("YYYY-MM-DD")
}

object DateUtils {

    fun stringToDate(date: String?, format: DateFormarEnum): Date? {
        val date = date ?: return null
        if (date.isEmpty()) return null

        try {
            val sdf = SimpleDateFormat(format.value, Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            return sdf.parse(date)
        } catch (e: Exception) {}
        return null
    }

    fun stringToLong(date: String?, format: DateFormarEnum): Long? = stringToDate(date, format)?.time
}