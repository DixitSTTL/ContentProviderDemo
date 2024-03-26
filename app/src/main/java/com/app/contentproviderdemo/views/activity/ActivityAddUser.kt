package com.app.contentproviderdemo.views.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.climate_trace.businesslogic.interfaces.GeneralClickListeners
import com.app.contentproviderdemo.R
import com.app.contentproviderdemo.buisnessLogic.viewmodel.ViewModelAddUser
import com.app.contentproviderdemo.databinding.ActivityAddUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityAddUser : AppCompatActivity() {

    lateinit var mBinding: ActivityAddUserBinding
    lateinit var mViewModel: ViewModelAddUser

    val generalClickListeners = object : GeneralClickListeners {
        override fun onClick(view: View) {

            if (validation()) {
                mViewModel.addUser()

            }
        }
    }

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

    }
}