INSERT INTO public.account (account_id, customer_id, country)
VALUES (11, 55, 'Estonia');

INSERT INTO public.balance (account_id, balance, currency_type)
VALUES ((SELECT account_id FROM public.account WHERE customer_id = 55), 11, 'EUR');
INSERT INTO public.balance (account_id, balance, currency_type)
VALUES ((SELECT account_id FROM public.account WHERE customer_id = 55), 100, 'SEK');
INSERT INTO public.balance (account_id, balance, currency_type)
VALUES ((SELECT account_id FROM public.account WHERE customer_id = 55), 500, 'GBP');
INSERT INTO public.balance (account_id, balance, currency_type)
VALUES ((SELECT account_id FROM public.account WHERE customer_id = 55), 15, 'USD');

