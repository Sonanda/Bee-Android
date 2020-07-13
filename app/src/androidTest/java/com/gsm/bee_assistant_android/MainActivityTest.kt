package com.gsm.bee_assistant_android

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.gsm.bee_assistant_android.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun userEmailTest() {
        //Espresso.onView(withId(R.id.user_email)).check(matches(withText("s18017@gsm.hs.kr")))
    }

    @Test
    fun userSchoolTest() {
        //Espresso.onView(withId(R.id.user_schoolName)).check(matches(withText("광주소프트웨어마이스터고등학교")))
    }
}