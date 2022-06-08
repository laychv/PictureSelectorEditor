package com.lib.picture_selector.app;

import android.content.Context;

import com.lib.picture_selector.engine.PictureSelectorEngine;

/**
 * @author：luck
 * @date：2019-12-03 15:12
 * @describe：PictureAppMaster
 */
public class PictureAppMaster implements IApp {

    private static PictureAppMaster mInstance;
    private IApp app;

    private PictureAppMaster() {
    }

    public static PictureAppMaster getInstance() {
        if (mInstance == null) {
            synchronized (PictureAppMaster.class) {
                if (mInstance == null) {
                    mInstance = new PictureAppMaster();
                }
            }
        }
        return mInstance;
    }

    @Override
    public Context getAppContext() {
        if (app == null) {
            return null;
        }
        return app.getAppContext();
    }

    @Override
    public PictureSelectorEngine getPictureSelectorEngine() {
        if (app == null) {
            return null;
        }
        return app.getPictureSelectorEngine();
    }

    public IApp getApp() {
        return app;
    }

    public void setApp(IApp app) {
        this.app = app;
    }
}
