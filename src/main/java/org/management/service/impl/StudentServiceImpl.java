package org.management.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.management.entity.Grade;
import org.management.entity.Student;
import org.management.entity.Subject;
import org.management.repository.StudentRepository;
import org.management.repository.SubjectRepository;
import org.management.service.StudentService;
import org.management.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public ResponseEntity<String> save(Student student) {
        studentRepository.save(student);
        return ResponseEntity.ok("Student saved successfully");
    }

    @Override
    public ResponseEntity<String> deleteStudent(Long studentId) {
        try {
            Student student = studentRepository.findById(studentId).orElse(null);
            if (student == null) {
                return ResponseEntity.badRequest().body("Student not found");
            }
            studentRepository.deleteById(studentId);
            return ResponseEntity.ok("Student deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> findStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    public ResponseEntity<String> addGrade(Grade grade, Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Subject optionalSubject = subjectRepository.findBySubjectName(grade.getSubjectName());
        if (optionalSubject == null) {
            return ResponseEntity.badRequest().body("Subject not found");

        }
        if (grade.getValue() > 20 || grade.getValue() < 0) {
            return ResponseEntity.badRequest().body("Grade value must be between 0 and 20");
         }
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.getGrades().add(grade);
            studentRepository.save(student);
            return ResponseEntity.ok("Grade saved successfully");
        } else {
            return ResponseEntity.badRequest().body("Student not found");
        }

    }

    @Override
    public Double calculateGrade(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        Student student = optionalStudent.get();
        List<Grade> grades = student.getGrades();
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }

        double totalWeighted = 0.0;
        double totalCoefficient = 0.0;
        for (Grade grade : grades) {
            Subject subject = subjectRepository.findBySubjectName(grade.getSubjectName());

            if (subject != null && subject.getCoefficient() != null) {
                double coefficient = subject.getCoefficient();
                totalWeighted += grade.getValue() * coefficient;
                totalCoefficient += coefficient;
            }
        }

        return totalCoefficient == 0 ? 0.0 : totalWeighted / totalCoefficient;
    }
}
