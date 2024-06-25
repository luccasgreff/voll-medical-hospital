create table consultas(

    id bigint not null auto_increment,
    paciente varchar(100) not null,
    medico varchar(100) not null,
    dataHora varchar(10) not null unique,

    primary key(id)

);