CREATE TABLE student2(
    id CHAR(7) PRIMARY KEY, -- 학번
    name VARCHAR2(10) not null, -- 이름
    dept VARCHAR2(20) not null, -- 학과
    address VARCHAR2(100) null  -- 주소
);

INSERT INTO student2 VALUES('1234567','홍길동','컴퓨터공학',NULL);
INSERT INTO student2 VALUES('1243211','이순신','멀티미디어',NULL);
INSERT INTO student2 VALUES('0034567','김철수','컴퓨터시스템',NULL);

SELECT*FROM student2;

COMMIT;

-----------------------------------------JDBC-------------------------------------------------------
SELECT*FROM student2;

--------------------------------------------------------------------------------------------------------
drop table books;
create table books(
    no char(6) primary key, -- 책번호
    title varchar(50) not null, -- 책이름
    author varchar(50) not null, -- 저자
    ea number not null, -- 책의 현재 개수
    fullea number not null -- 도서관에 있는 책의 총개수
);

insert into books values('000001','오라클기본','이황', 4,4);
insert into books values('000002','자바정복','율곡',3,3);
insert into books values('000003','HTML5','강감찬',5,5);
 
select * from books; 
 

commit;


--책대여
drop table bookRent;
create table bookRent( 
  no char(10) primary key, -- 대여번호
  id char(7) not null, -- 학번
  bookNo char(6) not null, -- 책번호
  rDate char(8) not null -- 대여일
  
);

insert into bookRent values('2017071301','1212121','000001','20170713');
insert into bookRent values('2017071302','1212123','000003','20170713');
insert into bookRent values('2017071303','1212124','000002','20170713');
insert into bookRent values('2017071304','1212125','000001','20170713');
insert into bookRent values('2017071305','1243211','000003','20170713');
insert into bookRent values('2017071306','0034567','000003','20170713');
insert into bookRent values('2019101007','1212126','000002','20191010');
insert into bookRent values('2019101008','1212127','000001','20191010');
select*from bookRent ; 

commit;

-----책대출내역
--대출번호, 학생명, 도서명, 대출일

SELECT bookRent.no,student2.name,books.title,bookRent.rDate
FROM student2,books,bookRent
WHERE bookRent.id = student2.id
AND bookRent.bookNo =books.no
AND student2.dept = '컴퓨터시스템'
order by bookRent.no; 

select count(bookRent.bookNo)
from bookRent,books
where bookRent.bookNo = books.no
and books.title='오라클기본';

select count(bookRent.bookNo)
from bookRent,books
where bookRent.bookNo = books.no
and books.title='자바정복';

select count(bookRent.bookNo)
from bookRent,books
where bookRent.bookNo = books.no
and books.title='HTML5';


commit;

------------Test용 -------------
SELECT*FROM student2 order by id asc;  -- 학생목록
select * from books; -- 책 목록
update books set ea=4 where no='000006'; -- 책의 개수 맞추기용
select*from bookRent;  -- 책 대여 목록
select id from student2 where id='1243211';
select id from bookRent where id='1243211' and bookno='000007';
select no from bookRent where no='202003056';
delete from bookrent where id='1243211' and bookno='000007';

select*from bookRent order by no asc; 
select s.id, b.no,br.no,s.name, b.title,rdate from bookrent br, student2 s,books b where br.id=s.id and br.bookno = b.no order by br.no asc;