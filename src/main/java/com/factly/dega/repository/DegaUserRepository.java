package com.factly.dega.repository;

import com.factly.dega.domain.DegaUser;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the DegaUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DegaUserRepository extends MongoRepository<DegaUser, String> {

}
