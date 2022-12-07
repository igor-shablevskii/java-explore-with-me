package ru.practicum.ewm.service.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.service.admin.AdminCompilationService;
import ru.practicum.ewm.storage.CompilationRepository;

import javax.persistence.EntityManager;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final CompilationRepository repository;
    private final EntityManager entityManager;

    @Transactional
    @Override
    public CompilationDto add(NewCompilationDto newCompilationsDto) {
        Compilation compilation = repository.saveAndFlush(CompilationMapper.toCompilation(newCompilationsDto));
        entityManager.refresh(compilation);

        return CompilationMapper.toCompilationDto(compilation);
    }


    @Override
    public void delete(Long compId) {
        repository.deleteById(compId);
    }

    @Override
    public void deleteEvent(Long compId, Long eventId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found"));
        Set<Event> events = compilation.getEvents();
        events.remove(Event.builder().id(eventId).build());
        repository.save(compilation);
    }

    @Override
    public void addEvent(Long compId, Long eventId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found"));
        Set<Event> events = compilation.getEvents();
        events.add(Event.builder().id(eventId).build());
        repository.save(compilation);
    }

    @Override
    public void unPin(Long compId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found"));
        compilation.setPinned(false);
        repository.save(compilation);
    }

    @Override
    public void pin(Long compId) {
        Compilation compilations = repository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found"));
        compilations.setPinned(true);
        repository.save(compilations);
    }
}