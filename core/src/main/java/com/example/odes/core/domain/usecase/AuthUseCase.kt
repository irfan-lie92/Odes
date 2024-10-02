package com.example.odes.core.domain.usecase

import com.example.odes.core.data.source.Resource
import com.example.odes.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    suspend fun login(email: String, password: String) : Flow<Resource<User>>

    suspend fun register(user: User) : Flow<Resource<User>>
}