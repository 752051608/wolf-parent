package com.wolf.wolf.server;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

/**
 * @author zhanghong
 */

//@ImportResource(locations = {"classpath:ApplicationContext.xml"})
@ComponentScan(basePackages = {"com.wolf"})
@MapperScan({"com.wolf.mapper"})
//@PropertySource("classpath:application.properties")
@SpringBootApplication
//@EnableSwagger2
@EnableScheduling
@EnableAspectJAutoProxy
@ServletComponentScan
public class WolfServerApplication extends WebMvcConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WolfServerApplication.class);

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false)
                .ignoreAcceptHeader(false)
                .favorParameter(true)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("json", MediaType.APPLICATION_JSON);
    }

    public static void main(String[] args) throws UnknownHostException {
        Environment env = SpringApplication.run(WolfServerApplication.class, args).getEnvironment();
        String port = env.getProperty("server.port", "8080");

        logger.info("Access URLs:\n----------------------------------------------------------\n\t"
                        + "Local: \t\thttp://127.0.0.1:{}\n\t"
                        + "External: \thttp://{}:{}\n----------------------------------------------------------",
                port,
                InetAddress.getLocalHost().getHostAddress(),
                port);
    }

    /**
     * 本地化设置
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.CHINA);
        return slr;
    }

}
