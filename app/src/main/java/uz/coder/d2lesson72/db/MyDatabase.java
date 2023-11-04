package uz.coder.d2lesson72.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import uz.coder.d2lesson72.models.Student;
import uz.coder.d2lesson72.service.DbService;

public class MyDatabase extends SQLiteOpenHelper implements DbService {

    public static final String DB_NAME = "D2Lesson72";
    public static final int DB_VERSION = 1;

    public static final String TABLE_NAME= "student";
    public static final String STUDENT_ID= "id";
    public static final String STUDENT_NAME= "name";
    public static final String STUDENT_AGE= "age";
    public static final String STUDENT_PHONE= "phone";

    public MyDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table student(id integer not null primary key autoincrement,name text not null,age integer not null,phone text not null unique)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void addStudent(Student student) {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME,student.getName());
        values.put(STUDENT_AGE,student.getAge());
        values.put(STUDENT_PHONE,student.getPhone());
        database.insert(TABLE_NAME,null,values);
    }

    @Override
    public void editStudent(Student student) {

    }

    @Override
    public void deleteStudent(Student student) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        String query = "delete from student where id =" + student.getId();
        readableDatabase.execSQL(query);
    }

    @Override
    public List<Student> getAllStudent() {
        List<Student> studentList = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "select * from student";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Student student = new Student();
                student.setId(Integer.parseInt(cursor.getString(0)));
                student.setName(cursor.getString(1));
                student.setAge(Integer.parseInt(cursor.getString(2)));
                student.setPhone(cursor.getString(3));
                studentList.add(student);
            }while (cursor.moveToNext());
        }

        return studentList;
    }
}
