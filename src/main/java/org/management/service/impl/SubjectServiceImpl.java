package org.management.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.management.entity.Course;
import org.management.entity.Subject;
import org.management.repository.AbsenceRepository;
import org.management.repository.CourseRepository;
import org.management.repository.SubjectRepository;
import org.management.service.AbsenceService;
import org.management.service.CourseService;
import org.management.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private CourseRepository courseRepository;

    private final CourseService courseService;

    @Override
    public ResponseEntity<String> save(Subject subject) {
        Subject subj = subjectRepository.findById(subject.getId()).orElse(null);
        if(subj != null) {
            return ResponseEntity.badRequest().body("Subject already exists !!!");
        }
        subjectRepository.save(subject);
        return ResponseEntity.ok("Subject saved successfully");
    }

    @Override
    public ResponseEntity<String> deleteSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElse(null);
        if (subject == null) {
            return ResponseEntity.badRequest().body("Subject does not exist");
        }
        Course course = courseRepository.findBySubjectId(subjectId);
        if (course != null) {
            courseService.deleteCourse(course.getId());
        }
        subjectRepository.deleteById(subjectId);
        return ResponseEntity.ok("Subject deleted successfully");
    }

    @Override
    public List<Subject> findAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public Optional<Subject> findSubjectById(Long subjectId) {
        return subjectRepository.findById(subjectId);
    }
}
