DROP TABLE IF EXISTS PUBLIC.LIKES_FILMS;
DROP TABLE IF EXISTS PUBLIC.FRIENDSHIP;
DROP TABLE IF EXISTS PUBLIC.STATUS_FRIENDSHIP;
DROP TABLE IF EXISTS PUBLIC.USERS;
DROP TABLE IF EXISTS PUBLIC.GENRES_FILMS;
DROP TABLE IF EXISTS PUBLIC.GENRES;
DROP TABLE IF EXISTS PUBLIC.FILMS;
DROP TABLE IF EXISTS PUBLIC.MPA;
--------------------------------------------------------РЕЙТИНГ--------------------------------------------------------
CREATE TABLE IF NOT EXISTS PUBLIC.MPA (
  MPA_ID INTEGER NOT NULL AUTO_INCREMENT,
  NAME VARCHAR_IGNORECASE NOT NULL,
  CONSTRAINT MPA_PK PRIMARY KEY (MPA_ID)
);
CREATE UNIQUE INDEX IF NOT EXISTS PRIMARY_KEY_12 ON PUBLIC.MPA (MPA_ID);
--------------------------------------------------------ФИЛЬМЫ--------------------------------------------------------
CREATE TABLE IF NOT EXISTS PUBLIC.FILMS (
  ID INTEGER NOT NULL AUTO_INCREMENT,
  NAME VARCHAR_IGNORECASE NOT NULL,
  DESCRIPTION VARCHAR_IGNORECASE(200),
  RELEASEDATE DATE NOT NULL,
  DURATION VARCHAR_IGNORECASE,
  MPA_ID INTEGER,
  CONSTRAINT FILM_PK PRIMARY KEY (ID),
  CONSTRAINT FILMS_MPA_FK FOREIGN KEY (MPA_ID) REFERENCES PUBLIC.MPA(MPA_ID) ON DELETE RESTRICT ON
  UPDATE
    RESTRICT
);
CREATE INDEX IF NOT EXISTS FILMS_MPA_FK_INDEX_3 ON PUBLIC.FILMS (MPA_ID);
CREATE UNIQUE INDEX IF NOT EXISTS PRIMARY_KEY_21 ON PUBLIC.FILMS (ID);
--------------------------------------------------------ЖАНРЫ--------------------------------------------------------
CREATE TABLE IF NOT EXISTS PUBLIC.GENRES (
  ID INTEGER NOT NULL AUTO_INCREMENT,
  NAME VARCHAR_IGNORECASE NOT NULL,
  CONSTRAINT GENRES_FILM_PK PRIMARY KEY (ID)
);
CREATE UNIQUE INDEX IF NOT EXISTS PRIMARY_KEY_7 ON PUBLIC.GENRES (ID);
--------------------------------------------------------ЖАНРЫ-ФИЛЬМА--------------------------------------------------------
CREATE TABLE IF NOT EXISTS PUBLIC.GENRES_FILMS (
  FILM_ID INTEGER NOT NULL,
  GENRES_ID INTEGER NOT NULL,
  CONSTRAINT GENRES_FILMS_FILMS_FK FOREIGN KEY (FILM_ID) REFERENCES PUBLIC.FILMS(ID) ON DELETE RESTRICT ON
  UPDATE
    RESTRICT,
    CONSTRAINT GENRES_FILMS_GENRES_FK FOREIGN KEY (GENRES_ID) REFERENCES PUBLIC.GENRES(ID) ON DELETE RESTRICT ON
  UPDATE
    RESTRICT
);
CREATE INDEX IF NOT EXISTS GENRES_FILMS_FILMS_FK_INDEX_F ON PUBLIC.GENRES_FILMS (FILM_ID);
CREATE INDEX IF NOT EXISTS GENRES_FILMS_GENRES_FK_INDEX_F ON PUBLIC.GENRES_FILMS (GENRES_ID);
-------------------------------------------------------- ПОЛЬЗОВАТЕЛИ--------------------------------------------------------
CREATE TABLE IF NOT EXISTS PUBLIC.USERS (
  EMAIL VARCHAR_IGNORECASE NOT NULL,
  LOGIN VARCHAR_IGNORECASE NOT NULL,
  NAME VARCHAR_IGNORECASE,
  BIRTHDAY DATE NOT NULL,
  ID INTEGER NOT NULL AUTO_INCREMENT,
  CONSTRAINT USERS_PK PRIMARY KEY (ID)
);
CREATE UNIQUE INDEX IF NOT EXISTS PRIMARY_KEY_4 ON PUBLIC.USERS (ID);
--------------------------------------------------------СТАТУС ДРУЖБЫ--------------------------------------------------------
CREATE TABLE IF NOT EXISTS PUBLIC.STATUS_FRIENDSHIP (
  STATUS_ID INTEGER NOT NULL AUTO_INCREMENT,
  NAME VARCHAR_IGNORECASE NOT NULL,
  CONSTRAINT STATUS_FRIENDSHIP_PK PRIMARY KEY (STATUS_ID)
);
CREATE UNIQUE INDEX IF NOT EXISTS PRIMARY_KEY_B ON PUBLIC.STATUS_FRIENDSHIP (STATUS_ID);
--------------------------------------------------------ДРУЖБА--------------------------------------------------------
CREATE TABLE IF NOT EXISTS PUBLIC.FRIENDSHIP (
  USER_ID INTEGER NOT NULL AUTO_INCREMENT,
  FRIENDS_ID INTEGER,
  STATUS_ID INTEGER,
  CONSTRAINT FRIENDSHIP_STATUS_FRIENDSHIP_FK FOREIGN KEY (STATUS_ID) REFERENCES PUBLIC.STATUS_FRIENDSHIP(STATUS_ID) ON DELETE RESTRICT ON
  UPDATE
    RESTRICT,
    CONSTRAINT FRIENDSHIP_USERS_FK FOREIGN KEY (USER_ID) REFERENCES PUBLIC.USERS(ID) ON DELETE RESTRICT ON
  UPDATE
    RESTRICT,
    CONSTRAINT FRIENDSHIP_USERS_FK_1 FOREIGN KEY (FRIENDS_ID) REFERENCES PUBLIC.USERS(ID) ON DELETE RESTRICT ON
  UPDATE
    RESTRICT
);
CREATE INDEX IF NOT EXISTS FRIENDSHIP_STATUS_FRIENDSHIP_FK_INDEX_C ON PUBLIC.FRIENDSHIP (STATUS_ID);
CREATE INDEX IF NOT EXISTS FRIENDSHIP_USERS_FK_1_INDEX_B ON PUBLIC.FRIENDSHIP (FRIENDS_ID);
CREATE INDEX IF NOT EXISTS FRIENDSHIP_USERS_FK_INDEX_C ON PUBLIC.FRIENDSHIP (USER_ID);
--------------------------------------------------------ЛАЙКИ-ФИЛЬМА--------------------------------------------------------
CREATE TABLE IF NOT EXISTS PUBLIC.LIKES_FILMS (
  FILM_ID INTEGER NOT NULL,
  USER_ID INTEGER NOT NULL,
  CONSTRAINT LIKES_FILMS_FILMS_FK FOREIGN KEY (FILM_ID) REFERENCES PUBLIC.FILMS(ID) ON DELETE RESTRICT ON
  UPDATE
    RESTRICT,
    CONSTRAINT LIKES_FILMS_USERS_FK FOREIGN KEY (USER_ID) REFERENCES PUBLIC.USERS(ID) ON DELETE RESTRICT ON
  UPDATE
    RESTRICT
);
CREATE INDEX IF NOT EXISTS LIKES_FILMS_FILMS_FK_INDEX_1 ON PUBLIC.LIKES_FILMS (FILM_ID);
CREATE INDEX IF NOT EXISTS LIKES_FILMS_USERS_FK_INDEX_1 ON PUBLIC.LIKES_FILMS (USER_ID);