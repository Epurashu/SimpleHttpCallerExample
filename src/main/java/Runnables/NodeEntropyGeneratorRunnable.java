package Runnables;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.util.List;
import java.util.UUID;

public class NodeEntropyGeneratorRunnable implements Runnable
{
    int numberOfOperations;
    String parentNodeId;
    String authToken;
    List<String> nodesIds;
    String nodePrefix = "updateD-" + UUID.randomUUID() + "-";

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public NodeEntropyGeneratorRunnable(int numberOfOperations, String parentNodeId, List<String> nodesIds)
    {
        this.numberOfOperations = numberOfOperations;
        this.parentNodeId = parentNodeId;
        this.nodesIds = nodesIds;
    }

    @Override public void run()
    {
        Requests apiRequests = new Requests();
        authToken = apiRequests.generateBasicAuthToken("admin", "admin");
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

        long startTime = System.currentTimeMillis();

        int numberOfOperationsCopy = numberOfOperations;

        //Update Nodes
        int listIterator = 0;
        do
        {
            apiRequests.updateNode(client, authToken, OBJECT_MAPPER, nodesIds.get(listIterator), nodePrefix + listIterator);
            listIterator++;

            if(listIterator > nodesIds.size())
            {
                listIterator = 0;
            }
            numberOfOperations--;

        }while(0 < numberOfOperations);

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;
        System.out.println(Thread.currentThread().getName() + " updated " + numberOfOperationsCopy + " nodes props in " + duration + "s");

        startTime = System.currentTimeMillis();

        //Delete Nodes
        for (int i = 0; i < nodesIds.size(); i++)
        {
            apiRequests.deleteNode(client, authToken, OBJECT_MAPPER, nodesIds.get(i));
        }

        endTime = System.currentTimeMillis();
        duration = (endTime - startTime) / 1000;
        System.out.println(Thread.currentThread().getName() + " Deleted " + nodesIds.size() + " nodes in " + duration + "s");
    }
}
