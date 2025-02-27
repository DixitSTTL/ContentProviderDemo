package com.app.contentproviderdemo.buisnessLogic.viewmodel

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import com.app.contentproviderdemo.buisnessLogic.pojo.PojoContacts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelContact @Inject constructor() : BaseViewModel() {

    var contactList = MutableLiveData<ArrayList<PojoContacts>>()

    @SuppressLint("Range")
    fun fetchContacts() {

        CoroutineScope(Dispatchers.IO).launch {
            val list = ArrayList<PojoContacts>()
            val contentResolver: ContentResolver = mApplication!!.contentResolver
            val uri = Uri.parse("content://com.android.contacts/data/phones")

            val projection = arrayOf(
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            )
            val cursor = contentResolver.query(
                uri,
                projection,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY
            )

            cursor?.let {
                if (it.count > 0) {
                    val name_column =
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    val num_column =
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

                    while (it.moveToNext()) {
                        val contactName = it.getString(name_column)
                        val contactNumber = it.getString(num_column)
                        list.add(PojoContacts(contactName, contactNumber))

                    }
                }
                withContext(Dispatchers.Main) {
                    contactList.value = list
                }

                it.close()
            }
            observeRefreshing.postValue(false)

        }
    }


}