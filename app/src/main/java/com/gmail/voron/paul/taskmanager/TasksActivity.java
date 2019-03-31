package com.gmail.voron.paul.taskmanager;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TasksActivity extends AppCompatActivity {

    public static final int ADD_TASK_REQUEST_CODE = 101;

    private RecyclerView rv;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(TasksActivity.this, AddTaskActivity.class));
                // запуск другой активити по нажатию кнопки

                startActivityForResult(new Intent(TasksActivity.this, AddTaskActivity.class), ADD_TASK_REQUEST_CODE);
                //запуск другой активити по нажатию кнопки с возвратом данных из нее
            }
        });


        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        TasksAdapter adapter = new TasksAdapter(new TaskClickListener() {
            @Override
            public void onClick(Task task) {
                Toast.makeText(TasksActivity.this,"Task in progress...", Toast.LENGTH_SHORT).show();
            }
        });
        rv.setAdapter(adapter);

        adapter.setData(generateFakeData());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK){
            if (data != null){
                Task task = ((Task) data.getSerializableExtra(Task.class.getName()));
                Toast.makeText(this, task.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public List<Task> generateFakeData(){

        Random random = new Random();
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            tasks.add(new Task( "Task " + i, random.nextInt(5)));
        }
        return tasks;
    }

    public static class TasksAdapter extends RecyclerView.Adapter<TaskViewHolder> {
        private final List<Task> data = new ArrayList<>();
        private final TaskClickListener taskClickListener;


        public TasksAdapter(TaskClickListener taskClickListener) {
            this.taskClickListener = taskClickListener;
        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int position) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View view = layoutInflater.inflate(R.layout.item_task, viewGroup, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (RecyclerView.NO_POSITION != position) {
                        taskClickListener.onClick(data.get(position));
                    }
                }
            });
            TaskViewHolder viewHolder = new TaskViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
            Task task = data.get(position);
            taskViewHolder.setData(task);
        }


        @Override
        public int getItemCount() {
            return data.size();
        }

        public void setData(List<Task> data) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTaskName;
        private final TextView tvTaskPriority;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            tvTaskPriority = itemView.findViewById(R.id.tvTaskPriority);
        }

        public void setData(Task task) {
            tvTaskName.setText(task.getName());
            //tvTaskPriority.setText(Integer.toString(task.getPriority()));
            tvTaskPriority.setTextColor(setTaskColor(task.getPriority()));
        }

        public int setTaskColor(int priority) {
            int color;
            switch(priority) {
                case 0:
                    color = Color.BLUE;
                    break;
                case 1:
                    color = Color.GREEN;
                    break;
                case 2:
                    color = Color.MAGENTA;
                    break;
                case 3:
                    color = Color.RED;
                    break;
                case 4:
                    color = Color.CYAN;
                    break;
                default:
                    color = Color.GRAY;
                    break;
            }
            return color;
        }

    }

    public interface TaskClickListener{
        public void onClick(Task task);
    }

}
