CREATE TABLE public.user_login (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    username VARCHAR(100) NOT NULL,
    password_hash TEXT NOT NULL,
    password_salt TEXT,
    hash_algorithm VARCHAR(50) NOT NULL,
    last_login_date TIMESTAMP,
    password_attempts INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50)
);

ALTER TABLE public.user_login ADD CONSTRAINT fk_user_id_user_login
FOREIGN KEY (user_id) REFERENCES public.user_app(id);
