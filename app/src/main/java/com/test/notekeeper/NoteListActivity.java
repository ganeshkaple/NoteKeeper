package com.test.notekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class NoteListActivity extends AppCompatActivity {
    private NoteRecyclerAdapter adapter;

    //private ArrayAdapter<NoteInfo> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_note_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(NoteListActivity.this, NoteActivity.class);
            startActivity(intent);
        });
        initializeDisplayContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void initializeDisplayContent() {
      /*  final ListView listView = findViewById(R.id.list_notes);
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
        });*/
      final RecyclerView recyclerView = findViewById(R.id.list_notes);
      final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
      recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new NoteRecyclerAdapter(this, null);
      recyclerView.setAdapter(adapter);


    }
}
