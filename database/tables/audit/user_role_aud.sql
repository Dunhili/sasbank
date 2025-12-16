CREATE TABLE audit.user_role_aud (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    action VARCHAR(6), -- 'INSERT', 'UPDATE', 'DELETE'
    user_id UUID NOT NULL,
    user_role VARCHAR(10) NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(50)
);
