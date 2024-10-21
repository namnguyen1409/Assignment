IF NOT EXISTS (SELECT * FROM roles WHERE Code = 'ADMIN')
BEGIN
    INSERT INTO roles (Code, Name, Description, Image) 
    VALUES ('ADMIN', N'quản trị viên', 'lorem ipsum', 'demo.jpg');
END

IF NOT EXISTS (SELECT * FROM roles WHERE Code = 'USER')
BEGIN
    INSERT INTO roles (Code, Name, Description, Image) 
    VALUES ('USER', N'người dùng', 'lorem ipsum', 'demo.jpg');
END

IF NOT EXISTS (SELECT * FROM roles WHERE Code = 'MANAGER')
BEGIN
    INSERT INTO roles (Code, Name, Description, Image) 
    VALUES ('MANAGER', N'quản lý', 'lorre', 'demo.jpg');
END


IF NOT EXISTS (SELECT * FROM roles WHERE Code = 'STAFF')
BEGIN
    INSERT INTO roles (Code, Name, Description, Image) 
    VALUES ('STAFF', N'nhân viên', 'lorem ipsum', 'demo.jpg');
END


DECLARE @Userrole_id INT;

SELECT @Userrole_id = Id FROM roles WHERE Code = 'USER';

IF NOT EXISTS (SELECT * FROM permissons WHERE Code = 'purchase')
BEGIN
    INSERT INTO permissons (Code, Name, Description, self_register, Image, role_id) 
    VALUES ('purchase', N'mua hàng', 'lorem ipsum', 1, 'demo.jpg', @Userrole_id);
END


IF NOT EXISTS (SELECT * FROM permissons WHERE Code = 'sell')
BEGIN
    INSERT INTO permissons (Code, Name, Description, self_register, Image, role_id) 
    VALUES ('sell', N'bán hàng', 'lorem ipsum', 1, 'demo.jpg', @Userrole_id);
END


IF NOT EXISTS (SELECT * FROM permissons WHERE Code = 'ewallet')
BEGIN
    INSERT INTO permissons (Code, Name, Description, self_register, Image, role_id) 
    VALUES ('ewallet', N'Ví điện tử', 'lorem ipsum', 1, 'demo.jpg', @Userrole_id);
END


IF NOT EXISTS (SELECT * FROM permissons WHERE Code = 'delivery')
BEGIN
    INSERT INTO permissons (Code, Name, Description, self_register, Image, role_id) 
    VALUES ('delivery', N'Giao hàng', 'lorem ipsum', 1, 'demo.jpg', @Userrole_id);
END


IF NOT EXISTS (SELECT * FROM permissons WHERE Code = 'social')
BEGIN
    INSERT INTO permissons (Code, Name, Description, self_register, Image, role_id) 
    VALUES ('social', N'Mạng xã hội', 'lorem ipsum', 1, 'demo.jpg', @Userrole_id);
END

