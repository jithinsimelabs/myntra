package org.simelabs.catelog.application.service.repository;

import org.simelabs.catelog.application.service.models.Category;
import org.simelabs.catelog.application.service.models.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {
}
