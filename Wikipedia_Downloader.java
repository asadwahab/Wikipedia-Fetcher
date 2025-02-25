package utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Wikipedia_Downloader implements Runnable {
    private String keyword;
    private String result;
    public String imageurl;
    public Wikipedia_Downloader(){

    }
    public Wikipedia_Downloader(String keyword) {
        this.keyword = keyword;
    }





    @Override
    public void run() {
        // Step 1: Clean the keyword
        if (keyword == null || keyword.length() == 0) {
            return;
        }
        this.keyword = this.keyword.trim().replaceAll("[ ]+", "_");

        // Step 2: Get URL
        String wikiURL = getWikipediaUrlForQuery(keyword);
        String response = "";
        String imageurl = "";
        try {
            // Step 3: Make a GET Request
            String wikipediaResponse = HttpURLConnectionExample.sendGet(wikiURL);

            // Step 4: Parsing the Useful Result using Jsoup
            Document document = Jsoup.parse(wikipediaResponse, "https://en.wikipedia.org");
            Elements childelement = document.body().select(".mw-parser-output > *");
            int state = 0;

            for (Element childelements : childelement) {
                if (state == 0) {
                    if (childelements.tagName().equals("table")) {
                        state = 1;
                    }
                } else if (state == 1) {
                    if (childelements.tagName().equals("p")) {
                        state = 2;
                        response = childelements.text();
                        break;
                    }
                }
            }
            try {
                imageurl = document.body().select(".infobox img").first().attr("src");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        WikiResult wikiresult = new WikiResult(this.keyword, response, imageurl);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(wikiresult);
        System.out.println(json);
    }

    private String getWikipediaUrlForQuery(String cleankeyword) {
        return "https://en.wikipedia.org/wiki/"+cleankeyword;
    }

    public static void main(String[] args) {
       TaskManager taskManager=new TaskManager(20);
       Wikipedia_Downloader wikipediaDownloader=new Wikipedia_Downloader("Albert Einstein");
       taskManager.waitTillQueueIsFreeAndAddTask(wikipediaDownloader);
    }
}
