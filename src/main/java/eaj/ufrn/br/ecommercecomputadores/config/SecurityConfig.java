package eaj.ufrn.br.ecommercecomputadores.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.web.server.ServerHttpSecurity.http;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http)  throws Exception{
        return http
                .authorizeHttpRequests(
                        auth ->{
//                            auth.requestMatchers("/js/**", "/css/**", "/images/**", "/fonts/**", "/img/**", "/").permitAll();
//                            auth.requestMatchers("/index").permitAll();
//                            auth.requestMatchers("/cadastroUsuario").permitAll();
//                            auth.requestMatchers("/login").permitAll();
//                            auth.requestMatchers("/admin", "/cadastro", "/deletar", "/editar","/salvar").hasRole("ADMIN");
//                            auth.requestMatchers("/adicionarCarrinho","/finalizarCompra", "/removerItemCarrinho/", "/verCarrinho").hasRole("USER");
                            auth.anyRequest().permitAll();
                        }
                        ).formLogin(login -> login.loginPage("/login"))
                            .logout(l->{
                                l.logoutUrl("/logout");
                                l.clearAuthentication(true);
                                l.deleteCookies().invalidateHttpSession(true);
                            })
                .build();
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
