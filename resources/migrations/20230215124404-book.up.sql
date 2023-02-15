CREATE TABLE book (
  id serial PRIMARY KEY,
  name VARCHAR ( 50 ) UNIQUE NOT NULL,
  ibsn VARCHAR ( 50 )  UNIQUE,
  describe  VARCHAR (200),
  quantity integer default 0
);

CREATE TABLE book_inst (
  id serial PRIMARY KEY,
  book_id serial NOT NULL,
  note VARCHAR (60)
);

ALTER TABLE book_inst ADD CONSTRAINT fk_book_inst__book FOREIGN KEY (book_id) REFERENCES book (id) ON delete CASCADE;

SELECT 3;
