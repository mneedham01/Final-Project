import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import com.google.common.graph.*;


/**
 *
 *
 *  @author Maggie Needham
 *  @version Fall 2023
 */
public class Main {

    /*
     * Computes and prints basic statistics
     */
    public void basicStats(MutableValueGraph<String, Integer> graph) {
        Integer nNodes = graph.nodes().size();
        Integer nEdges = graph.edges().size();
        // for max degree number, create a hash set of degrees of nodes
        HashSet<Integer> degrees;
        // establish degree sum
        Integer degreeSum = 0;
        // establish an iterator
        Iterator<Integer> iterator = graph.nodes().iterator();
        while (iterator.hasNext()) {
            Integer current = iterator.next().degree;
            // add to degree sum
            degreeSum += current;
            // add to hash set
            degrees.add(currrent);
        }
        Integer maxDegree = degrees.maximum();
        Integer avDegree = degreeSum / nNodes;

        System.out.println("NUMBER OF NODES = " + nNodes);
        System.out.println("NUMBER OF EDGES = " + nEdges);
        System.out.println("HIGHEST DEGREE OF A NODE = " + maxDegree);
        System.out.println("AVERAGE DEGREE OF NODES = " + avDegree);
    }

    /**
     * Prints the positive path recursivley
     */
    public ArrayList pathList (String current, String aim, HashMap<String, String> map) {
        // print out current
        System.out.println(current);
        // if current.get node path pointer is NOT aim
            // printPath (current.get node path pointer, aim)
    }

    /*
     * Given two nodes, print the most positive path between them
     */
    public void positivePath(String source, String target, MutableValueGraph<String, Integer> graph) {
        // Initalize map linking String node to associated cost -- start all at 0
        HashMap<String, Integer> costMap = new HashMap<String,Integer>();
        // Initalize map linking String node to String node path pointer -- start all as null
        HashMap<String, String> pathMap = new HashMap<String,String>();
        for (String str : graph.nodes()) {
            costMap.put(str, 0);
            pathMap.put(str, null);
        }
        // Initalize hashSet of unvisited nodes -- begin with all of graph's nodes except for source
        HashSet<String> unvisited = new HashSet<String>(graph.nodes());
        unvisited.remove(source);
        // while hashSet is not empty
        while (! unvisited.isEmpty()) {
            // select unvisited node with the highest cost
            // make a priority queue out of hashMap and nodeComparator
            PriorityQueue<String> pq = new PriorityQueue(unvisited, nodeComparator);
            // pop a node off of the queue
            String current = pq.poll();
            // note down cost for later use
            Integer currentCost = costMap.get(current);
            // take the node out of the hashSet
            unvisited.remove(current);
            // for all neighbors of the node EXCEPT the source:
            HashSet<String> neighbors = graph.adjacentNodes(current);
            neighbors.remove(source);
            for (String neigh : neighbors) {
                Integer currentToNeigh = graph.edgeValue(current, neigh);
                Integer neighCost = costMap.get(neigh);
                // if the cost of current node + cost going from current -> neighbor
                // is GREATER than the cost of the neighbor, then
                if (currentCost + currentToNeigh > neighCost) {
                    // update cost of neighbor
                    costMap.put(neigh,currentCost + currentToNeigh);
                    // update node path pointer of neighbor
                    pathMap.put(neigh, current);
                }
            }
        }
        // print out most positive path:
        System.out.println("MOST POSTIVE PATH FROM "+ source + " to "+ target + ": ");
        printPath(target, source, costMap);
    }

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

            // to print out data
            // System.out.println("source = "+ source+ "; target = "+ target+"; id = "+ id+ "; sentiment = "+ sentiment);
        }

        file.close();
    }

}

/**
 * Class implements Comparator interface
 */
public class nodeComparator implements Comparator<String> {
    /**
     * compare method
    */
    public int compare(String s1, String s2, HashMap<String, Integer> map) {
        // Compare method returns -1, 0, or 1 to say if it is less than, equal, or greater to the other
        Integer cost1 = map.get(s1);
        Integer cost2 = map.get(s2);
        // return -1 if s1's cost is less than s2's cost
        if (cost1 < cost2) {
            return -1;
        }
        // return 1 is s1's cost is greater than s2's cost
        if (cost1 > cost2) {
            return 1;
        }
        // if s1's cost is equal to s2's cost, then determine order alphabetically
        if (cost1 == cost2) {
            return cost1.compareTo(cost2);
        }
    }
}