package in.codeshuffle.scratchcardlayout.util;

import android.content.Context;

public class Utils {

    public static float dipToPx(Context context, float dipValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return dipValue * density;
    }
}
