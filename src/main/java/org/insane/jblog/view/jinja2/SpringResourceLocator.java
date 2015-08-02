package org.insane.jblog.view.jinja2;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.loader.ResourceLocator;

public class SpringResourceLocator implements ResourceLocator, ResourceLoaderAware {
    private final String prefix;
    private ResourceLoader resourceLoader;

    public SpringResourceLocator(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getString(String fullName, Charset encoding, JinjavaInterpreter interpreter) throws IOException {
        String location = prefix + fullName;
        Resource resource = resourceLoader.getResource(location);

        try (InputStream input = resource.getInputStream()) {
            return IOUtils.toString(input, encoding);
        }
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

}
