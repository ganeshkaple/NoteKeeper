package com.test.notekeeper;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Ganesh Kaple
 * @since 05-01-2018
 */
public class DataManagerTest {
    static DataManager dataManager;

    @BeforeClass
    public static void classSetUp() throws Exception {
        dataManager = DataManager.getInstance();
    }

    @Before
    public void setUp() throws Exception {
        dataManager.getNotes().clear();
        dataManager.initializeExampleNotes();
    }
    @Test
    public void createNewNote() throws Exception {
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

    @Test
    public void findSimilarNotes() throws Exception {
        final CourseInfo course = dataManager.getCourse("android_async");
        final String noteTitle = "test note title";
        final String noteText = "test note text";

        int index1 = dataManager.createNewNote();
        NoteInfo noteInfo1 = dataManager.getNotes().get(index1);
        noteInfo1.setCourse(course);
        noteInfo1.setText(noteText);
        noteInfo1.setTitle(noteTitle);

        /*int index2 = dataManager.createNewNote();
        NoteInfo noteInfo2 = dataManager.getNotes().get(index2);
        noteInfo2.setCourse(course);
        noteInfo2.setText(noteText);
        noteInfo2.setTitle(noteTitle);
*/
        int foundIndex1 = dataManager.findNote(noteInfo1);
        assertEquals(index1, foundIndex1);


        /*int foundIndex2 = dataManager.findNote(noteInfo2);
        assertEquals(index2,foundIndex2);
*/


    }
}