package com.app.contentproviderdemo.buisnessLogic.viewmodel

import android.annotation.SuppressLint
import android.provider.CallLog
import androidx.lifecycle.MutableLiveData
import com.app.contentproviderdemo.buisnessLogic.pojo.PojoCallLogs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ViewModelCallLogs @Inject constructor() : BaseViewModel() {

    var callLogList = MutableLiveData<ArrayList<PojoCallLogs>>()

    @SuppressLint("Range")
    fun fetchCallLogs() {

        CoroutineScope(Dispatchers.IO).launch {
            val list = ArrayList<PojoCallLogs>()
            val contentResolver = mApplication!!.contentResolver
            val projection = arrayOf(
                CallLog.Calls.NUMBER,
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE
            )
            val cursor = contentResolver.query(
                CallLog.Calls.CONTENT_URI, projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER
            )

            cursor?.let {
                if (it.count > 0) {
                    val num_column=cursor.getColumnIndex(CallLog.Calls.NUMBER)
                    val name_column=cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)
                    val call_date_column=cursor.getColumnIndex(CallLog.Calls.DATE)
                    val duration_column=cursor.getColumnIndex(CallLog.Calls.DURATION)
                    val type_column=cursor.getColumnIndex(CallLog.Calls.TYPE)

                    while (it.moveToNext()) {

                        val number = cursor.getString(num_column)
                        val name = cursor.getString(name_column)
                        val call_date = cursor.getLong(call_date_column)
                        val duration = cursor.getLong(duration_column)
                        val type = cursor.getInt(type_column)

                        val call_type = when (type) {
                            1 -> { "Incoming" }
                            2 -> { "Outgoing" }
                            else -> { "MissCalled" }
                        }

                        list.add(
                            PojoCallLogs(name, number, call_date, duration, call_type)
                        )

                    }
                    withContext(Dispatchers.Main) {

                        callLogList.value = list
                    }

                }
                it.close()
            }
        }

    }


}