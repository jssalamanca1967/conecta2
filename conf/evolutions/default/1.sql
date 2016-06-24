# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table cliente (
  id                        bigint auto_increment not null,
  nit                       varchar(255),
  nombre                    varchar(255),
  contrasenia               varchar(255),
  constraint uq_cliente_nit unique (nit),
  constraint pk_cliente primary key (id))
;

create table codigoqr (
  id                        bigint auto_increment not null,
  codigo                    varchar(255),
  capacidad_total           double,
  consumido                 double,
  cliente_id                bigint,
  usuario_id                bigint,
  constraint uq_codigoqr_codigo unique (codigo),
  constraint uq_codigoqr_cliente_id unique (cliente_id),
  constraint uq_codigoqr_usuario_id unique (usuario_id),
  constraint pk_codigoqr primary key (id))
;

create table usuario (
  id                        bigint auto_increment not null,
  nombre                    varchar(255),
  contrasenia               varchar(255),
  email                     varchar(255),
  cumpleanios               datetime(6),
  sexo                      varchar(255),
  constraint uq_usuario_email unique (email),
  constraint pk_usuario primary key (id))
;

alter table codigoqr add constraint fk_codigoqr_cliente_1 foreign key (cliente_id) references cliente (id) on delete restrict on update restrict;
create index ix_codigoqr_cliente_1 on codigoqr (cliente_id);
alter table codigoqr add constraint fk_codigoqr_usuario_2 foreign key (usuario_id) references usuario (id) on delete restrict on update restrict;
create index ix_codigoqr_usuario_2 on codigoqr (usuario_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table cliente;

drop table codigoqr;

drop table usuario;

SET FOREIGN_KEY_CHECKS=1;

