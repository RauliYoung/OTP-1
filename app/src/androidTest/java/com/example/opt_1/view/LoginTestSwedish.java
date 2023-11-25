package com.example.opt_1.view;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.opt_1.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginTestSwedish {

    @Rule
    public ActivityScenarioRule<LoginPage> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoginPage.class);

    @Test
    public void loginTestSwedish() {
        ViewInteraction appCompatSpinner = onView(
allOf(withId(R.id.languageSpinner),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
0),
isDisplayed()));
        appCompatSpinner.perform(click());
        
        DataInteraction appCompatCheckedTextView = onData(anything())
.inAdapterView(childAtPosition(
withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
0))
.atPosition(2);
        appCompatCheckedTextView.perform(click());
        
        ViewInteraction textInputEditText = onView(
allOf(withId(R.id.loginUserNameInput),
childAtPosition(
childAtPosition(
withClassName(is("com.google.android.material.textfield.TextInputLayout")),
0),
0),
isDisplayed()));
        textInputEditText.perform(replaceText("testi@testi.fi"), closeSoftKeyboard());
        
        ViewInteraction textInputEditText2 = onView(
allOf(withId(R.id.loginPasswordInput),
childAtPosition(
childAtPosition(
withClassName(is("com.google.android.material.textfield.TextInputLayout")),
0),
0),
isDisplayed()));
        textInputEditText2.perform(replaceText("testi123"), closeSoftKeyboard());
        
        ViewInteraction materialButton = onView(
allOf(withId(R.id.loginButton), withText("Logga in"),
childAtPosition(
allOf(withId(R.id.loginPage),
childAtPosition(
withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
2)),
4),
isDisplayed()));
        materialButton.perform(click());
        
        ViewInteraction materialButton2 = onView(
allOf(withId(R.id.logOut_Button), withText("Logga ut"),
childAtPosition(
allOf(withId(R.id.fragmentHome),
childAtPosition(
withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
0)),
7),
isDisplayed()));
        materialButton2.perform(click());
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
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
