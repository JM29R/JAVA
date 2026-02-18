create table consultas(
id bigint not null auto_increment,
medico_id bigint not null,
pacientes_id bigint not null,
fecha datetime not null,


primary key(id),
constraint fk_consultas_medico_id foreign key(medico_id) references medicos(id),
constraint fk_consultas_paciente_id foreign key(pacientes_id) references pacientes(id)

);