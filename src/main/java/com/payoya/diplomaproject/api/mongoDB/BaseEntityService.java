package com.payoya.diplomaproject.api.mongoDB;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("prod")
public class BaseEntityService {

    private IBaseEntityRepository repository;

    public BaseEntityService(IBaseEntityRepository repository) {
        this.repository = repository;
    }

    public List<BaseEntity> getAllEntities() {
        return repository.findAll();
    }

    public void saveEntity(BaseEntity entity) {
        repository.save(entity);
    }

}
