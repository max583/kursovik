create schema students;

create table students.users (userid bigint not null PRIMARY KEY, name text not null unique, password text not null);

create sequence students.userid_seq increment 1 start 1 no cycle as bigint;

create or replace function students.getuserid() returns trigger as $$ declare begin new.userid := nextval('students.userid_seq'); return new; end $$ language plpgsql;

create trigger getuserid_tr before insert on students.users for each row execute function students.getuserid();

create table students.roles (roleid bigint not null PRIMARY KEY, name text not null unique);

create table students.credentials (userid bigint not null REFERENCES students.users (userid) on delete cascade, roleid bigint not null REFERENCES students.roles (roleid) on delete cascade, UNIQUE (userid, roleid));

create or replace procedure students.new_user (username text, passwd text) as $$ begin insert into students.users (name, password) values (username,passwd); end $$ language plpgsql;

create or replace procedure students.del_user(username text) as $$ begin delete from students.users where name = username; end $$ language plpgsql;

create or replace procedure students.set_user_pwd(username text, new_passwd text) as
$$
begin
update students.users
set "password" = new_passwd
where name = username;
end
$$ language plpgsql;;

create or replace procedure students.grant_user_role(username text, rolename text) as
$$
declare
user_id students.users.name%TYPE;
role_id students.roles.name%TYPE;
begin
select userid into user_id
from students.users
where name = username;
select roleid into role_id
from students.roles
where name = rolename;
insert into students.credentials (userid, roleid) values (user_id,role_id);
end
$$ language plpgsql;;

create or replace procedure students.revoke_user_role(username text, rolename text) as
$$
declare
user_id students.users.name%TYPE;
role_id students.roles.name%TYPE;
begin
select userid into user_id from students.users where name = username;
select roleid into role_id from students.roles where name = rolename;
IF EXISTS(SELECT 1 FROM students.credentials WHERE userid = user_id AND roleid = role_id) then
    delete from students.credentials
    where userid = user_id
    AND roleid = role_id;
END IF;
end $$ language plpgsql;;

insert into students.roles (roleid, name) values (1, 'admin');
insert into students.roles (roleid, name) values (2, 'manager');

insert into students.users (userid, name, password) values (1,'admin','admin');

insert into students.credentials (userid, roleid) values (1,1);