import java.util.*;

public class BFS extends GraphReader {

    // Constructor that accepts a file path
    public BFS(String filePath) {
        super(filePath);
    }

    // Method to perform Breadth-First Search (BFS) traversal of the graph
    public void bfsTraversal(int startNode) {
        // Set to keep track of visited nodes
        Set<Integer> visited = new HashSet<>();

        // Queue for BFS traversal
        Queue<Integer> queue = new LinkedList<>();

        // Enqueue the start node
        queue.offer(startNode);
        visited.add(startNode);

        // Perform BFS traversal
        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            System.out.print(currentNode + " ");

            // Get adjacent nodes of the current node
            List<Edge> adjacentEdges = getAdjacentEdges(currentNode);
            for (Edge edge : adjacentEdges) {
                int neighborNode = edge.toNodeId;
                if (!visited.contains(neighborNode)) {
                    queue.offer(neighborNode);
                    visited.add(neighborNode);
                }
            }
        }
    }

    public static void main(String[] args) {
        // Path to the dataset file
        String filePath = "/Users/revanth/Desktop/PSA/PSAFinalProject/src/reachability1.txt";

        // Create an instance of BFS
        BFS bfs = new BFS(filePath);

        // Choose a starting node for BFS traversal
        int startNode = 0;

        // Perform BFS traversal starting from the specified node
        System.out.println("BFS Traversal from node " + startNode + ":");
        bfs.bfsTraversal(startNode);
    }
    /*
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     * */
}
