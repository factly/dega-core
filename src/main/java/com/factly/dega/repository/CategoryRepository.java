package com.factly.dega.repository;

import com.factly.dega.domain.Category;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data MongoDB repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends DegaCustomRepository<Category, String> {

}
