package com.muyan.repository;

import com.muyan.domain.dto.CodeShareSearchDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author huliua
 * @version 1.0
 * @date 2024-09-03 22:13
 */
@Repository
public interface CodeShareRepository extends ElasticsearchRepository<CodeShareSearchDto, Long> {
}
