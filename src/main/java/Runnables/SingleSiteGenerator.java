package Runnables;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.util.UUID;

/**
 * Runnable that creates sites .
 * Slower than @FastApplicationSafeRunnable but generates audit logs that can be deleted by cleanup.
 * Safe for multi-threaded workloads.
 */
public class SingleSiteGenerator
{
    String authToken;
    int iteration;

    String sitePrefix = "site-" + UUID.randomUUID() + "-";
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public String generateSite()
    {
        Requests apiRequests = new Requests();
        authToken = apiRequests.generateBasicAuthToken("admin", "admin");

        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

        iteration++;
        return apiRequests.createSite(client, authToken, OBJECT_MAPPER, sitePrefix + iteration++);
    }
}
