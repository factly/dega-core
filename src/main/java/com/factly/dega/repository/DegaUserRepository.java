package com.factly.dega.repository;

import com.factly.dega.domain.DegaUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the DegaUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DegaUserRepository extends MongoRepository<DegaUser, String> {
    @Query("{}")
    Page<DegaUser> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<DegaUser> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<DegaUser> findOneWithEagerRelationships(String id);

    @Query("{'email': ?0}")
    Optional<DegaUser> findByEmailId(String emailId);

    Optional<DegaUser> findBySlug(String slug);
}
