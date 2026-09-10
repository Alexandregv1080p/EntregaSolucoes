package com.fasttrade.android.di

import javax.inject.Inject
import javax.inject.Singleton

/** Token em memória — sem runBlocking no interceptor. */
@Singleton
class TokenManager @Inject constructor() {
    var token: String? = null
        private set

    fun save(token: String) { this.token = token }
    fun clear() { this.token = null }
}
