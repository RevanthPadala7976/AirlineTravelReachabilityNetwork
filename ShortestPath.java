/*
* ****FINDING SHORTEST PATH FROM ONE NODE TO ANOTHER ****
* I am using Dijkstra's Algorithm to find the shortest path between two nodes.
* */import java.util.*;

public class ShortestPath extends GraphReader {

    public ShortestPath(String filePath) {
        super(filePath);
    }

    // Method to find the shortest distance between two nodes using Dijkstra's algorithm
    public int findShortestDistance(int sourceNode, int destinationNode) {
        // Initialize a map to store distances from the source node to each node in the graph
        Map<Integer, Integer> distances = new HashMap<>();
        // Initialize a priority queue to store nodes based on their tentative distances from the source node
        PriorityQueue<NodeDistancePair> pq = new PriorityQueue<>();

        // Initializing distances to all nodes as infinity
//        System.out.println("Keys: "+adjacencyList.keySet());
        for (int node : adjacencyList.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
//        System.out.println("Distance: " + distances);
        // Set distance to the source node as 0
        distances.put(sourceNode, 0);
//        System.out.println("Source Distance: " + distances);
        pq.offer(new NodeDistancePair(sourceNode, 0));


        /*
        * The method enter a loop that continues until the priority queue is empty.
        * In each Iteration, it extracts the node with the shortest tentative distance from the priority queue.
        * If the extracted node is the destination node, the algorithm terminates, and the shortest distance is returned.
        * Otherwise, for each edge outgoing from the current node, it calculates the new distance to the adjacent node by adding edge weight to current distance.
        * If the new distance is less than the distance currently stored for the adjacent node, it updates the distance and adds
        * - the adjacent node to the priority queue with the new distance
        * This process continues until all reachable nodes are visited or until the destination node is reached.
        * */
        // Perform Dijkstra's algorithm
        while (!pq.isEmpty()) {
            NodeDistancePair current = pq.poll();
            int currentNode = current.node;
            int currentDistance = current.distance;

            // Stop the algorithm if the destination node is reached
            if (currentNode == destinationNode) {
                return currentDistance;
            }

            if (adjacencyList.containsKey(currentNode)) {
                List<Edge> edges = adjacencyList.get(currentNode);

                // Now you can safely iterate over the list of edges
                for (Edge edge : edges) {
                    int adjacentNode = edge.toNodeId;
                    int weight = Math.abs(edge.weight);
//                    System.out.println("CurrentNode edge: "+ adjacentNode);
                    int newDistance = currentDistance + weight;
//                    System.out.println("New Distance: " + newDistance);
//                    System.out.println("old dis: "+ distances.get(adjacentNode));
                    if (newDistance < distances.get(adjacentNode)) {
                        distances.put(adjacentNode, newDistance);
                        pq.offer(new NodeDistancePair(adjacentNode, newDistance));
                    }
                }
            } else {
                // Handle the case where the map does not contain the key
                System.err.println("No edges found for node " + currentNode);
            }
        }

        // If destination node is not reachable
        return Integer.MAX_VALUE;
    }

    // Class to represent a pair of node and its distance
    class NodeDistancePair implements Comparable<NodeDistancePair> {
        int node;
        int distance;

        public NodeDistancePair(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }

        @Override
        public int compareTo(NodeDistancePair other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        // Path to the dataset file
        String filePath = "/Users/revanth/Desktop/PSA/PSAFinalProject/src/reachability1.txt";
        // Create an instance of ShortestPath
        ShortestPath shortestPathFinder = new ShortestPath(filePath);

        // Choose source and destination nodes
        int sourceNode = 0;
        int destinationNode = 2;

        // Find the shortest distance between the source and destination nodes using Dijkstra's algorithm
        int shortestDistance = shortestPathFinder.findShortestDistance(sourceNode, destinationNode);

        // Display the shortest distance
        if (shortestDistance == Integer.MAX_VALUE) {
            System.out.println("No path exists from node " + sourceNode + " to node " + destinationNode);
        } else {
            System.out.println("Shortest distance from node " + sourceNode + " to node " + destinationNode + " is: " + shortestDistance);
        }

    }
    /*
     * Time Complexity: O((V + E) * log V) --> Priority queue takes O(log v), and each edge is processed once O(V + E)
     * Space Complexity: O(V)
     * */
}
