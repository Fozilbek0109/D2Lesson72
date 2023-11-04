package uz.coder.d2lesson72.service;

import java.util.List;

import uz.coder.d2lesson72.models.Student;

public interface DbService {
    void addStudent(Student student);
    void editStudent(Student student);
    void deleteStudent(Student student);
    List<Student> getAllStudent();
}
