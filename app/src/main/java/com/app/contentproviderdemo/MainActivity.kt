package com.app.contentproviderdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.climate_trace.businesslogic.interfaces.GeneralClickListeners
import com.app.contentproviderdemo.databinding.ActivityMainBinding
import com.app.contentproviderdemo.views.activity.ActivityAddUser
import com.app.contentproviderdemo.views.activity.ActivityCallLogs
import com.app.contentproviderdemo.views.activity.ActivityContacts
import com.app.contentproviderdemo.views.activity.ActivityImages
import com.app.contentproviderdemo.views.activity.ActivitySMS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding
    val generalClickListeners = object : GeneralClickListeners {
        override fun onClick(view: View) {
            when (view.id) {

                R.id.contactBtn -> {
                    startActivity(Intent(this@MainActivity, ActivityContacts::class.java))
                }

                R.id.callLogBtn -> {
                    startActivity(Intent(this@MainActivity, ActivityCallLogs::class.java))
                }

                R.id.smsBtn -> {
                    startActivity(Intent(this@MainActivity, ActivitySMS::class.java))
                }

                R.id.addUserBtn -> {
                    startActivity(Intent(this@MainActivity, ActivityAddUser::class.java))
                }

                R.id.imagesBtn -> {
                    startActivity(Intent(this@MainActivity, ActivityImages::class.java))
                }
            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.generalClickListeners = generalClickListeners

    }

}