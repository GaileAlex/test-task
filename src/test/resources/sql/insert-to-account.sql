INSERT INTO public.account (account_id, customer_id, country)
VALUES (50, 171820, 'Estonia');

INSERT INTO public.balance (account_id, balance, currency_type)
VALUES ((SELECT account_id FROM public.account WHERE customer_id = 171820), 10, 'EUR');

INSERT INTO public.account (account_id, customer_id, country)
VALUES (60, 17182, 'Estonia');

INSERT INTO public.balance (account_id, balance, currency_type)
VALUES ((SELECT account_id FROM public.account WHERE customer_id = 17182), 30, 'EUR');

INSERT INTO public.account (account_id, customer_id, country)
VALUES (70, 1718, 'Estonia');

INSERT INTO public.balance (account_id, balance, currency_type)
VALUES ((SELECT account_id FROM public.account WHERE customer_id = 1718), 20, 'EUR');
