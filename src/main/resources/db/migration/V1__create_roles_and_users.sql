CREATE TABLE roles (
   role_id          BIGSERIAL PRIMARY KEY,
   name      VARCHAR(30) NOT NULL UNIQUE,
   description VARCHAR(150)
);

INSERT INTO roles (name, description) VALUES
('ADMIN',    'Administrador del sistema, crea propietarios y restaurantes'),
('OWNER',    'Propietario de restaurante, crea empleados y gestiona su menú'),
('EMPLOYEE', 'Empleado de un restaurante'),
('CUSTOMER', 'Cliente de la plazoleta de comidas');

CREATE TABLE users (
   user_id    BIGSERIAL PRIMARY KEY,
   name       VARCHAR(50),
   last_name  VARCHAR(50),
   document   VARCHAR(15),
   phone      VARCHAR(13),
   birth_date DATE,
   email      VARCHAR(100) UNIQUE,
   password   VARCHAR(100),
   role_id    BIGINT NOT NULL REFERENCES roles(role_id)
);