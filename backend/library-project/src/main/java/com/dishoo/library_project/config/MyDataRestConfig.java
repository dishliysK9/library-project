package com.dishoo.library_project.config;

import com.dishoo.library_project.entity.Book;
import com.dishoo.library_project.entity.Message;
import com.dishoo.library_project.entity.Review;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private String allowedOrigins = "http://localhost:3000";
    private String allowedOrigins2 = "https://library-service-react-961766531040.europe-west1.run.app";

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration configuration, CorsRegistry cors) {
        var unsuportedActions = new HttpMethod[]{HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.PATCH, HttpMethod.POST};

        configuration.exposeIdsFor(Book.class);
        configuration.exposeIdsFor(Review.class);
        configuration.exposeIdsFor(Message.class);

        disableMethods(Book.class, configuration, unsuportedActions);
        disableMethods(Review.class, configuration, unsuportedActions);
        disableMethods(Message.class, configuration, unsuportedActions);

        cors.addMapping(configuration.getBasePath() + "/**")
                .allowedOrigins(allowedOrigins, allowedOrigins2);
    }

    private void disableMethods(Class clazz, RepositoryRestConfiguration config, HttpMethod[] unsuportedActions) {
        config.getExposureConfiguration()
                .forDomainType(clazz)
                .withItemExposure(((metdata, httpMethods) ->
                        httpMethods.disable(unsuportedActions)))
                .withCollectionExposure(((metdata, httpMethods) ->
                        httpMethods.disable(unsuportedActions)));
    }
}
