package uz.coder.d2lesson72;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.List;

import uz.coder.d2lesson72.adapter.MyAdapter;
import uz.coder.d2lesson72.databinding.ActivityMainBinding;
import uz.coder.d2lesson72.databinding.DialogLayoutBinding;
import uz.coder.d2lesson72.db.MyDatabase;
import uz.coder.d2lesson72.models.Student;

public class MainActivity extends AppCompatActivity {
    private List<Student> studentList;
    private MyDatabase database;
    private MyAdapter myAdapter;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = new MyDatabase(this);
        loadData();
        myAdapter = new MyAdapter(studentList, (student, position, v) -> {
            PopupMenu popupMenu = new PopupMenu(this,v, Gravity.RIGHT);
            popupMenu.inflate(R.menu.pop_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    switch (id){
                        case R.id.edit:
                            break;
                        case R.id.delete:
                            database.deleteStudent(student);
                            studentList.remove(student);
                            myAdapter.notifyItemRemoved(position);
                            myAdapter.notifyItemRangeChanged(position,studentList.size());
                            break;
                    }
                    return false;
                }

            });
                popupMenu.show();
        });
        binding.rec.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        binding.rec.addItemDecoration(dividerItemDecoration);
        binding.rec.setAdapter(myAdapter);
    }

    private void loadData() {
        studentList = new ArrayList<>();
        studentList = database.getAllStudent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.addStudent:
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                DialogLayoutBinding dialogLayoutBinding  = DialogLayoutBinding.inflate(LayoutInflater.from(MainActivity.this),null,false);
                alertDialog.setView(dialogLayoutBinding.getRoot());

                dialogLayoutBinding.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = dialogLayoutBinding.name.getText().toString();
                        String s1 = dialogLayoutBinding.age.getText().toString();
                        String s2 = dialogLayoutBinding.phone.getText().toString();

                        Student student = new Student();
                        student.setName(s);
                        student.setAge(Integer.parseInt(s1));
                        student.setPhone(s2);
                        database.addStudent(student);
                        studentList.add(student);
                        myAdapter.notifyItemInserted(studentList.size());
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();



                break;
        }
        return super.onOptionsItemSelected(item);
    }
}