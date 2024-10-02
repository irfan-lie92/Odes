package com.example.odes.core.domain.repository

import com.example.odes.core.data.source.Resource
import com.example.odes.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(email: String, password: String): Flow<Resource<User>>
    suspend fun register(user: User): Flow<Resource<User>>
}