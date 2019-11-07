insert into person(id, `name`, password) values ('106d3efc-bccd-4e1b-83e0-90022e38d6c2', 'admin', 'password');

insert into genre (id, `name`, code) values ('1cc9b82f-b3d5-4b1b-a3f9-eafc32dd6fa9', 'fantasy', '00001');
insert into genre (id, `name`, code) values ('67953367-7369-4458-b14c-1456a1d345a2', 'action', '00002');

insert into author (id, `name`) values ('22063de6-7ae3-46c9-8209-536eb914150a', 'Denis');
insert into author (id, `name`) values ('150cdadd-790a-4839-9654-9257e1ec0e21', 'Matveev');

insert into book (id, `name`, author_id, genre_id, isbn)
values ('5bfd646c-87fb-420f-8af1-3694e85e39f0', 'Some action',
 '22063de6-7ae3-46c9-8209-536eb914150a', '67953367-7369-4458-b14c-1456a1d345a2', '13-84356-2099345208');

insert into book (id, `name`, author_id, genre_id, isbn)
values ('05d8bdf0-863e-4d80-9dec-0ff333bfa03e', 'Some fantasy',
 '22063de6-7ae3-46c9-8209-536eb914150a', '1cc9b82f-b3d5-4b1b-a3f9-eafc32dd6fa9', '13-84356-1519626998');