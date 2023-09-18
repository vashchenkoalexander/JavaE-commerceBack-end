package com.payoya.diplomaproject.api.mongoDB;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("prod")
public interface IBaseEntityRepository extends MongoRepository<BaseEntity, Long> {
}
