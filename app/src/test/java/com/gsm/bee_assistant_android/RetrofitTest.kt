package com.gsm.bee_assistant_android

import com.gsm.bee_assistant_android.di.app.MyApplication
import com.gsm.bee_assistant_android.retrofit.domain.SchoolInfo
import com.gsm.bee_assistant_android.retrofit.network.SchoolInfoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
            .baseUrl(MyApplication.School_Info_Url)
            .build()

        return retrofit.create(SchoolInfoApi::class.java)
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getSchoolInfoTest() {

        val call = provideSchoolNameRetrofit().getSchoolInfoTest(apiKey = MyApplication.Api_Key, schoolKind = "elem_list", region =  "100260", schoolType = "")

        val res: Response<SchoolInfo> = call.execute()

        Assert.assertEquals(res.body()!!.dataSearch!!.content!![0].schoolName.toString(), "강솔초등학교")
    }
}