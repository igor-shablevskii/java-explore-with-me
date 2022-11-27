package ru.practicum.ewm.service.open.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.error.NotFoundException;
import ru.practicum.ewm.mapper.CompilationMapper;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.service.open.PublicCompilationService;
import ru.practicum.ewm.storage.CompilationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicCompilationServiceImpl implements PublicCompilationService {

    private final CompilationRepository repository;

    @Transactional
    @Override
    public CompilationDto getOne(Long compId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation not found"));
        log.info("Compilation: {}", compilation.getEvents());

        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size) {
        int page = from / size;
        Page<Compilation> compilations = repository.findAll(
                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("pinned"), pinned),
                        PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));

        return CompilationMapper.toListCompilationDto(compilations);
    }
}