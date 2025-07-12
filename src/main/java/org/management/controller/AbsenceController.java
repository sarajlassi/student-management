package org.management.controller;

import lombok.RequiredArgsConstructor;
import org.management.entity.Absence;
import org.management.service.AbsenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/absence")
public class AbsenceController {
    @Autowired
    private final AbsenceService absenceService;

    @PostMapping("/create")
    public ResponseEntity<String> saveAbsence(@RequestBody Absence absence) {
        return absenceService.save(absence);
    }
    @DeleteMapping("/delete/{absenceId}")
    public ResponseEntity<String> deleteAbsence(@PathVariable Long id) {
        return absenceService.deleteAbsence(id);
    }
    @GetMapping("/search")
    public List<Absence> getAllAbsences() {
        return absenceService.findAllAbsences();
    }


    @GetMapping("/search-by-student/{studentId}")
    public ResponseEntity<List<Absence>> getAbsenceByStudentId(@PathVariable Long studentId) {
        List<Absence> absence = absenceService.findAbsenceByStudentId(studentId);
        return ResponseEntity.ok(absence);
    }


    @GetMapping("/search/{absenceId}")
    public ResponseEntity<List<Absence>> findAbsenceById(@PathVariable Long absenceId) {
        List<Absence> absence = absenceService.findAbsenceById(absenceId);
        return ResponseEntity.ok(absence);
    }


    @PatchMapping("/update/{absenceId}")
    public ResponseEntity<String> updateAbsence(@PathVariable Long absenceId, @RequestBody Absence updatedAbsence) {
        List<Absence> existingCourse = absenceService.findAbsenceById(absenceId);
        if (existingCourse != null) {
            updatedAbsence.setId(absenceId);
            return absenceService.save(updatedAbsence);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/elimination")
    public ResponseEntity<String> checkElimination(
            @RequestParam Long studentId,
            @RequestParam Long courseId) {

        boolean isEliminated = absenceService.isStudentEliminated(studentId, courseId);
        if (isEliminated) {
            return ResponseEntity.ok("The student is eliminated from this course.");
        } else {
            return ResponseEntity.ok("The student is NOT eliminated from this course.");
        }
    }

    @PostMapping("/justify/{absenceId}")
    public ResponseEntity<String> justifyAbsence(@PathVariable Long absenceId) {
        return absenceService.justifyAbsence(absenceId);
    }
}
