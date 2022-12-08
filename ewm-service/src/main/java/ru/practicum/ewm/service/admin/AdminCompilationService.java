package ru.practicum.ewm.service.admin;

import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;

public interface AdminCompilationService {

    CompilationDto add(NewCompilationDto newCompilationsDto);

    void delete(Long compId);

    void deleteEvent(Long compId, Long eventId);

    void addEvent(Long compId, Long eventId);

    void unPin(Long compId);

    void pin(Long compId);
}
