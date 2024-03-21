package com.app.contentproviderdemo.buisnessLogic.di

import android.content.Context
import com.app.contentproviderdemo.MyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {

        return context
    }

    @Provides
    fun provideApplication(context: Context?): MyApplication {

        return context as MyApplication
    }
}