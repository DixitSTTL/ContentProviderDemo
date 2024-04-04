package com.app.contentproviderdemo.buisnessLogic.viewmodel

import android.content.ContentResolver
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import com.app.contentproviderdemo.buisnessLogic.pojo.PojoImages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelImages @Inject constructor() : BaseViewModel() {

    var imageList = MutableLiveData<ArrayList<PojoImages>>()

    fun fetchImages() {

        CoroutineScope(Dispatchers.IO).launch {
            val list = ArrayList<PojoImages>()
            val contentResolver: ContentResolver = mApplication!!.contentResolver
            val projection = arrayOf(
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.RESOLUTION,
                MediaStore.Images.Media._ID
            )
            val orderBy = MediaStore.Images.Media.DATE_MODIFIED + " DESC";


            val cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                orderBy
            )

            cursor?.let {

                if (it.count > 0) {

                    val data_column =
                        cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                    val id_column =
                        cursor.getColumnIndex(MediaStore.Images.Media._ID)
                    val height_column =
                        cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT)
                    val width_column =
                        cursor.getColumnIndex(MediaStore.Images.Media.WIDTH)
                    val size_column =
                        cursor.getColumnIndex(MediaStore.Images.Media.SIZE)

                    while (it.moveToNext()) {
                        val contactData = it.getString(data_column)
                        val contactId = it.getString(id_column)
                        val height = it.getString(height_column)
                        val width = it.getString(width_column)
                        val size = it.getLong(size_column)
                        list.add(
                            PojoImages(
                                uri = contactData,
                                id = contactId,
                                resolution = "$height X $width",
                                size = size
                            )
                        )

                    }
                    withContext(Dispatchers.Main) {
                        imageList.value = list
                    }

                }

                it.close()
            }
        }


    }


}