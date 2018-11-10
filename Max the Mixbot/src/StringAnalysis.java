import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringAnalysis {

    //keyword definitions
    private String[] keywords = new String[100]; // manual for now - could be automatically updated later?
    private void keywordDefinitions() {
        keywords[0] = "depression";
        keywords[1] = "drugs";
        keywords[2] = "drink";
        keywords[3] = "alcohol";
        keywords[4] = "anxiety";
        keywords[5] = "housing";
        keywords[6] = "food";
        keywords[7] = "sleep";
        keywords[8] = "money";
        keywords[9] = "parents";
        keywords[10] = "died";
        keywords[11] = "uni";
        //etc.
    }

    //category definitions
    private String[] categories = new String[50];

    private void categoryDefinitions() {
        categories[0] = "Abuse";
        categories[1] = "Bereavement";
        categories[2] = "Care";
        categories[3] = "Careers & Study";
        categories[4] = "Child Care";
        categories[5] = "Disability Support";
        categories[6] = "Domestic Violence";
        categories[7] = "Drugs & Alcohol";
        categories[8] = "Relationships";
        categories[9] = "General Health";
        categories[10] = "Housing";
        categories[11] = "Legal & Rights";
        categories[12] = "Mental Health";
        categories[13] = "Money";
        categories[14] = "Sexual Health";
        categories[15] = "Sexuality";
        categories[16] = "Self Harm";
        //etc
    }

    public static void main(String[] args) {
        StringAnalysis test = new StringAnalysis();
        test.analyseString(args[0]);
    }

    public String analyseString(String input) {
        //call all other methods from this
        if (characterCount(input))
            return "Could you explain a bit more?";
        this.keywordDefinitions();
        this.categoryDefinitions();

        //Jack and Hester put your shit here
        String[] urls = new String[100];

        return this.returnString(this.sortURLs(urls));
    }

    private String[] sortURLs(String[] articleURLs, String[] forumURLs) {
        // later - rank importance of URLs by severity
        // now - rank by number of times keyword occurs, then order

        return new String[]{articleURLs[0], articleURLs[1], forumURLs[0], forumURLs[1]};
    }

    private String returnString(String[] urls) {
        return "Here are some articles and forum posts that you might find useful:\n\n" + urls[0] + "\n" + urls[1] + "\n" + urls[2] + "\n" + urls[3];
    }

    private boolean characterCount(String input) {
        return input.length() < 20;
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
            System.out.println(allURLs.get(0));
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
}