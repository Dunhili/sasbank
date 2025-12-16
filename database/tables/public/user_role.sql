CREATE TABLE public.user_role (
    user_id UUID NOT NULL,
    user_role VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50),

    PRIMARY KEY (user_id, user_role)
);

ALTER TABLE public.user_role ADD CONSTRAINT fk_user_id_user_role
FOREIGN KEY (user_id) REFERENCES public.user_app(id) ON DELETE CASCADE;

CREATE INDEX user_role_user_id_idx ON public.user_role (user_id);
