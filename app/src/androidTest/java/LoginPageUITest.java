import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

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
        onView(withId(R.id.loginUserNameInput)).perform(typeText("testi@testi.fi")).check(matches(isDisplayed()));
        onView(withId(R.id.loginPasswordInput)).perform(typeText("testi123"),closeSoftKeyboard()).check(matches(isDisplayed()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click()).check(matches(isDisplayed()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.oldPasswordInputField)).check(matches(isDisplayed()));
    }
}