--  ALTER USER 'root'@'localhost' IDENTIFIED BY '12345678';

CREATE
    DATABASE IF NOT EXISTS cash_splittingTester;

USE
    cash_splittingTester;

DROP TABLE IF EXISTS repairdb;
DROP TABLE IF EXISTS applicationdb;
DROP TABLE IF EXISTS propertydb;
DROP TABLE IF EXISTS historydb;
DROP TABLE IF EXISTS userdb;

CREATE TABLE userdb
(
    uid       INT NOT NULL AUTO_INCREMENT,  -- auto_increment start with 1
    user_type ENUM('owner', 'renter') DEFAULT 'renter',
    username  VARCHAR(50) NOT NULL UNIQUE,
    password  VARCHAR(50) NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    lastname  VARCHAR(50) NOT NULL,
    email     VARCHAR(50) DEFAULT 'example@example.com',             -- optional info
    gender    ENUM('male', 'female', 'non-binary', 'prefer not to say') NOT NULL DEFAULT 'prefer not to say',
    balance   REAL NOT NULL DEFAULT 0,                    -- owner can have a NULL balance
    signed    ENUM('accepted','pending','rejected','none') NOT NULL DEFAULT 'none',
    address   VARCHAR(50) NOT NULL DEFAULT '',
    PRIMARY KEY (uid)
);

CREATE TABLE propertydb
(
    pid             INT  NOT NULL AUTO_INCREMENT,  -- auto_increment start with 1
    address         VARCHAR(100) NOT NULL,
    rid             INT NOT NULL DEFAULT 1,
    monthly_rent    REAL NOT NULL,
    type            ENUM('studio', '1b', '2b2b', '3b2b', '4b2b') NOT NULL,
    pname           VARCHAR(50) NOT NULL,

    PRIMARY KEY (pid),
    FOREIGN KEY (rid)
        REFERENCES userdb(uid)
);

CREATE TABLE applicationdb
(
    aid       INT NOT NULL AUTO_INCREMENT, -- auto_increment start with 1
    rid       INT NOT NULL,
    oid       INT NOT NULL,
    pid       INT NOT NULL,
    status    ENUM('processing', 'approved', 'denied') NOT NULL DEFAULT 'processing',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (aid),
    FOREIGN KEY (rid)
        REFERENCES userdb(uid),
    FOREIGN KEY (oid)
        REFERENCES userdb(uid),
    FOREIGN KEY (pid)
        REFERENCES propertydb(pid)

);

CREATE TABLE historydb
(
    cid                 INT NOT NULL AUTO_INCREMENT, -- auto_increment start with 1
    description         VARCHAR(100) NOT NULL DEFAULT 'Transaction',
    destination_id      INT NOT NULL,
    amount              REAL NOT NULL,
    time                DATETIME DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (cid),
    FOREIGN KEY (destination_id)
        REFERENCES userdb(uid)
);

CREATE TABLE repairdb
(
    mid                 INT NOT NULL AUTO_INCREMENT, -- auto_increment start with 1
    uid                 INT NOT NULL,
    description         VARCHAR(100) NOT NULL DEFAULT 'x broke',
    pid                 INT NOT NULL,
    status              ENUM('processing', 'completed') NOT NULL,
    create_time         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modify_time         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    priority            INT NOT NULL,                 -- 0: high, 1:medium, 2:low

    PRIMARY KEY (mid),
    FOREIGN KEY (pid)
        REFERENCES propertydb(pid)
);

INSERT INTO userdb (user_type, username, password, firstname, lastname, email, gender)
VALUES ('owner', 'admin', '12345', 'ad', 'min', 'admin@wisc.edu', 'female');

INSERT INTO userdb (user_type, username, password, firstname, lastname, email, gender, balance, signed, address)
VALUES ('renter', 'zsun273', '1234567', 'Zhuocheng', 'Sun', 'zsun273@wisc.edu', 'male', 0,
        'accepted', '777 University Ave, Apt 1027');

INSERT INTO userdb (user_type, username, password, firstname, lastname, email, gender, balance, signed, address)
VALUES ('renter', 'wang2537', '123456', 'Yueyu', 'Wang', 'wang2537@wisc.edu', 'male', 0,
        'accepted', '777 University Ave, Apt 426');

INSERT INTO userdb (user_type, username, password, firstname, lastname, email, gender, balance, signed, address)
VALUES ('renter', 'hyuan63', '123456', 'Haozhan', 'Yuan', 'hyuan63@wisc.edu', 'male', 0,
        'accepted', '777 University Ave, Apt 1006');

INSERT INTO userdb (user_type, username, password, firstname, lastname, email, gender, balance, signed, address)
VALUES ('renter', 'jzhang2279', '123456', 'Jinming', 'Zhang', 'jzhang2279@wisc.edu', 'male', 0,
        'accepted', '777 University Ave, Apt 1207');

INSERT INTO userdb (user_type, username, password, firstname, lastname, email, gender, balance, signed)
VALUES ('renter', 'rcao34', '123456', 'Ruoxi', 'Cao', 'rcao34@wisc.edu', 'female', 0,
        'pending');

INSERT INTO propertydb (address, rid, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 1027', 2, 1130, '2b2b', 'Lucky');

INSERT INTO propertydb (address, rid, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 426', 3, 1600, '2b2b', 'Lucky');

INSERT INTO propertydb (address, rid, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 1006', 4, 1100, '2b2b', 'Lucky');

INSERT INTO propertydb (address, rid, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 1207', 5, 1200, '2b2b', 'Lucky');

INSERT INTO propertydb (address, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 606', 1100, '3b2b', 'Lucky');

INSERT INTO propertydb (address, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 1106', 1100, '2b2b', 'Lucky');

INSERT INTO propertydb (address, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 708', 1100, '4b2b', 'Lucky');

INSERT INTO propertydb (address, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 709', 1300, '4b2b', 'Lucky');

INSERT INTO propertydb (address, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 710', 1500, '4b2b', 'Lucky');

INSERT INTO propertydb (address, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 711', 1200, '4b2b', 'Lucky');

INSERT INTO propertydb (address, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 712', 1000, '4b2b', 'Lucky');

INSERT INTO propertydb (address, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 713', 900, '4b2b', 'Lucky');

INSERT INTO propertydb (address, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 714', 1150, '4b2b', 'Lucky');

INSERT INTO propertydb (address, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 715', 1350, '4b2b', 'Lucky');

INSERT INTO propertydb (address, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 716', 1600, '4b2b', 'Lucky');

INSERT INTO propertydb (address, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 717', 2000, '4b2b', 'Lucky');

INSERT INTO propertydb (address, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 718', 2100, '4b2b', 'Lucky');

INSERT INTO propertydb (address, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 720', 1100, '4b2b', 'Lucky');

INSERT INTO propertydb (address, monthly_rent, type, pname)
VALUES ('777 University Ave, Apt 1107', 3200, '4b2b', 'Lucky');

INSERT INTO repairdb (uid, description, pid, status, priority)
VALUES (2, 'Stucked Toilet', 1, 'processing', 0);

INSERT INTO repairdb (uid, description, pid, status, create_time, modify_time, priority)
VALUES (3, 'Blinking light', 2, 'completed', '2021-09-27 8:20:00', '2021-09-27 8:20:00', 2);

INSERT INTO repairdb (uid, description, pid, status, priority)
VALUES (4, 'Oven Malfunction', 3, 'processing', 2);

INSERT INTO repairdb (uid, description, pid, status, priority)
VALUES (5, 'Emergency problem', 4, 'processing', 0);

INSERT INTO historydb (description, destination_id, amount)
VALUES ('lease', 2, 1600);

INSERT INTO historydb (description, destination_id, amount)
VALUES ('lease', 3, 1200);

INSERT INTO historydb (description, destination_id, amount)
VALUES ('lease', 4, 950);

INSERT INTO historydb (description, destination_id, amount)
VALUES ('lease', 2, 1600);

INSERT INTO historydb (description, destination_id, amount)
VALUES ('lease', 3, 950);

INSERT INTO applicationdb (rid, oid, pid)
VALUES (6, 1, 19);