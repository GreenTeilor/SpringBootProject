package by.teachmeskills.springbootproject.services.implementation;

import by.teachmeskills.springbootproject.entities.Role;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.principal.UserPrincipal;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//There are two methods for defining permission - hasRole and hasAuthority.
//The first doesn't require ROLE_ prefix.
//The second requires it so requestMatchers.hasRole("ADMIN") is equal to requestMatchers.hasAuthority("ROLE_ADMIN")
//But they do the same things.


//UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//@PreAuthorize() - поновее @RolesAllowed @Secured - постарее

@Service
@RequiredArgsConstructor
public class UserPrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            user.get().getRoles().add(Role.builder().id(0).name("ROLE_USER").build());
            return new UserPrincipal(user.get());
        } else {
            throw new UsernameNotFoundException("User wasn't found");
        }
    }
}
