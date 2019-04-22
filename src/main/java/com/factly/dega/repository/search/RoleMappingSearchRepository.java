package com.factly.dega.repository.search;

import com.factly.dega.domain.RoleMapping;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RoleMapping entity.
 */
public interface RoleMappingSearchRepository extends ElasticsearchRepository<RoleMapping, String> {
}
