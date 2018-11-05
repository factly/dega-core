package com.factly.dega.repository;

import com.factly.dega.domain.Format;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Format entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormatRepository extends MongoRepository<Format, String> {

}
