package com.app.contentproviderdemo.views.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.climate_trace.businesslogic.interfaces.GeneralClickListeners
import com.app.climate_trace.businesslogic.interfaces.GeneralItemClickListeners
import com.app.contentproviderdemo.R
import com.app.contentproviderdemo.buisnessLogic.pojo.UserModel
import com.app.contentproviderdemo.buisnessLogic.viewmodel.ViewModelAddUser
import com.app.contentproviderdemo.databinding.ActivityAddUserBinding
import com.app.contentproviderdemo.views.adapter.AdapterUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityAddUser : AppCompatActivity() {
    private val PERMISSION_CODE = 101

    lateinit var mBinding: ActivityAddUserBinding
    lateinit var mViewModel: ViewModelAddUser

    val generalClickListeners = object : GeneralClickListeners {
        override fun onClick(view: View) {

            if (validation()) {
                mViewModel.addUser()
                mViewModel.fetchUsers()

            }
        }
    }
    val generalItemClickListeners = object : GeneralItemClickListeners {
        override fun onItemClick(view: View?, position: Int, item: Any?) {
            val model = item as UserModel
            mViewModel.delete(model)
            mViewModel.fetchUsers()

        }
    }
    val mAdapter: AdapterUser = AdapterUser(generalItemClickListeners)


    private fun validation(): Boolean {

        if (!mViewModel.observerName.get()!!.isNotEmpty()) {
            return false
        } else if (!mViewModel.observerDepartment.get()!!.isNotEmpty()) {
            return false
        }

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_user)
        mViewModel = ViewModelProvider(this@ActivityAddUser)[ViewModelAddUser::class.java]
        mBinding.generalClickListeners = generalClickListeners
        mBinding.viewModel = mViewModel

        _init()
        observer()
        checkPermission()


    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                "com.demo.user.provider.PERMISSION_WRITE_DATA"
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf("com.demo.user.provider.PERMISSION_WRITE_DATA"),
                PERMISSION_CODE
            )
        } else {
            mViewModel.fetchUsers()
        }

    }

    private fun observer() {
        mViewModel.userList.observe(this) {
            mAdapter.setData(it)
        }
        mViewModel.observeRefreshing.observe(this) {
            mBinding.swipeRefresh.isRefreshing = it
        }

    }


    private fun _init() {
        mBinding.apply {
            recUsers.adapter = mAdapter
            recUsers.layoutManager =
                LinearLayoutManager(this@ActivityAddUser, LinearLayoutManager.VERTICAL, false)

            swipeRefresh.setOnRefreshListener {
                mViewModel.observeRefreshing.postValue(true)
                mViewModel.fetchUsers()

            }
        }
    }
}