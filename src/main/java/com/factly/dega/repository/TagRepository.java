package com.factly.dega.repository;

import com.factly.dega.domain.Tag;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Tag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TagRepository extends DegaCustomRepository<Tag, String> {

}
