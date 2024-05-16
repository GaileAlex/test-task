CREATE TABLE public.account
(
    account_id  bigint       NOT NULL GENERATED ALWAYS AS IDENTITY,
    customer_id bigint       NOT NULL,
    country     varchar(200) NOT NULL,

    UNIQUE (customer_id),
    CONSTRAINT account_pkey PRIMARY KEY (account_id)
);


