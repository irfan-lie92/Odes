package com.example.odes.core.di

import com.example.odes.core.data.repository.AuthRepositoryImpl
import com.example.odes.core.data.repository.PlacesRepositoryImpl
import com.example.odes.core.data.repository.UserRepositoryImpl
import com.example.odes.core.domain.repository.AuthRepository
import com.example.odes.core.domain.repository.PlacesRepository
import com.example.odes.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(authRepository: AuthRepositoryImpl) : AuthRepository

    @Binds
    abstract fun providePlacesRepository(placesRepository: PlacesRepositoryImpl) : PlacesRepository

    @Binds
    abstract fun provideUserRepository(userRepository: UserRepositoryImpl) : UserRepository
}