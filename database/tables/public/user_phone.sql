CREATE TABLE public.user_phone (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    phone_type VARCHAR(6) NOT NULL, -- 'HOME', 'MOBILE', 'WORK', 'FAX'
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50)
);

ALTER TABLE public.user_phone ADD CONSTRAINT fk_user_id_user_phone
FOREIGN KEY (user_id) REFERENCES public.user_app(id) ON DELETE CASCADE;

CREATE INDEX user_phone_user_id_idx ON public.user_phone (user_id);
