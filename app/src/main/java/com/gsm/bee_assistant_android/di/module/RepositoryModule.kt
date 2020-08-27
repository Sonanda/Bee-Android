package com.gsm.bee_assistant_android.di.module

import com.gsm.bee_assistant_android.retrofit.network.ClassroomApi
import com.gsm.bee_assistant_android.retrofit.network.SchoolApi
import com.gsm.bee_assistant_android.retrofit.network.SchoolInfoApi
import com.gsm.bee_assistant_android.retrofit.network.UserApi
import com.gsm.bee_assistant_android.retrofit.repository.ClassroomRepository
import com.gsm.bee_assistant_android.retrofit.repository.SchoolRepository
import com.gsm.bee_assistant_android.retrofit.repository.UserRepository
import com.gsm.bee_assistant_android.utils.NetworkUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideClassroomRepository(classroomApi: ClassroomApi, networkStatus: NetworkUtil) = ClassroomRepository(classroomApi, networkStatus)

    @Provides
    @Singleton
    fun provideUserRepository(userApi: UserApi, networkStatus: NetworkUtil) = UserRepository(userApi, networkStatus)

    @Provides
    @Singleton
    fun provideSchoolRepository(schoolInfoApi: SchoolInfoApi, schoolApi: SchoolApi, networkStatus: NetworkUtil) = SchoolRepository(schoolInfoApi, schoolApi, networkStatus)
}