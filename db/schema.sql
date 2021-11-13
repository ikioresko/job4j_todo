create table items(
id serial primary key,
description VARCHAR(300),
created TIMESTAMP with time zone,
done boolean,
user_id INT not null references users(id)
);

create table users(
id serial primary key,
name VARCHAR(15),
email VARCHAR(35),
password VARCHAR(15)
);

create table categories(
id serial primary key,
name VARCHAR(15)
);

create table items_categories(
item_id INT not null references items(id),
catlist_id INT not null references categories(id)
);