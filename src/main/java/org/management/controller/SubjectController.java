package org.management.controller;

import lombok.RequiredArgsConstructor;
import org.management.entity.Student;
import org.management.entity.Subject;
import org.management.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;


    @PostMapping("/create")
    public ResponseEntity<String> createSubject(@RequestBody Subject subject) {
        return subjectService.save(subject);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable Long id) {

        return subjectService.deleteSubject(id);
    }

    @GetMapping("/search")
    public List<Subject> getAllSubjects() {
        return subjectService.findAllSubjects();
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long id) {
        return subjectService.findSubjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping ("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Subject e) {
        return subjectService.findSubjectById(id)
                .map(existing -> {
                    e.setId(id);
                    return subjectService.save(e);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
