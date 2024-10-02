package com.example.odes.core.di

import android.content.Context
import com.example.odes.core.data.source.local.datastore.JekyDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Provides
    @Singleton
    fun provideJekyDataStore(@ApplicationContext context: Context): JekyDataStore {
        return JekyDataStore(context)
    }
}