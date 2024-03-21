package com.app.contentproviderdemo.buisnessLogic.viewmodel

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
            var list = ArrayList<PojoContacts>()
            val contentResolver: ContentResolver = mApplication!!.contentResolver
            val cursor = contentResolver.query(
                Uri.parse("content://com.android.contacts/contacts"), null, null, null, null
            )

            cursor?.let {
                if (it.count > 0) {
                    while (it.moveToNext()) {
                        val contactName =
                            it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        val contactId =
                            it.getString(it.getColumnIndex(ContactsContract.Contacts._ID))

                        // Fetching phone numbers
                        val phoneCursor = contentResolver.query(
                            Uri.parse("content://com.android.contacts/data/phones"),
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(contactId),
                            null
                        )
                        phoneCursor?.let { phoneCursor ->
                            if (phoneCursor.moveToFirst()) {
                                val phoneNumber = phoneCursor.getString(
                                    phoneCursor.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER
                                    )
                                )
                                list.add(PojoContacts(contactName, phoneNumber))

                            }
                            phoneCursor.close()
                        }
                    }
                    withContext(Dispatchers.Main){

                        contactList.value=list
                    }
                }
                it.close()
            }
        }

    }


}