CREATE OR REPLACE FUNCTION audit_user_role_changes()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO audit.user_role_aud (action, user_id, user_role, created_at, created_by)
        VALUES ('INSERT', NEW.user_id, NEW.user_role, NEW.created_at, NEW.created_by);
        RETURN NEW;
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO audit.user_role_aud (action, user_id, user_role, created_at, created_by)
        VALUES ('UPDATE', NEW.user_id, NEW.user_role, NEW.created_at, NEW.created_by);
        RETURN NEW;
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO audit.user_role_aud (action, user_id, user_role, created_at, created_by)
        VALUES ('DELETE', OLD.user_id, OLD.user_role, OLD.created_at, OLD.created_by);
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER audit_user_role_changes
AFTER INSERT OR UPDATE OR DELETE ON public.user_role
FOR EACH ROW EXECUTE FUNCTION audit_user_role_changes();
