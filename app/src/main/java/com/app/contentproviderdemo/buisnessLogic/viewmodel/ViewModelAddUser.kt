package com.app.contentproviderdemo.buisnessLogic.viewmodel

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.app.contentproviderdemo.buisnessLogic.pojo.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ViewModelAddUser @Inject constructor() : BaseViewModel() {

    var observerName = ObservableField<String>("")
    var observerDepartment = ObservableField<String>("")

    var userList = MutableLiveData<ArrayList<UserModel>>()

    @SuppressLint("Range")
    fun addUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val contentResolver = mApplication!!.contentResolver
            val PROVIDER_NAME = "com.app.providerdatabase.provider"
            val value = ContentValues()
            value.put("name", observerName.get())
            value.put("department", observerDepartment.get())
            // inserting into database through content URI
            try {
                val resultUri = contentResolver.insert(
                    Uri.parse("content://$PROVIDER_NAME/users"),
                    value
                )

                if (resultUri != null) {
                    observerName.set("")
                    observerDepartment.set("")
                }
            } catch (_: Exception) {
            }

        }
    }

    fun fetchUsers() {

        CoroutineScope(Dispatchers.IO).launch {
            val list = ArrayList<UserModel>()
            val contentResolver = mApplication!!.contentResolver
            val authority = "com.app.providerdatabase.provider"
            val uri = Uri.parse("content://${authority}/users")
            val cursor = contentResolver.query(
                uri, null, null, null, null
            )

            cursor?.let {
                if (it.count > 0) {
                    val userIdColumn = cursor.getColumnIndex("id")
                    val userNameColumn = cursor.getColumnIndex("name")
                    val userDepColumn = cursor.getColumnIndex("department")
                    while (it.moveToNext()) {
                        val id = cursor.getInt(userIdColumn)
                        val name = cursor.getString(userNameColumn)
                        val department = cursor.getString(userDepColumn)

                        list.add(
                            UserModel(id, name, department)
                        )

                    }

                }
                withContext(Dispatchers.Main) {

                    userList.value = list
                }
                it.close()
            }
            observeRefreshing.postValue(false)
        }

    }

    fun delete(model: UserModel) {
        CoroutineScope(Dispatchers.IO).launch {
            val contentResolver = mApplication!!.contentResolver
            val authority = "com.app.providerdatabase.provider"
            val uri = Uri.parse("content://${authority}/users")
            val where: String = "id" + "=?"
            val selectionArgs = arrayOf(model.id.toString())
            contentResolver.delete(
                uri, where, selectionArgs
            )
        }
    }


}