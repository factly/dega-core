package com.factly.dega.repository.search;

import com.factly.dega.domain.DegaUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DegaUser entity.
 */
public interface DegaUserSearchRepository extends ElasticsearchRepository<DegaUser, String> {
}
