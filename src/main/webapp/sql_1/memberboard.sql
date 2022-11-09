CREATE TABLE member2 (
	member_id varchar2(15),
	member_pw varchar2(13),
	member_name varchar2(15),
	member_age number,
	member_gender varchar2(6),
	member_email varchar2(30),
	PRIMARY KEY (member_id)
);

SELECT * FROM member2;



CREATE TABLE memberboard (
	board_num number,
	board_id varchar2(20),
	board_subject varchar2(50),
	board_content varchar2(2000),
	board_file varchar2(50),
	board_re_ref number,
	board_re_lev number,
	board_re_seq number,
	board_readcount number,
	board_date date,
	PRIMARY KEY (board_num)
);

SELECT * FROM memberboard;



ALTER TABLE memberboard ADD CONSTRAINT pk_board_id FOREIGN KEY(board_id)
REFERENCES member2(member_id) ON DELETE CASCADE;