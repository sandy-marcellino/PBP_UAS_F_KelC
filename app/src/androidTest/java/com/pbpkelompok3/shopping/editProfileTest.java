package com.pbpkelompok3.shopping;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class editProfileTest {

    @Rule
    public ActivityTestRule<SplashScreen> mActivityTestRule = new ActivityTestRule<>(SplashScreen.class);

    public static ViewAction waitFor(long delay) {
        return new ViewAction() {
            @Override public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override public String getDescription() {
                return "wait for " + delay + "milliseconds";
            }

            @Override public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(delay);
            }
        };
    }
    @Test
    public void editProfileTest() {
        onView(isRoot()).perform(waitFor(5000));
        ViewInteraction textInputEditText15 = onView(
                allOf(withId(R.id.inputEmailSignIn),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tvLayoutEmail),
                                        0),
                                0)));
        textInputEditText15.perform(scrollTo(), replaceText("dewasamudra17@gmail.com"), closeSoftKeyboard());

        ViewInteraction textInputEditText14 = onView(
                allOf(withId(R.id.inputPasswordSignin),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0)));
        textInputEditText14.perform(scrollTo(), replaceText("kertas123"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.login), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());
        onView(isRoot()).perform(waitFor(5000));

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appBarLayout),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        5),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        onView(isRoot()).perform(waitFor(5000));

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.btn_editProfile), withText("Change Profile"),
                        childAtPosition(
                                allOf(withId(R.id.btn_editProfile_layout),
                                        childAtPosition(
                                                withId(R.id.fragment_profile),
                                                5)),
                                0)));
        materialButton8.perform(scrollTo(), click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.input_firstEdit), withText("lawrenxius"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.first_name_layout),
                                        0),
                                0)));
        textInputEditText.perform(scrollTo(), replaceText(""));

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.input_firstEdit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.first_name_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btn_updateEdit), withText("Update"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.edit_profile_fragment),
                                        5),
                                1)));
        materialButton2.perform(scrollTo(), click());
        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.input_firstEdit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.first_name_layout),
                                        0),
                                0)));
        textInputEditText3.perform(scrollTo(), replaceText("lawrenxius"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.input_lastEdit), withText("benny"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.last_name_layout),
                                        0),
                                0)));
        textInputEditText4.perform(scrollTo(), replaceText(""));

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.input_lastEdit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.last_name_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btn_updateEdit), withText("Update"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.edit_profile_fragment),
                                        5),
                                1)));
        materialButton3.perform(scrollTo(), click());
        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.input_lastEdit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.last_name_layout),
                                        0),
                                0)));
        textInputEditText6.perform(scrollTo(), replaceText("benny"), closeSoftKeyboard());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.input_phoneEdit), withText("0821593595333"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_phone_layout),
                                        0),
                                0)));
        textInputEditText7.perform(scrollTo(), replaceText(""));

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.input_phoneEdit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_phone_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText8.perform(closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btn_updateEdit), withText("Update"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.edit_profile_fragment),
                                        5),
                                1)));
        materialButton4.perform(scrollTo(), click());
        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.input_phoneEdit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_phone_layout),
                                        0),
                                0)));
        textInputEditText9.perform(scrollTo(), replaceText("0821"), closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btn_updateEdit), withText("Update"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.edit_profile_fragment),
                                        5),
                                1)));
        materialButton5.perform(scrollTo(), click());
        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.input_phoneEdit), withText("0821"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_phone_layout),
                                        0),
                                0)));
        textInputEditText10.perform(scrollTo(), replaceText("08215935953333"));

        ViewInteraction textInputEditText11 = onView(
                allOf(withId(R.id.input_phoneEdit), withText("08215935953333"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_phone_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText11.perform(closeSoftKeyboard());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.btn_updateEdit), withText("Update"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.edit_profile_fragment),
                                        5),
                                1)));
        materialButton6.perform(scrollTo(), click());
        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction textInputEditText12 = onView(
                allOf(withId(R.id.input_phoneEdit), withText("08215935953333"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_phone_layout),
                                        0),
                                0)));
        textInputEditText12.perform(scrollTo(), replaceText("0821593595333"));

        ViewInteraction textInputEditText13 = onView(
                allOf(withId(R.id.input_phoneEdit), withText("0821593595333"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_phone_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText13.perform(closeSoftKeyboard());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.btn_updateEdit), withText("Update"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.edit_profile_fragment),
                                        5),
                                1)));
        materialButton7.perform(scrollTo(), click());
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
