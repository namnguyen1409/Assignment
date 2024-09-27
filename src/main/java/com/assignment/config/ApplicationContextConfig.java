package com.assignment.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 *
 * @author namnguyen
 */
@Configuration
@ComponentScan(basePackages = "com.assignment")
@EnableTransactionManagement
@EnableWebSecurity
public class ApplicationContextConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll()
            ).exceptionHandling(
                e -> e.accessDeniedPage("/error/403?reason=Truy cập của bạn có thể đến từ form không hợp lệ")
            );

        return http.build();
    }

    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource bundleMessageSource = new ReloadableResourceBundleMessageSource();
        bundleMessageSource.setBasename("classpath:messages");
        bundleMessageSource.setDefaultEncoding("UTF-8");
        return bundleMessageSource;
    }

    /**
     *
     * Cấu hình kết nối cơ sở dữ liệu SQL Server với HikariCP
     * @return 
     */
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

    /**
     *
     * Cấu hình SessionFactory sử dụng Hibernate với DataSource đã cấu hình ở
     * trên Sử dụng các entity class trong package com.assignment.model
     * @return LocalSessionFactoryBean
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.assignment.models.entities");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    /*
     * Cấu hình các thuộc tính của Hibernate
     * - SQL Server dialect: dùng để tạo ra các câu lệnh SQL phù hợp với SQL Server
     * - Show SQL: hiển thị các câu lệnh SQL trong quá trình chạy ứng dụng
     * - HBM2DDL Auto: tự động tạo bảng dữ liệu dựa trên các entity class
     */
    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.format_sql", "true");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        return hibernateProperties;
    }

    /*
     * 
     * Cấu hình Transaction Manager sử dụng HibernateTransactionManager
     * Sử dụng SessionFactory đã cấu hình ở trên
     * -> Quản lý các giao dịch với cơ sở dữ liệu (mở kết nối, commit, rollback,
     * đóng kết nối)
     */
    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        return hibernateTransactionManager;
    }

}
