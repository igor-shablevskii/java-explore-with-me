package ru.practicum.ewm.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.user.NewUserDto;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.service.admin.AdminUserService;
import ru.practicum.ewm.utils.Create;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Validated
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public List<UserDto> search(@RequestParam List<Long> ids) {
        log.info("Method: Get, path = /admin/users, AdminUserService/search, param: ids = {}", ids);

        return adminUserService.search(ids);
    }

    @PostMapping
    public UserDto add(@Validated(Create.class) @RequestBody NewUserDto newUserDto) {
        log.info("Method: Post, path = /admin/users, AdminUserService/add, requestBody: {}", newUserDto);

        return adminUserService.add(newUserDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Method: Delete, path = /admin/users/{id}, AdminUserService/delete, pathVariable: id = {}", id);

        adminUserService.delete(id);
    }
}