import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.google.common.graph.*;

/**
 *
 *
 *  @author Maggie Needham
 *  @version Fall 2023
 */
public class Main {

    /*
     * Reads in data and creates graph
     */
    public static void main(String[] args) {
        // establish graph
        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed().build();

        // start reading in file
        String filename = (args.length > 0) ? args[0] : "soc-redditHyperlinks-body.tsv";
        Scanner file = null;
        try {
            file = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.err.println("Cannot locate file.");
            System.exit(-1);
        }
        // skip first line
        file.nextLine();
        while (file.hasNextLine()) {
            String line = file.nextLine();
            // format: SOURCE_SUBREDDIT tab TARGET_SUBREDDIT tab POST_ID tab TIMESTAMP tab POST_LABEL tab POST_PROPERTIES
            // ex: leagueoflegends	teamredditteams	1u4nrps	2013-12-31 16:39:58	1
            // goal: source, target, id, property

            // Split into fields
            String[] fields = line.split("\\s+");
            String source = fields[0];
            String target = fields[1];
            Integer sentiment = Integer.parseInt(fields[5]);

            graph.putEdgeValue(source, target, sentiment);

            System.out.println("source = "+ source+ "; target = "+ target+"; id = "+ id+ "; sentiment = "+ sentiment);
        }
        file.close();
    }

}