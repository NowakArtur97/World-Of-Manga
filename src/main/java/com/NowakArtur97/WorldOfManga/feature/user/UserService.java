package com.NowakArtur97.WorldOfManga.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    public boolean isUsernameAlreadyInUse(String username) {

        return userRepository.existsUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, true,
                mapRolesToAuthorities(user.getRoles()));
    }

    public User loadLoggedInUsername() throws UsernameNotFoundException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
    }

    public boolean isUserLoggedIn() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return null != authentication && !("anonymousUser").equals(authentication.getName());
    }

    boolean isEmailAlreadyInUse(String email) {

        return userRepository.existsUserByEmail(email);
    }

    User save(User user) {

        userRepository.save(user);

        return user;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> usersRoles) {

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : usersRoles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
            authorities.add(authority);
        }

        return authorities;
    }
}