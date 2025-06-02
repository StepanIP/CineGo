package com.service.film.mapper;

import com.service.film.domain.Comment;
import com.service.film.dto.CommentRequestDto;
import com.service.film.dto.CommentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "filmId", source = "film.id")
    CommentResponseDto entityToDto(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "film", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Comment requestDtoToEntity(CommentRequestDto dto);
} 