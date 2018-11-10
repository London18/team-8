package CodeForPizza.NavBot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StringAnalysis {

    @RequestMapping("/chat")
    public String processChat(@RequestParam(value="userInput", defaultValue="default") String userInput) {
        StringAnalysis test = new StringAnalysis();
        return test.analyseString(userInput);
    }

    //keywords from user input
    private String[] currentKeywords = new String[100];
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
        keywords[12] = "cash";
        keywords[13] = "poor";
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

    public String analyseString(String input) {
        //call all other methods from this
        if (characterCount(input))
            return "Could you explain a bit more?";
        this.keywordDefinitions();
        this.categoryDefinitions();

        keywordFinder(input); //finds keywords in input

        String[] allUrls = new String[100];
        int categoryInference = categoryInference();

        for(int i = 0; i < allUrls.length; i++){
            if (currentKeywords[i] != null) {
                allUrls = new urlScraper(currentKeywords[i], categoryInference, 0).collectURLs();
            }
        }

        String returnString = returnString(sortURLs(allUrls, allUrls));

        System.out.println(returnString);
        return returnString;
    }

    private String[] sortURLs(String[] articleURLs, String[] forumURLs) {
        // later - rank importance of URLs by severity
        // now - rank by number of times keyword occurs, then order

        return new String[]{articleURLs[0], articleURLs[1], forumURLs[0], forumURLs[1]};

    }

    private String returnString(String[] urls) {
        return "Here are some articles and forum posts that you might find useful:\n\nArticles:\n" + urls[0] + "\n" + urls[1] + "\n\nForum Posts:\n" + urls[2] + "\n" + urls[3];
    }

    private boolean characterCount(String input) {
        return input.length() < 20;
    }

    public String[] keywordFinder(String input) {
        int index = 0;
        input = input.toLowerCase();
        String[] holder = input.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        int length = holder.length;
        for (int i = 0; i < keywords.length; i++) {
            for (int j = 0; j < length; j++) {
                if (holder[j].equals(keywords[i])) {
                    currentKeywords[index++] = keywords[i];
                }
            }
        }
        return currentKeywords;
    }

    /**
     * This gives us a main category, given currentKeywords
     */
    private int categoryInference() {
        int[] categoryCount = new int[30];
        for (int i=0; i < currentKeywords.length; i++) {
            if (currentKeywords[i]!=null) {
                if (currentKeywords[i].equals("depression") || currentKeywords[i].equals("anxiety") || currentKeywords[i].equals("sleep")) {
                    categoryCount[12]++;
                }
                else if (currentKeywords[i].equals("drugs") || currentKeywords[i].equals("drink") || currentKeywords[i].equals("alcohol")) {
                    categoryCount[7]++;
                }
                else if (currentKeywords[i].equals("housing")) {
                    categoryCount[10]++;
                }
                else if (currentKeywords[i].equals("food")) {
                    categoryCount[9]++;
                }
                else if (currentKeywords[i].equals("money") || currentKeywords[i].equals("cash") || currentKeywords[i].equals("poor")) {
                    categoryCount[13]++;
                }
                else if (currentKeywords[i].equals("parents")) {
                    categoryCount[8]++;
                }
                else if (currentKeywords[i].equals("died")) {
                    categoryCount[1]++;
                }
                else if (currentKeywords[i].equals("uni")) {
                    categoryCount[3]++;
                }
            }
        }
        int highestNoOfCounts = 0;
        int biggestCategory = 0;
        for (int i=0; i < categoryCount.length; i++) {
            if (categoryCount[i] > highestNoOfCounts) {
                highestNoOfCounts = categoryCount[i];
                biggestCategory = i;
            }
        }
        return biggestCategory;
    }
}
