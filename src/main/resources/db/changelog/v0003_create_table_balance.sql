CREATE TABLE public.balance
(
    id            bigint         NOT NULL GENERATED ALWAYS AS IDENTITY,
    account_id    bigint         NOT NULL,
    balance       decimal(20, 2) NULL default 0,
    currency_type currency_enum  NOT NULL,

    CONSTRAINT balance_pkey PRIMARY KEY (id),
    CONSTRAINT balance_fk FOREIGN KEY (account_id) REFERENCES public.account (account_id) ON DELETE CASCADE
);


