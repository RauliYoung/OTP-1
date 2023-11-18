package com.example.opt_1.view;


import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import com.example.opt_1.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginToActivityPage{
    private String test_user;
    private String test_pass;
    private final int timeout = 5000;

    @Before
    public void init() throws PackageManager.NameNotFoundException {
        ApplicationInfo ai = getApplicationContext().getPackageManager()
                .getApplicationInfo(getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);

        test_user = (String) ai.metaData.get("test_username");
        test_pass = (String) ai.metaData.get("test_password");
    }
    @Rule
    public ActivityScenarioRule<LoginPage> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoginPage.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Test
    public void loginToActivityPage() throws InterruptedException {
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.loginUserNameInput),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText(test_user), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.loginPasswordInput),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText(test_pass), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.loginPage),
                                        childAtPosition(
                                                withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                                2)),
                                4),
                        isDisplayed()));
        materialButton.perform(click());
        Thread.sleep(timeout);

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.activity), withContentDescription("Activity"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());
        Thread.sleep(timeout);

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.activity_StartActivityButton), withText("Start Activity"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.flFragment),
                                        0),
                                4),
                        isDisplayed()));
        materialButton2.perform(click());
        Thread.sleep(timeout);

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.activity_StopActivityButton), withText("Stop Activity"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.flFragment),
                                        0),
                                5),
                        isDisplayed()));
        materialButton3.perform(click());
        Thread.sleep(timeout);
        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.home), withContentDescription("Home"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());
        Thread.sleep(timeout);
        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.logOut_Button), withText("LogOut"),
                        childAtPosition(
                                allOf(withId(R.id.fragmentHome),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                7),
                        isDisplayed()));
        materialButton4.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
