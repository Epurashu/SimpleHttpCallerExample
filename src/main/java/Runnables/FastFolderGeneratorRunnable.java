package Runnables;

import Helpers.Requests;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.util.UUID;

/**
 * Runnable that creates nodes of type cm:folder.
 * One of the fastest way to generate audit logs.
 * Safe for multi-threaded workloads.
 */
public class FastFolderGeneratorRunnable implements Runnable
{
    int numberOfOperations;
    String parentNodeId;
    String authToken;

    String nodePrefix = "folder-" + UUID.randomUUID() + "-";
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public FastFolderGeneratorRunnable(int numberOfOperations, String parentNodeId)
    {
        this.numberOfOperations = numberOfOperations;
        this.parentNodeId = parentNodeId;
    }

    @Override public void run()
    {
        Requests apiRequests = new Requests();
        authToken = apiRequests.generateBasicAuthToken("admin", "admin");
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

        long startTime = System.currentTimeMillis();

        //Generate Folder Nodes
        for (int i = 0; i < numberOfOperations; i++)
        {
            apiRequests.createNode(client, authToken, OBJECT_MAPPER, parentNodeId, nodePrefix + i, "cm:folder");
        }

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;
        System.out.println(Thread.currentThread().getName() + " created " + numberOfOperations + " folders in " + duration + "s");
    }
}
