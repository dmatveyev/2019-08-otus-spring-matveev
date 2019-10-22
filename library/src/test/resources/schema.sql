DROP TABLE IF EXISTS PERSONS;
DROP TABLE IF EXISTS AUTHORS;
DROP TABLE IF EXISTS GENRES;
DROP TABLE IF EXISTS BOOKS;

CREATE TABLE PERSONS(ID UUID PRIMARY KEY, NAME VARCHAR(255));
CREATE TABLE AUTHORS(ID UUID PRIMARY KEY, NAME VARCHAR(255));
CREATE TABLE GENRES(ID UUID PRIMARY KEY, NAME VARCHAR(255), CODE VARCHAR (100));
CREATE TABLE BOOKS(
ID UUID PRIMARY KEY,
NAME VARCHAR(255),
GENRE_ID UUID,
ISBN VARCHAR(255),
AUTHOR_ID UUID );

 ALTER TABLE BOOKS
    ADD FOREIGN KEY (AUTHOR_ID)
    REFERENCES AUTHORS;
 ALTER TABLE BOOKS
    ADD FOREIGN KEY (GENRE_ID)
    REFERENCES GENRES;
