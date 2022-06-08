package com.lib.picture_selector.interfaces;

/**
 * @author：luck
 * @date：2021/12/5 9:56 下午
 * @describe：OnExternalQueryAlbumListener
 */
public interface OnQueryAlbumListener<T> {
    void onComplete(T result);
}
