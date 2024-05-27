import java.util.*;

public class AllSourcesShortestPath extends GraphReader {

    public AllSourcesShortestPath(String filePath) {
        super(filePath);
    }

    // Method to find all shortest paths using Floyd-Warshall algorithm
    public int[][] findAllShortestPaths() {
        int numNodes = adjacencyList.size(); // Determines the number of nodes in the graph
        int[][] distances = new int[numNodes][numNodes]; // Initializes a 2D array to store the shortest distances between all pairs of nodes.

        // Initialize distances matrix with maximum values
        for (int i = 0; i < numNodes; i++) {
            Arrays.fill(distances[i], Integer.MAX_VALUE);
            distances[i][i] = 0; // Distance from a node to itself is always 0
        }

        // Set initial distances based on the dataset

        /*
        * Iterates through each entry (node) in the `adjacencyList`.
        * For each node, iterate through its list of edges to adjacent nodes
        * Sets the initial distance from the current node to its adjacent nodes based on the edge weight in the dataset.
        * The `Math.abs()` function is used to ignore negative symbols in the edge weights
        * */
        for (Map.Entry<Integer, List<Edge>> entry : adjacencyList.entrySet()) {
            int fromNodeId = entry.getKey();
            for (Edge edge : entry.getValue()) {
                int toNodeId = edge.toNodeId;
                int weight = Math.abs(edge.weight); // Ignore negative symbols
                distances[fromNodeId][toNodeId] = weight;
            }
        }

        // Floyd-Warshall algorithm to update distances

        /*
        * Uses nested loops to iterate through each pair of nodes (`i` and `j`) and a middle node (`k`).
        * Updates the distance between nodes `i` to `k` and from `k` to `j` is not infinity, it calculates the new distance as the minimum of the
        * -current distance and the sum of distance from `i` to `k` and `k` to `j`.
        * Returns the `distance` matrix: the resulting `distances` matrix contains the shortest paths between all pairs of nodes in the graph.
        * */
        for (int k = 0; k < numNodes; k++) {
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    if (distances[i][k] != Integer.MAX_VALUE && distances[k][j] != Integer.MAX_VALUE) {
                        distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
                    }
                }
            }
        }

        return distances;
    }

    // Main method for testing
    public static void main(String[] args) {
        // Path to the dataset file
        String filePath = "/Users/revanth/Desktop/PSA/PSAFinalProject/src/reachability1.txt";

        // Create an instance of AllSourcesShortestPath
        AllSourcesShortestPath shortestPathFinder = new AllSourcesShortestPath(filePath);

        // Find all shortest paths using Floyd-Warshall algorithm
        int[][] shortestPaths = shortestPathFinder.findAllShortestPaths();

        // Display the shortest paths matrix
        System.out.println("Shortest Paths Matrix:");
        for (int i = 0; i < shortestPaths.length; i++) {
            for (int j = 0; j < shortestPaths[i].length; j++) {
                System.out.print((shortestPaths[i][j] == Integer.MAX_VALUE ? "INF" : shortestPaths[i][j]) + "\t");
            }
            System.out.println();
        }
    }
    /*
     * Time Complexity: O(V^3)
     * Space Complexity: O(V^2)
     * */

}
