package com.mytaxi.android_demo;

import android.content.Context;

import android.support.test.InstrumentationRegistry;

import android.support.test.espresso.Espresso;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerActions;

import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;

import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExampleInstrumentedTest {

    private String username;
    private String password;
    private String searchKeyword;
    private String driverName;

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setUserData() {
        mActivity = mActivityRule.getActivity();
        username = "whiteelephant261";
        password = "video";
        driverName = "Sarah Friedrich";
        searchKeyword = "sa";
    }

    @Test
    public void test1_UseAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.mytaxi.android_demo", appContext.getPackageName());
    }


    @Test
    public void test2_UserLogin() throws Exception {


        //Enter Username
        Espresso.onView(ViewMatchers.withId(R.id.edt_username)).perform(typeText(username));
        //Enter Password
        Espresso.onView(ViewMatchers.withId(R.id.edt_password)).perform(typeText(password));
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(click());

        Thread.sleep(7000);
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).perform(DrawerActions.open());


        // Assertion checking that username displayed is correct
        Espresso.onView(ViewMatchers.withId(R.id.nav_username))
                .check(ViewAssertions.matches(ViewMatchers.withText(username)));
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).perform(DrawerActions.open());


    }


    @Test
    public void test3_UserSearchDriverAndClick() throws InterruptedException {


        Thread.sleep(7000);

        //Typing the Search Keyword
        Espresso.onView(ViewMatchers.withId(R.id.textSearch))
                .perform(typeText(searchKeyword));


        Thread.sleep(10000);

        //Checking whether the desired driver is displayed in the list
        onView(withText(driverName))
                .inRoot(RootMatchers.withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        //Selecting the desired driver
        onView(withText(driverName))
                .inRoot(RootMatchers.withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .perform(scrollTo()).perform(click());
        //checking the drivername
        onView(withId(R.id.textViewDriverName)).check(matches(withText("Sarah Friedrich")));
        onView(withId(R.id.fab)).perform(click());


    }


}
