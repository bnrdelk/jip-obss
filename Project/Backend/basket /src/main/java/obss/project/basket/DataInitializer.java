package obss.project.basket;

import obss.project.basket.dto.RegisterDto;
import obss.project.basket.entity.*;
import obss.project.basket.repository.RoleRepository;
import obss.project.basket.repository.TagRepository;
import obss.project.basket.repository.UserRepository;
import obss.project.basket.service.AuthService;
import obss.project.basket.service.Impl.AuthServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashSet;
import java.util.Set;

// bunun yerine flyway ekle
@EnableJpaRepositories(basePackages = "obss.project.basket.repository")
@Configuration
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    // auto inits db for tags & create a system manager
    @Bean
    public CommandLineRunner initDatabase(TagRepository tagRepository, AuthService authService) {
        return args -> {
            for (TagType tagType : TagType.values()) {
                if (tagRepository.findByTagType(tagType).isEmpty()) {
                    Tag tag = new Tag();
                    tag.setTagType(tagType);
                    tagRepository.save(tag);
                }
                // Initialize admin role if it doesn't exist
                Role adminRole = roleRepository.findByName(RoleType.ADMIN)
                        .orElseGet(() -> {
                            Role role = new Role();
                            role.setName(RoleType.ADMIN);
                            return roleRepository.save(role);
                        });

                RegisterDto registerDto = new RegisterDto("admin", "root", "admin@root.com", "root");
                if (userRepository.findByEmail("admin@root.com").isEmpty()) {
                    authService.register(registerDto, "ADMIN");
                }
            }
        };
    }
}