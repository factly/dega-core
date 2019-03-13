package com.factly.dega.repository;

import com.factly.dega.domain.Organization;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data MongoDB repository for the Organization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationRepository extends DegaCustomRepository<Organization, String> {

    Optional<Organization> findBySlug(String slug);

}
