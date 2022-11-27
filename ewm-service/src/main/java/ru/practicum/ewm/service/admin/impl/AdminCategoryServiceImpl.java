package ru.practicum.ewm.service.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.NewCategoryDto;
import ru.practicum.ewm.error.ConflictException;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.service.admin.AdminCategoryService;
import ru.practicum.ewm.storage.CategoryRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository repository;

    @Override
    @Transactional
    public CategoryDto add(NewCategoryDto newCategoryDto) {
        try {
            Category categories = repository.save(CategoryMapper.toCategory(newCategoryDto));
            return CategoryMapper.toCategoryDto(categories);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CategoryDto edit(CategoryDto categoriesDto) {
        Category category = repository.findById(categoriesDto.getId())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        if (categoriesDto.getName() != null && !categoriesDto.getName().isEmpty()) {
            category.setName(categoriesDto.getName());
        }
        try {
            repository.saveAndFlush(category);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMessage());
        }

        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    @Transactional
    public void delete(Long catId) {
        repository.deleteById(catId);
    }
}