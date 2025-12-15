CREATE TABLE audit.user_phone_aud (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    action VARCHAR(6), -- 'INSERT', 'UPDATE', 'DELETE'
    user_phone_id UUID NOT NULL,
    user_id UUID NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    phone_type VARCHAR(6) NOT NULL,
    is_primary BOOLEAN,
    created_at TIMESTAMP,
    created_by VARCHAR(50),
    updated_at TIMESTAMP,
    updated_by VARCHAR(50)
);
