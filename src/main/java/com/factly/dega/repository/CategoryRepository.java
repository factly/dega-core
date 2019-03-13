package com.factly.dega.repository;

import com.factly.dega.domain.Category;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends DegaCustomRepository<Category, String> {

}
