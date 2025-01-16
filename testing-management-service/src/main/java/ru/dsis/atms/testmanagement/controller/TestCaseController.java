package ru.dsis.atms.testmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dsis.atms.testmanagement.dao.TestCaseDao;
import ru.dsis.atms.testmanagement.dto.TestCaseDto;
import ru.dsis.atms.testmanagement.service.TestCaseService;

import java.util.List;

@RestController
@RequestMapping("/test-cases")
public class TestCaseController {

    @Autowired
    private TestCaseService testCaseService;

    @GetMapping
    public List<TestCaseDao> getAllTestCases() {
        return testCaseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestCaseDao> getTestCaseById(@PathVariable int id) {
        TestCaseDao testCaseDao = testCaseService.findById(id);
        if (testCaseDao != null) {
            return new ResponseEntity<>(testCaseDao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<TestCaseDao> createTestCase(@RequestBody TestCaseDto testCaseDto) {
        TestCaseDao createdTestCaseDao = testCaseService.save(testCaseDto);
        return new ResponseEntity<>(createdTestCaseDao, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestCaseDao> updateTestCase(@PathVariable int id, @RequestBody TestCaseDto testCaseDto) {
        TestCaseDao updatedTestCaseDao = testCaseService.update(id, testCaseDto);
        if (updatedTestCaseDao != null) {
            return new ResponseEntity<>(updatedTestCaseDao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TestCaseDao> partialUpdateTestCase(@PathVariable int id, @RequestBody TestCaseDto testCaseDto) {
        TestCaseDao updatedTestCaseDao = testCaseService.partialUpdate(id, testCaseDto);
        if (updatedTestCaseDao != null) {
            return new ResponseEntity<>(updatedTestCaseDao, HttpStatus.OK);
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