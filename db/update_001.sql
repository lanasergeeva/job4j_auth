create table persons (
                        id serial primary key not null,
                        login varchar(2000),
                        password varchar(2000)
);

insert into persons (login, password) values ('master', '123');
insert into persons (login, password) values ('lana', '124');
insert into persons (login, password) values ('ivan', '125');