package ru.dsis.atms.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dsis.atms.tests.dao.TestPlan;
import ru.dsis.atms.tests.service.TestPlanService;

import java.util.List;

@RestController
@RequestMapping("/test-plans")
public class TestPlanController {

    @Autowired
    private TestPlanService testPlanService;

    @GetMapping
    public List<TestPlan> getAllTestPlans() {
        return testPlanService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestPlan> getTestPlanById(@PathVariable int id) {
        TestPlan testPlan = testPlanService.findById(id);
        if (testPlan != null) {
            return new ResponseEntity<>(testPlan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<TestPlan> createTestPlan(@RequestBody TestPlan testPlan) {
        TestPlan createdTestPlan = testPlanService.save(testPlan);
        return new ResponseEntity<>(createdTestPlan, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestPlan> updateTestPlan(@PathVariable int id, @RequestBody TestPlan testPlan) {
        TestPlan updatedTestPlan = testPlanService.update(id, testPlan);
        if (updatedTestPlan != null) {
            return new ResponseEntity<>(updatedTestPlan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TestPlan> partialUpdateTestPlan(@PathVariable int id, @RequestBody TestPlan testPlan) {
        TestPlan updatedTestPlan = testPlanService.partialUpdate(id, testPlan);
        if (updatedTestPlan != null) {
            return new ResponseEntity<>(updatedTestPlan, HttpStatus.OK);
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
