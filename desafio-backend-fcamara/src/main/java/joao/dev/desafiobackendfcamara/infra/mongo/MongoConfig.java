package joao.dev.desafiobackendfcamara.infra.mongo;

import jakarta.annotation.PostConstruct;
import joao.dev.desafiobackendfcamara.domain.customer.Customer;
import joao.dev.desafiobackendfcamara.domain.establishment.Establishment;
import joao.dev.desafiobackendfcamara.domain.user.User;
import joao.dev.desafiobackendfcamara.domain.vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@Configuration
public class MongoConfig {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void initIndexes() {
        mongoTemplate.indexOps(Customer.class)
                .ensureIndex(new Index().on("phoneNumber", Sort.Direction.ASC).unique());
        mongoTemplate.indexOps(Customer.class)
                .ensureIndex(new Index().on("document", Sort.Direction.ASC).unique());

        mongoTemplate.indexOps(Establishment.class)
                .ensureIndex(new Index().on("phoneNumber", Sort.Direction.ASC).unique());
        mongoTemplate.indexOps(Establishment.class)
                .ensureIndex(new Index().on("document", Sort.Direction.ASC).unique());

        mongoTemplate.indexOps(User.class)
                .ensureIndex(new Index().on("username", Sort.Direction.ASC).unique());

        mongoTemplate.indexOps(Vehicle.class)
                .ensureIndex(new Index().on("plate", Sort.Direction.ASC).unique());
    }
}
