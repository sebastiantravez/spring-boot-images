package com.mscompprueba.ricardotravez.service;

import com.mscompprueba.ricardotravez.dto.UsersDto;
import com.mscompprueba.ricardotravez.model.Users;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UsersService {
    Users save (Users users);
    UsersDto update (UsersDto usersDto);
    Users buscarPersonaPorId(Long id);
    UsersDto login(String username, String password);
    List<UsersDto> listar();
    void delete(Long id);
}

