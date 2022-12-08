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
        log.info("Method: Post, path = admin/categories, AdminCategoryController/add, requestBody: {}", newCategoryDto);

        return adminCategoryService.add(newCategoryDto);
    }

    @PatchMapping
    public CategoryDto edit(@Validated(Update.class) @RequestBody CategoryDto categoryDto) {
        log.info("Method: Patch, path = admin/categories, AdminCategoryController/edit, requestBody: {}", categoryDto);

        return adminCategoryService.edit(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void delete(@PathVariable Long catId) {
        log.info("Method: Delete, path = admin/categories/{catId}, AdminCategoryController/delete, param: catId={}",
                catId);

        adminCategoryService.delete(catId);
    }
}