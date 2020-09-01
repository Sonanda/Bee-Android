package com.gsm.bee_assistant_android

import com.gsm.bee_assistant_android.retrofit.domain.classroom.ClassroomTokenUpdate
import com.gsm.bee_assistant_android.retrofit.domain.school.SchoolInfo
import com.gsm.bee_assistant_android.retrofit.domain.user.UserInfo
import com.gsm.bee_assistant_android.retrofit.domain.user.UserToken
import com.gsm.bee_assistant_android.retrofit.network.SchoolInfoApi
import com.gsm.bee_assistant_android.retrofit.network.UserApi
import org.junit.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitTest {

    // Null Exception 오류
    @Mock
    lateinit var schoolNameRetrofit: SchoolInfoApi

    private fun provideSchoolNameRetrofit() : SchoolInfoApi {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.SCHOOL_INFO_URL)
            .build()

        return retrofit.create(SchoolInfoApi::class.java)
    }

    private fun provideUserRetrofit(): UserApi {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()

        return retrofit.create(UserApi::class.java)
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getSchoolInfoTest() {
//
//        val call = provideSchoolNameRetrofit().getSchoolInfoTest(apiKey = BuildConfig.SCHOOL_API_KEY, schoolKind = "elem_list", region =  "100260", schoolType = "")
//
//        val res: Response<SchoolInfo> = call.execute()
//
//        Assert.assertEquals(res.body()!!.dataSearch!!.content!![0].schoolName.toString(), "강솔초등학교")
    }

    @Test
    fun getUserToken() {

//        val call = provideUserRetrofit().getUserTokenTest("itggood2420@gmail.com")
//
//        val res: Response<UserToken> = call.execute()
//
//        println(res.body()!!.token)
    }

    @Test
    fun updateClassroomToken() {

//        val call = provideUserRetrofit().updateClassroomTokenTest(ClassroomTokenUpdate(
//            email = "s18017@gsm.hs.kr",
//            access_token = "여기에 access_token",
//            refresh_token = "여기에 refresh_token"
//        ))
//
//        val res: Response<UserToken> = call.execute()
//
//        println(res.body()?.token)
    }

    @Test
    fun getUserInfo() {

//        val call = provideUserRetrofit().getUserInfoTest("여기에 userToken")
//
//        val res: Response<UserInfo> = call.execute()
//
//        println(res.body()?.refresh_token)
    }
}