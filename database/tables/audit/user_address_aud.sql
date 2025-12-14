CREATE TABLE audit.user_address_aud (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    action VARCHAR(6), -- 'INSERT', 'UPDATE', 'DELETE'
    user_address_id UUID NOT NULL,
    address_line_1 VARCHAR(250),
    address_line_2 VARCHAR(250),
    address_line_3 VARCHAR(250),
    state VARCHAR(50),
    city VARCHAR(250),
    zipcode VARCHAR(20),
    country VARCHAR(100),
    is_primary BOOLEAN,
    created_at TIMESTAMP,
    created_by VARCHAR(50),
    updated_at TIMESTAMP,
    updated_by VARCHAR(50)
);
