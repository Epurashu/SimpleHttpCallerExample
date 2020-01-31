package Main;

import Helpers.SimpleHttpRequestSender;

public class MainClass
{
    public static void main(String[] args) throws InterruptedException
    {
        int threadPool = 5;
        int numberOfFolders = 100;
        int numberOfContentNodes = 10;
        int numberOfContentNodesUpdates = 15;

        for (int i = 0; i < args.length; i++)
        {
            String argument = args[i];
            String[] argumentSplit = argument.split("=");

            switch (argumentSplit[0])
            {
            case "threads":
                threadPool = Integer.valueOf(argumentSplit[1]);
                break;
            case "folders":
                numberOfFolders = Integer.valueOf(argumentSplit[1]);
                break;
            case "nodes":
                numberOfContentNodes = Integer.valueOf(argumentSplit[1]);
                break;
            case "updates":
                numberOfContentNodesUpdates = Integer.valueOf(argumentSplit[1]);
                break;
            }
        }

        SimpleHttpRequestSender sender = new SimpleHttpRequestSender(threadPool, numberOfFolders, numberOfContentNodes, numberOfContentNodesUpdates);

        sender.start();
    }
}
