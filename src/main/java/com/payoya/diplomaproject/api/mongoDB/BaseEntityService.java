package com.payoya.diplomaproject.api.mongoDB;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseEntityService {

    private IBaseEntityRepository repository;

    public BaseEntityService(IBaseEntityRepository repository) {
        this.repository = repository;
    }

    public List<BaseEntity> getAllEntities() {
        return repository.findAll();
    }

    public BaseEntity saveEntity(BaseEntity entity) {
        return repository.save(entity);
    }

}
