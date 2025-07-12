package org.management.repository;

import org.management.entity.Absence;
import org.management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    @Query("SELECT COUNT(a) FROM Absence a WHERE a.student.id = :studentId AND a.course.id = :courseId AND a.justified = false")
    long countUnjustifiedAbsences(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    List<Absence> findAbsencesByCourseId(Long courseId);

    List<Absence> findByStudentId(Long studentId);

    List<Absence> findAbsenceById(Long absenceId);
}
