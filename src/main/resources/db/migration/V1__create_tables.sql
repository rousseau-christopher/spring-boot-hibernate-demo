create sequence author_seq start with 1 increment by 1;

create sequence book_seq start with 1 increment by 1;

create table author
(
    id        bigint not null,
    firstname varchar(255),
    lastname  varchar(255),
    primary key (id)
);

create table book
(
    author_id bigint not null,
    id        bigint not null,
    isbn      varchar(255),
    label     varchar(255),
    summary   text,
    primary key (id),
    unique (isbn)
);

alter table if exists book
    add constraint author_foreign_key
    foreign key (author_id)
    references author;
