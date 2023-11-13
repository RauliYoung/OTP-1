import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;

import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.opt_1.R;
import com.example.opt_1.view.LoginPage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginPageUITest {

    @Rule
    public ActivityScenarioRule<LoginPage> mActivityRule = new ActivityScenarioRule<>(LoginPage.class);
    @Test
    public void testLoginButton() {
        try {
            Thread.sleep(3000);
            onView(allOf(isDisplayed(), withId(R.id.loginUserNameInput), isClickable())).perform(typeText("testi@testi.fi")).check(matches(isDisplayed()));
            Thread.sleep(2000);
            onView(allOf(isDisplayed(), withId(R.id.loginPasswordInput), isClickable())).perform(typeText("testi123"),closeSoftKeyboard() ).check(matches(isDisplayed()));
            Thread.sleep(2000);
            closeSoftKeyboard();
            Thread.sleep(2000);
            onView(withId(R.id.loginButton)).perform(click()).check(matches(isDisplayed()));
            Log.d("BUTTON CLICKED", "Button has been clicked!");
            Thread.sleep(5000);
            onView(withId(R.id.oldPasswordInputField)).check(matches(isDisplayed()));
            Log.d("DONE", "All tests are done!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}