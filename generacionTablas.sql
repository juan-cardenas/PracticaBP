--CREACION DE TABLAS


create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to postgres;

create table persona
(
    id_persona     bigserial
        constraint persona_pkey
            primary key,
    apellidos      varchar(255) not null,
    direccion      varchar(255) not null,
    email          varchar(255),
    esta_activo    varchar(1)   not null,
    identificacion varchar(10) not null,
    nombres        varchar(255) not null
);

alter table persona
    owner to postgres;

create table cuenta
(
    id_cuenta   bigserial
        constraint cuenta_pkey
            primary key,
    codigo      varchar(255)   not null
        constraint uk_lggsdbrocmski7w86pwdxagce
            unique,
    esta_activo varchar(1)     not null,
    fecha       timestamp      not null,
    monto       numeric(19, 2) not null,
    id_persona  bigint
        constraint fk_cuenta_persona
            references persona
);

alter table cuenta
    owner to postgres;

create table transaccion
(
    id_transaccion bigserial
        constraint transaccion_pkey
            primary key,
    codigo         varchar(255)   not null
        constraint uk_d51vygp2h613nb8hmvn6l5w84
            unique,
    esta_activo    varchar(1)     not null,
    fecha          timestamp      not null,
    monto          numeric(19, 2) not null,
    tipo           varchar(1)     not null,
    id_cuenta      bigint
        constraint fk_transaccion_cuenta
            references cuenta
);

alter table transaccion
    owner to postgres;


