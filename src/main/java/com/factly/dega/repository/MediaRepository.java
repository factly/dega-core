package com.factly.dega.repository;

import com.factly.dega.domain.Media;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data MongoDB repository for the Media entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MediaRepository extends MongoRepository<Media, String> {

    Optional<Media> findByClientIdAndSlug(String clientId, String slug);

}
