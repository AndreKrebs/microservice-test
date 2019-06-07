package com.testando.blog.service.mapper;

import com.testando.blog.domain.*;
import com.testando.blog.service.dto.PostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Post and its DTO PostDTO.
 */
@Mapper(componentModel = "spring", uses = {BlogMapper.class, TagMapper.class})
public interface PostMapper extends EntityMapper<PostDTO, Post> {

    @Mapping(source = "blog.id", target = "blogId")
    @Mapping(source = "blog.name", target = "blogName")
    PostDTO toDto(Post post);

    @Mapping(source = "blogId", target = "blog")
    Post toEntity(PostDTO postDTO);

    default Post fromId(Long id) {
        if (id == null) {
            return null;
        }
        Post post = new Post();
        post.setId(id);
        return post;
    }
}
