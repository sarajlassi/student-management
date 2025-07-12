package org.management.service;

import org.management.entity.Subject;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    ResponseEntity<String> save(Subject subject);
    ResponseEntity<String> deleteSubject(Long subjectId);
    List<Subject> findAllSubjects();
    Optional<Subject> findSubjectById(Long subjectId);
}
