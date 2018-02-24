package com.test.notekeeper;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ganesh on 2/24/18.
 */

public class NoteKeeperProviderContract {
    public static final String AUTHORITY = "com.jwhh.notekeeper.provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    private NoteKeeperProviderContract() {
    }

    protected interface CourseIdColumns {
        String COLUMN_COURSE_ID = "course_id";

    }

    protected interface CoursesColumns {

        String COLUMN_COURSE_TITLE = "course_title";
    }

    protected interface NotesColumns {

        String COLUMN_NOTE_TITLE = "note_title";
        String COLUMN_NOTE_TEXT = "note_text";
    }

    /**
     * BaseColumns provides column name for _id column
     */
    public static final class Courses implements BaseColumns, CoursesColumns, CourseIdColumns {
        public static final String PATH = "courses";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, PATH);

    }

    public static final class Notes implements BaseColumns, NotesColumns, CourseIdColumns {
        public static final String PATH = "notes";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, PATH);

    }
}
