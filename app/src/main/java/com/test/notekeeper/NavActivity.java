package com.test.notekeeper;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.test.notekeeper.NoteKeeperDatabaseContract.NoteInfoEntry;
import com.test.notekeeper.NoteKeeperProviderContract.Courses;
import com.test.notekeeper.NoteKeeperProviderContract.Notes;

import java.util.List;

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {
    public static final int LOADER_NOTES = 0;
    private NoteRecyclerAdapter noteRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private NavigationView navigationView;
    private CoursesRecyclerAdapter coursesRecyclerAdapter;
    private List<CourseInfo> courses;
    private GridLayoutManager gridLayoutManager;
    private NoteKeeperOpenHelper mDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDbOpenHelper = new NoteKeeperOpenHelper(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NavActivity.this, NoteActivity.class));
            }
        });

        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_notification, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_data_sync, false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);

        PreferenceManager.setDefaultValues(this, R.xml.pref_notification, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_data_sync, false);
        initializeDisplayContent();
    }

    @Override
    protected void onDestroy() {
        mDbOpenHelper.close();
        super.onDestroy();
    }

    private void initializeDisplayContent() {
      /*  final ListView listView = findViewById(R.id.list_notes);
        final List<NoteInfo> notes = DataManager.getInstance().getNotes();

        noteRecyclerAdapter = new ArrayAdapter<NoteInfo>(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(noteRecyclerAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
                //    NoteInfo note = (NoteInfo) listView.getItemAtPosition(position);
                intent.putExtra(NOTE_POSITION, position);
                startActivity(intent);
            }
        });*/
        DataManager.loadFromDatabase(mDbOpenHelper);
        recyclerView = findViewById(R.id.list_items);
        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.course_grid_span));
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        coursesRecyclerAdapter = new CoursesRecyclerAdapter(this, courses);
        noteRecyclerAdapter = new NoteRecyclerAdapter(this, null);
        displayNotes();
       /* displayCourses();*/

    }

    private void displayNotes() {

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(noteRecyclerAdapter);


        selectNavigationMenuItem(R.id.nav_notes);

    }

    private void selectNavigationMenuItem(int nav_notes) {
        Menu menu = navigationView.getMenu();
        menu.findItem(nav_notes).setChecked(true);
    }

    private void loadNotes() {
        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
        final String[] noteColumns = {
                NoteInfoEntry.COLUMN_NOTE_TITLE,
                NoteInfoEntry.COLUMN_COURSE_ID,
                NoteInfoEntry._ID};
        String noteOrderBy = NoteInfoEntry.COLUMN_COURSE_ID + "," + NoteInfoEntry.COLUMN_NOTE_TITLE;
        final Cursor notesCursor = db.query(NoteInfoEntry.TABLE_NAME, noteColumns,
                null, null, null, null, noteOrderBy);
        noteRecyclerAdapter.changeCursor(notesCursor);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(LOADER_NOTES, null, this);
        updateNavHeader();
    }

    private void updateNavHeader() {
        View headerView= navigationView.getHeaderView(0);
        TextView userName = headerView.findViewById(R.id.name);
        TextView email = headerView.findViewById(R.id.email);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this  );
        String userNameAS = preferences.getString("user_display_name", "");
        String emailId = preferences.getString("user_email","");
        userName.setText(userNameAS);
        email.setText(emailId);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new  Intent(this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notes) {
            displayNotes();
            // Handle the camera action
        } else if (id == R.id.nav_courses) {
            displayCourses();

        } else if (id == R.id.nav_share) {
            /*handleSelection("Dude, Stop sharing everything");*/
            handleShare();

        } else if (id == R.id.nav_send) {
            handleSelection("Send");

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleShare() {
        View v = findViewById(R.id.list_items);
        Snackbar.make(v,"share to " + PreferenceManager.getDefaultSharedPreferences(this).getString("user_favorier_social",""),
                BaseTransientBottomBar.LENGTH_LONG).show();
    }

    private void handleSelection(String msg) {
        View view = findViewById(R.id.list_items);
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();

    }

    private void displayCourses() {
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(coursesRecyclerAdapter);
        selectNavigationMenuItem(R.id.nav_courses);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        CursorLoader loader = null;
        if (id == LOADER_NOTES) {

                    final String[] noteColumns = {
                            Notes._ID,
                            Notes.COLUMN_NOTE_TITLE,
                            Notes.COLUMN_COURSE_TITLE

                    };
            final String noteOrderBy = Courses.COLUMN_COURSE_TITLE +
                            ", " + NoteInfoEntry.COLUMN_NOTE_TITLE;

            loader = new CursorLoader(this, Notes.CONTENT_EXPANDED_URI, noteColumns, null, null, noteOrderBy);


                }


        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == LOADER_NOTES) {
            noteRecyclerAdapter.changeCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == LOADER_NOTES) {
            noteRecyclerAdapter.changeCursor(null);
        }
    }


}
