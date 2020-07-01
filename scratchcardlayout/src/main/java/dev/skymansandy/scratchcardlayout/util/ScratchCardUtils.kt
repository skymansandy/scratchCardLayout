package dev.skymansandy.scratchcardlayout.util

import android.content.Context

/**
 * Utility methods for scratch card library needs
 */
object ScratchCardUtils {
    /**
     * Utility method to convert density independent pixel to actual screen pixel value
     * @param context context of the display environment
     * @param dipValue dip value which is to be converted to px
     * @return pixel value corresponding to provided `dipValue` for the current screen density
     */
    fun dipToPx(context: Context, dipValue: Float): Float {
        val density = context.resources.displayMetrics.density
        return dipValue * density
    }
}