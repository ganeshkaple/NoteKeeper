package com.test.notekeeper;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Ganesh Kaple
 * @since 05-01-2018
 */
public class DataManagerTest {
    @Test
    public void createNewNote() throws Exception {
        DataManager dataManager = DataManager.getInstance();
        final CourseInfo courseInfo = dataManager.getCourse("android_async");
        final String noteTitle = "test note title";
        final String noteText = "test note text";

        int index = dataManager.createNewNote();
        NoteInfo noteInfo = dataManager.getNotes().get(index);
        noteInfo.setCourse(courseInfo);
        noteInfo.setText(noteText);
        noteInfo.setTitle(noteTitle);
        NoteInfo compareInfo = dataManager.getNotes().get(index);
        //assertSame(noteInfo, compareInfo);
        assertEquals(courseInfo, compareInfo.getCourse());
        assertEquals(noteText, compareInfo.getText());
        assertEquals(noteTitle, compareInfo.getTitle());
    }

}