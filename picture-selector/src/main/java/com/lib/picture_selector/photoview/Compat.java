package com.lib.picture_selector.photoview;

import android.view.View;

class Compat {

    public static void postOnAnimation(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }
}
