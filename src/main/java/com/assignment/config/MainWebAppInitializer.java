package com.assignment.config;

import java.net.URISyntaxException;
import java.nio.file.Paths;

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
            var path = Paths.get(MainWebAppInitializer.class.getClassLoader().getResource("tmp").toURI());
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement(path.toAbsolutePath().toString(), 2097152, 4194304, 30000);
            registration.setMultipartConfig(multipartConfigElement);
            System.out.println("path: " + path.toAbsolutePath());
        } catch (URISyntaxException e) {
            System.out.println(e);
        }
    }

}
