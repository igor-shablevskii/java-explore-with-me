package ru.practicum.ewm.service.admin;

import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.NewCategoryDto;

public interface AdminCategoryService {

    CategoryDto add(NewCategoryDto newCategoryDto);

    CategoryDto edit(CategoryDto categoriesDto);

    void delete(Long catId);

}