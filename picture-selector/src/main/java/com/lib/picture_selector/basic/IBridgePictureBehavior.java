package com.lib.picture_selector.basic;

/**
 * @author：luck
 * @date：2022/1/12 9:32 上午
 * @describe：IBridgePictureBehavior
 */
public interface IBridgePictureBehavior {
    /**
     * finish activity
     *
     * @param result data
     */
    void onSelectFinish(PictureCommonFragment.SelectorResult result);
}
