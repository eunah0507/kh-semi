create table kh_member(
   kh_id varchar2(30),
   kh_pw varchar2(30),
   kh_name varchar2(30),
   kh_tel number,
   PRIMARY KEY (kh_id)
);

select * from kh_member;

create table kh_boardq(
   q_num number,
   q_id varchar2(30),
   q_subject varchar2(30),
   q_content varchar2(1000),
   q_re_ref number,
   q_re_lev number,
   q_re_seq number,
   q_date date,
   q_readcount number,
   PRIMARY KEY (q_num)
);

create table kh_boardr(
   r_num number,
   r_id varchar2(30),
   r_subject varchar2(30),
   r_content varchar2(1000),
   r_date date,
   r_readcount number,
   PRIMARY KEY (r_num)
);

create table kh_boardn(
   n_num number,
   n_subject varchar2(30),
   n_content varchar2(1000),
   n_date date,
   PRIMARY KEY (n_num)
);

create table kh_product(
   p_num number,
   P_file varchar2(30),
   p_name varchar2(30),
   p_level number,
   p_content varchar2(1000),
   PRIMARY KEY (p_num)
);

drop table kh_product purge;

create table kh_book(
   b_num number,
   b_pnum number,
   b_product varchar2(30),
   b_name varchar2(30),
   b_date date,
   PRIMARY KEY (b_num)
);

drop table kh_book purge;

alter table kh_boardq add constraint q_id foreign key(q_id) references kh_member(kh_id) on delete cascade;

alter table kh_boardr add constraint r_id foreign key(r_id) references kh_member(kh_id) on delete cascade;

alter table kh_book add  constraint b_name foreign key(b_name) references kh_member(kh_id) on delete cascade;

alter table kh_book add constraint b_product foreign key(b_pnum) references kh_product(p_num) on delete cascade;

ALTER TABLE kh_boardq DROP CONSTRAINT q_id;

ALTER TABLE kh_boardr DROP CONSTRAINT r_id;

ALTER TABLE kh_book DROP CONSTRAINT b_name;

ALTER TABLE kh_book DROP CONSTRAINT b_product;

SELECT * FROM    ALL_CONSTRAINTS
WHERE    TABLE_NAME = 'kh_product';