CREATE TABLE audit.user_login_aud (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    action VARCHAR(6), -- 'INSERT', 'UPDATE', 'DELETE'
    user_login_id UUID NOT NULL,
    username VARCHAR(100) NOT NULL,
    password_hash TEXT NOT NULL,
    password_salt TEXT,
    hash_algorithm VARCHAR(50) NOT NULL,
    last_login_date TIMESTAMP,
    password_attempts INTEGER,
    created_at TIMESTAMP,
    created_by VARCHAR(50),
    updated_at TIMESTAMP,
    updated_by VARCHAR(50)
);

ALTER TABLE audit.user_login_aud ADD CONSTRAINT fk_user_login_id_user_login_aud
FOREIGN KEY (user_login_id) REFERENCES public.user_login(id);
