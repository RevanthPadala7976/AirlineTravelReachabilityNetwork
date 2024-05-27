/*
* **** FORD-FULKERSON ALGORITHM ****
* INITIALIZATION:
*            1. Start with an initial flow of 0.
*            2. Create a residual graph that initially mirror the original graph
* FINDING AUGMENTING PATH:
*            1. Repeat until no augmenting paths can be found;
*                   * Use a search algorithm (DFS) to find an augmenting path from the source node to the sink node in the residual graph.
*                   * An augmenting path is a path from the source to the sink where all edges have positive residual capacities.
*                   * If no augmenting path is found, terminate the algorithm.
* UPDATING FLOW:
*       * Once an augmenting path is found, determine the bottleneck capacity of the path. The bottleneck capacity is the minimum residual capacity
*         of the edge along the path.
*       * Increase the flow along the augmenting path by the bottleneck capacity.
*       * Update the residual capacities of the edges:
*                       * Subtract the flow from the forward edges along the augmenting path.
*                       * Add the flow to the backward edges (reverse edges) along with augmenting path.
*                         if a backward edge does not exist, create one with capacity equal to the flow.
*       * Repeat the process of finding augmenting paths and updating the flow until no more augmenting paths can be found.
*
* TERMINATION:
*       * When no more augmenting paths can be found, the maximum flow has been achieved.
*
* OUTPUT:
*       * The Maximum flow is equal to the flow leaving the source node (or entering the sink node).
* */
import java.util.*;

public class FordFulkersonAlgorithm extends GraphReader {

    public FordFulkersonAlgorithm(String filePath) {
        super(filePath);
    }

    // Method to find the maximum flow in the network using Ford-Fulkerson algorithm
    public int findMaximumFlow(int sourceNode, int sinkNode) {
        int maxFlow = 0; // Initial flow of 0

        // Create a residual graph initially identical to the original graph
        Map<Integer, List<Edge>> residualGraph = new HashMap<>(adjacencyList);

        // Keep track of visited nodes during each path traversal
        Set<Integer> visited = new HashSet<>();

        // Repeat the augmenting path search until no more augmenting paths can be found
        boolean foundAugmentingPath;
        do {
            foundAugmentingPath = false; // Reset flag for each iteration

            // Find an augmenting path using DFS
            List<Integer> augmentingPath = new ArrayList<>();
            visited.clear(); // Clear visited set for each new path search
            if (dfs(sourceNode, sinkNode, visited, augmentingPath, residualGraph)) {
                foundAugmentingPath = true; // Mark that an augmenting path is found

                // Find the bottleneck capacity of the augmenting path
                int bottleneckCapacity = Integer.MAX_VALUE;
                for (int i = 0; i < augmentingPath.size() - 1; i++) {
                    int u = augmentingPath.get(i);
                    int v = augmentingPath.get(i + 1);
                    for (Edge edge : residualGraph.get(u)) {
                        if (edge.toNodeId == v) {
                            bottleneckCapacity = Math.min(bottleneckCapacity, edge.weight);
                            break;
                        }
                    }
                }

                // Update the residual graph and maximum flow
                for (int i = 0; i < augmentingPath.size() - 1; i++) {
                    int u = augmentingPath.get(i);
                    int v = augmentingPath.get(i + 1);
                    for (Edge edge : residualGraph.get(u)) {
                        if (edge.toNodeId == v) {
                            edge.weight -= bottleneckCapacity; // Subtract from forward edge
                            break;
                        }
                    }
                    // Add a backward edge if it doesn't already exist
                    boolean backwardEdgeExists = false;
                    for (Edge edge : residualGraph.get(v)) {
                        if (edge.toNodeId == u) {
                            edge.weight += bottleneckCapacity;
                            backwardEdgeExists = true;
                            break;
                        }
                    }
                    if (!backwardEdgeExists) {
                        residualGraph.get(v).add(new Edge(v, u, bottleneckCapacity));
                    }
                }

                // Increment the maximum flow by the bottleneck capacity of the augmenting path
                maxFlow += bottleneckCapacity;
            }
        } while (foundAugmentingPath); // Continue loop if an augmenting path is found

        // Return the maximum flow
        return maxFlow;
    }


    // Depth-first search (DFS) to find an augmenting path
    private boolean dfs(int currentNode, int sinkNode, Set<Integer> visited, List<Integer> augmentingPath, Map<Integer, List<Edge>> graph) {
        visited.add(currentNode);
        augmentingPath.add(currentNode);
        if (currentNode == sinkNode) {
            return true; // If sink node reached, augmenting path found
        }
        for (Edge edge : graph.getOrDefault(currentNode, Collections.emptyList())) {
            if (!visited.contains(edge.toNodeId) && edge.weight > 0) {
                if (dfs(edge.toNodeId, sinkNode, visited, augmentingPath, graph)) {
                    return true; // If augmenting path found in child node, return true
                }
            }
        }
        augmentingPath.remove(augmentingPath.size() - 1); // Backtrack if no augmenting path found
        return false;
    }

    public static void main(String[] args) {
        // Path to the dataset file
        String filePath = "/Users/revanth/Desktop/PSA/PSAFinalProject/src/reachability1.txt";

        // Create an instance of FordFulkersonAlgorithm
        FordFulkersonAlgorithm fordFulkersonSolver = new FordFulkersonAlgorithm(filePath);

        // Choose source and sink nodes
        int sourceNode = 0;
        int sinkNode = 2;

        // Find the maximum flow between the source and sink nodes using Ford-Fulkerson algorithm
        int maxFlow = fordFulkersonSolver.findMaximumFlow(sourceNode, sinkNode);

        // Display the maximum flow
        System.out.println("Maximum Flow from node " + sourceNode + " to node " + sinkNode + ": " + maxFlow);
    }
    /*
     * Time Complexity: O((V + E) * F) --> DFS takes O(V + E), Termination takes O(F), F is the maximum flow
     * Space Complexity: O(V + E)
     * */
}
