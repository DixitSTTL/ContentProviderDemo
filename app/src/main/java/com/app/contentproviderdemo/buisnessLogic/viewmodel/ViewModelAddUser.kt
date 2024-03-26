package com.app.contentproviderdemo.buisnessLogic.viewmodel

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Observable
import android.net.Uri
import android.provider.CallLog
import android.provider.Telephony
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.app.contentproviderdemo.buisnessLogic.pojo.PojoCallLogs
import com.app.contentproviderdemo.buisnessLogic.pojo.PojoSMS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ViewModelAddUser @Inject constructor() : BaseViewModel() {

    var observerName = ObservableField<String>()
    var observerDepartment = ObservableField<String>()

    @SuppressLint("Range")
    fun addUser() {

        CoroutineScope(Dispatchers.IO).launch {
            val contentResolver = mApplication!!.contentResolver

            val PROVIDER_NAME = "com.app.providerdatabase.provider"
            val value = ContentValues()
            value.put("name",observerName.get())
            value.put("department",observerDepartment.get())
            // inserting into database through content URI
            val cursor = contentResolver.insert(
                Uri.parse("content://$PROVIDER_NAME/users"),
                value

            )

            if (cursor!=null){
                observerName.set("")
                observerDepartment.set("")
            }
        }

    }


}