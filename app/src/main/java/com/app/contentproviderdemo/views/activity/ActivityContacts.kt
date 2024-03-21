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
import com.app.contentproviderdemo.buisnessLogic.viewmodel.ViewModelContact
import com.app.contentproviderdemo.databinding.ActivityContactsBinding
import com.app.contentproviderdemo.views.adapter.AdapterContacts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityContacts : AppCompatActivity() {

    private val READ_CONTACTS_PERMISSION_CODE = 101

    lateinit var mBinding: ActivityContactsBinding

    lateinit var mViewModel: ViewModelContact

    val mAdapter: AdapterContacts = AdapterContacts()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this@ActivityContacts, R.layout.activity_contacts)
        mViewModel = ViewModelProvider(this)[ViewModelContact::class.java]

        _init()
        initObserver()
        checkPermission()
    }

    private fun initObserver() {
        mViewModel.contactList.observe(this, {
            mAdapter.setList(it)
            Log.d("fdnb", "nvf " + it.size)


        })


    }

    private fun _init() {

        mBinding.recContacts.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mBinding.recContacts.adapter = mAdapter

    }


    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                READ_CONTACTS_PERMISSION_CODE
            )
        } else {
            mViewModel.fetchContacts()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_CONTACTS_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mViewModel.fetchContacts()
            }
        }
    }
}