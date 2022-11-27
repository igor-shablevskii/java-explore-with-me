package ru.practicum.ewm.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.NewCategoryDto;
import ru.practicum.ewm.service.admin.AdminCategoryService;
import ru.practicum.ewm.utils.Create;
import ru.practicum.ewm.utils.Update;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
@Validated
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @PostMapping
    public CategoryDto add(@Validated(Create.class) @RequestBody NewCategoryDto newCategoryDto) {
        return adminCategoryService.add(newCategoryDto);
    }

    @PatchMapping
    public CategoryDto edit(@Validated(Update.class) @RequestBody CategoryDto categoriesDto) {
        return adminCategoryService.edit(categoriesDto);
    }

    @DeleteMapping("/{catId}")
    public void delete(@PathVariable Long catId) {
        adminCategoryService.delete(catId);
    }
}