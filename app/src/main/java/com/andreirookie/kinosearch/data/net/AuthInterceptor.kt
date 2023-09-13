package com.andreirookie.kinosearch.data.net

import com.andreirookie.kinosearch.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val currentRequest = chain.request()
        val authRequest = currentRequest.newBuilder()
            .addHeader("X-API-KEY", "${BuildConfig.API_KEY}")
            .build()

        return chain.proceed(authRequest)
    }
}