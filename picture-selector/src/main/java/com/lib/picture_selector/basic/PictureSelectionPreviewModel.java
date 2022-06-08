package com.lib.picture_selector.basic;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.lib.picture_selector.PictureSelectorPreviewFragment;
import com.lib.picture_selector.R;
import com.lib.picture_selector.config.PictureConfig;
import com.lib.picture_selector.config.PictureSelectionConfig;
import com.lib.picture_selector.config.SelectMimeType;
import com.lib.picture_selector.engine.ImageEngine;
import com.lib.picture_selector.entity.LocalMedia;
import com.lib.picture_selector.interfaces.OnExternalPreviewEventListener;
import com.lib.picture_selector.manager.SelectedManager;
import com.lib.picture_selector.style.PictureSelectorStyle;
import com.lib.picture_selector.style.PictureWindowAnimationStyle;
import com.lib.picture_selector.utils.ActivityCompatHelper;
import com.lib.picture_selector.utils.DoubleUtils;

import java.util.ArrayList;

/**
 * @author：luck
 * @date：2022/1/17 6:10 下午
 * @describe：PictureSelectionPreviewModel
 */
public final class PictureSelectionPreviewModel {
    private final PictureSelectionConfig selectionConfig;
    private final PictureSelector selector;

    public PictureSelectionPreviewModel(PictureSelector selector) {
        this.selector = selector;
        selectionConfig = PictureSelectionConfig.getCleanInstance();
        selectionConfig.isPreviewZoomEffect = false;
    }


    /**
     * Image Load the engine
     *
     * @param engine Image Load the engine
     *               <p>
     *               <a href="https://github.com/LuckSiege/PictureSelector/blob/version_component/app/src/main/java/com/luck/pictureselector/GlideEngine.java">
     *               </p>
     * @return
     */
    public PictureSelectionPreviewModel setImageEngine(ImageEngine engine) {
        if (PictureSelectionConfig.imageEngine != engine) {
            PictureSelectionConfig.imageEngine = engine;
        }
        return this;
    }

    /**
     * PictureSelector theme style settings
     *
     * @param uiStyle <p>
     *                Use {@link  PictureSelectorStyle
     *                It consists of the following parts and can be set separately}
     *                {@link com.lib.picture_selector.style.TitleBarStyle}
     *                {@link com.lib.picture_selector.style.AlbumWindowStyle}
     *                {@link com.lib.picture_selector.style.SelectMainStyle}
     *                {@link com.lib.picture_selector.style.BottomNavBarStyle}
     *                {@link com.lib.picture_selector.style.PictureWindowAnimationStyle}
     *                <p/>
     * @return PictureSelectorStyle
     */
    public PictureSelectionPreviewModel setSelectorUIStyle(PictureSelectorStyle uiStyle) {
        if (uiStyle != null) {
            PictureSelectionConfig.selectorStyle = uiStyle;
        }
        return this;
    }

    /**
     * Preview Full Screen Mode
     *
     * @param isFullScreenModel
     * @return
     */
    public PictureSelectionPreviewModel isPreviewFullScreenMode(boolean isFullScreenModel) {
        selectionConfig.isPreviewFullScreenMode = isFullScreenModel;
        return this;
    }

    /**
     * Intercept external preview click events, and users can implement their own preview framework
     *
     * @param listener
     * @return
     */
    public PictureSelectionPreviewModel setExternalPreviewEventListener(OnExternalPreviewEventListener listener) {
        PictureSelectionConfig.onExternalPreviewEventListener = listener;
        return this;
    }


    /**
     * @param isHidePreviewDownload Previews do not show downloads
     * @return
     */
    public PictureSelectionPreviewModel isHidePreviewDownload(boolean isHidePreviewDownload) {
        selectionConfig.isHidePreviewDownload = isHidePreviewDownload;
        return this;
    }

    /**
     * preview LocalMedia
     *
     * @param currentPosition current position
     * @param isDisplayDelete if visible delete
     * @param list            preview data
     */
    public void startFragmentPreview(int currentPosition, boolean isDisplayDelete, ArrayList<LocalMedia> list) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Activity activity = selector.getActivity();
            if (activity == null) {
                throw new NullPointerException("Activity cannot be null");
            }
            if (PictureSelectionConfig.imageEngine == null && selectionConfig.chooseMode != SelectMimeType.ofAudio()) {
                throw new NullPointerException("imageEngine is null,Please implement ImageEngine");
            }
            if (list == null || list.size() == 0) {
                throw new NullPointerException("preview data is null");
            }
            FragmentManager fragmentManager = null;
            if (activity instanceof AppCompatActivity) {
                fragmentManager = ((AppCompatActivity) activity).getSupportFragmentManager();
            } else if (activity instanceof FragmentActivity) {
                fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
            }
            if (fragmentManager == null) {
                throw new NullPointerException("FragmentManager cannot be null");
            }
            if (ActivityCompatHelper.checkFragmentNonExits((FragmentActivity) activity, PictureSelectorPreviewFragment.TAG)) {
                PictureSelectorPreviewFragment fragment = PictureSelectorPreviewFragment.newInstance();
                ArrayList<LocalMedia> previewData = new ArrayList<>(list);
                fragment.setExternalPreviewData(currentPosition, previewData.size(), previewData, isDisplayDelete);
                FragmentInjectManager.injectSystemRoomFragment(fragmentManager, PictureSelectorPreviewFragment.TAG, fragment);
            }
        }
    }

    /**
     * preview LocalMedia
     *
     * @param currentPosition current position
     * @param isDisplayDelete if visible delete
     * @param list            preview data
     */
    public void startActivityPreview(int currentPosition, boolean isDisplayDelete, ArrayList<LocalMedia> list) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Activity activity = selector.getActivity();
            if (activity == null) {
                throw new NullPointerException("Activity cannot be null");
            }
            if (PictureSelectionConfig.imageEngine == null && selectionConfig.chooseMode != SelectMimeType.ofAudio()) {
                throw new NullPointerException("imageEngine is null,Please implement ImageEngine");
            }
            if (list == null || list.size() == 0) {
                throw new NullPointerException("preview data is null");
            }
            Intent intent = new Intent(activity, PictureSelectorSupporterActivity.class);
            SelectedManager.addSelectedPreviewResult(list);
            intent.putExtra(PictureConfig.EXTRA_EXTERNAL_PREVIEW, true);
            intent.putExtra(PictureConfig.EXTRA_PREVIEW_CURRENT_POSITION, currentPosition);
            intent.putExtra(PictureConfig.EXTRA_EXTERNAL_PREVIEW_DISPLAY_DELETE, isDisplayDelete);
            Fragment fragment = selector.getFragment();
            if (fragment != null) {
                fragment.startActivity(intent);
            } else {
                activity.startActivity(intent);
            }
            PictureWindowAnimationStyle windowAnimationStyle = PictureSelectionConfig.selectorStyle.getWindowAnimationStyle();
            activity.overridePendingTransition(windowAnimationStyle.activityEnterAnimation, R.anim.ps_anim_fade_in);
        }
    }

}
