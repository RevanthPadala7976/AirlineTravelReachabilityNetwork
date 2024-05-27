/*
* EXPLANATION OF NETWORK ANALYSIS:
* 1. Understanding Network Structure:
*               Network analysis helps us understand the structure of the transportation network by identifying nodes
*               and edges. This understanding is crucial for optimizing transportation routes, identifying important
*               nodes, and evaluating network resilience.
* 2. Identifying Important Nodes:
*               By Calculating Centrality measures such as degree centrality and betweenness centrality, we can identify
*               important nodes in the transportation network. Important nodes may have high centrality measures, indicating that they
*               play crucial role in connecting different parts of the network.
* 3. Optimizing Routes and Infrastructure:
*               Network analysis enables us to optimize transportation routes and infrastructure by identifying key nodes and routes
*               with high traffic or importance. This information can be used to improve route planning, reduce congestion, and
*               enhance the efficiency of transportation systems.
* 4. Decision Support:
*               Insights gained from network analysis can support decision-making processes in urban planning, infrastructure development,
*               and transportation policy. By understanding the network structure and dynamics, stakeholders can make informed decision to
*               improve transportation systems and enhance connectivity with the region.
* */

import java.util.*;

public class NetworkAnalysis extends GraphReader {

    // Constructor inheriting from GraphReader
    public NetworkAnalysis(String filePath) {
        super(filePath);
    }
    /*
    * BETWEENNESS CENTRALITY:
    *       Betweenness centrality measure the extent to which a node lies on the shortest paths between other nodes in the network.
    *       A node with high betweenness centrality acts as a bridge or connector between different parts of the network.
    *
    * Calculation:
    *
    * 1. Shortest Path: Calculating the shortest paths between all pairs of nodes in the network using BFS.
    * 2. Counting Shortest Paths: For each node in the Network, count the number of shortest paths that pass through it.
    * 3. Normalizing: Normalize the counts to account of variations in network size.
    * 4. Betweenness Centrality: The Betweenness Centrality of a node is the sum of the fractions of all pairs of nodes that pass through it.
    *
    * EXAMPLE:
    *       If individual A lies on many shortest paths between other individuals, their betweenness centrality would be high, indicating
    *       that they pay a significant role in connecting different parts of the social network.
    * */
    // Method to calculate betweenness centrality of nodes (using Brandes' algorithm)
    public Map<Integer, Double> calculateBetweennessCentrality() {
        Map<Integer, Double> betweennessCentrality = new HashMap<>();

        // Initialize betweenness centrality for all nodes to 0
        for (Integer nodeId : adjacencyList.keySet()) {
            betweennessCentrality.put(nodeId, 0.0);
        }

        // Iterate over all nodes as potential sources
        for (Integer sourceNode : adjacencyList.keySet()) {
            /*
            * Before starting the BFS traversal, we initialize several data structures:
            * Distance Map:
            *       To store shortest distance from the source node to each node in the graph. Initially, all distances
            *       are set to a large value except for the source node, which has a distance of 0.
            * Sigma Map:
            *       This keeps track of the number of shortest paths from the source node to each node. Initially, all sigma
            *       values are set to 0 except for the source node, which has a sigma value of 1.
            * Path Map:
            *       This stores lists of predecessor nodes for each node. it is used to track the shortest paths during the BFS traversal.
            * Delta Map:
            *       This map is used to for the accumulation phase to accumulate dependencies for each node.
            * */
            // Initialize data structures for the algorithm
            Map<Integer, List<Integer>> paths = new HashMap<>();
            Map<Integer, Integer> distance = new HashMap<>();
            Map<Integer, Double> sigma = new HashMap<>();
            Map<Integer, Double> delta = new HashMap<>();
            Queue<Integer> queue = new LinkedList<>();
            Stack<Integer> stack = new Stack<>();

            // Initialization
            for (Integer nodeId : adjacencyList.keySet()) {
                paths.put(nodeId, new ArrayList<>());
                distance.put(nodeId, -1);
                sigma.put(nodeId, 0.0);
                delta.put(nodeId, 0.0);
            }
            distance.put(sourceNode, 0);
            sigma.put(sourceNode, 1.0);
            queue.offer(sourceNode);

            // Breadth-first search
            while (!queue.isEmpty()) {
                int currentNode = queue.poll();
                stack.push(currentNode);
                for (Edge edge : getAdjacentEdges(currentNode)) {
                    int neighbor = edge.toNodeId;
                    // Neighbor found for the first time
                    if (distance.get(neighbor) < 0) {
                        queue.offer(neighbor);
                        distance.put(neighbor, distance.get(currentNode) + 1);
                    }
                    // Shortest path to neighbor via currentNode?
                    if (distance.get(neighbor) == distance.get(currentNode) + 1) {
                        sigma.put(neighbor, sigma.get(neighbor) + sigma.get(currentNode));
                        paths.get(neighbor).add(currentNode);
                    }
                }
            }
            /*
            * ACCUMULATION:
            *
            * Backtracking form Destination Nodes:
            *           After completing the BFS traversal, we backtrack from the destination nodes (nodes reached during BFS)
            *           to the source node along the shortest paths.
            * Dependency Accumulation:
            *           During backtracking, for each node on the path from a destination node to the source node, we calculate the dependency
            *           (delta) of the node on the updating the delta map accordingly.
            * Update Betweenness Centrality:
            *           As we backtrack, we update the betweenness centrality of each node by adding its computed delta value.
            *           This step ensures that each node's betweenness centrality reflects its role as a bridge or connector between
            *           other nodes in the graph.
            * */
            // Accumulation
            while (!stack.isEmpty()) {
                int currentNode = stack.pop();
                for (int predecessor : paths.get(currentNode)) {
                    double factor = (sigma.get(predecessor) / sigma.get(currentNode)) * (1 + delta.get(currentNode));
                    delta.put(predecessor, delta.get(predecessor) + factor);
                }
                if (currentNode != sourceNode) {
                    betweennessCentrality.put(currentNode, betweennessCentrality.get(currentNode) + delta.get(currentNode));
                }
            }
        }

        return betweennessCentrality;
    }


    // Method to print top 5 nodes with the highest centrality measures
    public void printTopCentralityNodes(Map<Integer, Double> centrality, String measureName) {
        // Sort the centrality map by value in descending order
        List<Map.Entry<Integer, Double>> sortedEntries = new ArrayList<>(centrality.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Print top 5 nodes with the highest centrality
        System.out.println("Top 5 Nodes with Highest " + measureName + " Centrality:");
        int count = 0;
        for (Map.Entry<Integer, Double> entry : sortedEntries) {
            if (count >= 5) {
                break; // Stop after printing the top 5 nodes
            }
            System.out.println("Node " + entry.getKey() + ": " + entry.getValue());
            count++;
        }
        System.out.println();
    }

    // Main method for testing
    public static void main(String[] args) {
        // Path to the dataset file
        String filePath = "/Users/revanth/Desktop/PSA/PSAFinalProject/src/reachability.txt";

        // Create an instance of NetworkAnalysis
        NetworkAnalysis networkAnalysis = new NetworkAnalysis(filePath);

        // Calculate betweenness centrality
        Map<Integer, Double> betweennessCentrality = networkAnalysis.calculateBetweennessCentrality();

        // Print top 5 nodes with the highest betweenness centrality
        networkAnalysis.printTopCentralityNodes(betweennessCentrality, "Betweenness");
    }
    /*
     * Time Complexity: O(V * (V + E)) --> Stack takes O(V), and BFS takes O(V + E)
     * Space Complexity: O(V)
     * */
}
