create table if not exists categoria (
    id int auto_increment primary key,
    nombre varchar(100) not null unique,
    descripcion varchar(255),
    eliminado boolean default false,
    created_at timestamp default current_timestamp
);

create table if not exists usuario (
    id int auto_increment primary key,
    nombre varchar(100) not null,
    apellido varchar(100) not null,
    mail varchar(150) not null unique,
    celular varchar(50),
    contrasenia varchar(255) not null,
    rol varchar(20) not null,
    eliminado boolean default false,
    created_at timestamp default current_timestamp
);

create table if not exists producto (
    id int auto_increment primary key,
    nombre varchar(150) not null,
    precio double not null,
    descripcion text,
    stock int not null default 0,
    imagen varchar(255),
    disponible boolean default true,
    categoria_id int not null,
    eliminado boolean default false,
    created_at timestamp default current_timestamp,
    foreign key (categoria_id) references categoria(id)
);

create table if not exists pedido (
    id int auto_increment primary key,
    fecha date not null,
    estado varchar(20) not null,
    total double not null default 0.0,
    forma_pago varchar(20) not null,
    usuario_id int not null,
    eliminado boolean default false,
    created_at timestamp default current_timestamp,
    foreign key (usuario_id) references usuario(id)
);

create table if not exists detalle_pedido (
    id int auto_increment primary key,
    cantidad int not null,
    subtotal double not null,
    pedido_id int not null,
    producto_id int not null,
    eliminado boolean default false,
    created_at timestamp default current_timestamp,
    foreign key (pedido_id) references pedido(id) on delete cascade,
    foreign key (producto_id) references producto(id)
);