CREATE TABLE public.transaction
(
    id          bigint         NOT NULL GENERATED ALWAYS AS IDENTITY,
    account_id  bigint         NOT NULL,
    amount     decimal(20, 2) NULL,
    currency    currency_enum  NOT NULL,
    direction   direction_enum NOT NULL,
    date        TIMESTAMP      NOT NULL default current_timestamp,
    description varchar(200)   NOT NULL,

    CONSTRAINT transaction_pkey PRIMARY KEY (id),
    CONSTRAINT transaction_fk FOREIGN KEY (account_id) REFERENCES public.account (account_id) ON DELETE CASCADE
);


