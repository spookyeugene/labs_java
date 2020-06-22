package web;

import java.util.Scanner;
import java.io.IOException;
import java.net.MalformedURLException;

import static java.lang.System.exit;

public class Main {



    public static void main(String[] args) throws IOException {
//        System.out.println("hello user! write the website url");
//        Scanner in =  new Scanner(System.in);
//        String link = in.next();

        int version = 2;


        int depth = 3;
        String link = "https://www.google.ru/";

        if(version == 1) {
            Crawler crawler = new Crawler(new URLDepth(link, 1, 1), depth );
        }
        else{
            URLPool pool = new URLPool(depth);
            pool.addPair(new URLDepth(link, 1));
            int numThread = 20;

            for (int m = 0; m < numThread; m++) {
                Thread thread = new Thread(new AsyncCrawler(pool));
                thread.start();
            }
        }

//        System.out.println("what version of this app do u wanna use (v1/v2)");
//        String version = in.next();
//        System.out.println("how deep i need 2 search");



    }
}
