package com.app.contentproviderdemo.views.activity

import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.app.contentproviderdemo.R
import com.app.contentproviderdemo.buisnessLogic.viewmodel.ViewModelImages
import com.app.contentproviderdemo.databinding.ActivityImagesBinding
import com.app.contentproviderdemo.views.adapter.AdapterImages
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityImages : AppCompatActivity() {
    private val READ_IMAGES_PERMISSION_CODE = 101

    private lateinit var mBinding: ActivityImagesBinding
    private lateinit var mViewModel: ViewModelImages
    private val mAdapter = AdapterImages()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this@ActivityImages, R.layout.activity_images)
        mViewModel = ViewModelProvider(this@ActivityImages).get(ViewModelImages::class.java)

        _init()
        initObserver()
        checkPermission()
//        mViewModel.fetchImages()
    }

    private fun initObserver() {
        mViewModel.imageList.observe(this) {
            mAdapter.setList(it)
            mBinding.totalCount.text = it.size.toString()

        }


    }

    private fun _init() {

        mBinding.recImages.layoutManager =
            GridLayoutManager(this, 2)
        mBinding.recImages.adapter = mAdapter

    }


    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                    READ_IMAGES_PERMISSION_CODE
                )
            } else {
                mViewModel.fetchImages()
            }

        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_IMAGES_PERMISSION_CODE
                )
            } else {
                mViewModel.fetchImages()
            }
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_IMAGES_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mViewModel.fetchImages()
            }
        }
    }
}