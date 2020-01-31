package Helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.NodeModel;
import models.SiteModel;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class Requests
{
    final static String PROTOCOL = "http://";
    final static String URL = "localhost:";
    final static String PORT = "8082/";
    final static String PATH = "alfresco/api/-default-/public/alfresco/versions/1";
    final static String NODES = "/nodes/";
    final static String CHILDREN = "/children";
    final static String MOVE = "/move";
    final static String SITES = "/sites";


    public static String generateBasicAuthToken(String username, String password)
    {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    public String createNode(HttpClient client, String authToken, ObjectMapper objectMapper, String parentNodeId, String nodeName, String nodeType)
    {
        //Prepare Json Body
        JsonObject userPropsUpdate = Json.createObjectBuilder()
            .add("name", nodeName)
            .add("nodeType", nodeType)
            .build();
        String postBody = userPropsUpdate.toString();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(PROTOCOL + URL + PORT + PATH + NODES + parentNodeId + CHILDREN))
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
        NodeModel jsonResponse = null;
        try
        {
            jsonResponse = objectMapper.readValue(response.body(), NodeModel.class);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        return jsonResponse.onModel().getId();
    }

    public void moveNode(HttpClient client, String authToken, ObjectMapper objectMapper, String parentId, String nodeId)
    {
        //Prepare Json Body
        JsonObject userPropsUpdate = Json.createObjectBuilder()
            .add("targetParentId", parentId)
            .build();
        String postBody = userPropsUpdate.toString();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(PROTOCOL + URL + PORT + PATH + NODES + nodeId + MOVE))
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
        NodeModel jsonResponse = null;
        try
        {
            jsonResponse = objectMapper.readValue(response.body(), NodeModel.class);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
    }

    public String createSite(HttpClient client, String authToken, ObjectMapper objectMapper, String siteTitle)
    {
        //Prepare Json Body
        JsonObject userPropsUpdate = Json.createObjectBuilder()
            .add("title", siteTitle)
            .add("visibility", "PUBLIC")
            .build();
        String postBody = userPropsUpdate.toString();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(PROTOCOL + URL + PORT + PATH + SITES))
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
        SiteModel jsonResponse = null;
        try
        {
            jsonResponse = objectMapper.readValue(response.body(), SiteModel.class);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        return jsonResponse.onModel().getGuid();
    }

    public void updateNode(HttpClient client, String authToken, ObjectMapper objectMapper, String nodeId, String newNodeName)
    {
        //Prepare Json Body
        JsonObject userPropsUpdate = Json.createObjectBuilder()
            .add("name", newNodeName)
            .add("properties", Json.createObjectBuilder()
                .add("cm:title", "title-" + newNodeName)
                .add("cm:description", "description-" + newNodeName))
            .build();
        String putBody = userPropsUpdate.toString();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(PROTOCOL + URL + PORT + PATH + NODES + nodeId))
            .header("Content-Type", "application/json")
            .header("Authorization", authToken)
            .PUT(HttpRequest.BodyPublishers.ofString(putBody))
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
        NodeModel jsonResponse = null;
        try
        {
            jsonResponse = objectMapper.readValue(response.body(), NodeModel.class);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
    }

    public void updateNodeContent(HttpClient client, String authToken, ObjectMapper objectMapper, String nodeId, String newNodeContent)
    {
        //Prepare Json Body
        JsonObject userPropsUpdate = Json.createObjectBuilder()
            .add("name", newNodeContent)
            .add("properties", Json.createObjectBuilder()
                .add("cm:title", "title-" + newNodeContent)
                .add("cm:description", "description-" + newNodeContent))
            .build();
        String putBody = userPropsUpdate.toString();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(PROTOCOL + URL + PORT + PATH + NODES + nodeId + "/content"))
            .header("Content-Type", "application/json")
            .header("Authorization", authToken)
            .PUT(HttpRequest.BodyPublishers.ofString(putBody))
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
        NodeModel jsonResponse = null;
        try
        {
            jsonResponse = objectMapper.readValue(response.body(), NodeModel.class);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteNode(HttpClient client, String authToken, ObjectMapper objectMapper, String nodeId)
    {

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(PROTOCOL + URL + PORT + PATH + NODES + nodeId))
            .header("Authorization", authToken)
            .DELETE()
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
        if (204 != response.statusCode())
        {
            System.out.println("Failed to delete node " + nodeId);
        }
    }

    public void sendRequest()
    {

    }



}
