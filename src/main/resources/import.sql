

insert  into USER (id,username,password,name,email)values(1,"admin","123456","lixi","517187090@qq.com");
insert  into USER (id,username,password,name,email)values(2,"guest","123456","zz","517187090@qq.com");


insert into Authoirty (id, name ) values (1, "ROLE_ADMIN");
insert into Authoirty (id, name ) values (2, "ROLE_USER");

insert into user_authority(user_id,authority_id) values(1,1);
insert into user_authority(user_id,authority_id) values(2,2);