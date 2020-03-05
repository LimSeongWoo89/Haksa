CREATE TABLE student2(
    id CHAR(7) PRIMARY KEY, -- �й�
    name VARCHAR2(10) not null, -- �̸�
    dept VARCHAR2(20) not null, -- �а�
    address VARCHAR2(100) null  -- �ּ�
);

INSERT INTO student2 VALUES('1234567','ȫ�浿','��ǻ�Ͱ���',NULL);
INSERT INTO student2 VALUES('1243211','�̼���','��Ƽ�̵��',NULL);
INSERT INTO student2 VALUES('0034567','��ö��','��ǻ�ͽý���',NULL);

SELECT*FROM student2;

COMMIT;

-----------------------------------------JDBC-------------------------------------------------------
SELECT*FROM student2;

--------------------------------------------------------------------------------------------------------
drop table books;
create table books(
    no char(6) primary key, -- å��ȣ
    title varchar(50) not null, -- å�̸�
    author varchar(50) not null, -- ����
    ea number not null, -- å�� ���� ����
    fullea number not null -- �������� �ִ� å�� �Ѱ���
);

insert into books values('000001','����Ŭ�⺻','��Ȳ', 4,4);
insert into books values('000002','�ڹ�����','����',3,3);
insert into books values('000003','HTML5','������',5,5);
 
select * from books; 
 

commit;


--å�뿩
drop table bookRent;
create table bookRent( 
  no char(10) primary key, -- �뿩��ȣ
  id char(7) not null, -- �й�
  bookNo char(6) not null, -- å��ȣ
  rDate char(8) not null -- �뿩��
  
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

-----å���⳻��
--�����ȣ, �л���, ������, ������

SELECT bookRent.no,student2.name,books.title,bookRent.rDate
FROM student2,books,bookRent
WHERE bookRent.id = student2.id
AND bookRent.bookNo =books.no
AND student2.dept = '��ǻ�ͽý���'
order by bookRent.no; 

select count(bookRent.bookNo)
from bookRent,books
where bookRent.bookNo = books.no
and books.title='����Ŭ�⺻';

select count(bookRent.bookNo)
from bookRent,books
where bookRent.bookNo = books.no
and books.title='�ڹ�����';

select count(bookRent.bookNo)
from bookRent,books
where bookRent.bookNo = books.no
and books.title='HTML5';


commit;

------------Test�� -------------
SELECT*FROM student2 order by id asc;  -- �л����
select * from books; -- å ���
update books set ea=4 where no='000006'; -- å�� ���� ���߱��
select*from bookRent;  -- å �뿩 ���
select id from student2 where id='1243211';
select id from bookRent where id='1243211' and bookno='000007';
select no from bookRent where no='202003056';
delete from bookrent where id='1243211' and bookno='000007';

select*from bookRent order by no asc; 
select s.id, b.no,br.no,s.name, b.title,rdate from bookrent br, student2 s,books b where br.id=s.id and br.bookno = b.no order by br.no asc;