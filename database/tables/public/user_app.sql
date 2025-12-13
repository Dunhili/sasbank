CREATE TABLE public.user_app (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(100),
    middle_name VARCHAR(100),
    last_name VARCHAR(100),
    gender VARCHAR(1),  -- 'M', 'F', 'N'
    email_address VARCHAR(250),
    ssn VARCHAR(11),
    birthday DATE,
    status VARCHAR(10), -- 'ACTIVE', 'INACTIVE', 'LOCKED', 'BANNED'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50) DEFAULT
);
