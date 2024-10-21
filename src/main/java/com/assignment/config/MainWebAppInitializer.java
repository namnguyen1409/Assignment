package com.assignment.config;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration.Dynamic;

@SuppressWarnings("null")
public class MainWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
            ApplicationContextConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected void customizeRegistration(Dynamic registration) {
        try {
            // Lấy đường dẫn đến thư mục tạm thời
            var path = Paths.get(MainWebAppInitializer.class.getClassLoader().getResource("tmp").toURI());
            
            // Cấu hình cho Multipart
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                path.toAbsolutePath().toString(), // Thư mục tạm thời
                DataSize.ofMegabytes(100).toBytes(), // 100MB
                DataSize.ofMegabytes(2000).toBytes(), // 2GB
                1024 * 1024 // 1MB
            );
            // Gán cấu hình multipart cho đăng ký servlet
            registration.setMultipartConfig(multipartConfigElement);
        } catch (URISyntaxException e) {
            System.out.println(e);
        }
    }
    

}
