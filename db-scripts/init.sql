CREATE TABLE permissions (
    id SERIAL PRIMARY KEY,
    permission_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50) NOT NULL,
    role_id INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE role_permissions (
    role_id INT NOT NULL,
    permission_id INT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (permission_id) REFERENCES permissions(id)
);

CREATE OR REPLACE FUNCTION set_created_at()
RETURNS TRIGGER AS $$
BEGIN
  NEW.created_at = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_your_table
BEFORE INSERT ON users
FOR EACH ROW
EXECUTE FUNCTION set_created_at();

INSERT INTO permissions (id, permission_name)
VALUES
(0, 'READ'),
(1, 'WRITE'),
(2, 'MODIFY');

INSERT INTO roles (id, role_name)
VALUES
(0, 'ADMIN'),
(1, 'USER');

INSERT INTO role_permissions (role_id, permission_id)
VALUES
(0, 0),
(0, 1),
(0, 2),
(1, 0);

INSERT INTO users (username, password, name, role_id)
VALUES
('AbraKadabra228', '12345678', 'Walter', 0),
('Champion', '87654321', 'John', 1);