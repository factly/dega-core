package com.factly.dega.repository.search;

import com.factly.dega.domain.Format;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Format entity.
 */
public interface FormatSearchRepository extends ElasticsearchRepository<Format, String> {
}
