package ru.konstantinpetrov.mailresponse.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
    // login/passw хранятся в памяти
    // Здесь заданы login,passw,role для каждого пользователя системы
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth
//        .inMemoryAuthentication()
//        .withUser("normaluser").password(passwordEncoder().encode("123456")).roles("USERS")
//        .and()
//        .withUser("admin").password(passwordEncoder().encode("123456")).roles("ADMIN")
//        ;
//    }

    @Override
    // Указываю, что доступ ко всем страницам должен проходить только после аутентификации
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors().and()
        .csrf().disable()
        .authorizeRequests()
//        .antMatchers("/admin/*").hasRole("ADMIN")   // /adm/** только для пользователей группы "ADMIN"
        .antMatchers("/**").permitAll()       // /open/** разрешено всем без входа в систему
//        .anyRequest().authenticated()              // остальные страницы только после входа в систему
//        .and()
//        
//        .formLogin()
//        .loginPage("/login")                         // url для запроса login
//        .failureUrl("/login?error=true")             // url, куда отправиться при ошибке аутентификации
//        .defaultSuccessUrl("/hello")                 // страница по умолчанию, если пользователь просто обратился к странице "/login"
//        .permitAll()
        
//        .and()
//        .logout()
//        .logoutUrl("/logout")                        // url для выхода из системы
//        .logoutSuccessUrl("/logoutDone")             // куда отправиться после успешного выхода из системы
//        .permitAll()
        ;
    }
    
//    @Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(0); 
//        return bean;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}