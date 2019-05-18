package com.example.eazibaking;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        mActivityRule.getActivity();
        idlingResource = MainActivity.getIdleResource();
        Espresso.registerIdlingResources(idlingResource);
    }


    @Test
    public void enSureIngredientFlowWorks() {

        //card showed
        onView(withId(R.id.rv_recipe)).check(matches(isDisplayed()));
        //click on card
        onView(withId(R.id.rv_recipe)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //recipe detail display
        onView(withId(R.id.rv_recipe_detail)).check(matches(isDisplayed()));
        //click on one step
        onView(withId(R.id.rv_recipe_detail)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //ingredient detail display
        onView(withId(R.id.tv_quantity)).check(matches(isDisplayed()));
    }

    @Test
    public void enSureStepFlowWorks() {
        //card showed
        onView(withId(R.id.rv_recipe)).check(matches(isDisplayed()));
        //click on card
        onView(withId(R.id.rv_recipe)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //recipe detail display
        onView(withId(R.id.rv_recipe_detail)).check(matches(isDisplayed()));
        //click on one step
        onView(withId(R.id.rv_recipe_detail)).perform(RecyclerViewActions.scrollToPosition(10));
        onView(withId(R.id.rv_recipe_detail)).perform(RecyclerViewActions.actionOnItemAtPosition(10, click()));
        //ingredient detail display
        onView(withId(R.id.tv_short_description)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }
}
