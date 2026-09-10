package com.fasttrade.android.network

import retrofit2.http.*

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val name: String, val email: String, val password: String)
data class AuthResponse(val token: String, val name: String, val email: String, val role: String)
data class Product(val id: Long, val title: String, val description: String?,
                   val price: Double, val kind: String)

interface ApiService {
    @POST("/api/auth/login")
    suspend fun login(@Body req: LoginRequest): AuthResponse

    @POST("/api/auth/register")
    suspend fun register(@Body req: RegisterRequest): AuthResponse

    @GET("/api/products")
    suspend fun products(): List<Product>

    @GET("/api/products/{id}")
    suspend fun product(@Path("id") id: Long): Product
}
