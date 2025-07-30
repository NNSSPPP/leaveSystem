# leaveSystem

leaveSystem is personal leave management system that can submit requestformleave apporved,rejected and view static leave and export in excel using visual studio code, intellij, PostgreSQL

## Features

* dashboard show summary with one user
* submit requestformleave
* update status : approved,rejected
* view static leave
* export excel with static leave

## Tool

* Frontend
* * visual studio code :running program frontend
* * Angular version 18
 ```
#check angular version
ng version
 ```

* Backend
**  IntelliJ : running program backend
**  Spring Framework version 3.3.0
**  JDK version 17.0
**  PostgreSQL version 15.13 : running program database
**  Maven version 3.9.11
**  Swagger

```
#check version JDK
java --version
 ```

# How to use backend 
* After You have tool already open flie leaveSystemBE At IntelliJ
* Created database and table at PostgreSQL (database name leaveSystem)
```
CREATE TABLE users (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL,
    department VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
 ```
```
CREATE TABLE leave_types (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    max_days INTEGER
);

 ```
INSERT INTO leave_types (name, description, max_days)
VALUES 
  ('ลาพักร้อน', 'การลาพักร้อนประจำปี', 10),
  ('ลาป่วย', 'การลาป่วยตามสิทธิ์', 30),
  ('ลากิจ', 'การลากิจส่วนตัว', 7);

```
CREATE TABLE leave_types (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    max_days INTEGER
);

 ```
```
CREATE TABLE leave_requests (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    leave_type_id INTEGER NOT NULL REFERENCES leave_types(id),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(20),
    reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
 ```
```
CREATE TABLE leave_balances (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    leave_type_id INTEGER NOT NULL REFERENCES leave_types(id),
    year INTEGER NOT NULL,
    remaining_days INTEGER NOT NULL 
);
 ```
* Next step you run in IntelliJ
* Finally you open Swagger link: http://localhost:8080/swagger-ui/index.html#/

# How to use frontend 
* After You have tool already open flie leaveSystemFE At visual studio code
* install boostarp,xlsx saver
```
#install boostarp
npm install boostarp
 ```
```
#install xlsx saver (for export excel)
npm install xlsx file-saver

npm install --save-dev @types/file-saver

 ```

* Next step you run in terminal
  ```
ng serve

 ```
* Finally you open web with http://localhost:4200/

# You can use this System

# Confess

* Don't do sonarqube eith leaveSystemFE and leaveSystemFE



