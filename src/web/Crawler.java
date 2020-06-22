package web;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.exit;

public class Crawler {

    final int MAX_DEPTH;

    ArrayList<URLDepth> not_searched = new ArrayList<>();
    ArrayList<URLDepth> searched = new ArrayList<>();

    Crawler(URLDepth site, int max_depth) throws IOException {
        this.MAX_DEPTH = max_depth;
        not_searched.add(site);
        searchLinks();

    }

    public void searchLinks() throws IOException {

        String html_code;
        String tagA = "";
        String link = "";
        int tagA_start = 0;
        int tagA_end;
        int link_start;
        int link_end;

        ArrayList<URLDepth> list2add = new ArrayList<>();
        ArrayList<URLDepth> list2remove = new ArrayList<>();

        while (true) {
            for (URLDepth site : not_searched) {
                while (tagA_start != 1) {
                    html_code = site.getHtml_code();
                    // searching html tag <a> ... any text ... </a>]
                    tagA_start = html_code.indexOf("<a", tagA_start + 1);
                    tagA_end = html_code.indexOf("/a>", tagA_start);
                    try {
                        tagA = html_code.substring(tagA_start, tagA_end);
                    } catch (StringIndexOutOfBoundsException e) {
                        break;
                    }
                    // searching Link in tagA
                    link_start = tagA.indexOf("href=\"");
                    link_end = tagA.indexOf("\"", link_start + 6);
                    link = tagA.substring(link_start + 6, link_end);
                    //
                    URLDepth newlink = new URLDepth(link, site.getDepth() + 1);
                    System.out.println(newlink.getLink()+ ' ' + newlink.getDepth());

                    if (newlink.getDepth() < MAX_DEPTH) { list2add.add(newlink); }
                }
                if (!searched.contains(site)) { searched.add(site); }
                list2remove.add(site);
            }
            not_searched.addAll(list2add);
            not_searched.removeAll(list2remove);
            list2add.clear();
            list2remove.clear();

            if(not_searched.size() == 0){
                System.out.print("end");
                return;
            }
        }
    }
}







