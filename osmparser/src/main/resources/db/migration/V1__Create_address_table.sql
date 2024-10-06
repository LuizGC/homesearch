CREATE TABLE ADDRESSES (
  id serial PRIMARY KEY,
  street VARCHAR,
  number VARCHAR,
  city VARCHAR,
  location geography(Point, 4326)
);
