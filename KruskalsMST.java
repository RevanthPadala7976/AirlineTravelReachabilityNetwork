/*
* **** KRUSKAL'S ALGORITHM ****
* Kruskal's algorithm finds the cheapest way to connect all nodes in the graph without creating cycles.
* STEPS:
*       1. Start with all edges considered and no connections made.
*       2. Pick the cheapest edge that doesn't connect already connected nodes (avoiding cycles).
*       3. Add the edge to the result and connect the nodes it joins.
*       4. Repeat steps 2 and 3 until all nodes are connected.
*
* This approach ensures to build the cheapest possible network by greedily picking the best edges first.
* */

import java.util.*;

// Class to represent a subset for union-find
class Subset {
    int parent;
    int rank;

    // Constructor for Subset class
    public Subset(int parent, int rank) {
        this.parent = parent;
        this.rank = rank;
    }
}

public class KruskalsMST extends GraphReader {

    // Constructor that accepts a file path
    public KruskalsMST(String filePath) {
        super(filePath);
    }

    // Kruskal's algorithm to find Minimum Spanning Tree
    public List<Edge> findMST() {
        List<Edge> mst = new ArrayList<>();
        List<Edge> edges = getAllEdges();

        // Find the maximum node ID to determine array size
        int maxNodeId = Integer.MIN_VALUE;
        for (Edge edge : edges) {
            maxNodeId = Math.max(maxNodeId, Math.max(edge.fromNodeId, edge.toNodeId));
        }

        int[] parent = new int[maxNodeId+1];
        int[] rank = new int[maxNodeId+1];

        // Initialize parent and rank arrays
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        for (Edge edge : edges) {
            int x = find(parent, edge.fromNodeId);
            int y = find(parent, edge.toNodeId);

            // If including this edge does not cause a cycle, add it to the MST
            if (x != y) {
                mst.add(edge);
                union(parent, rank, x, y);
            }
        }

        return mst;
    }

    // Method to find the subset in which the element belongs (Path Compression)
    private int find(int[] parent, int i) {
        if (parent[i] != i) {
            parent[i] = find(parent, parent[i]);
        }
        return parent[i];
    }

    // Method to perform union of two subsets (Union by Rank)
    private void union(int[] parent, int[] rank, int x, int y) {
        int xRoot = find(parent, x);
        int yRoot = find(parent, y);

        if (rank[xRoot] < rank[yRoot]) {
            parent[xRoot] = yRoot;
        } else if (rank[xRoot] > rank[yRoot]) {
            parent[yRoot] = xRoot;
        } else {
            parent[yRoot] = xRoot;
            rank[xRoot]++;
        }
    }

    // Method to get all edges from the graph
    private List<Edge> getAllEdges() {
        List<Edge> edges = new ArrayList<>();
        for (List<Edge> edgeList : adjacencyList.values()) {
            edges.addAll(edgeList);
        }
        return edges;
    }

    public static void main(String[] args) {
        // Path to the dataset file
        String filePath = "/Users/revanth/Desktop/PSA/PSAFinalProject/src/reachability1.txt";

        // Create a new KruskalMST object
        KruskalsMST graph = new KruskalsMST(filePath);

        // Find Minimum Spanning Tree using Kruskal's algorithm
        List<Edge> mst = graph.findMST();

        // Print the Minimum Spanning Tree
        System.out.println("Minimum Spanning Tree:");
        int count = 0;
        for (Edge edge : mst) {
            count++;
            System.out.println(edge.fromNodeId + " -> " + edge.toNodeId + ", Weight: " + edge.weight);
        }
        System.out.println("\n Number of edges: " + count);
    }
    /*
    * Time Complexity: O(E log E)
    * Space Complexity: O(E + V)
    * */
}




///*
//* Output:
//* `58 -> 88, weight: 10 `: This line represents an edge from node 58 to 88 with a weight of 10
//* */
