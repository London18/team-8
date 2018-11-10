import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class urlScraper {

    private String keyword;
    private int category;
    private int searchType;
    private String[] urls = new String[100];

    public urlScraper(String kw, int ctgy, int st){
        keyword = kw;
        category = ctgy;
        searchType = st;
    }

    public String[] collectURLs(){

        if(searchType == 0){
            articleSearch(keyword, category);
        }
        else if (searchType == 1){
            forumSearch(keyword);
        }

        return urls;
    }

    public void articleSearch(String keyword, int category){
        String url = "https://www.themix.org.uk/wp-json/wp/v2/posts?categories=" + category + "&search=";

        constructAPICall(keyword, url);
    }

    public void forumSearch(String keyword){
        String url = "";

        constructAPICall(keyword, url);
    }

    public void constructAPICall(String searchTerm, String searchURL){

        //Sets the https statement to be processed based on current API.
        String search = searchURL + searchTerm;

        //Constructs HTTP object
        HttpURLConnection connection = null;

        try{
            URI uri = new URI(search);

            URL url = new URL(uri.toURL().toString());

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String textFromPage;
            textFromPage = in.readLine();

            ArrayList<String> allURLs = pullURLs(textFromPage);

            urlToArray(allURLs);
        }
        catch (Exception E){
            System.err.println(E);
        }
    }

    public ArrayList<String> pullURLs(String text){

        ArrayList<String> extractedURLs = new ArrayList<>();

        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find()){
            extractedURLs.add(text.substring(urlMatcher.start(0), urlMatcher.end(0)));
        }

        return extractedURLs;
    }

    public void urlToArray(ArrayList<String> allURLs){

        for(int i = 0; i < allURLs.size(); i++){
            urls[i] = allURLs.get(i);
        }
    }
}
