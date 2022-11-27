package ru.practicum.ewm.service.admin;

import ru.practicum.ewm.dto.user.NewUserDto;
import ru.practicum.ewm.dto.user.UserDto;

import java.util.List;

public interface AdminUserService {

    List<UserDto> search(List<Long> ids);

    UserDto add(NewUserDto newUserDto);

    void delete(Long id);
}
