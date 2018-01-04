package com.test.notekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import static com.test.notekeeper.NoteActivity.NOTE_POSITION;

public class NoteListActivity extends AppCompatActivity {

    private ArrayAdapter<NoteInfo> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });
        initializeDisplayContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void initializeDisplayContent() {
        final ListView listView = findViewById(R.id.list_note);
        final List<NoteInfo> notes = DataManager.getInstance().getNotes();

        adapter = new ArrayAdapter<NoteInfo>(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
                //    NoteInfo note = (NoteInfo) listView.getItemAtPosition(position);
                intent.putExtra(NOTE_POSITION, position);
                startActivity(intent);
            }
        });
    }
}
