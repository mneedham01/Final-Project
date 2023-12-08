import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 *
 *  @author Maggie Needham
 *  @version Fall 2023
 */
public class Main {

    /*
     * Method to read in data
     */
    public static void readData(String filename) {
        Scanner file = null;
        try {
            file = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.err.println("Cannot locate file.");
            System.exit(-1);
        }
        // skip first line
        file.next();
        while (file.hasNextLine()) {
            String line = file.nextLine();
            // format: SOURCE_SUBREDDIT tab TARGET_SUBREDDIT tab POST_ID tab TIMESTAMP tab POST_LABEL tab POST_PROPERTIES
            // ex: leagueoflegends	teamredditteams	1u4nrps	2013-12-31 16:39:58	1
            // goal: source, target, id, property

            // Split into fields
            String[] fields = line.split("\\s+");
            String source = fields[0];
            String target = fields[1];
            String id = fields[2];
            String sentiment = fields[5];

            System.out.println("source = "+ source+ "; target = "+ target+"; id = "+ id+ "; sentiment = "+ sentiment);
        }
        file.close();
    }

    /*
     *
     */
    public static void main(String[] args) {
        readData("soc-redditHyperlinks-body.tsv");
    }

}