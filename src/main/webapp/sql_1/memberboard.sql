CREATE TABLE member3 (
	member_id varchar2(15),
	member_pw varchar2(13),
	member_name varchar2(15),
	member_age number,
	member_gender varchar2(6),
	member_email varchar2(30),
	member_address varchar2(50),
	PRIMARY KEY (member_id)
);

SELECT * FROM member3;



CREATE TABLE memberboard_2 (
	board_num number,
	board_id varchar2(20),
	board_address varchar2(50),
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

SELECT * FROM memberboard_2;



ALTER TABLE memberboard_2 ADD CONSTRAINT pk_board_id_2 FOREIGN KEY(board_id)
REFERENCES member3(member_id) ON DELETE CASCADE;

drop table memberboard_2 purge;
drop table member3 purge;