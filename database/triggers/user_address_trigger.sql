CREATE OR REPLACE FUNCTION audit_user_address_changes()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO audit.user_address_aud (action, user_address_id, user_id, address_line_1, address_line_2, address_line_3, state, city, zipcode, country, is_primary, created_at, created_by, updated_at, updated_by)
        VALUES ('INSERT', NEW.id, NEW.user_id, NEW.address_line_1, NEW.address_line_2, NEW.address_line_3, NEW.state, NEW.city, NEW.zipcode, NEW.country, NEW.is_primary, NEW.created_at, NEW.created_by, NEW.updated_at, NEW.updated_by);
        RETURN NEW;
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO audit.user_address_aud (action, user_address_id, user_id, address_line_1, address_line_2, address_line_3, state, city, zipcode, country, is_primary, created_at, created_by, updated_at, updated_by)
        VALUES ('UPDATE', NEW.id, NEW.user_id, NEW.address_line_1, NEW.address_line_2, NEW.address_line_3, NEW.state, NEW.city, NEW.zipcode, NEW.country, NEW.is_primary, NEW.created_at, NEW.created_by, NEW.updated_at, NEW.updated_by);
        RETURN NEW;
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO audit.user_address_aud (action, user_address_id, user_id, address_line_1, address_line_2, address_line_3, state, city, zipcode, country, is_primary, created_at, created_by, updated_at, updated_by)
        VALUES ('DELETE', OLD.id, OLD.user_id, OLD.address_line_1, OLD.address_line_2, OLD.address_line_3, OLD.state, OLD.city, OLD.zipcode, OLD.country, OLD.is_primary, OLD.created_at, OLD.created_by, OLD.updated_at, OLD.updated_by);
        RETURN OLD;
    END IF;
RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER user_address_audit_trigger
AFTER INSERT OR UPDATE OR DELETE ON public.user_address
FOR EACH ROW EXECUTE FUNCTION audit_user_address_changes();
