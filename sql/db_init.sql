--  ALTER USER 'root'@'localhost' IDENTIFIED BY '12345678';

CREATE
    DATABASE IF NOT EXISTS cash_splittingTester;

USE
    cash_splittingTester;

DROP TABLE IF EXISTS friend_chatdb;
DROP TABLE IF EXISTS group_chatdb;
DROP TABLE IF EXISTS passworddb;
DROP TABLE IF EXISTS frienddb;
DROP TABLE IF EXISTS friend_appdb;
DROP TABLE IF EXISTS reminderdb;
DROP TABLE IF EXISTS transactiondb;
DROP TABLE IF EXISTS groupdb;
DROP TABLE IF EXISTS userdb;
DROP TABLE IF EXISTS faqdb;


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
    status          ENUM('valid', 'invalid') NOT NULL DEFAULT 'valid',

    PRIMARY KEY (uid, friend_id),
    FOREIGN KEY (uid)
        REFERENCES userdb(uid),
    FOREIGN KEY (friend_id)
        REFERENCES userdb(uid)
);

CREATE TABLE friend_appdb
(
    aid                 INT NOT NULL AUTO_INCREMENT,
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

CREATE TABLE reminderdb
(
    rid                 INT NOT NULL AUTO_INCREMENT,
    source              INT NOT NULL,
    destination         INT NOT NULL,
    status              ENUM('received', 'pending') NOT NULL DEFAULT 'pending',
    modify_time         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (rid),
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

CREATE TABLE group_chatdb
(
    gcid            INT NOT NULL AUTO_INCREMENT,
    gid             INT NOT NULL,
    uid             INT NOT NULL,   -- this is the sender
    content         VARCHAR(140) NOT NULL, -- single message limit 140 chars
    sendtime        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (gcid),
    FOREIGN KEY (uid)
        REFERENCES userdb(uid),
    FOREIGN KEY (gid)
        REFERENCES groupdb(gid)
);

CREATE TABLE friend_chatdb
(
    fcid            INT NOT NULL AUTO_INCREMENT,
    source             INT NOT NULL,
    destination             INT NOT NULL,   -- this is the sender
    content         VARCHAR(300) NOT NULL, -- single message limit 300 chars
    sendtime        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (fcid),
    FOREIGN KEY (source, destination)
        REFERENCES frienddb(friend_id, uid)
);

CREATE TABLE transactiondb
(
    tid                 INT NOT NULL AUTO_INCREMENT,
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

CREATE TABLE faqdb
(
    faqid               INT NOT NULL AUTO_INCREMENT,
    question            VARCHAR(300) NOT NULL,
    answer              VARCHAR(300) NOT NULL DEFAULT 'no answer',
    PRIMARY KEY (faqid)
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

INSERT INTO passworddb(uid, password)
VALUES (1, '1450575459');

INSERT INTO passworddb(uid, password)
VALUES (2, '1450575459');

INSERT INTO passworddb(uid, password)
VALUES (3, '1450575459');

INSERT INTO passworddb(uid, password)
VALUES (4, '1450575459');

INSERT INTO passworddb(uid, password)
VALUES (5, '1450575459');

INSERT INTO passworddb(uid, password)
VALUES (6, '1450575459');

INSERT INTO passworddb(uid, password)
VALUES (7, '1450575459');

INSERT INTO passworddb(uid, password)
VALUES (8, '1450575459');

INSERT INTO friend_appdb(source, destination)
VALUES (2, 3);

INSERT INTO friend_appdb(source, destination)
VALUES (2, 4);

INSERT INTO friend_appdb(source, destination)
VALUES (4, 2);

INSERT INTO friend_appdb(source, destination)
VALUES (2, 5);

INSERT INTO friend_appdb(source, destination)
VALUES (5, 2);

INSERT INTO friend_appdb(source, destination)
VALUES (3, 5);

INSERT INTO frienddb(friend_id, uid, status)
VALUES (4, 6, 'invalid');

INSERT INTO frienddb(friend_id, uid, status)
VALUES (6, 4, 'invalid');

INSERT INTO frienddb(friend_id, uid)
VALUES (4, 7);

INSERT INTO frienddb(friend_id, uid)
VALUES (7, 4);

INSERT INTO frienddb(friend_id, uid)
VALUES (4, 8);

INSERT INTO frienddb(friend_id, uid)
VALUES (8, 4);

INSERT INTO friend_appdb(source, destination, status)
VALUES (6, 4, 'approved');

INSERT INTO friend_appdb(source, destination, status)
VALUES (7, 4, 'approved');

INSERT INTO friend_appdb(source, destination, status)
VALUES (8, 4, 'approved');

INSERT INTO friend_appdb(source, destination, status)
VALUES (4, 7, 'approved');

INSERT INTO friend_appdb(source, destination, status)
VALUES (4, 6, 'pending');

INSERT INTO friend_appdb(source, destination, status)
VALUES (1, 8, 'pending');

INSERT INTO friend_appdb(source, destination, status)
VALUES (8, 1, 'pending');

INSERT INTO groupdb(gid, uid, groupname)
VALUES (1, 1, 'yuegu-group');

INSERT INTO groupdb(gid, uid, groupname, status)
VALUES (1, 2, 'yuegu-group', 'invalid');

INSERT INTO groupdb(gid, uid, groupname, status)
VALUES (-1, 1, 'yuegu-group', 'invalid');

INSERT INTO group_chatdb(gid, uid, content)
VALUES (1, 1, 'I am the boss of the group, aka yuegu');

INSERT INTO group_chatdb(gid, uid, content)
VALUES (1, 1, 'Call me boss or dalao, you have to');

INSERT INTO friend_chatdb(source, destination, content)
VALUES (4, 7, 'hallo, nice to meet you');

SELECT SLEEP(1);

INSERT INTO friend_chatdb(source, destination, content)
VALUES (7, 4, 'hallo, nice to meet you too');

SELECT SLEEP(1);

INSERT INTO friend_chatdb(source, destination, content)
VALUES (7, 4, 'what\'s your class today?');

SELECT SLEEP(1);

INSERT INTO friend_chatdb(source, destination, content)
VALUES (4, 7, 'CS506, and CS537, they are challenging but interesting');

SELECT SLEEP(1);

INSERT INTO friend_chatdb(source, destination, content)
VALUES (4, 7, 'and I love them very much');

SELECT SLEEP(1);

INSERT INTO friend_chatdb(source, destination, content)
VALUES (4, 7, 'I highly recommend you to take these courses in the next semester');

SELECT SLEEP(1);

INSERT INTO friend_chatdb(source, destination, content)
VALUES (7, 4, 'Okey, I will consider your suggestion');

SELECT SLEEP(1);

INSERT INTO friend_chatdb(source, destination, content)
VALUES (7, 4, 'plus you haven\'t pay my money back, pls pay it');

INSERT INTO faqdb(question, answer)
VALUES ('How to transfer money to your friend?',
        'You cannot transfer money directly in this website,
you can use the provided link to paypal to conduct transaction');

INSERT INTO faqdb(question, answer)
VALUES ('How to ensure the user password security',
        'We use a separate table to store the password of users,
and each password in using hashcode to store them');

INSERT INTO faqdb(question, answer)
VALUES ('Who made this application?', 'A group taking CS 506 in UW-Madison');



