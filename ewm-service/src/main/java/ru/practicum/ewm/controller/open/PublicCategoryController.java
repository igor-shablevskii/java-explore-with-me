package ru.practicum.ewm.controller.open;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.service.open.PublicCategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {

    private final PublicCategoryService publicCategoryService;

    @GetMapping("/{catId}")
    public CategoryDto getOne(@PathVariable Long catId) {
        log.info("Method: Get, path = /categories/{catId}, PublicCategoryController/getOne, " +
                "pathVariable: catId={}", catId);

        return publicCategoryService.getOne(catId);
    }

    @GetMapping
    public List<CategoryDto> getAll(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                    @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Method: Get, path = /categories, PublicCategoryController/getAll, " +
                "params: from={}, size={}", from, size);

        return publicCategoryService.getAll(from, size);
    }
}