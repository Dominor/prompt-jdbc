DROP DATABASE IF EXISTS ac;
CREATE DATABASE ac;
USE ac;
CREATE TABLE user(
  id integer AUTO_INCREMENT,
  username CHAR(64),
  password CHAR(64),
  email CHAR(64),
  firstname CHAR(64),
  lastname CHAR(64),
  phone CHAR(64),
  primary key (id)
);

INSERT INTO user VALUES(1, "vitor", "2easy2guess", "noneyourbusiness@masterhacker.com", "Vitor", "Ferreira", "NoneYourBusinessAgain");
INSERT INTO user VALUES(2, "socrates", "myCountryIsTheWorld", "master.philosopher@naturalphilosophy.club", "Socrates", "NotPM", "TooOldToHaveOne");
INSERT INTO user VALUES(3, "Descartes", "cogitoergosum", "thinker@naturalphilosophy.club", "René", "Descartes", "IdkAboutItThusItDoesNotExist");