package org.simelabs.catelog.application.service.repository;

import org.simelabs.catelog.application.service.models.Brand;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends MongoRepository<Brand, String> {
    Optional<Brand> findByName(String name);
}
