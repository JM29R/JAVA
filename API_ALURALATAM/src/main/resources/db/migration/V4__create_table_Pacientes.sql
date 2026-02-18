create table pacientes(

    id bigint not null auto_increment,
    nombre varchar(100) not null,
    email varchar(100) not null,
    documento varchar(12) not null,
    calle varchar(100) not null,
    barrio varchar(100) not null,
    codigo_postal varchar(12) not null,
    complemento varchar(100) not null,
    numero varchar(20) not null,
    estado varchar(100) not null,
    ciudad varchar(100) not null,
    telefono varchar(120) not null,
    activo TINYINT(1) NOT NULL,



    primary key(id)


)