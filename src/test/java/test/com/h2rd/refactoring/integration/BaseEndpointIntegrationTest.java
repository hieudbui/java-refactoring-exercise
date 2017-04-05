package test.com.h2rd.refactoring.integration;

import com.h2rd.refactoring.web.MyApplication;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.springframework.web.context.ContextLoaderListener;

public abstract class BaseEndpointIntegrationTest extends JerseyTest {

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    @Override
    protected DeploymentContext configureDeployment() {
        return ServletDeploymentContext
                .forServlet(new ServletContainer(new ResourceConfig(new MyApplication())))
                .addListener(ContextLoaderListener.class)
                .contextParam("contextConfigLocation", "com.h2rd.refactoring.ApplicationConfiguration")
                .contextParam("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext")
                .build();
    }

}
