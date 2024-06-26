package com.example.onlinebookstore.mapper;

import com.example.onlinebookstore.config.MapperConfig;
import com.example.onlinebookstore.dto.CategoryRequestDto;
import com.example.onlinebookstore.dto.CategoryResponseDto;
import com.example.onlinebookstore.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    Category toModel(CategoryRequestDto categoryDto);
}
