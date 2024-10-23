package ru.konstantinpetrov.mailresponse.backend.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.entity.Roles;
import ru.konstantinpetrov.mailresponse.backend.entity.User;
import ru.konstantinpetrov.mailresponse.backend.jwt.JwtUtil;
import ru.konstantinpetrov.mailresponse.backend.service.CustomUserDetailsService;
import ru.konstantinpetrov.mailresponse.backend.service.UserService;

@Component
@RequiredArgsConstructor
public class AuthenticationModeratorDelegate implements JavaDelegate {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private  Boolean isAuth;


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
        String successMessage = "Пользователь " + userName + " успешно аутентифицирован.";
        delegateExecution.setVariable("operationMessage", successMessage);

        // Получаем объект пользователя по имени
        User user = userService.findUser(userName);
        // Сохраняем ID пользователя в переменной Camunda
        delegateExecution.setVariable("userId", user.getUserId());

        if(user.getRole() == Roles.MODERATOR){
            delegateExecution.setVariable("moderatorRoleApprove", true);
            
        }else{
            delegateExecution.setVariable("moderatorRoleApprove", false);
            throw new Exception("Permission denied");
        }
    }

}
