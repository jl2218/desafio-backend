package joao.dev.desafiobackendfcamara.repositories;

import joao.dev.desafiobackendfcamara.domain.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
