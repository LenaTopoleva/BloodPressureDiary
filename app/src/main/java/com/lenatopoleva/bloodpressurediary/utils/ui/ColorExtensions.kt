package com.lenatopoleva.bloodpressurediary.utils.ui

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import com.lenatopoleva.bloodpressurediary.R
import com.lenatopoleva.bloodpressurediary.data.entity.Color

fun Color.getColorRes() : Int = when (this) {
    Color.LIGHT_GREEN -> R.color.color_light_green
    Color.DARK_GREEN -> R.color.color_dark_green
    Color.ORANGE -> R.color.color_orange
    Color.YELLOW -> R.color.color_yellow
    Color.GREEN -> R.color.color_green
    Color.BLUE -> R.color.color_blue
    Color.RED -> R.color.color_red
}

fun Color.getColorInt(context : Context) = ResourcesCompat.getColor(context.resources, getColorRes(), null)
