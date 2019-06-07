package com.testando.blog.service;

import com.mysql.fabric.xmlrpc.base.Array;
import com.testando.blog.domain.Blog;
import com.testando.blog.repository.BlogRepository;
import com.testando.blog.repository.search.BlogSearchRepository;
import com.testando.blog.service.dto.BlogDTO;
import com.testando.blog.service.mapper.BlogMapper;

import org.elasticsearch.common.util.iterable.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Blog.
 */
@Service
@Transactional
public class BlogService {

    private final Logger log = LoggerFactory.getLogger(BlogService.class);

    private final BlogRepository blogRepository;

    private final BlogMapper blogMapper;

    private final BlogSearchRepository blogSearchRepository;

    public BlogService(BlogRepository blogRepository, BlogMapper blogMapper, BlogSearchRepository blogSearchRepository) {
        this.blogRepository = blogRepository;
        this.blogMapper = blogMapper;
        this.blogSearchRepository = blogSearchRepository;
    }

    /**
     * Save a blog.
     *
     * @param blogDTO the entity to save
     * @return the persisted entity
     */
    public BlogDTO save(BlogDTO blogDTO) {
        log.debug("Request to save Blog : {}", blogDTO);
        Blog blog = blogMapper.toEntity(blogDTO);
        blog = blogRepository.save(blog);
        BlogDTO result = blogMapper.toDto(blog);
        blogSearchRepository.save(blog);
        return result;
    }

    /**
     * Get all the blogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BlogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Blogs");
        return blogRepository.findAll(pageable)
            .map(blogMapper::toDto);
    }


    /**
     * Get one blog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BlogDTO> findOne(Long id) {
        log.debug("Request to get Blog : {}", id);
        
        Iterable<Long> idEs = Arrays.asList(id);
        
        
        
        Iterable<Blog> blogs = blogSearchRepository.findAllById(idEs);
        
        System.out.println("#########################################");
        blogs.forEach(blog -> {
        	System.out.println(blog.toString());
        });
        System.out.println("#########################################");
        return blogRepository.findById(id)
            .map(blogMapper::toDto);
    }

    /**
     * Delete the blog by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Blog : {}", id);
        blogRepository.deleteById(id);
        blogSearchRepository.deleteById(id);
    }

    /**
     * Search for the blog corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BlogDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Blogs for query {}", query);
        return blogSearchRepository.search(queryStringQuery(query), pageable)
            .map(blogMapper::toDto);
    }
}
