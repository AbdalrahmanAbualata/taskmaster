package com.example.taskmaster;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    List<Task> tasks = new ArrayList<>();
    Context context;

    public TaskAdapter(List<Task> tasks , Context context) {
        this.tasks = tasks;
        this.context = context;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{
        Task task;
        View itemView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task,parent,false);
        TaskViewHolder taskViewHolder=new TaskViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.task = tasks.get(position);
        TextView title = holder.itemView.findViewById(R.id.fragment_title_text);
        TextView body = holder.itemView.findViewById(R.id.fragment_body_text);
        TextView state= holder.itemView.findViewById(R.id.fragment_state_text);

        title.setText(holder.task.title);
        body.setText(holder.task.body);
        state.setText(holder.task.state);
//        number.setText(Integer.toString(holder.number.days));  in case we have integer we need to convert it to integer

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent taskDetailsIntent = new Intent(context, TaskDetailActivity.class); //v.getContext, TaskDetailActivity.class
                taskDetailsIntent.putExtra("taskName",holder.task.title);
                context.startActivity(taskDetailsIntent);// v.getContext.startActivity(taskDetailsIntent)
            }
        });

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

}
