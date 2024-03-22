package com.app.contentproviderdemo.views.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.contentproviderdemo.R
import com.app.contentproviderdemo.buisnessLogic.viewmodel.ViewModelSMS
import com.app.contentproviderdemo.databinding.ActivitySmsBinding
import com.app.contentproviderdemo.views.adapter.AdapterSMS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitySMS : AppCompatActivity() {
    private val READ_SMS_PERMISSION_CODE = 101

    lateinit var mBinding: ActivitySmsBinding

    lateinit var mViewModel: ViewModelSMS

    val mAdapter: AdapterSMS = AdapterSMS()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =
            DataBindingUtil.setContentView(this@ActivitySMS, R.layout.activity_sms)
        mViewModel = ViewModelProvider(this@ActivitySMS)[ViewModelSMS::class.java]

        _init()
        initObserver()
        checkPermission()
    }

    private fun initObserver() {
        mViewModel.smsList.observe(this, {
            Log.d("ggbgbg","bgbgb")
            mAdapter.setList(it)

        })

    }

    private fun _init() {

        mBinding.recSMS.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mBinding.recSMS.adapter = mAdapter

    }


    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_SMS),
                READ_SMS_PERMISSION_CODE
            )
        } else {
            mViewModel.fetchAllSMS()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_SMS_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mViewModel.fetchAllSMS()
            }
        }
    }
}