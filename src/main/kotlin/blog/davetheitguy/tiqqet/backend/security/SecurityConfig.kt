package blog.davetheitguy.tiqqet.backend.security

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import javax.sql.DataSource

@Configuration
class SecurityConfig {
    @Bean
    @Profile("debug")
    fun memoryUserDetailsManager(): UserDetailsManager = InMemoryUserDetailsManager()

    @Bean
    @Profile("!debug")
    fun userDetailsManager(datasource: DataSource): UserDetailsManager = JdbcUserDetailsManager(datasource)

    @Bean
    @Profile("debug")
    fun debugPasswordEncoder(): PasswordEncoder = NoOpPasswordEncoder.getInstance()

    @Bean
    @Profile("!debug")
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests {
            it.requestMatchers("/").authenticated()
                .requestMatchers("/login").permitAll()
        }.formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    @Profile("debug")
    fun run(userDetailsManager: UserDetailsManager) = CommandLineRunner {
        val user = SimpleUserDetails().apply {
            setUsername("admin")
            setPassword("admin")
            addAuthority("admin")
        }
        userDetailsManager.createUser(user)
    }
}