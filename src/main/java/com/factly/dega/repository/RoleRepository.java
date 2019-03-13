package com.factly.dega.repository;

import com.factly.dega.domain.Role;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Role entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleRepository extends DegaCustomRepository<Role, String> {

}
