create table items(
id serial primary key,
description TEXT,
created TIMESTAMP with time zone,
done boolean
);