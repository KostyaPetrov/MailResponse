package ru.konstantinpetrov.mailresponse.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.konstantinpetrov.mailresponse.backend.dtoLayer.GetUserDTO;
import ru.konstantinpetrov.mailresponse.backend.entity.*;
import ru.konstantinpetrov.mailresponse.backend.jwt.JwtUtil;
import ru.konstantinpetrov.mailresponse.backend.service.CustomUserDetailsService;
import ru.konstantinpetrov.mailresponse.backend.repository.UserRepository;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final CustomUserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody GetUserDTO userDTO) throws Exception {
        System.out.println("Controller get User: " + userDTO);
        System.out.println("name " + userDTO.getName());
        System.out.println("password " + userDTO.getName());
        User user= new User();
        user.setName(userDTO.getName());
        user.setRole(Roles.USER);
        user.setBlockStatus(BlockStatus.FREE);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        System.out.println("User: " + user);
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
