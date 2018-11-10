import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

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

    public static void main(String[] args) {
        StringAnalysis test = new StringAnalysis();
        test.keywordDefinitions();
        test.constructArticleAPICallSearchTerm("grief");
    }

    public String analyseString(String input) {
        //call all other methods from this
        if (characterCount(input))
            return "Could you explain a bit more?";
        return input;
    }

    private boolean characterCount(String input) {
        return input.length() < 20;
    }

    public void  constructArticleAPICallSearchTerm(String searchTerm){

        //Sets the https statement to be processed based on current API.
        String coreStatement = "https://www.themix.org.uk/wp-json/wp/v2/posts?categories=28&search=";
        String search = coreStatement + searchTerm;

        //Contructs HTTP object
        HttpURLConnection connection = null;

        try{
            URI uri = new URI(search);

            URL url = new URL(uri.toURL().toString());

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

        }
        catch (Exception E){
            System.err.println(E);
        }
    }
}