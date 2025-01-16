package ru.dsis.atms.testmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dsis.atms.testmanagement.dao.TestPlanDao;
import ru.dsis.atms.testmanagement.dto.TestPlanDto;
import ru.dsis.atms.testmanagement.service.TestPlanService;

import java.util.List;

@RestController
@RequestMapping("/test-plans")
public class TestPlanController {

    @Autowired
    private TestPlanService testPlanService;

    @GetMapping
    public List<TestPlanDao> getAllTestPlans() {
        return testPlanService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestPlanDao> getTestPlanById(@PathVariable int id) {
        TestPlanDao testPlanDao = testPlanService.findById(id);
        if (testPlanDao != null) {
            return new ResponseEntity<>(testPlanDao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<TestPlanDao> createTestPlan(@RequestBody TestPlanDto testPlanDto) {
        TestPlanDao createdTestPlanDao = testPlanService.save(testPlanDto);
        return new ResponseEntity<>(createdTestPlanDao, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestPlanDao> updateTestPlan(@PathVariable int id, @RequestBody TestPlanDto testPlanDto) {
        TestPlanDao updatedTestPlanDao = testPlanService.update(id, testPlanDto);
        if (updatedTestPlanDao != null) {
            return new ResponseEntity<>(updatedTestPlanDao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TestPlanDao> partialUpdateTestPlan(@PathVariable int id, @RequestBody TestPlanDto testPlanDto) {
        TestPlanDao updatedTestPlanDao = testPlanService.partialUpdate(id, testPlanDto);
        if (updatedTestPlanDao != null) {
            return new ResponseEntity<>(updatedTestPlanDao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestPlan(@PathVariable int id) {
        boolean isDeleted = testPlanService.delete(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
