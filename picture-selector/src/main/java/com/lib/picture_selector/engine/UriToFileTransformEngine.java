package com.lib.picture_selector.engine;

import android.content.Context;

import com.lib.picture_selector.interfaces.OnKeyValueResultCallbackListener;

/**
 * @author：luck
 * @date：2021/11/23 8:23 下午
 * @describe：UriToFileTransformEngine
 */
public interface UriToFileTransformEngine {
    /**
     * Custom Sandbox File engine
     * <p>
     * Users can implement this interface, and then access their own sandbox framework to plug
     * the sandbox path into the {@link LocalMedia} object;
     * </p>
     * <p>
     * This is an asynchronous thread callback
     * </p>
     *
     * @param context  context
     * @param srcPath
     * @param mineType
     */
    void onUriToFileAsyncTransform(Context context, String srcPath, String mineType, OnKeyValueResultCallbackListener call);
}
