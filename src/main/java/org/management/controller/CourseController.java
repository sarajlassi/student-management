package org.management.controller;

import lombok.RequiredArgsConstructor;
import org.management.entity.Course;
import org.management.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/course")
public class CourseController {
    @Autowired
    private final CourseService courseService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<String> saveCourse(@RequestBody Course course) {
        return courseService.save(course);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) {
        return courseService.deleteCourse(courseId);
    }

    @GetMapping("/search")
    public List<Course> getAllCourses() {
        return courseService.findAllCourses();
    }

    @GetMapping("/search/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId) {
        Optional<Course> course = courseService.findCourseById(courseId);
        return course.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/{courseId}")
    public ResponseEntity<String> updateCourse(@PathVariable Long courseId, @RequestBody Course updatedCourse) {
        Optional<Course> existingCourse = courseService.findCourseById(courseId);
        if (existingCourse.isPresent()) {
            updatedCourse.setId(courseId);
            return courseService.save(updatedCourse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
