package com.test.notekeeper;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Ganesh Kaple
 * @since 06-01-2018
 */
@RunWith(JUnit4.class)
public class NoteCreationTest {
    @Rule
    public ActivityTestRule<NoteListActivity> noteListActivityActivityTestRule = new ActivityTestRule<>(NoteListActivity.class);

    @Test
    public void createNewNote() {

        final String noteTitle = "Test Title";
        final String noteText = "Test Text dummy";

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.text_note_title)).perform(typeText(noteTitle));
        onView(withId(R.id.text_note_text)).perform(typeText(noteText));

    }
}