package org.management.service.impl;

import lombok.AllArgsConstructor;
import org.management.entity.Absence;
import org.management.entity.Course;
import org.management.repository.AbsenceRepository;
import org.management.repository.CourseRepository;
import org.management.service.AbsenceService;
import org.management.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    @Autowired
    private AbsenceRepository absenceRepository;
    private final AbsenceService absenceService;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public ResponseEntity<String> save(Course course) {
        try {
            Course c = courseRepository.findById(course.getId()).orElse(null);
            if(c != null) {
                return ResponseEntity.badRequest().body("Course already exists !!!");
            }
            courseRepository.save(course);
            return ResponseEntity.ok("Course added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteCourse(Long courseId) {
        try {
            List<Absence> absenceList = absenceRepository.findAbsencesByCourseId(courseId);
            Optional<Course> course = courseRepository.findById(courseId);
            if(course.isEmpty()) {
                return ResponseEntity.badRequest().body("Course not found");
            }
            if (absenceList != null && !absenceList.isEmpty()) {
                absenceList.forEach(absence -> {
                    absenceService.deleteAbsence(absence.getId());
                });
            }
            courseRepository.deleteById(courseId);
            return ResponseEntity.ok("Course deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> findCourseById(Long courseId) {
        return courseRepository.findById(courseId);
    }
}
