create keyspace sdk with replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

create table orders
(
    order_no UUID primary key,
    user_id  BIGINT
);