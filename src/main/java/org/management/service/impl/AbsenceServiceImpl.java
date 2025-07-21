package org.management.service.impl;

import ch.qos.logback.classic.pattern.Abbreviator;
import org.management.entity.Absence;
import org.management.repository.AbsenceRepository;
import org.management.service.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbsenceServiceImpl implements AbsenceService {
    @Autowired
    private AbsenceRepository absenceRepository;

    @Override
    public ResponseEntity<String> save(Absence absence) {
        Absence abs = absenceRepository.findById(absence.getId()).orElse(null);
        if(abs != null) {
            return ResponseEntity.badRequest().body("Absence already exists !!!");
        }
        absenceRepository.save(absence);
        return ResponseEntity.ok("Absence created successfully");
    }

    @Override
    public ResponseEntity<String> deleteAbsence(Long absenceId) {
        Optional<Absence> absence = absenceRepository.findById(absenceId);
        if(absence.isEmpty()) {
            return ResponseEntity.badRequest().body("Absence doesn't exist");
        }
        absenceRepository.deleteById(absenceId);
        return ResponseEntity.ok("Absence deleted successfully");
    }

    @Override
    public List<Absence> findAllAbsences() {
        return absenceRepository.findAll();
    }

    @Override
    public List<Absence> findAbsenceByStudentId(Long studentId) {
        return absenceRepository.findByStudentId(studentId);
    }

    @Override
    public List<Absence> findAbsenceById(Long absenceId) {
        return absenceRepository.findAbsenceById(absenceId);
    }

    @Override
    public Boolean isStudentEliminated(Long studentId, Long courseId) {
        long unjustifiedCount = absenceRepository.countUnjustifiedAbsences(studentId, courseId);
        return unjustifiedCount > 3;
    }

    @Override
    public ResponseEntity<String> justifyAbsence(Long absenceId) {
        Optional<Absence> absenceOptional = absenceRepository.findById(absenceId);
        if (absenceOptional.isPresent()) {
            Absence absence = absenceOptional.get();
            absence.setJustified(true);
            save(absence);
            return ResponseEntity.ok("Absence justified successfully");

        }
        else {
            return ResponseEntity.ok("Absence not found");
        }
    }
}
