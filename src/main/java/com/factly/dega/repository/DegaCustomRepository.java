package com.factly.dega.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.factly.dega.domain.Post;

/**
 * Created by Sravan on 12/6/2018.
 */
@NoRepositoryBean
public interface DegaCustomRepository<T, ID> extends MongoRepository<T, ID> {

    Page<T> findByClientId(String clientId, Pageable pageable);

    Optional<T> findByClientIdAndSlug(String clientId, String slug);
}
