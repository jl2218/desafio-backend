package joao.dev.desafiobackendfcamara.repositories;

import joao.dev.desafiobackendfcamara.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByUsername(String username);
}
