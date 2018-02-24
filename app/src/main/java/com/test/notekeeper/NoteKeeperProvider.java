package com.test.notekeeper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.test.notekeeper.NoteKeeperProviderContract.AUTHORITY;
import static com.test.notekeeper.NoteKeeperProviderContract.Notes.PATH_EXPANDED;

public class NoteKeeperProvider extends ContentProvider {

    private static final int COURSES = 0;
    private static final int NOTES = 1;
    private static final int NOTES_EXPANDED = 2;
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, NoteKeeperProviderContract.Courses.PATH, COURSES);

        uriMatcher.addURI(AUTHORITY, NoteKeeperProviderContract.Notes.PATH, NOTES);
        uriMatcher.addURI(AUTHORITY, PATH_EXPANDED, NOTES_EXPANDED);
    }

    private NoteKeeperOpenHelper databaseOpenHelper;

    public NoteKeeperProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        databaseOpenHelper = new NoteKeeperOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int uriMatch = uriMatcher.match(uri);
        Cursor cursor = null;
        SQLiteDatabase database = databaseOpenHelper.getReadableDatabase();
        switch (uriMatch) {
            case COURSES:
                cursor = database.query(NoteKeeperDatabaseContract.CourseInfoEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case NOTES:
                cursor = database.query(NoteKeeperDatabaseContract.NoteInfoEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case NOTES_EXPANDED:
                cursor = notesExpandedQuery(database, projection, selection, selectionArgs, sortOrder);
                break;
        }
        return cursor;
        // return database.query(NoteKeeperDatabaseContract.CourseInfoEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }

    private Cursor notesExpandedQuery(SQLiteDatabase database, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        String[] columns = new String[projection.length];
        for (int idx = 0; idx < projection.length; idx++) {
            columns[idx] = projection[idx].equals(BaseColumns._ID) ||
                    projection[idx].equals(NoteKeeperProviderContract.CourseIdColumns.COLUMN_COURSE_ID) ?
                    NoteKeeperDatabaseContract.NoteInfoEntry.getQName(projection[idx]) : projection[idx];
        }

        String tablesWithJoin = NoteKeeperDatabaseContract.NoteInfoEntry.TABLE_NAME + " JOIN " +
                NoteKeeperDatabaseContract.CourseInfoEntry.TABLE_NAME + " ON " + NoteKeeperDatabaseContract.NoteInfoEntry.getQName(NoteKeeperDatabaseContract.NoteInfoEntry.COLUMN_COURSE_ID)
                + " = " + NoteKeeperDatabaseContract.CourseInfoEntry.getQName(NoteKeeperDatabaseContract.CourseInfoEntry.COLUMN_COURSE_ID);
        return database.query(tablesWithJoin, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
