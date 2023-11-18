package com.mscompprueba.ricardotravez.controller;

import com.mscompprueba.ricardotravez.dto.UsersDto;
import com.mscompprueba.ricardotravez.model.Users;
import com.mscompprueba.ricardotravez.service.IUploadFileService;
import com.mscompprueba.ricardotravez.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private IUploadFileService uploadFileService;

    private Path location;

    @PostMapping("/registrar")
    public ResponseEntity<Users> addUser(
            @RequestParam("username") String wusername,
            @RequestParam("email") String wemail,
            @RequestParam("password") String wpassword,
            @RequestParam("file") MultipartFile imagen
    ) {
        try {
            Users users = new Users();
            users.setUsername(wusername);
            users.setEmail(wemail);
            users.setPassword(wpassword);

            if (!imagen.isEmpty()) {
                String uniqueFileName = uploadFileService.copy(imagen);
                users.setImage(uniqueFileName);
            } else {
                System.out.println("No se proporcionó una imagen para cargar");
            }

            Users createdProduct = usersService.save(users);
            return ResponseEntity.ok(createdProduct);
        } catch (Exception e) {
            System.err.println("Error al procesar la solicitud: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/actualizar")
    public ResponseEntity<UsersDto> updateUser(@RequestBody UsersDto usersDto) {
        UsersDto updatedUser = usersService.update(usersDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Users user = usersService.buscarPersonaPorId(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UsersDto> loginUser(@RequestParam String username, @RequestParam String password) {
        UsersDto loggedInUser = usersService.login(username, password);
        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsersDto>> getAllUsers() {
        List<UsersDto> usersList = usersService.listar();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usersService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/imagen/{imagen}")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable("imagen") String imagen) throws IOException {
        location = Paths.get("uploads");
        Path file = location.resolve(imagen);
        Resource resource = new UrlResource(file.toUri());

        if (resource.exists() && resource.isReadable()) {
            byte[] imagenBytes = Files.readAllBytes(file);
            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(imagenBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
