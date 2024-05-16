insert into public.trace_request (date, trace_id, type, request)
values ('2024-04-20 17:47:57.054542', '49416656-a0d9-41fa-8a96-ad82a58d272b', 'request', '{
  "dnt": "1",
  "body": "{\n  \"customerId\": 10,\n  \"country\": \"string\",\n  \"currencyTypes\": [\n    \"EUR\"\n  ]\n}",
  "host": "localhost:8095",
  "path": "/account",
  "accept": "*/*",
  "cookie": "JSESSIONID=1F9797E3D6E875ACEC9C1DA6A3221FBB; Idea-202833f1=8eecce71-a89d-4eb7-9a78-57248ef95361; JSESSIONID=9603E559CE0E9E3720713CA862CD1D51; session_user-id_cookie=37f8a847-c727-46b2-a96c-6b356ba4bfa0",
  "origin": "http://localhost:8095",
  "referer": "http://localhost:8095/api/v1/swagger-ui/index.html",
  "sec-ch-ua": "\"Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123\"",
  "connection": "keep-alive",
  "user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36",
  "content-type": "application/json",
  "content-length": "81",
  "sec-fetch-dest": "empty",
  "sec-fetch-mode": "cors",
  "sec-fetch-site": "same-origin",
  "accept-encoding": "gzip, deflate, br, zstd",
  "accept-language": "en-GB,en;q=0.9,ru-RU;q=0.8,ru;q=0.7,en-US;q=0.6,et;q=0.5",
  "sec-ch-ua-mobile": "?0",
  "sec-ch-ua-platform": "\"Windows\""
}'),
       ('2024-04-20 17:47:57.058827', '49416656-a0d9-41fa-8a96-ad82a58d272b', 'response', '{
         "body": "{\"accountId\":1,\"customerId\":10,\"balances\":[{\"balance\":null,\"currencyType\":\"EUR\"}]}"
       }');
