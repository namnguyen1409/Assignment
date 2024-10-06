package com.assignment.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import com.assignment.filter.JwtAuthenticationFilter;
import com.assignment.service.CustomUserDetailsService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.servlet.ServletContext;

@Configuration
@ComponentScan(basePackages = "com.assignment")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class ApplicationContextConfig {

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(customUserDetailsService()).passwordEncoder(passwordEncoder());
        return builder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ServletContext servletContext) throws Exception {
        http.csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        )
                .authorizeHttpRequests(authz -> authz
                .requestMatchers(new LoggingRequestMatcher()).permitAll()
                .requestMatchers(
                        "/error/**",
                        "/register/**",
                        "/login/**",
                        "/api/**",
                        "/WEB-INF/views/**",
                        "/resources/**",
                        "/confirm/**"
                ).permitAll()
                .anyRequest().authenticated()
                ).formLogin(
                        // Customizer.withDefaults()
                        form -> form.loginPage("/login").permitAll()
                                .usernameParameter("loginInfo")
                                .passwordParameter("password")
                                .failureHandler((request, response, exception) -> {
                                    // Thiết lập thông báo lỗi trong request
                                    request.getSession().setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
                                    response.sendRedirect("/login?error=true");
                                })
                )
                .oauth2Login(
                        oauth2 -> oauth2.loginPage("/login").permitAll()
                )
                .logout(
                        logout -> logout.logoutUrl("/logout").permitAll()
                )
                .exceptionHandling(e -> e
                .accessDeniedPage("/error/403?reason=Truy cập của bạn có thể đến từ form không hợp lệ")
                );
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource bundleMessageSource = new ReloadableResourceBundleMessageSource();
        bundleMessageSource.setBasename("classpath:messages");
        bundleMessageSource.setDefaultEncoding("UTF-8");
        return bundleMessageSource;
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(
                "jdbc:sqlserver://localhost:1433;databaseName=assignment;encrypt=true;trustServerCertificate=true");
        config.setUsername("sa");
        config.setPassword("1409");
        config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return new HikariDataSource(config);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.assignment.models.entities");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
//        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.transaction.coordinator_class", "jdbc");
        hibernateProperties.setProperty("hibernate.format_sql", "true");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        return hibernateProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        return hibernateTransactionManager;
    }

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost("smtp.gmail.com");
        mailSenderImpl.setPort(465);
        mailSenderImpl.setProtocol("smtps");
        mailSenderImpl.setDefaultEncoding("UTF-8");
        mailSenderImpl.setUsername("allconnectemail@gmail.com");
        mailSenderImpl.setPassword("bpoj irgx svhc xizh");

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.smtp.ssl.enable", "true");
        mailSenderImpl.setJavaMailProperties(javaMailProperties);

        return mailSenderImpl;
    }

}
