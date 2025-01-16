CREATE TABLE permissions (
    id SERIAL PRIMARY KEY,
    permission_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    role_description VARCHAR(50) UNIQUE NOT NULL,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50) NOT NULL,
    role_name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_name) REFERENCES roles(role_name)
);

CREATE TABLE tokens (
    id SERIAL PRIMARY KEY,
    token VARCHAR(512) UNIQUE NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE role_permissions (
    role_name VARCHAR(50) NOT NULL,
    permission_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (role_name, permission_name),
    FOREIGN KEY (role_name) REFERENCES roles(role_name),
    FOREIGN KEY (permission_name) REFERENCES permissions(permission_name)
);

INSERT INTO permissions (permission_name)
VALUES
('READ'),
('EXECUTE'),
('EDIT'),
('CREATE'),
('DELETE'),
('REGISTER'),
('PROJECT');

INSERT INTO roles (role_name, role_description)
VALUES
('DIRECTOR', 'Руководитель'),
('TEST_ANALYST', 'Тест-аналитик'),
('TESTER', 'Тестировщик');

INSERT INTO role_permissions (role_name, permission_name)
VALUES
('TESTER', 'READ'),
('TESTER', 'EXECUTE'),
('TEST_ANALYST', 'READ'),
('TEST_ANALYST', 'EXECUTE'),
('TEST_ANALYST', 'EDIT'),
('TEST_ANALYST', 'CREATE'),
('TEST_ANALYST', 'DELETE'),
('DIRECTOR', 'READ'),
('DIRECTOR', 'EXECUTE'),
('DIRECTOR', 'EDIT'),
('DIRECTOR', 'CREATE'),
('DIRECTOR', 'DELETE'),
('DIRECTOR', 'REGISTER'),
('DIRECTOR', 'PROJECT');

INSERT INTO users (username, password, name, role_name)
VALUES
('string', 'string', 'string', 'DIRECTOR'),
('AbraKadabra228', '12345678', 'Walter', 'TEST_ANALYST'),
('Champion', '87654321', 'John', 'TESTER');