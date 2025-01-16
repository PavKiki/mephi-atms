CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255),
    project_id INT,
    test_plan_id INT
);

CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    task_id INT,
    FOREIGN KEY (task_id) REFERENCES tasks(id)
);

CREATE TABLE test_plans (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    project_id INT,
    task_id INT,
    FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (task_id) REFERENCES tasks(id)
);

ALTER TABLE tasks ADD
    FOREIGN KEY (project_id) REFERENCES projects(id);

ALTER TABLE tasks ADD
    FOREIGN KEY (test_plan_id) REFERENCES test_plans(id);

CREATE TABLE test_cases (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    pre_condition VARCHAR(255),
    post_condition VARCHAR(255),
    project_id INT,
    test_plan_id INT,
    FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (test_plan_id) REFERENCES test_plans(id)
);

CREATE TABLE steps (
    id SERIAL PRIMARY KEY,
    ordering INT NOT NULL,
    action VARCHAR(255),
    expected_result VARCHAR(255),
    test_case_id INT,
    FOREIGN KEY (test_case_id) REFERENCES test_cases(id)
);

INSERT INTO tasks (description)
VALUES
    ('Task 1 description'),
    ('Task 2 description'),
    ('Task 3 description');

INSERT INTO projects (name, task_id) VALUES ('Project Alpha', 1);
UPDATE tasks SET project_id = 1 WHERE id = 1;

INSERT INTO test_plans (name, project_id, task_id) VALUES ('Test Plan 1', 1, 2);
UPDATE tasks SET test_plan_id = 1 WHERE id = 2;

INSERT INTO test_plans (name, project_id) VALUES ('Test Plan 2', 1);

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