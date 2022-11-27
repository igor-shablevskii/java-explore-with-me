package ru.practicum.ewm.service.open;

import ru.practicum.ewm.dto.compilation.CompilationDto;

import java.util.List;

public interface PublicCompilationService {

    CompilationDto getOne(Long compId);

    List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size);
}
