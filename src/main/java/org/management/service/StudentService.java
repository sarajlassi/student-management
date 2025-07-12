package org.management.service;

import org.apache.coyote.Response;
import org.management.entity.Grade;
import org.management.entity.Student;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    ResponseEntity<String> save(Student student);

    ResponseEntity<String> deleteStudent(Long studentId);

    List<Student> findAllStudents();

    Optional<Student> findStudentById(Long studentId);

    ResponseEntity<String> addGrade(Grade grade, Long studentId);
    Double calculateGrade(Long studentId);
}
