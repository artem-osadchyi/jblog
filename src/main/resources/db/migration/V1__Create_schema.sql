create table post (
    id serial primary key,
    title varchar(255) not null unique,
    content text not null,
    created_at timestamp not null,
    edited_at timestamp,

    check(edited_at > created_at)
);

create table tag (
    id serial primary key,
    name varchar(50) not null unique
);

create table post_tag (
    post_id integer references post(id),
    tag_id integer references tag(id) on delete cascade,

    unique(post_id, tag_id)
);
