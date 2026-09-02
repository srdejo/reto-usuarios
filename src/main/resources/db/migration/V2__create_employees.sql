CREATE TABLE employees (
   employee_id   BIGINT PRIMARY KEY REFERENCES users(user_id),
   restaurant_id BIGINT NOT NULL
);
