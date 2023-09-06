package com.payoya.diplomaproject.api.mongoDB;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBaseEntityRepository extends MongoRepository<BaseEntity, Long> {
}
