package com.app.contentproviderdemo.buisnessLogic.viewmodel

import android.annotation.SuppressLint
import android.net.Uri
import android.provider.CallLog
import android.provider.Telephony
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.contentproviderdemo.buisnessLogic.pojo.PojoSMS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ViewModelSMS @Inject constructor() : BaseViewModel() {

    var smsList = MutableLiveData<ArrayList<PojoSMS>>()


    @SuppressLint("Range")
    fun fetchAllSMS() {

        CoroutineScope(Dispatchers.IO).launch {
            val list = ArrayList<PojoSMS>()
            val contentResolver = mApplication!!.contentResolver
            val uri = Uri.parse("content://sms")
            val selection: String = CallLog.Calls.DURATION + " > ?"
            val sortOrder: String = CallLog.Calls.DURATION + " ASC"
            val projection = arrayOf(
                Telephony.Sms.CREATOR,
                Telephony.Sms.ADDRESS,
                Telephony.Sms.DATE,
                Telephony.Sms.BODY,
                Telephony.Sms.TYPE
            )
            val cursor = contentResolver.query(
                uri, projection, null, null, null
            )
            Log.d("ggbgbg", "start")

            cursor?.let {
                if (it.count > 0) {

                    val address_column = cursor.getColumnIndex(Telephony.Sms.ADDRESS)
                    val person_column = cursor.getColumnIndex(Telephony.Sms.CREATOR)
                    val body_column = cursor.getColumnIndex(Telephony.Sms.BODY)
                    val type_column = cursor.getColumnIndex(Telephony.Sms.TYPE)
                    val date_column = cursor.getColumnIndex(Telephony.Sms.DATE)

                    while (it.moveToNext()) {
                        val address: String = it.getString(address_column)
//                        val person: String = it.getString(person_column)
                        val body: String = it.getString(body_column)
                        val type: Int = it.getInt(type_column)
                        val date: Long = it.getLong(date_column)
                        val smsType = if (type == 1) {
                            "Received"
                        } else if (type == 2) {
                            "Sent"
                        } else {
                            ""
                        }



                        list.add(PojoSMS(smsType, address, date, body))

                    }
                    withContext(Dispatchers.Main) {
                        smsList.value = list
                    }

                }
                it.close()
            }
        }

    }


}