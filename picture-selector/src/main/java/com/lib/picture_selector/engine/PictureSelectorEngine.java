package com.lib.picture_selector.engine;


import com.lib.picture_selector.entity.LocalMedia;
import com.lib.picture_selector.interfaces.OnInjectLayoutResourceListener;
import com.lib.picture_selector.interfaces.OnResultCallbackListener;

/**
 * @author：luck
 * @date：2020/4/22 11:36 AM
 * @describe：PictureSelectorEngine
 */
public interface PictureSelectorEngine {

    /**
     * Create ImageLoad Engine
     *
     * @return
     */
    ImageEngine createImageLoaderEngine();

    /**
     * Create compress Engine
     *
     * @return
     */
    CompressFileEngine createCompressFileEngine();

    /**
     * Create loader data Engine
     *
     * @return
     */
    ExtendLoaderEngine createLoaderDataEngine();

    /**
     * Create UriToFileTransformEngine  Engine
     *
     * @return
     */
    UriToFileTransformEngine createUriToFileTransformEngine();

    /**
     * Create LayoutResource  Listener
     *
     * @return
     */
    OnInjectLayoutResourceListener createLayoutResourceListener();

    /**
     * Create Result Listener
     *
     * @return
     */
    OnResultCallbackListener<LocalMedia> getResultCallbackListener();
}
