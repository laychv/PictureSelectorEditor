package com.lib.picture_selector.interfaces;

import com.lib.picture_selector.entity.LocalMediaFolder;

/**
 * @author：luck
 * @date：2021/11/18 7:50 下午
 * @describe：OnAlbumItemClickListener
 */
public interface OnAlbumItemClickListener {
    /**
     * 专辑列表点击事件
     *
     * @param position  下标
     * @param curFolder 当前相册
     */
    void onItemClick(int position, LocalMediaFolder curFolder);

}
