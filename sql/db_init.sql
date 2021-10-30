--  ALTER USER 'root'@'localhost' IDENTIFIED BY '12345678';

CREATE
    DATABASE IF NOT EXISTS cash_splittingTester;

USE
    cash_splittingTester;

DROP TABLE IF EXISTS passworddb;
DROP TABLE IF EXISTS frienddb;
DROP TABLE IF EXISTS friend_appdb;
DROP TABLE IF EXISTS transactiondb;
DROP TABLE IF EXISTS groupdb;
DROP TABLE IF EXISTS userdb;


CREATE TABLE userdb
(
    uid       INT NOT NULL AUTO_INCREMENT,  -- auto_increment start with 1
    username  VARCHAR(50) NOT NULL UNIQUE,
    firstname VARCHAR(50) NOT NULL,
    lastname  VARCHAR(50) NOT NULL,
    email     VARCHAR(50) DEFAULT 'example@example.com',
    total_balance   REAL NOT NULL DEFAULT 0,
    borrowed REAL NOT NULL DEFAULT 0,
    lent     REAL NOT NULL DEFAULT 0,
    default_currency ENUM('USD', 'CNY', 'EUR', 'JPY', 'GBP', 'SGD', 'CAD', 'AUD') NOT NULL DEFAULT 'USD',
    PRIMARY KEY (uid)
);

CREATE TABLE passworddb
(
    pid             INT NOT NULL AUTO_INCREMENT,  -- auto_increment start with 1
    uid             INT NOT NULL,
    password        VARCHAR(50) NOT NULL,

    PRIMARY KEY (pid),
    FOREIGN KEY (uid)
        REFERENCES userdb(uid)
);

CREATE TABLE frienddb
(
    friend_id       INT NOT NULL,
    uid             INT NOT NULL,
    status          ENUM('valid', 'invalid'),

    PRIMARY KEY (uid, friend_id),
    FOREIGN KEY (uid)
        REFERENCES userdb(uid),
    FOREIGN KEY (friend_id)
        REFERENCES userdb(uid)
);

CREATE TABLE friend_appdb
(
    aid                 INT NOT NULL AUTO_INCREMENT, -- auto_increment start with 1
    source              INT NOT NULL,
    destination         INT NOT NULL,
    status              ENUM('approved', 'denied', 'pending') NOT NULL DEFAULT 'pending',
    modify_time         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (aid),
    FOREIGN KEY (source)
        REFERENCES userdb(uid),
    FOREIGN KEY (destination)
        REFERENCES userdb(uid)
);

CREATE TABLE groupdb
(
    gid             INT NOT NULL,
    uid             INT NOT NULL,
    groupname       VARCHAR(50) NOT NULL,
    status          ENUM('valid', 'invalid') NOT NULL DEFAULT 'valid',

    PRIMARY KEY (gid, uid),
    FOREIGN KEY (uid)
        REFERENCES userdb(uid)
);

CREATE TABLE transactiondb
(
    tid                 INT NOT NULL AUTO_INCREMENT, -- auto_increment start with 1
    source              INT NOT NULL,
    destination         INT NOT NULL,
    currency            ENUM('USD', 'CNY', 'EUR', 'JPY', 'GBP', 'SGD', 'CAD', 'AUD') NOT NULL DEFAULT 'USD',
    status              ENUM('paid', 'unpaid') NOT NULL DEFAULT 'unpaid',
    amount              REAL NOT NULL,
    comment             VARCHAR(100) NOT NULL DEFAULT 'Transaction',
    create_time         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_time         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    gid                 INT NOT NULL DEFAULT -1,    -- -1 means individual transaction

    PRIMARY KEY (tid),
    FOREIGN KEY (source)
        REFERENCES userdb(uid),
    FOREIGN KEY (destination)
        REFERENCES userdb(uid),
    FOREIGN KEY (gid)
        REFERENCES groupdb(gid)
);

INSERT INTO userdb(username, firstname, lastname)
VALUES ('yuegu', 'yueyu', 'wang');

INSERT INTO userdb(username, firstname, lastname)
VALUES ('leo', 'zhuocheng', 'sun');

INSERT INTO userdb(username, firstname, lastname)
VALUES ('hyuan63', 'haozhan', 'yuan');

INSERT INTO userdb(username, firstname, lastname)
VALUES ('ccc', 'cecheng', 'chen');

INSERT INTO userdb(username, firstname, lastname)
VALUES ('lhg', 'heguang', 'lin');

INSERT INTO userdb(username, firstname, lastname)
VALUES ('phyTA', 'kairui', 'zhang');

INSERT INTO userdb(username, firstname, lastname)
VALUES ('Insipid', 'xulin', 'yang');

INSERT INTO userdb(username, firstname, lastname)
VALUES ('Zhijiang', 'zihan', 'zhu');