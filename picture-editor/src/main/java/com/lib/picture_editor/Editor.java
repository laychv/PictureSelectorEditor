package com.lib.picture_editor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Editor {

    private static final String EXTRA_PREFIX = BuildConfig.LIBRARY_PACKAGE_NAME;
    public static final String EXTRA_INPUT_URI = EXTRA_PREFIX + ".InputUri";
    public static final String EXTRA_OUTPUT_URI = EXTRA_PREFIX + ".OutputUri";
    private final Intent mCropIntent;
    private final Bundle mCropBundle;

    private Editor(Uri source, Uri destination) {
        mCropIntent = new Intent();
        mCropBundle = new Bundle();
        mCropBundle.putParcelable(EXTRA_INPUT_URI, source);
        mCropBundle.putParcelable(EXTRA_OUTPUT_URI, destination);
    }

    public static Editor of(@NonNull Uri source, @NonNull Uri destination) {
        return new Editor(source, destination);
    }

    public void startEdit(@NonNull Context context, @NonNull Fragment fragment, int requestCode) {
        fragment.startActivityForResult(getIntent(context), requestCode);
    }

    private Intent getIntent(Context context) {
        mCropIntent.setClass(context, EditorActivity.class);
        mCropIntent.putExtras(mCropBundle);
        return mCropIntent;
    }
}
