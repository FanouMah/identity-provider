CREATE TABLE session_users(
   Id_session_user SERIAL,
   nom VARCHAR(255)  NOT NULL,
   prenom VARCHAR(255)  NOT NULL,
   date_naissance DATE NOT NULL,
   email VARCHAR(255)  NOT NULL,
   password VARCHAR(255)  NOT NULL,
   date_demande DATE NOT NULL,
   PRIMARY KEY(Id_session_user),
   UNIQUE(email)
);

CREATE TABLE users(
   Id_users SERIAL,
   nom VARCHAR(255)  NOT NULL,   
    prenom VARCHAR(255)  NOT NULL,
   date_inscription TIMESTAMP NOT NULL,
   date_naissance DATE NOT NULL,
   email VARCHAR(255)  NOT NULL,
   password VARCHAR(255)  NOT NULL,
   nombre_tentative INTEGER NOT NULL,
   PRIMARY KEY(Id_users),
   UNIQUE(email)
);

CREATE TABLE token_inscription(
   Id_token_inscription SERIAL,
   code VARCHAR(255)  NOT NULL,
   date_expiration TIMESTAMP NOT NULL,
   Id_session_user INTEGER NOT NULL,
   PRIMARY KEY(Id_token_inscription),
   UNIQUE(code),
   FOREIGN KEY(Id_session_user) REFERENCES session_users(Id_session_user)
);

CREATE TABLE historique_tentative(
   Id_historique_tentative SERIAL,
   date_tentative TIMESTAMP NOT NULL,
   PRIMARY KEY(Id_historique_tentative)
);

CREATE TABLE token_connexion(
   Id_token_connexion SERIAL,
   code VARCHAR(255)  NOT NULL,
   Id_users INTEGER NOT NULL,
   PRIMARY KEY(Id_token_connexion),
   UNIQUE(code),
   FOREIGN KEY(Id_users) REFERENCES users(Id_users)
);
