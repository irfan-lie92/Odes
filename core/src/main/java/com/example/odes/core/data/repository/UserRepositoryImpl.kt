package com.example.odes.core.data.repository

import com.example.odes.core.data.source.Resource
import com.example.odes.core.data.source.local.datastore.JekyDataStore
import com.example.odes.core.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dataStore: JekyDataStore) : UserRepository {
    override suspend fun isUserLoggedIn(): Flow<Resource<Boolean>> {
        return flow<Resource<Boolean>> {
            dataStore.email.collect {
                emit(Resource.Success(it.isNotEmpty()))
            }
        }.catch { e ->
            emit(Resource.Error(-1, e.message ?: "Something wrong."))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun storeEmail(email: String) {
        dataStore.storeData(JekyDataStore.EMAIL, email)
    }

    override suspend fun logout() {
        dataStore.clear()
    }
}