CREATE TABLE audit.user_app_aud (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    action VARCHAR(6), -- 'INSERT', 'UPDATE', 'DELETE'
    user_id UUID NOT NULL,
    first_name VARCHAR(100),
    middle_name VARCHAR(100),
    last_name VARCHAR(100),
    gender VARCHAR(1),
    email_address VARCHAR(250),
    ssn VARCHAR(11),
    birthday DATE,
    status VARCHAR(10),
    created_at TIMESTAMP,
    created_by VARCHAR(50),
    updated_at TIMESTAMP,
    updated_by VARCHAR(50)
);

ALTER TABLE audit.user_app_aud ADD CONSTRAINT fk_user_id_user_app_aud
FOREIGN KEY (user_id) REFERENCES public.user_app(id);
