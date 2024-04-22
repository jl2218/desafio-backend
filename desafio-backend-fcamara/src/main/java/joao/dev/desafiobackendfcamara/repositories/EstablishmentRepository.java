package joao.dev.desafiobackendfcamara.repositories;

import joao.dev.desafiobackendfcamara.domain.establishment.Establishment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstablishmentRepository extends MongoRepository<Establishment, String> {

    Optional<Establishment> findByDocument(String document);

    boolean existsByDocument(String document);
}
