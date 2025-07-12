package org.management.service;

import org.management.entity.Course;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    ResponseEntity<String> save(Course course);
    ResponseEntity<String> deleteCourse(Long courseId);
    List<Course> findAllCourses();
    Optional<Course> findCourseById(Long courseId);

}
