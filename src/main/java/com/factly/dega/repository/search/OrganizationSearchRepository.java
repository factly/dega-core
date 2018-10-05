package com.factly.dega.repository.search;

import com.factly.dega.domain.Organization;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Organization entity.
 */
public interface OrganizationSearchRepository extends ElasticsearchRepository<Organization, String> {
}
