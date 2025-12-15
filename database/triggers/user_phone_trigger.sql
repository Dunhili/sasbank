CREATE OR REPLACE FUNCTION audit_user_phone_changes()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO audit.user_phone_aud (action, user_phone_id, user_id, phone_number, phone_type, is_primary, created_at, created_by, updated_at, updated_by)
        VALUES ('INSERT', NEW.id, NEW.user_id, NEW.phone_number, NEW.phone_type, NEW.is_primary, NEW.created_at, NEW.created_by, NEW.updated_at, NEW.updated_by);
        RETURN NEW;
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO audit.user_phone_aud (action, user_phone_id, user_id, phone_number, phone_type, is_primary, created_at, created_by, updated_at, updated_by)
        VALUES ('UPDATE', NEW.id, NEW.user_id, NEW.phone_number, NEW.phone_type, NEW.is_primary, NEW.created_at, NEW.created_by, NEW.updated_at, NEW.updated_by);
        RETURN NEW;
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO audit.user_phone_aud (action, user_phone_id, user_id, phone_number, phone_type, is_primary, created_at, created_by, updated_at, updated_by)
        VALUES ('DELETE', OLD.id, OLD.user_id, OLD.phone_number, OLD.phone_type, OLD.is_primary, OLD.created_at, OLD.created_by, OLD.updated_at, OLD.updated_by);
        RETURN NEW;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER user_phone_audit_trigger
AFTER INSERT OR UPDATE OR DELETE ON public.user_phone
FOR EACH ROW EXECUTE FUNCTION audit_user_phone_changes();
