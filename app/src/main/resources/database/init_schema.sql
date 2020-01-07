CREATE TABLE IF NOT EXISTS `user` (
  id             VARCHAR(36)  NOT NULL,
  email          VARCHAR(255) NOT NULL,
  password       VARCHAR(255) NULL,
  PRIMARY KEY (id)
);