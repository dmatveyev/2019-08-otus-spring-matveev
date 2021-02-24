package ru.otus.spring01.library.rest;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthRestController {

    @PostMapping(value = "/authMe")
    public ResponseEntity<AuthUser> authMe(@RequestBody Object authorDto) {

        AuthUser user = AuthUser.builder().userId("1").email("email@email.emaio").login(true).build();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Data
    @Builder
    static class AuthUser {

        private String userId;
        private String email;
        private Boolean login;
    }
}
