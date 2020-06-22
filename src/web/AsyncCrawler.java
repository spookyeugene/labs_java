package web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import static java.lang.System.exit;


public class AsyncCrawler implements Runnable {
    URLPool urlPool;
    int amount = 0;

    AsyncCrawler(URLPool pool) throws IOException {
        this.urlPool = pool;
    }

    public void searchLinks() throws IOException {
        URLDepth site = urlPool.getPair();
        int depth = site.getDepth();

            String html_code = "";
            try{
                LineNumberReader reader = new LineNumberReader(new InputStreamReader(site.getURL().openStream()));
                String string = reader.readLine();
                while (string != null) {
                    html_code = string;
//                    System.out.println(html_code);

                    String tagA = "";
                    String link = "";
                    int tagA_start = 0;
                    int tagA_end;
                    int link_start;
                    int link_end;

                    while (tagA_start != -1) {
                        // searching html tag <a> ... any text ... </a>]
                        tagA_start = html_code.indexOf("<a", tagA_start + 1);
//                        System.out.println(tagA_start);
                        tagA_end = html_code.indexOf("/a>", tagA_start);
//                        System.out.println(tagA_end);
                        try {
                            tagA = html_code.substring(tagA_start, tagA_end);
                        } catch (StringIndexOutOfBoundsException e) { continue; }
                        // searching Link in tagA
                        link_start = tagA.indexOf("href=\"");
                        link_end = tagA.indexOf("\"", link_start + 6);
                        link = tagA.substring(link_start + 6, link_end);
                        //
                        urlPool.addPair(new URLDepth(link, (depth + 1)));
                        amount++;
                        System.out.println(amount + ' ' +link + ' ' + (depth + 1));
                    }
                    string = reader.readLine();
                }
                reader.close();
            } catch (Exception e) { e.printStackTrace();System.out.print("error"); }
        }

    @Override
    public void run() {
        while (true) {
            try {
                searchLinks();
//                System.out.print("end");
////                exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
