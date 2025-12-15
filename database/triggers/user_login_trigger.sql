CREATE OR REPLACE FUNCTION audit_user_login_changes()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO audit.user_login_aud (action, user_login_id, username, password, hash_algorithm, last_login_date, password_attempts, created_at, created_by, updated_at, updated_by)
        VALUES ('INSERT', NEW.id, NEW.username, NEW.password, NEW.hash_algorithm, NEW.last_login_date, NEW.password_attempts, NEW.created_at, NEW.created_by, NEW.updated_at, NEW.updated_by);
        RETURN NEW;
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO audit.user_login_aud (action, user_login_id, username, password, hash_algorithm, last_login_date, password_attempts, created_at, created_by, updated_at, updated_by)
        VALUES ('UPDATE', NEW.id, NEW.username, NEW.password, NEW.hash_algorithm, NEW.last_login_date, NEW.password_attempts, NEW.created_at, NEW.created_by, NEW.updated_at, NEW.updated_by);
        RETURN NEW;
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO audit.user_login_aud (action, user_login_id, username, password, hash_algorithm, last_login_date, password_attempts, created_at, created_by, updated_at, updated_by)
        VALUES ('DELETE', OLD.id, OLD.username, OLD.password, OLD.hash_algorithm, OLD.last_login_date, OLD.password_attempts, OLD.created_at, OLD.created_by, OLD.updated_at, OLD.updated_by);
    RETURN OLD;
    END IF;
RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER user_login_audit_trigger
AFTER INSERT OR UPDATE OR DELETE ON public.user_login
FOR EACH ROW EXECUTE FUNCTION audit_user_login_changes();
