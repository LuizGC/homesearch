CREATE TABLE SUPERMARKETS (
  id serial PRIMARY KEY,
  address_id integer REFERENCES ADDRESSES,
  name VARCHAR
);
