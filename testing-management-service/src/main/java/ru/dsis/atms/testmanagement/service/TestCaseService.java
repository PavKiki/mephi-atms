package ru.dsis.atms.testmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.testmanagement.dao.StepDao;
import ru.dsis.atms.testmanagement.dao.TestCaseDao;
import ru.dsis.atms.testmanagement.dto.TestCaseCreationDto;
import ru.dsis.atms.testmanagement.dto.TestCaseDto;
import ru.dsis.atms.testmanagement.repository.TestCaseRepository;

import java.util.List;

@Service
public class TestCaseService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    public List<TestCaseDao> findAll() {
        return testCaseRepository.findAll();
    }

    public TestCaseDao findById(int id) {
        return testCaseRepository.findById(id);
    }

    public TestCaseDao save(TestCaseCreationDto testCaseCreationDto) {
        return testCaseRepository.save(testCaseCreationDto);
    }

    public TestCaseDao update(int id, TestCaseDto testCaseDto) {
        return testCaseRepository.update(id, testCaseDto);
    }

    public TestCaseDao partialUpdate(int id, TestCaseDto testCaseDto) {
        var existingTestCase = testCaseRepository.findById(id);
        if (existingTestCase == null) {
            return null;
        }

        if (testCaseDto.getName() != null) {
            existingTestCase.setName(testCaseDto.getName());
        }
        if (testCaseDto.getPreCondition() != null) {
            existingTestCase.setPreCondition(testCaseDto.getPreCondition());
        }
        if (testCaseDto.getPostCondition() != null) {
            existingTestCase.setPostCondition(testCaseDto.getPostCondition());
        }
        if (testCaseDto.getStatus() != null) {
            existingTestCase.setStatus(testCaseDto.getStatus());
        }
        if (testCaseDto.getTestPlanId() != null) {
            existingTestCase.setTestPlanId(testCaseDto.getTestPlanId());
        }
        if (testCaseDto.getProjectId() != null) {
            existingTestCase.setProjectId(testCaseDto.getProjectId());
        }
        return testCaseRepository.update(id, existingTestCase.toDto());
    }

    public boolean delete(int id) {
        return testCaseRepository.delete(id);
    }

    public List<StepDao> getSteps(int id) {
        return testCaseRepository.getSteps(id);
    }
}
