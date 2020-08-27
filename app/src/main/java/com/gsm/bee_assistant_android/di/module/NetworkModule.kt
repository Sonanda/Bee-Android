package com.gsm.bee_assistant_android.di.module

import com.gsm.bee_assistant_android.BuildConfig
import com.gsm.bee_assistant_android.retrofit.network.ClassroomApi
import com.gsm.bee_assistant_android.retrofit.network.SchoolApi
import com.gsm.bee_assistant_android.retrofit.network.SchoolInfoApi
import com.gsm.bee_assistant_android.retrofit.network.UserApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(provideHttpLoggingInterceptor())
        .build()

    @Provides
    @Singleton
    fun provideSchoolNameRetrofit() : SchoolInfoApi {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(provideOkHttpClient())
            .baseUrl(BuildConfig.SCHOOL_INFO_URL)
            .build()

        return retrofit.create(SchoolInfoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSchoolRetrofit() : SchoolApi {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(provideOkHttpClient())
            .baseUrl(BuildConfig.BASE_URL)
            .build()

        return retrofit.create(SchoolApi::class.java)
    }

    @Provides
    @Singleton
    fun provideClassroomRetrofit() : ClassroomApi {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(provideOkHttpClient())
            .baseUrl(BuildConfig.BASE_URL)
            .build()

        return retrofit.create(ClassroomApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRetrofit(): UserApi {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(provideOkHttpClient())
            .baseUrl(BuildConfig.BASE_URL)
            .build()

        return retrofit.create(UserApi::class.java)
    }
}