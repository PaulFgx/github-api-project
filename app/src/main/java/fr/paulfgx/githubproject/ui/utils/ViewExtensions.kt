package fr.paulfgx.githubproject.ui.utils

import android.util.TypedValue
import android.view.View
import androidx.paging.PagedListAdapter
import kotlin.math.roundToInt

/**
 * Top level functions : Allow access from everywhere without class name prefix
 */
fun View.dp(number: Number): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        number.toFloat(),
        this.resources.displayMetrics
    ).roundToInt()
}

/**
 * Extensions methods
 */
fun View.hide(){
    visibility = View.GONE
}