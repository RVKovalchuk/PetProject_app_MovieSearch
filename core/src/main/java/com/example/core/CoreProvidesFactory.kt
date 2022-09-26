package com.example.core

import android.content.Context
import com.example.core_api.CoreComponentInterface
import com.example.core_impl.DaggerCoreComponentImpl

object CoreProvidesFactory {
    fun provideCoreComponent(context: Context): CoreComponentInterface {
        return DaggerCoreComponentImpl.factory().create(context = context)
    }
}