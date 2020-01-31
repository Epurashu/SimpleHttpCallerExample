package Helpers;

import Runnables.FastFolderGeneratorRunnable;
import Runnables.NodeEntropyGeneratorRunnable;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleHttpRequestSender
{
    final static String PROTOCOL = "http://";
    final static String URL = "localhost:";
    final static String PORT = "8082/";
    final static String PATH = "alfresco/api/-default-/public/alfresco/versions/1";
    final static String NODES = "/nodes/";
    final static String CHILDREN = "/children";
    final static String MOVE = "/move";
    final static String SITES = "/sites";

    final static String FOLDER_TYPE = "cm:folder";
    final static String CONTENT_TYPE = "cm:content";

    final static int EXECUTION_NUMBERS = 10;

    public void start() throws InterruptedException
    {
        ExecutorService pool = Executors.newFixedThreadPool(EXECUTION_NUMBERS);

        SingleSiteGenerator siteGenerator = new SingleSiteGenerator();

        for (int i = 0; i < EXECUTION_NUMBERS; i++)
        {
            pool.execute(new FastFolderGeneratorRunnable(250, "-my-"));
//            Thread thread = new Thread(new FastFolderGeneratorRunnable(100, "-my-"));
//            thread.start();
        }
        pool.shutdown();

        do
        {
            String siteId = siteGenerator.generateSite();
            ContentGenerator contentGenerator = new ContentGenerator(10, siteId);
            List<String> nodeIDs = contentGenerator.generateContent();
            contentGenerator.updateContent(nodeIDs);
            Thread thread = new Thread(new NodeEntropyGeneratorRunnable(15, siteId, nodeIDs));
            thread.start();
        }while(!pool.isTerminated());

    }
}
