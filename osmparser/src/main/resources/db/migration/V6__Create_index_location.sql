CREATE INDEX address_location_idx
  ON addresses
  USING GIST (location);