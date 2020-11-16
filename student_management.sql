IF NOT EXISTS(SELECT 1 FROM sys.databases WHERE name='student_management_java')
    CREATE DATABASE student_management_java
GO
USE student_management_java
GO
-- create table tblrole
CREATE TABLE tblrole(
id int identity(1,1) PRIMARY KEY,
rolename nvarchar(50) NOT NULL,
CONSTRAINT IX_tblrole_role_unique UNIQUE(rolename)

)
GO
-- create table tbluser
CREATE TABLE tbluser(
	id int identity (1,1) PRIMARY KEY,
	username nvarchar(20) NOT NULL UNIQUE,
	password nvarchar(200)NOT NULL,
	email varchar(50) null UNIQUE,
	roleId int ,
	verifyCode int default null,
	status  int default 1,
	daterelease date
	CONSTRAINT FK_tblroldid FOREIGN KEY(roleId) REFERENCES tblrole(id)
)
GO
-- Create table tblclass
CREATE TABLE tblclass(
	id int identity(1,1) PRIMARY KEY,
	classname varchar(20),
	CONSTRAINT IX_tblclass_classname_unique UNIQUE(classname)
)
GO

--CREATE tblgpa
	/*CREATE TABLE tblmark(
		id int identity(1,1) PRIMARY KEY,
		studentid varchar(20),
	
		CONSTRAINT IX_tblmark_studentid_unique UNIQUE(studentid)
	)*/
-- Create table tblstudent
CREATE TABLE tblstudent(
	id varchar(20) NOT NULL PRIMARY KEY,
	name nvarchar(50) NOT NULL,
	age int NOT NULL,
	gender smallint ,
	address nvarchar(200) NOT NULL,
	phone varchar(11) NOT NULL ,
	gpa float NOT NULL,
	status smallint default 1,
	classid int NOT NULL,	
	CONSTRAINT IX_tblstudent_phone_unique UNIQUE(phone)
)
GO
/*
*	Procedure add verifycode
*	Check if exists email  parameter
	Then add verifyCode with in parameter
	return output 0 error
	Else return 1 error
*/
CREATE PROCEDURE sp_add_verifycode_password(
	@email varchar(50),
	@verifycode int,
	@err int output
	
)
AS
	BEGIN 
		IF EXISTS( SELECT * FROM tbluser where email LIKE @email AND status =1 )
			BEGIN
				UPDATE tbluser SET verifyCode = @verifycode WHERE email LIKE @email;
				SET @err = 0;
			END		
		ELSE 
			BEGIN
			SET @err = 1;
			END
			
	END

GO
SELECT * FROM tbluser
declare @a varchar(50),@veri int,@err int 
EXEC sp_add_verifycode_password 'truonggiang2298@gmail.com',1233,@err output
select @err
--************************
--Procedure change password bằng email
CREATE PROCEDURE sp_change_password_by_email(
@email varchar(50),
@password varchar(200)
)
AS
	BEGIN
		IF  EXISTS(select   1 FROM tbluser where email LIKE @email and status = 1 )
			BEGIN
				UPDATE tbluser SET password = @password WHERE email LIKE @email
			END
	END
GO
/*
	Procedure check tbl user username exists
*/
CREATE PROCEDURE sp_check_username_not_exists(
@username varchar(20),
@err int output
)
AS
	BEGIN
		IF NOT EXISTS(SELECT 1 FROM tbluser where username LIKE @username)
			BEGIN
				SET @err = 0;
			END
		ELSE
			BEGIN
				SET @err =1;
			END
	END
GO
/*
	Procedure check tbluser email exists
*/
CREATE PROCEDURE sp_check_useremail_not_exists(
@email varchar(50),
@err int output
)
AS
	BEGIN
		IF NOT EXISTS(SELECT 1 FROM tbluser where email LIKE @email)
			BEGIN
				SET @err = 0;
			END
		ELSE
			BEGIN
				SET @err =1;
			END
	END
GO

select * from tbluser
declare  @err int 
exec sp_check_username_not_exists 'admin9',@err output
SELECT @err
--****************
CREATE PROCEDURE sp_insert_user(
	@username varchar(20),
	@password nvarchar(200),
	@email varchar(50),
	@roleId int,
	@verifyCode int,
	@status int,
	@daterelease date,
	@err int output

	
)
AS
	BEGIN 				
		IF NOT EXISTS(select   username,   email FROM tbluser where username LIKE @username OR email LIKE @email )		
			BEGIN 		
				INSERT INTO tbluser VALUES(@username,@password,@email,@roleId,@verifyCode,@status,@daterelease)
				IF @@ROWCOUNT >0
					BEGIN
						SET @err =0;
					END
				ELSE
					BEGIN
					SET @err =1;
					END							
			END
		ELSE					
			BEGIN				
					SET @err =1;			
			END
	END
GO

-- them tai khoan dau tien

/*
select *from tbluser
declare @user varchar(50),@mes nvarchar(50)
EXEC sp_check_username_exists 'admin9',@mes OUTPUT
SELECT @mes
*/
GO


--Tạo thủ tục lấy user by email
CREATE PROCEDURE sp_get_user_byemail(
@email varchar(50)
)
AS 
	BEGIN
	select TOP 1 *FROM tbluser where email = @email 
	END
GO
/*
	Procedure confirm user
*/

CREATE PROCEDURE sp_confirmuser(
@email varchar(50),
@err int output
)
AS
	BEGIN
		IF  EXISTS(SELECT 1 FROM tbluser where email LIKE @email)
			BEGIN
				UPDATE tbluser SET status = 1 where email LIKE @email
				SET @err = 0;
			END
		ELSE
			BEGIN
				SET @err =1;
			END
	END
GO



/*
	Procedure checkUserLogin
	Return no error if username or email match @name
	and password match @password
*/
CREATE PROCEDURE sp_check_user_login(
@name varchar(50),
@password varchar(200),
@err int output
)
AS
	BEGIN
		IF  EXISTS(SELECT 1 FROM tbluser WHERE password LIKE @password AND (  username LIKE @name OR email LIKE @name))
			BEGIN
				SET @err = 0;
			END
		ELSE
			BEGIN
				SET @err =1;
			END
	END
GO
select* from tbluser
CREATE PROCEDURE sp_get_user_by_name(
@name varchar(50)


)
AS
	BEGIN
		IF  EXISTS(SELECT 1 FROM tbluser WHERE  username LIKE @name OR email LIKE @name)
			BEGIN
			
				SELECT * FROM tbluser where username LIKE @name OR email LIKE @name	
			END	
		ELSE
			BEGIN
				SELECT NULL FROM tbluser 
			END
			
	END
GO

-- Tạo thủ tục lấy danh sách user kết hợp rolename

AlTER PROCEDURE sp_get_list_map_user_with_role

AS
	BEGIN
		SELECT * from tbluser us
		JOIN tblrole role
		ON us.roleId = role.id
		AND us.username NOT LIKE  'admin'
		ORDER BY us.roleId
	END
GO

-- Thủ tục lay list role
ALTER PROCEDURE sp_get_list_role
AS
	BEGIN
		select * from tblrole
	END
GO
EXEC sp_get_list_role
-- thủ tục đếm số sinh viên
CREATE PROC sp_get_count_student
@count int OUTPUT
AS
	BEGIN
		SELECT id From tblstudent
		set @count = @@ROWCOUNT
	END
	RETURN @count
GO
declare @i int;
EXEC sp_get_count_student @i OUTput;
SELECT @i;
GO
-- Thủ tục thêm mới sinh viên
CREATE PROC sp_addST (
	@id varchar(20),
	@name nvarchar(50) ,
	@age int ,
	@gender smallint,
	@address nvarchar(200),
	@phone varchar(11) ,
	@gpa float,
	@status smallint,
	@classid int
)
AS
BEGIN
INSERT INTO tblstudent VALUES(	@id,@name,@age,@gender,@address,@phone,@gpa,@status,@classid)
END
--Thủ tục lấy sinh viên theo Id
CREATE PROCEDURE sp_get_Student_By_Id
@id varchar(20)
AS
	BEGIN
		SELECT *FROM tblstudent 
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
	@status smallint,
	@classid int
)
AS
BEGIN
UPDATE  tblstudent SET	name=@name,age=@age,gender=@gender,address=@address,phone=@phone,gpa=@gpa,status=@status,classid = @classid WHERE id=@id
END
GO
--Thủ tục xóa sinh viên
CREATE PROC sp_deleteSt(
	@id varchar(20),
	@status smallint
)

AS
BEGIN
	UPDATE tblstudent SET status = @status WHERE id = @id
END
GO
