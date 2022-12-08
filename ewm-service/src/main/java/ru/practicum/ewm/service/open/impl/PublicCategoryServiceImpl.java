package ru.practicum.ewm.service.open.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.service.open.PublicCategoryService;
import ru.practicum.ewm.storage.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicCategoryServiceImpl implements PublicCategoryService {

    private final CategoryRepository repository;

    @Override
    public CategoryDto getOne(Long catId) {
        Category category = repository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        int page = from / size;
        Page<Category> categories = repository
                .findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));

        return CategoryMapper.toListCategory(categories);
    }
}