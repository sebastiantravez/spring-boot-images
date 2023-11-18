package com.mscompprueba.ricardotravez.service.impl;

import com.mscompprueba.ricardotravez.dto.UsersDto;
import com.mscompprueba.ricardotravez.model.Users;
import com.mscompprueba.ricardotravez.repository.UsersRepository;
import com.mscompprueba.ricardotravez.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Users save(Users users) {
        usersRepository.save(users);
        return users;
    }


    @Override
    public UsersDto update(UsersDto usersDto) {
        Optional<Users> usersOptional = usersRepository.findById(usersDto.getId());
        if (usersOptional.isEmpty()) {
            throw new IllegalArgumentException("Error no se puede actualizar");
        }
        usersOptional.get().setUsername(usersDto.getUsername());
        usersOptional.get().setEmail(usersDto.getEmail());
        usersOptional.get().setPassword(usersDto.getPassword());
        usersOptional.get().setImage(usersDto.getImage().toString());
        this.usersRepository.save(usersOptional.get());
        return usersDto;
    }

    @Override
    public Users buscarPersonaPorId(Long id) {
        Optional<Users> optionalUsers = usersRepository.findById(id);
        if (!optionalUsers.isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        return optionalUsers.get();
    }

    @Override
    public UsersDto login(String username, String password) {
        Users users =  usersRepository.findByUsernameAndPassword(username, password);

        if (users != null) {
            UsersDto usersDto = new UsersDto(
                    users.getId(),
                    users.getUsername(),
                    users.getEmail(),
                    "",
                    users.getImage(),
                    null
            );
            return usersDto;
        } else {
            throw new RuntimeException("Error: No se puede loguear");
        }
    }

    @Override
    public List<UsersDto> listar() {
        return usersRepository.findAll().stream().map(users -> UsersDto.builder()
                        .id(users.getId())
                        .username(users.getUsername())
                        .email(users.getEmail())
                        .password(null)
                        .image(users.getImage())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Optional<Users> usersOptional = usersRepository.findById(id);
        if (usersOptional.isPresent()) {
            usersRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Error: No se puede eliminar, el usuario no existe");
        }
    }
}
