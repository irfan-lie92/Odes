package com.example.odes.core.di

import android.content.Context
import androidx.room.Room
import com.example.odes.core.data.source.local.room.JekyDatabase
import com.example.odes.core.data.source.local.room.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : JekyDatabase = Room.databaseBuilder(
        context,
        JekyDatabase::class.java,
        "jeky.db"
    ).allowMainThreadQueries().build()

    @Provides
    fun provideJekyDao(db: JekyDatabase): UserDao = db.userDao()

}