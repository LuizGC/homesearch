CREATE TABLE ADDRESS (
  id serial,
  street VARCHAR,
  number VARCHAR,
  city VARCHAR,
  location geography(Point, 4326)
);
