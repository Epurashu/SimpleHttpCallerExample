package Helpers;

import Runnables.FastFolderGeneratorRunnable;
import Runnables.NodeEntropyGeneratorRunnable;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleHttpRequestSender
{
    int threadPool;
    int numberOfFolders;
    int numberOfContentNodes;
    int numberOfContentNodesUpdates;

    public SimpleHttpRequestSender(int threadPool, int numberOfFolders, int numberOfContentNodes, int numberOfContentNodesUpdates)
    {
        this.threadPool = threadPool;
        this.numberOfFolders = numberOfFolders;
        this.numberOfContentNodes = numberOfContentNodes;
        this.numberOfContentNodesUpdates = numberOfContentNodesUpdates;
    }

    public void start() throws InterruptedException
    {
        ExecutorService pool = Executors.newFixedThreadPool(threadPool);

        SingleSiteGenerator siteGenerator = new SingleSiteGenerator();

        for (int i = 0; i < threadPool; i++)
        {
            pool.execute(new FastFolderGeneratorRunnable(numberOfFolders, "-my-"));
        }
        pool.shutdown();

        do
        {
            String siteId = siteGenerator.generateSite();
            ContentGenerator contentGenerator = new ContentGenerator(numberOfContentNodes, siteId);
            List<String> nodeIDs = contentGenerator.generateContent();
            contentGenerator.updateContent(nodeIDs);
            Thread thread = new Thread(new NodeEntropyGeneratorRunnable(numberOfContentNodesUpdates, siteId, nodeIDs));
            thread.start();
        } while (!pool.isTerminated());

        System.out.println("Execution DONE !!!");
    }
}
