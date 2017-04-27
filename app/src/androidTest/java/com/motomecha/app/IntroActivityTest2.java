package com.motomecha.app;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class IntroActivityTest2 {

    @Rule
    public ActivityTestRule<IntroActivity> mActivityTestRule = new ActivityTestRule<>(IntroActivity.class);

    @Test
    public void introActivityTest2() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_next), withText("Next"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction viewPager = onView(
                allOf(withId(R.id.view_pager), isDisplayed()));
        viewPager.perform(swipeLeft());

        ViewInteraction viewPager2 = onView(
                allOf(withId(R.id.view_pager), isDisplayed()));
        viewPager2.perform(swipeLeft());

        ViewInteraction viewPager3 = onView(
                allOf(withId(R.id.view_pager), isDisplayed()));
        viewPager3.perform(swipeLeft());

        ViewInteraction viewPager4 = onView(
                allOf(withId(R.id.view_pager), isDisplayed()));
        viewPager4.perform(swipeLeft());

        ViewInteraction viewPager5 = onView(
                allOf(withId(R.id.view_pager), isDisplayed()));
        viewPager5.perform(swipeRight());

        ViewInteraction viewPager6 = onView(
                allOf(withId(R.id.view_pager), isDisplayed()));
        viewPager6.perform(swipeLeft());

        ViewInteraction viewPager7 = onView(
                allOf(withId(R.id.view_pager), isDisplayed()));
        viewPager7.perform(swipeLeft());

        ViewInteraction viewPager8 = onView(
                allOf(withId(R.id.view_pager), isDisplayed()));
        viewPager8.perform(swipeLeft());

        ViewInteraction viewPager9 = onView(
                allOf(withId(R.id.view_pager), isDisplayed()));
        viewPager9.perform(swipeLeft());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_next), withText(" Get Started ! "), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.mobile_text),
                        withParent(withId(R.id.relativeLayout)),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.mobile_text),
                        withParent(withId(R.id.relativeLayout)),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("9688160712"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.mobile_text), withText("9688160712"),
                        withParent(withId(R.id.relativeLayout)),
                        isDisplayed()));
        appCompatEditText3.perform(pressImeActionButton());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.login_continue),
                        withParent(withId(R.id.relativeLayout)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

    }

}
