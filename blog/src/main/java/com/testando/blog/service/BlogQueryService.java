package com.testando.blog.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.testando.blog.domain.Blog;
import com.testando.blog.domain.*; // for static metamodels
import com.testando.blog.repository.BlogRepository;
import com.testando.blog.repository.search.BlogSearchRepository;
import com.testando.blog.service.dto.BlogCriteria;
import com.testando.blog.service.dto.BlogDTO;
import com.testando.blog.service.mapper.BlogMapper;

/**
 * Service for executing complex queries for Blog entities in the database.
 * The main input is a {@link BlogCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BlogDTO} or a {@link Page} of {@link BlogDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BlogQueryService extends QueryService<Blog> {

    private final Logger log = LoggerFactory.getLogger(BlogQueryService.class);

    private final BlogRepository blogRepository;

    private final BlogMapper blogMapper;

    private final BlogSearchRepository blogSearchRepository;

    public BlogQueryService(BlogRepository blogRepository, BlogMapper blogMapper, BlogSearchRepository blogSearchRepository) {
        this.blogRepository = blogRepository;
        this.blogMapper = blogMapper;
        this.blogSearchRepository = blogSearchRepository;
    }

    /**
     * Return a {@link List} of {@link BlogDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BlogDTO> findByCriteria(BlogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Blog> specification = createSpecification(criteria);
        return blogMapper.toDto(blogRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BlogDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BlogDTO> findByCriteria(BlogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Blog> specification = createSpecification(criteria);
        return blogRepository.findAll(specification, page)
            .map(blogMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BlogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Blog> specification = createSpecification(criteria);
        return blogRepository.count(specification);
    }

    /**
     * Function to convert BlogCriteria to a {@link Specification}
     */
    private Specification<Blog> createSpecification(BlogCriteria criteria) {
        Specification<Blog> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Blog_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Blog_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Blog_.description));
            }
        }
        return specification;
    }
}
