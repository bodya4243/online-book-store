package com.example.onlinebookstore.mapper;

import com.example.onlinebookstore.config.MapperConfig;
import com.example.onlinebookstore.dto.BookDtoWithoutCategoryIds;
import com.example.onlinebookstore.dto.BookRequestDto;
import com.example.onlinebookstore.dto.BookResponseDto;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.model.Category;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookResponseDto toDto(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookResponseDto bookResponseDto, Book book) {
        if (book.getCategoryId() != null) {
            bookResponseDto.setCategoryIds(book.getCategoryId().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet()));
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoryId", source = "categoryIds", qualifiedByName = "categoryById")
    Book toModel(BookRequestDto requestDto);

    @Named("categoryById")
    default Set<Category> categoryById(Set<Long> categoryIds) {
        return categoryIds.stream()
                .map(Category::new)
                .collect(Collectors.toSet());
    }

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);
}
