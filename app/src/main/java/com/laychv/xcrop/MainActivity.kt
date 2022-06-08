package com.laychv.xcrop

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.laychv.xcrop.databinding.ActivityMainBinding
import com.lib.picture_editor.Editor
import com.lib.picture_selector.basic.PictureSelector
import com.lib.picture_selector.config.PictureConfig
import com.lib.picture_selector.config.PictureMimeType
import com.lib.picture_selector.config.SelectMimeType
import com.lib.picture_selector.interfaces.OnMediaEditInterceptListener
import com.lib.picture_selector.utils.DateUtils
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "仿微信图片裁剪"

        binding.fab.setOnClickListener { view ->
            initGallery()
        }
    }

    private fun initGallery() {
        PictureSelector.create(this)
            .openGallery(SelectMimeType.ofImage())
            .isDisplayCamera(true)
            .setImageEngine(GlideEngine) // 外部传入图片加载引擎，必传项
            .setMaxSelectNum(9) // 最大图片选择数量
            .setMinSelectNum(1) // 最小选择数量
//            .setCompressEngine(ImageCompressEngine())
            .isPreviewImage(true)
            // 裁剪
//            .setCropEngine(crop())
            // 编辑
            .setEditMediaInterceptListener(edit())
            .forResult(PictureConfig.CHOOSE_REQUEST)
    }

    private fun edit() = OnMediaEditInterceptListener { fragment, currentLocalMedia, requestCode ->
        val currentEditPath = currentLocalMedia!!.availablePath
        val inputUri =
            if (PictureMimeType.isContent(currentEditPath)) Uri.parse(
                currentEditPath
            ) else Uri.fromFile(
                File(currentEditPath)
            )
        val destinationUri = Uri.fromFile(
            File(getSandboxPath(), DateUtils.getCreateFileName("CROP_") + ".jpeg")
        )

        val xcrop = Editor.of(inputUri, destinationUri)
        xcrop.startEdit(this, fragment, requestCode)
    }

    private fun getSandboxPath(): String {
        val externalFilesDir: File? = this.getExternalFilesDir("")
        val customFile = File(externalFilesDir?.absolutePath, "Sandbox")
        if (!customFile.exists()) {
            customFile.mkdirs()
        }
        return customFile.absolutePath + File.separator
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                Glide.with(this)
                    .load(PictureSelector.obtainSelectorList(data)[0].availablePath)
                    .into(binding.ivShower)
            }
        }
    }
}