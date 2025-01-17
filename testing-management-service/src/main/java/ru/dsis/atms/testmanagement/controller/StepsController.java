package ru.dsis.atms.testmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dsis.atms.testmanagement.dao.StepDao;
import ru.dsis.atms.testmanagement.dto.StepDto;
import ru.dsis.atms.testmanagement.service.StepsService;

import java.util.List;

@RestController
@RequestMapping("/steps")
public class StepsController {

    @Autowired
    StepsService stepsService;

    @GetMapping
    public List<StepDao> findAllSteps() {
        return stepsService.findAllSteps();
    }

    @PostMapping
    public ResponseEntity<StepDao> createStep(@RequestBody StepDto stepDto) {
        return new ResponseEntity<>(stepsService.save(stepDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StepDao> findStepById(@PathVariable int id) {
        var stepDao = stepsService.findStepById(id);
        if (stepDao != null) {
            return new ResponseEntity<>(stepDao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StepDao> updateStep(@PathVariable int id, @RequestBody StepDto stepDto) {
        var updatedStep = stepsService.update(id, stepDto);
        if (updatedStep != null) {
            return new ResponseEntity<>(updatedStep, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StepDao> partialUpdateStep(@PathVariable int id, @RequestBody StepDto stepDto) {
        var updatedStep = stepsService.partialUpdate(id, stepDto);
        if (updatedStep != null) {
            return new ResponseEntity<>(updatedStep, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStep(@PathVariable int id) {
        boolean isDeleted = stepsService.delete(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
