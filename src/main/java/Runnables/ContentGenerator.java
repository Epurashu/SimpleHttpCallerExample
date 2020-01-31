package Runnables;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Runnable that creates sites and nodes of type cm:content.
 * Slower than @FastApplicationSafeRunnable but generates audit logs that can be deleted by cleanup.
 * Not safe for multi-threaded workloads.
 */
public class ContentGenerator
{
    int numberOfOperations;
    String parentNodeId;
    String authToken;
    Requests apiRequests = new Requests();

    HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

    String nodePrefix = "file-" + UUID.randomUUID() + "-";
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public ContentGenerator(int numberOfOperations, String parentNodeId)
    {
        this.numberOfOperations = numberOfOperations;
        this.parentNodeId = parentNodeId;
    }

    public List<String> generateContent()
    {

        authToken = apiRequests.generateBasicAuthToken("admin", "admin");



        long startTime = System.currentTimeMillis();

        //Generate Content Nodes
        List<String> nodeIDs = new ArrayList<>();
        for (int i = 0; i < numberOfOperations; i++)
        {
            nodeIDs.add(apiRequests.createNode(client, authToken, OBJECT_MAPPER, parentNodeId, nodePrefix + i, "cm:content"));
        }

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;
        System.out.println(Thread.currentThread().getName() + " created " + numberOfOperations + " files in " + duration + "s");

        return nodeIDs;
    }

    public void updateContent(List<String> nodeIDs)
    {
        authToken = apiRequests.generateBasicAuthToken("admin", "admin");

        long startTime = System.currentTimeMillis();

        //Update Nodes Content
        for (int i = 0; i < numberOfOperations; i++)
        {
            apiRequests.updateNodeContent(client, authToken, OBJECT_MAPPER, nodeIDs.get(i), "update" + nodePrefix + i);
        }

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;
        System.out.println(Thread.currentThread().getName() + " updated " + numberOfOperations + " nodes content in " + duration + "s");
    }
}
