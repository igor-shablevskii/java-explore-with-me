package ru.practicum.ewm.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.model.Compilation;
import ru.practicum.ewm.model.Event;

import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {

    public static Compilation toCompilation(NewCompilationDto newCompilationsDto) {
        return Compilation.builder()
                .events(newCompilationsDto.getEvents()
                        .stream()
                        .map(e -> Event.builder().id(e).build())
                        .collect(Collectors.toSet()))
                .pinned(newCompilationsDto.getPinned())
                .title(newCompilationsDto.getTitle())
                .build();
    }

    public static CompilationDto toCompilationDto(Compilation compilations) {
        return CompilationDto.builder()
                .id(compilations.getId())
                .events(compilations.getEvents()
                        .stream()
                        .map(CompilationMapper::toEventShortDto)
                        .collect(Collectors.toList()))
                .pinned(compilations.getPinned())
                .title(compilations.getTitle())
                .build();
    }

    private static CompilationDto.EventShortDto toEventShortDto(Event event) {
        return CompilationDto.EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CompilationDto.EventShortDto.CategoryDto.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(CompilationDto.EventShortDto.UserShortDto.builder()
                        .id(event.getInitiator().getId())
                        .name(event.getInitiator().getName())
                        .build())
                .paid(event.getPaid())
                .title(event.getTitle())
                .build();
    }

    public static List<CompilationDto> toListCompilationDto(Page<Compilation> compilations) {
        return compilations.stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }
}