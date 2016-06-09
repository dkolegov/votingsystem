INSERT IGNORE INTO users(username,password,enabled)
VALUES ('visitor1','$2a$11$wxLxN9x6tLq2wcsFDCIrm.lBhTjL05/1s9UPjzOIyx4lqPKNHHPRK', true);--password=qwerty
INSERT IGNORE INTO users(username,password,enabled)
VALUES ('visitor2','$2a$11$DQgCbuhVkWGQMSuKCE6Ei.rh/S7jhH0KoNv3ujuG0A0troRXGso2e', true);--password=123456
INSERT IGNORE INTO users(username,password,enabled)
VALUES ('visitor3','$2a$11$m32aM2.j/V0J4X1Vl29oEOGnmYa5DcAfbgJkJMKam8bF0imsclr56', true);--password=qwerty123
INSERT IGNORE INTO users(username,password,enabled)
VALUES ('admin','$2a$11$m32aM2.j/V0J4X1Vl29oEOGnmYa5DcAfbgJkJMKam8bF0imsclr56', true);--password=qwerty123

INSERT IGNORE INTO user_roles (username, role)
VALUES ('visitor1', 'ROLE_USER');
INSERT IGNORE INTO user_roles (username, role)
VALUES ('visitor2', 'ROLE_USER');
INSERT IGNORE INTO user_roles (username, role)
VALUES ('visitor3', 'ROLE_USER');
INSERT IGNORE INTO user_roles (username, role)
VALUES ('admin', 'ROLE_ADMIN');
--INSERT INTO user_roles (username, role)
--VALUES ('admin', 'USER');