package com.MoP2022.Team10.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MimeType;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("*","http://localhost:8080")
                .allowedMethods("GET","POST","OPTIONS");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
       converters.removeIf(converter->supportsXml(converter) || hasXmlMapper(converter));
    }

    private boolean supportsXml(HttpMessageConverter<?> converter){
        return converter.getSupportedMediaTypes().stream().map(MimeType::getSubtype).anyMatch(subType -> subType.equalsIgnoreCase("xml"));
    }

    private boolean hasXmlMapper(HttpMessageConverter<?> converter){
        return converter instanceof MappingJackson2HttpMessageConverter
                && ((MappingJackson2HttpMessageConverter)converter).getObjectMapper().getClass().equals(XmlMapper.class);
    }
}
