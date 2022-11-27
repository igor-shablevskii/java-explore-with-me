package ru.practicum.ewm.controller.open;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.service.open.PublicCompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {

    private final PublicCompilationService publicCompilationService;

    @GetMapping
    public List<CompilationDto> getAll(@RequestParam(required = false) Boolean pinned,
                                       @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                       @RequestParam(defaultValue = "10") @Positive Integer size) {
        return publicCompilationService.getAll(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getOne(@PathVariable Long compId) {
        return publicCompilationService.getOne(compId);
    }
}
