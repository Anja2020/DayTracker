package com.example.daytracker

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/*fun formatDays(days: List<DayQuality>, resources: Resources): List<Spanned> {
    val daysList = mutableListOf<Spanned>()
    days.forEach {
        val sb = StringBuilder()
        sb.apply {
            append(resources.getString(R.string.day))
            append("\t${it.recordTime}<br>")

            //append("\t${convertLongToDateString(it.recordTime, resources)}<br>")
            append("<br>")
            append(resources.getString(R.string.quality))
            append("\t${convertNumericQualityToString(it.dayQuality, resources)}<br>")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            daysList.add(Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY))
        } else {
            daysList.add(HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY))
        }

    }
    return daysList
}*/



fun formatDay(date: LocalDate, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.day))
        append("\t${convertDateString(date)}<br>")
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

fun formatQuality(quality: Int, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.quality))
        append("\t${convertNumericQualityToString(quality)}<br>")
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}


private fun convertNumericQualityToString(quality: Int): String {
    return when (quality) {
        1 -> "Very bad"
        2 -> "OK"
        3 -> "Very good"
        else -> "--"
    }
}


private fun convertDateString(date: LocalDate?): String? {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return date?.format(formatter)
}

