package it.com.example;

import com.example.DemoApplication;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.validation.ValidationFeature;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

public class IntegrationTest extends JerseyTest {

    @Test
    @Category(IntegrationTests.class)
    public void testGet() {
        final WebTarget target = getRootTarget("/");

        final Response response = target.request().get();

        Assert.assertNotNull("Response must not be null", response);
        Assert.assertEquals("Response does not have expected response code",
                Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertEquals("Hello YaaS!", response.readEntity(String.class));
    }

    protected WebTarget getRootTarget(final String rootResource) {
        URI baseURI = getBaseUri();
        try {
            String sslSetting = System.getProperty("jersey.config.test.container.ssl");
            if (sslSetting != null && Boolean.parseBoolean(sslSetting)) {
                baseURI = new URI("https", baseURI.getUserInfo(), baseURI.getHost(), baseURI.getPort(), baseURI.getPath(), baseURI.getQuery(), baseURI.getFragment());
            }
        } catch (URISyntaxException e) {
            Assert.fail(e.getMessage());
        }
        return client().target(baseURI).path(rootResource);
    }

    @Override
    protected final Application configure() {
        final ResourceConfig application = new ResourceConfig();
        application.register(DemoApplication.class);

        // needed for json serialization
        application.register(JacksonFeature.class);

        // bean validation
        application.register(ValidationFeature.class);

        // configure spring context
        // application.property("contextConfigLocation", "classpath:/META-INF/applicationContext.xml");

        // disable bean validation for tests
        //application.property(ServerProperties.BV_FEATURE_DISABLE, "true");

        return application;
    }
}

