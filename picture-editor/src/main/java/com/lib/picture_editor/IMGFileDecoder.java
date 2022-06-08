package com.lib.picture_editor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by felix on 2017/12/26 下午3:07.
 */

public class IMGFileDecoder {

    private final Uri uri;
    private final Context context;

    public IMGFileDecoder(Uri uri, Context context) {
        this.uri = uri;
        this.context = context;
    }

    public Bitmap decode() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inJustDecodeBounds = false;// must false ???

        if (uri == null) {
            return null;
        }

        String path = uri.getPath();
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        try {
            InputStream is = context.getContentResolver().openInputStream(uri);
            return BitmapFactory.decodeStream(is, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File file = new File(path);
        if (file.exists()) {
            return BitmapFactory.decodeFile(path, options);
        }
        return null;
    }
}
