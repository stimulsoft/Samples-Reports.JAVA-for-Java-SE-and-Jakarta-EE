package com.stimulsoft.demo;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stimulsoft.web.servlet.StiWebResourceServletJk;
import com.stimulsoft.webdesigner.servlet.StiWebDesignerActionServletJk;
import com.stimulsoft.webviewer.servlet.StiWebViewerActionServletJk;

import jakarta.servlet.http.HttpServlet;

@Configuration
public class StimulsoftConfig {
    @Bean
    public ServletRegistrationBean<HttpServlet> webviewerActionBean() {
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new StiWebViewerActionServletJk());
        servRegBean.addUrlMappings("/stimulsoft_webviewer_action");
        servRegBean.setLoadOnStartup(1);
        System.out.println("Webserver Registered");
        return servRegBean;
    }

    @Bean
    public ServletRegistrationBean<HttpServlet> webResourceBean() {
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new StiWebResourceServletJk());
        servRegBean.addUrlMappings("/stimulsoft_web_resource/*");
        servRegBean.setLoadOnStartup(1);
        System.out.println("Resources Registered");
        return servRegBean;
    }

    @Bean
    public ServletRegistrationBean<HttpServlet> webdesignerActionBean() {
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new StiWebDesignerActionServletJk());
        servRegBean.addUrlMappings("/stimulsoft_webdesigner_action/*");
        servRegBean.setLoadOnStartup(1);
        System.out.println("Resources Registered");
        return servRegBean;
    }
}
