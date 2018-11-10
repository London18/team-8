import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;


public class StringAnalysis {

    public static void main(String[] args) {
        StringAnalysis test = new StringAnalysis();

        test.constructArticleAPICallSearchTerm("grief");
    }

    public String analyseString(String input) {
        //call all other methods from this
        return input;
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