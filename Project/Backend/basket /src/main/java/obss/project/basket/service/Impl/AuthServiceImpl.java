package obss.project.basket.service.Impl;

import jakarta.validation.Valid;
import obss.project.basket.config.security.JwtTokenProvider;
import obss.project.basket.dto.LoginDto;
import obss.project.basket.dto.RegisterDto;
import obss.project.basket.entity.*;
import obss.project.basket.repository.AdminRepository;
import obss.project.basket.repository.RoleRepository;
import obss.project.basket.repository.SellerRepository;
import obss.project.basket.repository.UserRepository;
import obss.project.basket.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SellerRepository sellerRepository;
    private final AdminRepository adminRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, SellerRepository sellerRepository, AdminRepository adminRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.sellerRepository = sellerRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    @Transactional
    public String register(@Valid RegisterDto registerDto, String roleParam) {

        if (userRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            return "Email already exists!";
        }

        if ("seller".equalsIgnoreCase(roleParam)) {
            Seller user = new Seller();
            user.setRoles(Set.of(getOrCreateRole(RoleType.USER), getOrCreateRole(RoleType.SELLER)));
            user.setName(registerDto.getName());
            user.setSurname(registerDto.getSurname());
            user.setEmail(registerDto.getEmail());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            sellerRepository.save(user);
        } else if ("user".equalsIgnoreCase(roleParam)) {
            User user = new User();
            user.setRoles(Set.of(getOrCreateRole(RoleType.USER)));
            user.setName(registerDto.getName());
            user.setSurname(registerDto.getSurname());
            user.setEmail(registerDto.getEmail());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            userRepository.save(user);
        } else if ("admin".equalsIgnoreCase(roleParam)) {
            Admin user = new Admin();
            user.setRoles(Set.of(getOrCreateRole(RoleType.ADMIN),getOrCreateRole(RoleType.USER),getOrCreateRole(RoleType.SELLER)));
            user.setName(registerDto.getName());
            user.setSurname(registerDto.getSurname());
            user.setEmail(registerDto.getEmail());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            adminRepository.save(user);
        } else {
            throw new IllegalArgumentException("Invalid role parameter: " + roleParam);
        }

        return "User registered successfully!";
    }


    private Role getOrCreateRole(RoleType roleType) {
        return roleRepository.findByName(roleType)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(roleType);
                    return roleRepository.save(newRole);
                });
    }

}
