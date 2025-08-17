package winwin.auth_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import winwin.auth_api.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
