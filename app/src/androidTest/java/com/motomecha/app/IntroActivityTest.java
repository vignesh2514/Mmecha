package com.motomecha.app;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class IntroActivityTest {

    @Rule
    public ActivityTestRule<IntroActivity> mActivityTestRule = new ActivityTestRule<>(IntroActivity.class);

    @Test
    public void introActivityTest() {
    }

}
