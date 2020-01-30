import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.NodeResponseModel;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimpleHttpRequestSender
{
    final static String PROTOCOL = "http://";
    final static String URL = "localhost:";
    final static String PORT = "8082/";
    final static String PATH = "alfresco/api/-default-/public/alfresco/versions/1/nodes/";
    final static String CHILDREN = "/children";
    final static String MOVE = "/move";

    final static String FOLDER_TYPE = "cm:folder";
    final static String CONTENT_TYPE = "cm:content";

    final static int EXECUTION_NUMBERS= 2;

    public void start() throws InterruptedException
    {
        String basicAuthToken = basicAuth("admin", "admin");

        Runnable worker = new RunnableNodeCreator(5000, 1,10, basicAuthToken);

        ExecutorService executor = Executors.newFixedThreadPool(30);

        long startTime = System.currentTimeMillis();

//        for(int i=0; i<EXECUTION_NUMBERS; i++)
//            executor.execute(worker);
//        executor.shutdown();
//
//        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        for(int i=0; i<EXECUTION_NUMBERS; i++)
        {
            Thread thread = new Thread(new RunnableNodeCreator(0, 15,10, basicAuthToken));
            thread.start();
        }

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime)/1000;
        System.out.println("Total duration of execution: " + duration);
    }

    private static String basicAuth(String username, String password)
    {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    public class RunnableNodeCreator implements Runnable
    {
        int numberOfFolders;
        int numberOfContentNodes;
        int numberOfMoves;
        String authToken;

        String folderNodePrefix = "node" + UUID.randomUUID();
        String contentNodePrefix = "node" + UUID.randomUUID();

        private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        RunnableNodeCreator(int numberOfFolders, int numberOfContentNodes, int numberOfMoves, String authToken)
        {
            this.numberOfFolders = numberOfFolders;
            this.numberOfContentNodes = numberOfContentNodes;
            this.numberOfMoves = numberOfMoves;
            this.authToken = authToken;
        }

        @Override public void run()
        {

            HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

            long startTime = System.currentTimeMillis();

            //Generate Folder Nodes
            List<String> folderIDs = new ArrayList<>();
            for (int i=0 ; i<numberOfFolders; i++)
            {
                folderIDs.add(createNode(client, authToken, OBJECT_MAPPER, folderNodePrefix+i , FOLDER_TYPE));
            }

            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime)/1000;
            System.out.println("Total duration of creating " + numberOfFolders + " folderNodes taken: " + duration);

            startTime = System.currentTimeMillis();
            //Generate Content Nodes
            List<String> nodesIDs = new ArrayList<>();
            for (int i = 0; i< numberOfContentNodes; i++)
            {
                nodesIDs.add(createNode(client, authToken, OBJECT_MAPPER, contentNodePrefix+i , CONTENT_TYPE));
            }

            endTime = System.currentTimeMillis();
            duration = (endTime - startTime)/1000;
            System.out.println("Total duration of creating " + numberOfContentNodes + " contentNodes taken: " + duration);

            //Generate Nodes movement from Folder to Folder
//            for(int i=0; i<numberOfMoves; i++)
//            {
//                for(int j=0; j<numberOfFolders; j++)
//                {
//                    String folderId = folderIDs.get(j);
//                    for(int k=0; k<numberOfContentNodes; k++)
//                    {
//                        moveNode(client, authToken, OBJECT_MAPPER, folderId, nodesIDs.get(k));
//                    }
//                }
//                System.out.println("Finished " + (i+1) + "move.");
//            }
        }
    }

    public String createNode(HttpClient client, String authToken, ObjectMapper objectMapper, String nodeName, String nodeType)
    {
        
        //Prepare Json Body
        JsonObject userPropsUpdate = Json.createObjectBuilder()
            .add("name", nodeName)
            .add("nodeType", nodeType)
            .build();
        String postBody = userPropsUpdate.toString();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(PROTOCOL + URL + PORT + PATH + "-my-" + CHILDREN))
            .header("Content-Type", "application/json")
            .header("Authorization", authToken)
            .POST(HttpRequest.BodyPublishers.ofString(postBody))
            .build();
        HttpResponse<String> response = null;
        try
        {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        NodeResponseModel jsonResponse = null;
        try
        {
            jsonResponse = objectMapper.readValue(response.body(), NodeResponseModel.class);
        }
        catch (JsonProcessingException e)
        {
            System.out.println(request.toString());
            System.out.println(postBody);
            System.out.println(response.body());
            e.printStackTrace();
        }

        return jsonResponse.getEntry().getId();
    }

    public void moveNode(HttpClient client, String authToken, ObjectMapper objectMapper, String parentId, String nodeId)
    {
        //Prepare Json Body
        JsonObject userPropsUpdate = Json.createObjectBuilder()
            .add("targetParentId", parentId)
            .build();
        String postBody = userPropsUpdate.toString();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(PROTOCOL + URL + PORT + PATH + nodeId + MOVE))
            .header("Content-Type", "application/json")
            .header("Authorization", authToken)
            .POST(HttpRequest.BodyPublishers.ofString(postBody))
            .build();
        HttpResponse<String> response = null;
        try
        {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        NodeResponseModel jsonResponse = null;
        try
        {
            jsonResponse = objectMapper.readValue(response.body(), NodeResponseModel.class);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteNode()
    {

    }

    public void sendRequest()
    {

    }
}
