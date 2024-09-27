package com.assignment.config;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author namnguyen
 */
@SuppressWarnings("null")
public class MainWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
   
  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[] {
        ApplicationContextConfig.class
    };
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] { "/" };
  }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }
}