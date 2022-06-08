package com.lib.picture_selector.basic;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.lib.picture_selector.R;
import com.lib.picture_selector.utils.ActivityCompatHelper;

/**
 * @author：luck
 * @date：2021/12/6 1:28 下午
 * @describe：FragmentInjectManager
 */
public class FragmentInjectManager {
    /**
     * inject fragment
     *
     * @param activity          root activity
     * @param targetFragmentTag fragment tag
     * @param targetFragment    target fragment
     */
    public static void injectFragment(FragmentActivity activity, String targetFragmentTag, Fragment targetFragment) {
        if (ActivityCompatHelper.checkFragmentNonExits(activity, targetFragmentTag)) {
            activity.getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, targetFragment, targetFragmentTag)
                    .addToBackStack(targetFragmentTag)
                    .commitAllowingStateLoss();
        }
    }

    /**
     * inject fragment
     *
     * @param fragmentManager   root activity FragmentManager
     * @param targetFragmentTag fragment tag
     * @param targetFragment    target fragment
     */
    public static void injectSystemRoomFragment(FragmentManager fragmentManager, String targetFragmentTag, Fragment targetFragment) {
        fragmentManager.beginTransaction()
                .add(android.R.id.content, targetFragment, targetFragmentTag)
                .addToBackStack(targetFragmentTag)
                .commitAllowingStateLoss();
    }
}
