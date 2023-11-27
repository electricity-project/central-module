package com.electricity.project.centralmodule.domains.gateway.properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.util.Properties;

public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name,  EncodedResource encodedResource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(encodedResource.getResource());
        Properties properties = factory.getObject();

        String filename = encodedResource.getResource().getFilename();
        if (filename != null && properties != null) {
            return new PropertiesPropertySource(filename, properties);
        } else {
            return null;
        }
    }

}
