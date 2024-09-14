package com.andreirookie.kinosearch.di

import com.andreirookie.kinosearch.data.net.ApiService
import com.andreirookie.kinosearch.data.net.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {
    private const val BASE_URL = "https://kinopoiskapiunofficial.tech"

    @Provides
    fun provideAuthInterceptor():  AuthInterceptor =  AuthInterceptor()

    @Reusable
    @Provides
    fun provideOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .build()
    }

    @Reusable
    @Provides
    fun provideRxJava3CallAdapterFactory(): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, callAdapterFactory: RxJava3CallAdapterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}