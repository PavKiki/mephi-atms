CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE test_plans (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    task_key VARCHAR(255) NOT NULL,
    project_id INT,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);

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

INSERT INTO projects (name) VALUES ('Project Alpha');
INSERT INTO projects (name) VALUES ('Project Beta');

INSERT INTO test_plans (name, task_key, project_id) VALUES ('Test Plan 1', 'BCA-007', 2);
INSERT INTO test_plans (name, task_key) VALUES ('Test Plan 2', 'ABC-001');

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