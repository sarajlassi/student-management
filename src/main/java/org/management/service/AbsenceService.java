package org.management.service;

import org.apache.coyote.Response;
import org.management.entity.Absence;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AbsenceService {
    ResponseEntity<String> save(Absence absence);

    ResponseEntity<String> deleteAbsence(Long absenceId);

    List<Absence> findAllAbsences();

    List<Absence> findAbsenceByStudentId(Long studentId);
    List<Absence> findAbsenceById(Long absenceId);

    Boolean isStudentEliminated(Long studentId, Long courseId);

    ResponseEntity<String> justifyAbsence(Long absenceId);
}
