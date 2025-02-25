package utility;

public class WikiResult {
    public String query;
    public String textresult;
    public String image_url;

    public WikiResult(String query,String textresult,String image_url){
        this.query = query;
        this.textresult=textresult;
        this.image_url=image_url;
    }

    public WikiResult() {
    }

}
