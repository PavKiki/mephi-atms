CREATE TYPE TASK_STATUS_ENUM AS ENUM ('SUCCESS', 'FAIL', 'TO DO');

CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE test_plans (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    task_id INT NOT NULL,
    status TASK_STATUS_ENUM DEFAULT 'TO DO',
    project_id INT NOT NULL,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

CREATE TABLE test_cases (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    pre_condition VARCHAR(255),
    post_condition VARCHAR(255),
    status TASK_STATUS_ENUM DEFAULT 'TO DO',
    project_id INT,
    test_plan_id INT,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    FOREIGN KEY (test_plan_id) REFERENCES test_plans(id) ON DELETE SET NULL
);

CREATE TABLE steps (
    id SERIAL PRIMARY KEY,
    ordering INT NOT NULL,
    action VARCHAR(255),
    expected_result VARCHAR(255),
    actual_result VARCHAR(255),
    status TASK_STATUS_ENUM DEFAULT 'TO DO',
    test_case_id INT NOT NULL,
    FOREIGN KEY (test_case_id) REFERENCES test_cases(id) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION set_project_id_on_test_case_insert()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.test_plan_id IS NOT NULL THEN
        SELECT project_id INTO NEW.project_id
        FROM test_plans
        WHERE test_plans.id = NEW.test_plan_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_test_case_status()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.status = 'SUCCESS' AND (OLD.status = 'FAIL' OR OLD.status = 'TO DO') THEN
        IF NOT EXISTS (
            SELECT 1
            FROM steps
            WHERE test_case_id = NEW.test_case_id
            AND (status = 'FAIL' OR status = 'TO DO')
        ) THEN
            UPDATE test_cases
            SET status = 'SUCCESS'
            WHERE id = NEW.test_case_id;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_test_plan_status()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.status = 'SUCCESS' AND (OLD.status = 'FAIL' OR OLD.status = 'TO DO') AND NEW.test_plan_id IS NOT NULL THEN
        IF NOT EXISTS (
            SELECT 1
            FROM test_cases
            WHERE test_plan_id = NEW.test_plan_id
            AND (status = 'FAIL' OR status = 'TO DO')
        ) THEN
            UPDATE test_plans
            SET status = 'SUCCESS'
            WHERE id = NEW.test_plan_id;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_project_id_on_test_case_insert_trigger
BEFORE INSERT ON test_cases
FOR EACH ROW
EXECUTE FUNCTION set_project_id_on_test_case_insert();

CREATE TRIGGER check_steps_status
AFTER UPDATE OF status ON steps
FOR EACH ROW
WHEN (NEW.status = 'SUCCESS')
EXECUTE FUNCTION update_test_case_status();

CREATE TRIGGER check_test_cases_status
AFTER UPDATE OF status ON test_cases
FOR EACH ROW
WHEN (NEW.status = 'SUCCESS')
EXECUTE FUNCTION update_test_plan_status();

INSERT INTO projects (name) VALUES ('Project Alpha');
INSERT INTO projects (name) VALUES ('Project Beta');

INSERT INTO test_plans (name, task_id, project_id) VALUES ('Test Plan 1', 1, 2);
INSERT INTO test_plans (name, task_id, project_id) VALUES ('Test Plan 2', 2, 1);

INSERT INTO test_cases (name, pre_condition, post_condition, test_plan_id)
VALUES ('Test Case 1', 'Pre Condition 1', 'Post Condition 1', 1);
INSERT INTO test_cases (name, pre_condition, post_condition, project_id)
VALUES ('Test Case 2', 'Pre Condition 2', 'Post Condition 2', 1);

INSERT INTO steps (ordering, action, expected_result, test_case_id)
VALUES (1, 'Step 1 Action', 'Step 1 Expected Result', 1);
INSERT INTO steps (ordering, action, expected_result, test_case_id)
VALUES (2, 'Step 2 Action', 'Step 2 Expected Result', 1);
INSERT INTO steps (ordering, action, expected_result, test_case_id)
VALUES (1, 'Step 1 Action', 'Step 1 Expected Result', 2);
INSERT INTO steps (ordering, action, expected_result, test_case_id)
VALUES (2, 'Step 2 Action', 'Step 2 Expected Result', 2);