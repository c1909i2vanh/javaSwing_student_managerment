CREATE DATABASE  qlsv_swing
GO
USE qlsv_swing
GO
CREATE TABLE student(
	id varchar(20) NOT NULL PRIMARY KEY,
	name nvarchar(50) NOT NULL,
	age int NOT NULL,
	gender smallint ,
	address nvarchar(200) NOT NULL,
	phone varchar(11) NOT NULL,
	gpa float,
	status smallint

)
GO
INSERT INTO student VALUES ('B8861',N'Nguyễn Trường Giang',31,1,N'Phú vinh An khánh Hoài Đức Hà Nội','0934646892',6.5,1)
GO
-- thủ tục lấy tất cả sinh viên
CREATE PROC sp_getAll AS
SELECT * FROM student WHERE status =1
GO
-- thủ tục thêm sinh viên
CREATE PROC sp_addST (
	@id varchar(20),
	@name nvarchar(50) ,
	@age int ,
	@gender smallint,
	@address nvarchar(200),
	@phone varchar(11) ,
	@gpa float,
	@status smallint
)
AS
BEGIN
INSERT INTO student VALUES(	@id,@name,@age,@gender,@address,@phone,@gpa,@status)
END
GO
EXEC sp_getAll
GO
SELECT count(id) From student
GO
-- thủ tục đếm số sinh viên
CREATE PROC sp_get_count_student
@count int OUTPUT
AS
	BEGIN
		SELECT id From student
		set @count = @@ROWCOUNT
	END
	RETURN @count
GO
declare @i int;
EXEC sp_get_count_student @i OUTput;
SELECT @i;
GO
--Thủ tục lấy sinh viên theo Id
CREATE PROCEDURE sp_get_Student_By_Id
@id varchar(20)
AS
	BEGIN
		SELECT *FROM student 
		WHERE id = @id
	END

	DECLARE @id1 varchar(20);
	EXEC sp_get_Student_By_Id 'B0002'
GO
-- Thủ tục cập nhật sinh viên
CREATE PROC sp_editSt (
	@id varchar(20),
	@name nvarchar(50) ,
	@age int ,
	@gender smallint,
	@address nvarchar(200),
	@phone varchar(11) ,
	@gpa float,
	@status smallint
)
AS
BEGIN
UPDATE  student SET	name=@name,age=@age,gender=@gender,address=@address,phone=@phone,gpa=@gpa,status=@status WHERE id=@id
END
GO
--Thủ tục xóa sinh viên
CREATE PROC sp_deleteSt(
@id varchar(20),
@status smallint
)

AS
BEGIN
UPDATE student SET status = @status WHERE id = @id
END

