package ru.practicum.ewm.service.open;

import ru.practicum.ewm.dto.category.CategoryDto;

import java.util.List;

public interface PublicCategoryService {

    CategoryDto getOne(Long catId);

    List<CategoryDto> getAll(Integer from, Integer size);
}