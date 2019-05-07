package in.codeshuffle.scratchcardlayout.util;

import android.content.Context;

/**
 * Utility methods for scratch card library needs
 */
public class ScratchCardUtils {

    /**
     * Utility method to convert density independent pixel to actual screen pixel value
     * @param context context of the display environment
     * @param dipValue dip value which is to be converted to px
     * @return pixel value corresponding to provided {@code dipValue} for the current screen density
     */
    public static float dipToPx(Context context, float dipValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return dipValue * density;
    }
}
