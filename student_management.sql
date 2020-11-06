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
ALTER PROCEDURE sp_addnewuser(
	@username varchar(20),
	@password nvarchar(200),
	@email varchar(50),
	@mes nvarchar(100) OUTPUT,
	@err nvarchar(100) output
	
)
AS
DECLARE @abc varchar(20),
 @abc1 varchar(20)

	BEGIN 				
		IF NOT EXISTS(select  username, email FROM tbluser where username LIKE @username OR email LIKE @email )
			
					BEGIN 		
					INSERT INTO tbluser(username,password,email) VALUES(@username,@password,@email)
					IF @@ROWCOUNT =1
						BEGIN 
							print 'them moi thanh cong';
							SET @mes = 'Đăng ký thành công';
						END
					ELSE
						BEGIN
							print 'them moi that bai';
							SET @mes = 'Đăng ký thất bại!Xin kiểm tra lại';
						END
					END
		ELSE			
			IF @abc IS NOT NULL

				BEGIN
				SET @err = @abc;
					print 'ten dang nhap ton tai! moi that bai';
					SET @mes  ='Username đã tồn tại! Vui lòng nhập lại';
					print @err;
				END
			ELSE 
			BEGIN
				SET @err = @abc;
					print 'ten dang nhap ton ta1111111i! moi that bai';
					SET @mes  ='Username đã tồn tại! Vui lòng nhập lại';
					print @err;
				END
	END
GO
-- them tai khoan dau tien
INSERT INTO tbluser(username,password,email) VALUES('admin9','123456','a@')
-- test thủ tục thêm tài khoản
declare @username varchar(20),@pass nvarchar(200),@mail varchar(50),@mes nvarchar(100),@err nvarchar(100),@err1 nvarchar(220)
EXEC sp_addnewuser 'admin9','123456','a',@mes OUTPUT,@err OUTPUT 
select @mes,@err