package com.factly.dega.repository;

import com.factly.dega.domain.Format;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Format entity.
 */
@Repository
public interface FormatRepository extends DegaCustomRepository<Format, String> {

}
