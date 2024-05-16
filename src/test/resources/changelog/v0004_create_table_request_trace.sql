CREATE TABLE public.trace_request
(
    id       bigint      NOT NULL GENERATED ALWAYS AS IDENTITY,
    date     TIMESTAMP   NOT NULL default current_timestamp,
    trace_id uuid        NOT NULL,
    type     varchar(20) NOT NULL,
    request  jsonb       NOT NULL,

    CONSTRAINT trace_request_pkey PRIMARY KEY (id)
);


