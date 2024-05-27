import java.util.*;

public class DFS extends GraphReader {

    // Constructor that accepts a file path
    public DFS(String filePath) {
        super(filePath);
    }

    // Method to perform Depth-First Search (DFS) traversal of the graph
    public void dfsTraversal(int startNode) {
        // Set to keep track of visited nodes
        Set<Integer> visited = new HashSet<>();

        // Perform DFS traversal recursively
        dfsRecursive(startNode, visited);
    }

    // Recursive helper method for DFS traversal
    private void dfsRecursive(int currentNode, Set<Integer> visited) {
        // Mark the current node as visited
        visited.add(currentNode);
        System.out.print(currentNode + " ");

        // Get adjacent nodes of the current node
        List<Edge> adjacentEdges = getAdjacentEdges(currentNode);
        for (Edge edge : adjacentEdges) {
            int neighborNode = edge.toNodeId;
            if (!visited.contains(neighborNode)) {
                // Recursively visit unvisited neighbor nodes
                dfsRecursive(neighborNode, visited);
            }
        }
    }

    public static void main(String[] args) {
        // Path to the dataset file
        String filePath = "/Users/revanth/Desktop/PSA/PSAFinalProject/src/reachability1.txt";

        // Create an instance of DFS
        DFS dfs = new DFS(filePath);

        // Choose a starting node for DFS traversal
        int startNode = 0;

        // Perform DFS traversal starting from the specified node
        System.out.println("DFS Traversal from node " + startNode + ":");
        dfs.dfsTraversal(startNode);
    }
    /*
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     * */
}
