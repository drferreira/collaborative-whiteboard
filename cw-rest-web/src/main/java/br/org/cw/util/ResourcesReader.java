package br.org.cw.util;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by drferreira on 31/08/15.
 */
public class ResourcesReader implements Serializable {

    @Inject
    private ServletContext servletContext;

    public InputStream fetchWebAppFile(String file){
        return servletContext.getResourceAsStream(file);
    }
}
