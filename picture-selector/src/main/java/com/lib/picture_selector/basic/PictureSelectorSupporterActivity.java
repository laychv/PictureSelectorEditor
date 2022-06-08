package com.lib.picture_selector.basic;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.lib.picture_selector.PictureSelectorFragment;
import com.lib.picture_selector.PictureSelectorPreviewFragment;
import com.lib.picture_selector.R;
import com.lib.picture_selector.config.PictureConfig;
import com.lib.picture_selector.config.PictureSelectionConfig;
import com.lib.picture_selector.entity.LocalMedia;
import com.lib.picture_selector.immersive.ImmersiveManager;
import com.lib.picture_selector.manager.SelectedManager;
import com.lib.picture_selector.style.PictureWindowAnimationStyle;
import com.lib.picture_selector.style.SelectMainStyle;
import com.lib.picture_selector.utils.StyleUtils;

import java.util.ArrayList;

/**
 * @author：luck
 * @date：2021/11/17 9:59 上午
 * @describe：PictureSelectorSupporterActivity
 */
public class PictureSelectorSupporterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        immersive();
        setContentView(R.layout.ps_activity_container);
        setupFragment();
    }

    private void immersive() {
        SelectMainStyle mainStyle = PictureSelectionConfig.selectorStyle.getSelectMainStyle();
        int statusBarColor = mainStyle.getStatusBarColor();
        int navigationBarColor = mainStyle.getNavigationBarColor();
        boolean isDarkStatusBarBlack = mainStyle.isDarkStatusBarBlack();
        if (!StyleUtils.checkStyleValidity(statusBarColor)) {
            statusBarColor = ContextCompat.getColor(this, R.color.ps_color_grey);
        }
        if (!StyleUtils.checkStyleValidity(navigationBarColor)) {
            navigationBarColor = ContextCompat.getColor(this, R.color.ps_color_grey);
        }
        ImmersiveManager.immersiveAboveAPI23(this, statusBarColor, navigationBarColor, isDarkStatusBarBlack);
    }

    private void setupFragment() {
        if (getIntent().hasExtra(PictureConfig.EXTRA_EXTERNAL_PREVIEW)
                && getIntent().getBooleanExtra(PictureConfig.EXTRA_EXTERNAL_PREVIEW, false)) {
            int position = getIntent().getIntExtra(PictureConfig.EXTRA_PREVIEW_CURRENT_POSITION, 0);
            PictureSelectorPreviewFragment fragment = PictureSelectorPreviewFragment.newInstance();
            ArrayList<LocalMedia> previewResult = SelectedManager.getSelectedPreviewResult();
            ArrayList<LocalMedia> previewData = new ArrayList<>(previewResult);
            boolean isDisplayDelete = getIntent()
                    .getBooleanExtra(PictureConfig.EXTRA_EXTERNAL_PREVIEW_DISPLAY_DELETE, false);
            fragment.setExternalPreviewData(position, previewData.size(), previewData, isDisplayDelete);
            FragmentInjectManager.injectFragment(this, PictureSelectorPreviewFragment.TAG, fragment);
        } else {
            FragmentInjectManager.injectFragment(this, PictureSelectorFragment.TAG,
                    PictureSelectorFragment.newInstance());
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void finish() {
        super.finish();
        PictureWindowAnimationStyle windowAnimationStyle = PictureSelectionConfig.selectorStyle.getWindowAnimationStyle();
        overridePendingTransition(0, windowAnimationStyle.activityExitAnimation);
    }
}
