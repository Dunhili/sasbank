CREATE TABLE public.user_address (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    address_line_1 VARCHAR(250),
    address_line_2 VARCHAR(250),
    address_line_3 VARCHAR(250),
    state VARCHAR(50),
    city VARCHAR(250),
    zipcode VARCHAR(20),
    country VARCHAR(100),
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50)
);

ALTER TABLE public.user_address ADD CONSTRAINT fk_user_id_user_address
FOREIGN KEY (user_id) REFERENCES public.user_app(id);
