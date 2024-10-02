package com.example.odes.core.domain.repository

import com.example.odes.core.data.source.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun isUserLoggedIn(): Flow<Resource<Boolean>>
    suspend fun storeEmail(email: String)
    suspend fun logout()
}