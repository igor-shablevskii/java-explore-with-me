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
        log.info("Method: Post, path = /admin/compilations, AdminCompilationController/add," +
                "requestBody: {}", newCompilationsDto);

        return adminCompilationService.add(newCompilationsDto);
    }

    @DeleteMapping("/{compId}")
    public void delete(@PathVariable Long compId) {
        log.info("Method: Delete, path = /admin/compilations/{compId}, AdminCompilationController/delete," +
                "param: compId={}", compId);

        adminCompilationService.delete(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEvent(@PathVariable Long compId,
                            @PathVariable Long eventId) {
        log.info("Method: Delete, path = /admin/compilations/{compId}/events/{eventId}, " +
                "AdminCompilationController/deleteEvent, params: compId={}, eventId={}", compId, eventId);

        adminCompilationService.deleteEvent(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEvent(@PathVariable Long compId,
                         @PathVariable Long eventId) {
        log.info("Method: Patch, path = /admin/compilations/{compId}/events/{eventId}, " +
                "AdminCompilationController/addEvent, params: compId={}, eventId={}", compId, eventId);

        adminCompilationService.addEvent(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unPin(@PathVariable Long compId) {
        log.info("Method: Delete, path = /admin/compilations/{compId}/pin, AdminCompilationController/unPin, " +
                "param: compId={}", compId);

        adminCompilationService.unPin(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pin(@PathVariable Long compId) {
        log.info("Method: Patch, path = /admin/compilations/{compId}/pin, AdminCompilationController/pin, param: compId={}", compId);

        adminCompilationService.pin(compId);
    }
}