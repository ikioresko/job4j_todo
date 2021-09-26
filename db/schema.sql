create table items(
id serial primary key,
description TEXT,
created TIMESTAMP with time zone,
done boolean,
user_id INT not null references users(id)
);

create table users(
id serial primary key,
name TEXT,
email TEXT,
password TEXT
);