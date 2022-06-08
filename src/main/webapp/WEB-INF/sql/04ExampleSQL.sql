USE mydb6;

CREATE TABLE Member (
	id VARCHAR(20) PRIMARY KEY,
    password VARCHAR(20) NOT NULL,
    email VARCHAR(20) NOT NULL UNIQUE,
    nickName VARCHAR(20) NOT NULL UNIQUE,
    
    inserted DATETIME NOT NULL DEFAULT NOW()
    );
    
    DESC Member;
    
    CREATE TABLE Auth (
		memberId VARCHAR(20) NOT NULL,
        role VARCHAR(20) NOT NULL,
        FOREIGN KEY (memberId) REFERENCES Member(id)
        );
    ALTER TABLE Member
    MODIFY COLUMN email VARCHAR(50) NOT NULL UNIQUE;
    
	ALTER TABLE Member
    MODIFY COLUMN password VARCHAR(100) NOT NULL;
    
    SELECT * FROM Member;
    SELECT * FROM Auth;
    INSERT INTO Auth
    VALUES ('admin','ROLE_ADMIN');
    
    
    INSERT INTO Auth (memberId, role)
    (SELECT id, 'ROLE_USER'
    FROM Member
    WHERE id NOT IN (SELECT memberId FROM Auth));
-- Board 테이블에 Member의 id 참조하는 칼럼 추가
DESC Board;
ALTER TABLE Board
ADD COLUMN memberId VARCHAR(20) NOT NULL REFERENCES Member(id) AFTER body;

ALTER TABLE Board
MODIFY COLUMN memberId VARCHAR(20) NOT NULL;

SELECT * FROM Board;
DESC Reply;

ALTER TABLE Reply
ADD COLUMN memberId VARCHAR(20) NOT NULL DEFAULT 'user' REFERENCES Member(id);

ALTER TABLE Reply
MODIFY COLUMN memberId VARCHAR(20) NOT NULL AFTER content;


