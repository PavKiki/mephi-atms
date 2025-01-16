package ru.dsis.atms.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dsis.atms.tests.dao.TestCase;

import java.util.List;

@RestController
@RequestMapping("/test-cases")
public class TestCaseController {

    @Autowired
    private TestCaseService testCaseService;

    @GetMapping
    public List<TestCase> getAllTestCases() {
        return testCaseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestCase> getTestCaseById(@PathVariable int id) {
        TestCase testCase = testCaseService.findById(id);
        if (testCase != null) {
            return new ResponseEntity<>(testCase, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<TestCase> createTestCase(@RequestBody TestCase testCase) {
        TestCase createdTestCase = testCaseService.save(testCase);
        return new ResponseEntity<>(createdTestCase, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestCase> updateTestCase(@PathVariable int id, @RequestBody TestCase testCase) {
        TestCase updatedTestCase = testCaseService.update(id, testCase);
        if (updatedTestCase != null) {
            return new ResponseEntity<>(updatedTestCase, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TestCase> partialUpdateTestCase(@PathVariable int id, @RequestBody TestCase testCase) {
        TestCase updatedTestCase = testCaseService.partialUpdate(id, testCase);
        if (updatedTestCase != null) {
            return new ResponseEntity<>(updatedTestCase, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestCase(@PathVariable int id) {
        boolean isDeleted = testCaseService.delete(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}