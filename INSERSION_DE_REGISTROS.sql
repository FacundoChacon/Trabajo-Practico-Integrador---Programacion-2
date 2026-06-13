use food_store_db;

insert into usuario (nombre, apellido, mail, celular, contrasenia, rol) 
values ('Juan', 'Pérez', 'admin@foodstore.com', '2615555555', 'admin123', 'ADMIN');

insert into categoria (nombre, descripcion) 
values ('Hamburguesas', 'Variedad de hamburguesas caseras con papas');