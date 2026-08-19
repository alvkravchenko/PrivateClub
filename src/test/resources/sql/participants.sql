INSERT INTO participants (id, first_name, last_name, email, phone, created_at)
VALUES
    (gen_random_uuid(), 'Alex', 'Sidorov', 'alex@webclient.ru', '+79001112233', now()),
    (gen_random_uuid(), 'Mary', 'Ivanova', 'maria@webclient.ru', '+79004445566', now()),
    ('123e4567-e89b-12d3-a456-426614174000', 'Oleg', 'Veshiy', 'veshiy@webclient.ru', '+79007778899', now()),
    ('223e4567-e89b-12d3-a456-426614174001', 'Удален', 'Удаленный', 'delete@webclient.ru', '+79001112233', now());