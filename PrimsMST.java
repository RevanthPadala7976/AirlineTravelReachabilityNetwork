import java.util.*;

public class PrimsMST extends GraphReader {

    // Constructor that accepts a file path
    public PrimsMST(String filePath) {
        super(filePath);
    }

    // Prim's Algorithm to find Minimum Spanning Tree (MST)
    public List<Edge> primsMST() {
        List<Edge> mst = new ArrayList<>(); // An empty list to store the edges of the MST
        Set<Integer> visited = new HashSet<>(); // Set to track visited vertices
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight)); // Priority queue to store edges

        // Start from vertex 0
        int startVertex = 0;
        visited.add(startVertex);

        // Add all edges incident to startVertex to the priority queue
        if (adjacencyList.containsKey(0)) {
            for (Edge edge : adjacencyList.get(0)) {
                pq.offer(edge);
            }
        }

        while (!pq.isEmpty()) {
            Edge minEdge = pq.poll();
            int nextNode = minEdge.toNodeId;

            // If adding this edge doesn't create a cycle, add it to the MST
            if (!visited.contains(nextNode)) {
                mst.add(minEdge);
                visited.add(nextNode);

                // Add all edges incident to the newly visited vertex to the priority queue
                for (Edge edge : adjacencyList.get(nextNode)) {
                    if (!visited.contains(edge.toNodeId)) {
                        pq.add(edge);
                    }
                }
            }
        }

        return mst;
    }

    public static void main(String[] args) {
        // Path to the dataset file
        String filePath = "/Users/revanth/Desktop/PSA/PSAFinalProject/src/reachability1.txt";

        // Create a new MinimumSpanningTree object
        PrimsMST graph = new PrimsMST(filePath);

        // Find Minimum Spanning Tree (MST) using Prim's Algorithm
        List<Edge> mst = graph.primsMST();

        // Print the edges of the MST
        System.out.println("Minimum Spanning Tree (MST) edges:");
        for (Edge edge : mst) {
            System.out.println(edge.fromNodeId + " -> " + edge.toNodeId + ", Weight: " + edge.weight);
        }
    }
    /*
     * Time Complexity: O(E log E)
     * Space Complexity: O(E)
     * */
}
