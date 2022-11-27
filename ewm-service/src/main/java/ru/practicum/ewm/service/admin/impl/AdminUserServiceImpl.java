package ru.practicum.ewm.service.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.user.NewUserDto;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.error.ConflictException;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.service.admin.AdminUserService;
import ru.practicum.ewm.storage.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository repository;

    @Override
    public List<UserDto> search(List<Long> ids) {
        List<User> users = repository.findAllById(ids);

        return UserMapper.toListUserDto(users);
    }

    @Override
    @Transactional
    public UserDto add(NewUserDto newUserDto) {
        try {
            User user = repository.save(UserMapper.toUser(newUserDto));
            return UserMapper.toUserDto(user);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}