package com.example.cityfootie_compose.datasource.remote

import com.example.cityfootie_compose.model.Player
import retrofit2.Response

interface PlayerRemoteDataSource {
    suspend fun login(email: String, password: String): Response<Player>
}