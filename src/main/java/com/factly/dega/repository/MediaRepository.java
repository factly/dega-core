package com.factly.dega.repository;

import com.factly.dega.domain.Media;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Media entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MediaRepository extends DegaCustomRepository<Media, String> {

}
