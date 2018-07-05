--
-- This will create the table we will use to keep track of all json objects
--

CREATE TABLE `json` (
  `path`	TEXT NOT NULL UNIQUE,
  `value`	TEXT,
  PRIMARY KEY(`path`)
);