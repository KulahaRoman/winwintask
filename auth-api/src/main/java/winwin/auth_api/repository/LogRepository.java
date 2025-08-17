package winwin.auth_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import winwin.auth_api.entity.Log;

import java.util.UUID;

public interface LogRepository extends JpaRepository<Log, UUID> {
}
