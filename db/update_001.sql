create table employees
(
    id      serial primary key,
    name    varchar(2000),
    surname varchar(2000),
    itn     bigint
);

create table persons
(
    id          serial primary key not null,
    login       varchar(2000),
    password    varchar(2000),
    employee_id int references employee (id)
);

insert into employees (name, surname, itn)
values ('Ivan', 'Ivanov', 223344556688);
insert into employees (name, surname, itn)
values ('Lana', 'Sergeeva', 113344556688);
insert into employees (name, surname, itn)
values ('Maxim', 'Sergeev', 773344556688);

insert into persons (login, password, employee_id)
values ('master', '123', 1);
insert into persons (login, password, employee_id)
values ('lana', '124', 2);
insert into persons (login, password, employee_id)
values ('ivan', '125', 2);
insert into persons (login, password, employee_id)
values ('perviy', '987', 3);
insert into persons (login, password, employee_id)
values ('dart', '478', 3);
insert into persons (login, password, employee_id)
values ('user', '126', 1);


