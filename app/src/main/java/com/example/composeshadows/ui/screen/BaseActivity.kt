package com.example.composeshadows.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.composeshadows.entity.ActivityWithShadowButtonParams
import com.example.composeshadows.ui.util.ActivityWithShadowButtonHelper

open class BaseActivity : ComponentActivity() {

    protected var params: ActivityWithShadowButtonParams = ActivityWithShadowButtonParams()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        params = ActivityWithShadowButtonHelper.unpack(this)
    }
}