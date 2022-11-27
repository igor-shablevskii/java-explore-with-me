package ru.practicum.ewm.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.service.admin.AdminCompilationService;
import ru.practicum.ewm.utils.Create;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
@Validated
public class AdminCompilationController {

    private final AdminCompilationService adminCompilationService;

    @PostMapping
    public CompilationDto add(@Validated(Create.class) @RequestBody NewCompilationDto newCompilationsDto) {
        return adminCompilationService.add(newCompilationsDto);
    }

    @DeleteMapping("/{compId}")
    public void delete(@PathVariable Long compId) {
        adminCompilationService.delete(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEvent(@PathVariable Long compId,
                            @PathVariable Long eventId) {
        adminCompilationService.deleteEvent(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEvent(@PathVariable Long compId,
                         @PathVariable Long eventId) {
        adminCompilationService.addEvent(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unPin(@PathVariable Long compId) {
        adminCompilationService.unPin(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pin(@PathVariable Long compId) {
        adminCompilationService.pin(compId);
    }

}
