package com.app.contentproviderdemo.buisnessLogic.viewmodel

import androidx.lifecycle.ViewModel
import com.app.contentproviderdemo.MyApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    @JvmField
    @Inject
    var mApplication: MyApplication? = null


}