package com.lib.picture_selector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lib.picture_selector.basic.PictureCommonFragment;
import com.lib.picture_selector.config.PictureSelectionConfig;
import com.lib.picture_selector.entity.LocalMedia;
import com.lib.picture_selector.manager.SelectedManager;
import com.lib.picture_selector.permissions.PermissionChecker;
import com.lib.picture_selector.permissions.PermissionConfig;
import com.lib.picture_selector.permissions.PermissionResultCallback;
import com.lib.picture_selector.utils.SdkVersionUtils;
import com.lib.picture_selector.utils.ToastUtils;

/**
 * @author：luck
 * @date：2021/11/22 2:26 下午
 * @describe：PictureOnlyCameraFragment
 */
public class PictureOnlyCameraFragment extends PictureCommonFragment {
    public static final String TAG = PictureOnlyCameraFragment.class.getSimpleName();

    public static PictureOnlyCameraFragment newInstance() {
        return new PictureOnlyCameraFragment();
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public int getResourceId() {
        return R.layout.ps_empty;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (SdkVersionUtils.isQ()) {
            openSelectedCamera();
        } else {
            PermissionChecker.getInstance().requestPermissions(this,
                    PermissionConfig.WRITE_EXTERNAL_STORAGE, new PermissionResultCallback() {
                        @Override
                        public void onGranted() {
                            openSelectedCamera();
                        }

                        @Override
                        public void onDenied() {
                            handlePermissionDenied(PermissionConfig.WRITE_EXTERNAL_STORAGE);
                        }
                    });
        }
    }

    @Override
    public void dispatchCameraMediaResult(LocalMedia media) {
        int selectResultCode = confirmSelect(media, false);
        if (selectResultCode == SelectedManager.ADD_SUCCESS) {
            dispatchTransformResult();
        } else {
            onKeyBackFragmentFinish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            onKeyBackFragmentFinish();
        }
    }

    @Override
    public void handlePermissionSettingResult(String[] permissions) {
        onPermissionExplainEvent(false, null);
        boolean isHasPermissions;
        if (PictureSelectionConfig.onPermissionsEventListener != null) {
            isHasPermissions = PictureSelectionConfig.onPermissionsEventListener
                    .hasPermissions(this, permissions);
        } else {
            isHasPermissions = PermissionChecker.isCheckCamera(getContext());
            if (SdkVersionUtils.isQ()) {
            } else {
                isHasPermissions = PermissionChecker.isCheckWriteStorage(getContext());
            }
        }
        if (isHasPermissions) {
            openSelectedCamera();
        } else {
            if (!PermissionChecker.isCheckCamera(getContext())) {
                ToastUtils.showToast(getContext(), getString(R.string.ps_camera));
            } else if (!PermissionChecker.isCheckWriteStorage(getContext())) {
                ToastUtils.showToast(getContext(), getString(R.string.ps_jurisdiction));
            }
            onKeyBackFragmentFinish();
        }
    }
}
