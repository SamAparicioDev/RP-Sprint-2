DROP TABLE IF EXISTS user_table;
CREATE TABLE IF NOT EXISTS user_table(
  id SERIAL PRIMARY KEY,
  username varchar(60) UNIQUE NOT NULL,
  email varchar(60) UNIQUE NOT NULL,
  password varchar(60)  NOT NULL,
  first_name varchar(60) NOT NULL,
  last_name varchar(60) NOT NULL,
  age int not null
 );