package org.management.controller;

import lombok.RequiredArgsConstructor;
import org.management.entity.Grade;
import org.management.entity.Student;
import org.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/search")
    public List<Student> getAll() {
        return studentService.findAllStudents();
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        return studentService.findStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Student e) {
        return studentService.save(e);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Student e) {
        return studentService.findStudentById(id)
                .map(existing -> {
                    e.setId(id);
                    return studentService.save(e);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/add-grade/{studentId}")
    public ResponseEntity<String> addGrade(@RequestBody Grade grade, @PathVariable Long studentId) {
        return studentService.addGrade(grade, studentId);
    }

    @PostMapping("/calculate-grade/{studentId}")
    public Double calculateGrade(@PathVariable Long studentId) {
        return studentService.calculateGrade(studentId);
    }
}
