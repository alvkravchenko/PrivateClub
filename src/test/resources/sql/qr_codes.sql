INSERT INTO participants (id, first_name, last_name, email, phone, created_at)
VALUES
    ('333e4567-e89b-12d3-a456-426614174002','Тест','Участник','test@qr.ru','+79001112233',now());

INSERT INTO qr_codes (id, qr_code, participant_id, is_active, created_at, deleted)
VALUES
    ('444e4567-e89b-12d3-a456-426614174003','555e4567-e89b-12d3-a456-426614174004','333e4567-e89b-12d3-a456-426614174002',
     true,
     now(),
     false
    ),
    ('666e4567-e89b-12d3-a456-426614174005','777e4567-e89b-12d3-a456-426614174006','333e4567-e89b-12d3-a456-426614174002',
     false,
     now(),
     false
    );