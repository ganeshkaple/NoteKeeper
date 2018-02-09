package com.test.notekeeper;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author Ganesh Kaple
 * @since 09-01-2018
 */

public class CoursesRecyclerAdapter extends RecyclerView.Adapter<CoursesRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private final List<CourseInfo> courses;

    public CoursesRecyclerAdapter(Context context, List<CourseInfo> noteInfos) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        this.courses = noteInfos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_course_list, parent, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CourseInfo course = courses.get(position);
        holder.currentPosition = position;
        holder.textCourse.setText(course.getTitle());
        // holder.textViewText.setText(course.getTitle());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public final TextView textCourse;
        public int currentPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            textCourse = itemView.findViewById(R.id.text_tiitle);

            itemView.setOnClickListener(v -> {
               /* Intent intent = new Intent(context, NoteActivity.class);
                intent.putExtra(NoteActivity.NOTE_POSITION, currentPosition);
                context.startActivity(intent);*/
                Snackbar.make(v, courses.get(currentPosition).getTitle(), Snackbar.LENGTH_LONG).show();
            });
        }
    }
}