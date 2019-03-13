package com.factly.dega.repository;

import com.factly.dega.domain.Status;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data MongoDB repository for the Status entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusRepository extends DegaCustomRepository<Status, String> {

    Optional<Status> findByName(String name);

}
