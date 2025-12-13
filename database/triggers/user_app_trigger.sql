CREATE OR REPLACE FUNCTION audit_user_app_changes()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO audit.user_app_aud (action, user_id, first_name, middle_name, last_name, gender, email_address, ssn, birthday, status, created_at, created_by, updated_at, updated_by)
        VALUES ('INSERT', NEW.id, NEW.first_name, NEW.middle_name, NEW.last_name, NEW.gender, NEW.email_address, NEW.ssn, NEW.birthday, NEW.status, NEW.created_at, NEW.created_by, NEW.updated_at, NEW.updated_by);
        RETURN NEW;
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO audit.user_app_aud (action, user_id, first_name, middle_name, last_name, gender, email_address, ssn, birthday, status, created_at, created_by, updated_at, updated_by)
        VALUES ('UPDATE', NEW.id, NEW.first_name, NEW.middle_name, NEW.last_name, NEW.gender, NEW.email_address, NEW.ssn, NEW.birthday, NEW.status, NEW.created_at, NEW.created_by, NEW.updated_at, NEW.updated_by);
        RETURN NEW;
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO audit.user_app_aud (action, user_id, first_name, middle_name, last_name, gender, email_address, ssn, birthday, status, created_at, created_by, updated_at, updated_by)
        VALUES ('DELETE', OLD.id, OLD.first_name, OLD.middle_name, OLD.last_name, OLD.gender, OLD.email_address, OLD.ssn, OLD.birthday, OLD.status, OLD.created_at, OLD.created_by, OLD.updated_at, OLD.updated_by);
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER audit_user_app_changes
AFTER INSERT OR UPDATE OR DELETE ON public.user_app
FOR EACH ROW EXECUTE FUNCTION audit_user_app_changes();
