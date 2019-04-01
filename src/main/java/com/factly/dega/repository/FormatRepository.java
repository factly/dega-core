package com.factly.dega.repository;

import com.factly.dega.domain.Format;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data MongoDB repository for the Format entity.
 */
@Repository
public interface FormatRepository extends DegaCustomRepository<Format, String> {

}
