package ru.konstantinpetrov.mailresponse.backend.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.jwt.JwtUtil;
import ru.konstantinpetrov.mailresponse.backend.service.CustomUserDetailsService;

@RequiredArgsConstructor
public class AuthenticationDelegate implements JavaDelegate{
    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService userDetailsService;
    private JwtUtil jwtUtil;
    private Boolean isAuth;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception{
        String userName = (String) delegateExecution.getVariable("userName");
        String password = (String) delegateExecution.getVariable("password");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userName, password)
            );
        } catch (BadCredentialsException e) {
            isAuth=false;
            delegateExecution.setVariable("isAuth", isAuth);
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        final String jwt = jwtUtil.generateToken(userDetails);

        delegateExecution.setVariable("jwt", jwt);
        isAuth=true;
        delegateExecution.setVariable("isAuth", isAuth);

    }

}
