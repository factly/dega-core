package com.factly.dega.repository;

import java.util.Optional;

import com.factly.dega.domain.Status;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Status entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusRepository extends MongoRepository<Status, String> {

    Optional<Status> findByName(String name);

    Optional<Status> findByClientIdAndSlug(String clientId, String slug);
}
