package ru.dsis.atms.testmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.testmanagement.dao.StepDao;
import ru.dsis.atms.testmanagement.dto.StepDto;
import ru.dsis.atms.testmanagement.repository.StepsRepository;

import java.util.List;

@Service
public class StepsService {

    @Autowired
    StepsRepository stepsRepository;

    public List<StepDao> findAllSteps() {
        return stepsRepository.findAll();
    }

    public StepDao save(StepDto stepDto) {
        return stepsRepository.save(stepDto);
    }

    public StepDao findStepById(int id) {
        return stepsRepository.findById(id);
    }

    public StepDao update(int id, StepDto stepDto) {
        return stepsRepository.update(id, stepDto);
    }

    public StepDao partialUpdate(int id, StepDto stepDto) {
        var existingStep = stepsRepository.findById(id);
        if (existingStep == null) {
            return null;
        }

        if (stepDto.getOrdering() != null) {
            existingStep.setOrdering(stepDto.getOrdering());
        }
        if (stepDto.getAction() != null) {
            existingStep.setAction(stepDto.getAction());
        }
        if (stepDto.getExpectedResult() != null) {
            existingStep.setExpectedResult(stepDto.getExpectedResult());
        }
        if (stepDto.getActualResult() != null) {
            existingStep.setActualResult(stepDto.getActualResult());
        }
        if (stepDto.getStatus() != null) {
            existingStep.setStatus(stepDto.getStatus());
        }
        if (stepDto.getTestCaseId() != null) {
            existingStep.setTestCaseId(stepDto.getTestCaseId());
        }
        return stepsRepository.update(id, stepDto);
    }

    public boolean delete(int id) {
        return stepsRepository.delete(id);
    }
}
