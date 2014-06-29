CREATE TABLE authority (
  id  integer,
  authority varchar(255),
  primary key (id)
);

CREATE TABLE credentials (
  id  integer,
  enabled boolean not null,
  name varchar(255) not null,
  password varchar(255) not null,
  version integer,
  primary key (id)
);

CREATE TABLE credentials_authorities (
  credentials_id bigint not null,
  authorities_id bigint not null
);