package com.app.contentproviderdemo.views.activity

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.contentproviderdemo.R
import com.app.contentproviderdemo.buisnessLogic.viewmodel.ViewModelCallLogs
import com.app.contentproviderdemo.databinding.ActivityCallLogsBinding
import com.app.contentproviderdemo.views.adapter.AdapterCallLogs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityCallLogs : AppCompatActivity() {
    private val READ_CALL_LOGS_PERMISSION_CODE = 101

    lateinit var mBinding: ActivityCallLogsBinding

    lateinit var mViewModel: ViewModelCallLogs

    val mAdapter: AdapterCallLogs = AdapterCallLogs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =
            DataBindingUtil.setContentView(this@ActivityCallLogs, R.layout.activity_call_logs)
        mViewModel = ViewModelProvider(this@ActivityCallLogs)[ViewModelCallLogs::class.java]

        _init()
        initObserver()
        checkPermission()

    }

    private fun initObserver() {
        mViewModel.callLogList.observe(this) {
            mAdapter.setList(it)
            mBinding.totalCount.text = it.size.toString()

        }
        mViewModel.observeRefreshing.observe(this) {
            mBinding.swipeRefresh.isRefreshing = it
        }


    }

    private fun _init() {

        mBinding.apply {
            recCallLogs.layoutManager =
                LinearLayoutManager(this@ActivityCallLogs, LinearLayoutManager.VERTICAL, false)
            recCallLogs.adapter = mAdapter
            swipeRefresh.setOnRefreshListener {
                mViewModel.observeRefreshing.postValue(true)
                mViewModel.fetchCallLogs()

            }
        }

    }


    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_CALL_LOG),
                READ_CALL_LOGS_PERMISSION_CODE
            )
        } else {
            mViewModel.fetchCallLogs()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_CALL_LOGS_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mViewModel.fetchCallLogs()
            }
        }
    }
}