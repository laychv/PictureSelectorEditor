package com.lib.picture_selector.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lib.picture_selector.R;

/**
 * @author：luck
 * @date：2021/11/19 5:11 下午
 * @describe：PictureCommonDialog
 */
public class PictureCommonDialog extends Dialog implements View.OnClickListener {

    private OnDialogEventListener eventListener;

    public PictureCommonDialog(Context context, String title, String content) {
        super(context, R.style.Picture_Theme_Dialog);
        setContentView(R.layout.ps_common_dialog);
        Button btnCancel = findViewById(R.id.btn_cancel);
        Button btnCommit = findViewById(R.id.btn_commit);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tv_content = findViewById(R.id.tv_content);
        tvTitle.setText(title);
        tv_content.setText(content);
        btnCancel.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        setDialogSize();
    }

    public static PictureCommonDialog showDialog(Context context, String title, String content) {
        PictureCommonDialog dialog = new PictureCommonDialog(context, title, content);
        dialog.show();
        return dialog;
    }

    private void setDialogSize() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        getWindow().setWindowAnimations(R.style.PictureThemeDialogWindowStyle);
        getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            dismiss();
        } else if (id == R.id.btn_commit) {
            dismiss();
            if (eventListener != null) {
                eventListener.onConfirm();
            }
        }
    }

    /**
     * 对外暴露的点击事件
     *
     * @param eventListener
     */
    public void setOnDialogEventListener(OnDialogEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public interface OnDialogEventListener {
        void onConfirm();
    }
}
