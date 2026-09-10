package com.fasttrade.android.di

import com.fasttrade.android.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // ponytail: URL como constante; externalizar em BuildConfig se necessário
    private const val BASE_URL = "http://10.0.2.2:8080"

    @Provides @Singleton
    fun okHttp(tokenManager: TokenManager): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val req = tokenManager.token?.let { tok ->
                    chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $tok")
                        .build()
                } ?: chain.request()
                chain.proceed(req)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()

    @Provides @Singleton
    fun retrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides @Singleton
    fun apiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}
