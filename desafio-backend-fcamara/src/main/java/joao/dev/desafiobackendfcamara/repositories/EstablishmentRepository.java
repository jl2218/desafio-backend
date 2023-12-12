package joao.dev.desafiobackendfcamara.repositories;

import joao.dev.desafiobackendfcamara.domain.establishment.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {

    Optional<Establishment> findByDocument(String document);

    boolean existsByDocument(String document);
}
