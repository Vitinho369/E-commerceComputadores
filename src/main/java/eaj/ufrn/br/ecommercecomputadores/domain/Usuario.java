package eaj.ufrn.br.ecommercecomputadores.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.UserDatabase;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="usuario")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome de usuário não pode ser vazio")
    private String username;

    @NotBlank(message = "O nome de usuário não pode ser vazio")
    private String password;

    @NotNull(message = "O usuário deve ser um admin ou um usuário comum")
    private boolean isAdmin;

//    public boolean getIsAdmin() {
//        return isAdmin;
//    }
//
//    public void setAdmin(boolean isAdmin){
//        this.isAdmin = isAdmin;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.isAdmin){
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else{
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
