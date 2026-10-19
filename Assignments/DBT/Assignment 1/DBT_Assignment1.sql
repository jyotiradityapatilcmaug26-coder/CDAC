
CREATE DATABASE LibraryDB;

USE LibraryDB;

CREATE TABLE Authors (
    author_id INT PRIMARY KEY,
    name VARCHAR(100),
    country VARCHAR(50)
);
DESCRIBE Authors;

CREATE TABLE Books (
    book_id INT PRIMARY KEY,
    title VARCHAR(150),
    author_id INT,
    published_year YEAR,
    available_copies INT,
    FOREIGN KEY (author_id) REFERENCES Authors(author_id)
);

DESCRIBE Books;

CREATE TABLE Members (
    member_id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15)
);
DESCRIBE Members;

CREATE TABLE Borrow_Records (
    record_id INT PRIMARY KEY,
    member_id INT,
    book_id INT,
    borrow_date DATE,
    return_date DATE,
    FOREIGN KEY (member_id) REFERENCES Members(member_id),
    FOREIGN KEY (book_id) REFERENCES Books(book_id)
);
DESCRIBE Borrow_Records;

ALTER TABLE Books
ADD genre VARCHAR(50);
DESCRIBE Books;
DROP TABLE Borrow_Records;

INSERT INTO Authors (author_id, name, country)
VALUES
(1, 'R. K. Sharma', 'India'),
(2, 'James Clear', 'USA'),
(3, 'Robert C. Martin', 'USA');

SELECT * FROM Authors;

INSERT INTO Books
(book_id, title, author_id, published_year, available_copies, genre)
VALUES
(1, 'Database Systems', 1, 2018, 5, 'Education'),
(2, 'Clean Code', 3, 2008, 4, 'Programming'),
(3, 'Atomic Habits', 2, 2018, 6, 'Self-Help'),
(4, 'SQL Fundamentals', 1, 2020, 3, 'Education'),
(5, 'The Pragmatic Programmer', 3, 2019, 2, 'Programming');

SELECT * FROM Books;

INSERT INTO Members (member_id, name, email, phone)
VALUES
(1, 'Rahul Patil', 'rahul@gmail.com', '9876543210'),
(2, 'Priya Sharma', 'priya@gmail.com', '9876543211'),
(3, 'Amit Joshi', 'amit@gmail.com', '9876543212');

SELECT * FROM Members;

USE LibraryDB;

CREATE TABLE Borrow_Records (
    record_id INT PRIMARY KEY,
    member_id INT,
    book_id INT,
    borrow_date DATE,
    return_date DATE,
    FOREIGN KEY (member_id) REFERENCES Members(member_id),
    FOREIGN KEY (book_id) REFERENCES Books(book_id)
);

SHOW TABLES;

DESCRIBE Borrow_Records;

INSERT INTO Borrow_Records
(record_id, member_id, book_id, borrow_date, return_date)
VALUES
(1, 1, 1, '2026-10-01', '2026-10-10'),
(2, 2, 3, '2026-10-03', '2026-10-12'),
(3, 3, 2, '2026-10-05', NULL),
(4, 1, 4, '2026-10-07', NULL);

SELECT * FROM Borrow_Records;

SELECT *
FROM Books
WHERE published_year > 2015;

-- Question 13
CREATE TABLE Publishers (
    publisher_id INT PRIMARY KEY,
    publisher_name VARCHAR(100)
);

CREATE TABLE Publisher_Books (
    book_id INT PRIMARY KEY,
    publisher_id INT,
    FOREIGN KEY (publisher_id)
        REFERENCES Publishers(publisher_id)
);

-- Question 14
SELECT DISTINCT m.member_id, m.name, m.email
FROM Members m
JOIN Borrow_Records br
    ON m.member_id = br.member_id
JOIN Books b
    ON br.book_id = b.book_id
WHERE b.title = 'Database Systems';

-- Question 15
UPDATE Books
SET available_copies = available_copies - 1
WHERE book_id = 1;

SELECT book_id, title, available_copies
FROM Books
WHERE book_id = 1;

-- Question 16
DELETE FROM Borrow_Records
WHERE member_id = 3;

DELETE FROM Members
WHERE member_id = 3;

-- Question 17
UPDATE Books
SET title = 'Advanced Database Systems'
WHERE book_id = 1;

SELECT * FROM Books
WHERE book_id = 1;

-- Question 18
SELECT
    b.book_id,
    b.title,
    a.name AS author_name
FROM Books b
JOIN Authors a
    ON b.author_id = a.author_id;

-- Question 19
DELETE FROM Books
WHERE published_year < 2000;

SELECT *
FROM Books
WHERE published_year < 2000;

-- Question 20
SELECT
    b.book_id,
    b.title
FROM Books b
LEFT JOIN Borrow_Records br
    ON b.book_id = br.book_id
WHERE br.book_id IS NULL;
