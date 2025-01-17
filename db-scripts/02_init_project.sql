CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    summary VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    test_plan_id INT
);

CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE test_plans (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    project_id INT,
    task_id INT,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE
);

ALTER TABLE tasks ADD
    FOREIGN KEY (test_plan_id) REFERENCES test_plans(id) ON DELETE SET NULL;

CREATE TABLE test_cases (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    pre_condition VARCHAR(255),
    post_condition VARCHAR(255),
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
    test_case_id INT,
    FOREIGN KEY (test_case_id) REFERENCES test_cases(id) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION update_task_with_test_plan_id()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.task_id IS NOT NULL THEN
        UPDATE tasks SET test_plan_id = NEW.id WHERE id = NEW.task_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_task
AFTER INSERT ON test_plans
FOR EACH ROW
EXECUTE FUNCTION update_task_with_test_plan_id();

CREATE OR REPLACE FUNCTION update_test_plan_with_task_id()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.test_plan_id IS NOT NULL THEN
        UPDATE test_plans SET task_id = NEW.id WHERE id = NEW.test_plan_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_task
AFTER INSERT ON tasks
FOR EACH ROW
EXECUTE FUNCTION update_test_plan_with_task_id();

INSERT INTO tasks (summary, description)
VALUES
    ('Task 1', 'Task 1 description'),
    ('Task 2', 'Task 2 description'),
    ('Task 3', 'Task 3 description');

INSERT INTO projects (name) VALUES ('Project Alpha');

INSERT INTO test_plans (name, project_id, task_id) VALUES ('Test Plan 1', 1, 2);
INSERT INTO test_plans (name, project_id) VALUES ('Test Plan 2', 1);
INSERT INTO tasks (summary, description, test_plan_id) VALUES ('Task 4', 'Task 4 description', 2);

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