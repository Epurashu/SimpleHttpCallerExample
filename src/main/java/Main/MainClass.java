package Main;

import Helpers.SimpleHttpRequestSender;

public class MainClass
{
    public static void main(String[] Args) throws InterruptedException
    {
        SimpleHttpRequestSender sender = new SimpleHttpRequestSender();

        sender.start();
    }
}
