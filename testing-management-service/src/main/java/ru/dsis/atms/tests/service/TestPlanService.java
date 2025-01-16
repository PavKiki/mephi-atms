package ru.dsis.atms.tests.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.tests.dao.TestPlan;

import java.util.List;

@Service
public class TestPlanService {

    @Autowired
    private TestPlanRepository testPlanRepository;

    public List<TestPlan> findAll() {
        return testPlanRepository.findAll();
    }

    public TestPlan findById(int id) {
        return testPlanRepository.findById(id);
    }

    public TestPlan save(TestPlan testPlan) {
        return testPlanRepository.save(testPlan);
    }

    public TestPlan update(int id, TestPlan testPlan) {
        return testPlanRepository.update(id, testPlan);
    }

    public TestPlan partialUpdate(int id, TestPlan testPlan) {
        var existingTestPlan = testPlanRepository.findById(id);
        if (existingTestPlan == null) {
            return false;
        }

        if (testPlan.getName() != null) {
            existingTestPlan.setName(testPlan.getName());
        }
        if (testPlan.getTaskId() != null) {
            existingTestPlan.setTaskId(testPlan.getTaskId());
        }
        if (testPlan.getProjectId() != null) {
            existingTestPlan.setProjectId(testPlan.getProjectId());
        }

        return update(id, existingTestPlan);
    }

    public boolean delete(int id) {
        return testPlanRepository.delete(id);
    }
}
