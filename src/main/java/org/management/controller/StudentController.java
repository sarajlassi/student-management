package org.management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.management.entity.Grade;
import org.management.entity.Student;
import org.management.repository.StudentRepository;
import org.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;


    @Operation(summary = "Get client by ID", description = "Returns a client by their code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client found"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Student e) {
        Student student = studentRepository.findById(e.getId()).get();
        if(student != null) {
            return ResponseEntity.badRequest().body("Student already exists");
        }
        return studentService.save(e);
    }
     @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Student e) {
        return studentService.findStudentById(id)
                .map(existing -> {
                    e.setId(id);
                    return studentService.save(e);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-grade/{studentId}")
    public ResponseEntity<String> addGrade(@RequestBody Grade grade, @PathVariable Long studentId) {
        return studentService.addGrade(grade, studentId);
    }

    @PostMapping("/calculate-grade/{studentId}")
    public Double calculateGrade(@PathVariable Long studentId) {
        return studentService.calculateGrade(studentId);
    }
}
