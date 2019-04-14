package com.factly.dega.repository;

import com.factly.dega.domain.RoleMapping;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the RoleMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleMappingRepository extends MongoRepository<RoleMapping, String> {

}
