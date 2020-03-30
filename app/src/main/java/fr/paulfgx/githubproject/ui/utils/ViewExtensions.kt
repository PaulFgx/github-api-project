package fr.paulfgx.githubproject.ui.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlin.math.roundToInt

/**
 * Extensions methods
 * Top level functions : Allow access from everywhere without class name prefix
 */
fun View.dp(number: Number): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        number.toFloat(),
        this.resources.displayMetrics
    ).roundToInt()
}

fun View.hide(){
    visibility = View.GONE
}

fun View.dismissKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}