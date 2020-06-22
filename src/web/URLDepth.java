package web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.net.URL;

public class URLDepth {

    private String link;
    private int depth;
    private URL url;
    private String html_code = "";

    public URLDepth(String link, int depth) throws IOException {
        this.link = link;
        this.depth = depth;

        try {
            url = new URL(link);
        }
        catch (MalformedURLException e) {
            System.err.println("Link doesnt work :( ");
            return;
        }

    }
    public URLDepth(String link, int depth, int first_version) throws IOException {
        this.link = link;
        this.depth = depth;

        try { url = new URL(link); }
        catch (MalformedURLException e) { System.err.println("Link doesnt work :( ");return; }
        getPageCode();
    }

    private String getPageCode() throws IOException {
        try {
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(this.url.openStream()));
            String string = reader.readLine();
            while (string != null) {
                this.html_code += string;
                string = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.print("error");
        }
        return html_code;
    }
    @Override
    public boolean equals(Object ob) {
        if (ob instanceof URLDepth) {
            URLDepth o = (URLDepth) ob;
            return this.link.equals(o.getLink());
        }
        return false;
    }

    public String getLink() {
            return link;
        }

    public int getDepth() {
            return depth;
        }

    public URL getURL() { return url; }

    public String getHtml_code(){return html_code;}

    public String getDocPath() {
            return url.getPath();
        }

    public String getWebHost() {
            return url.getHost();
        }


}
