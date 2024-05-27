/*
* **** DATA SET DESCRIPTION ****
* FILE PATH: `/Users/revanth/Desktop/PSA/PSAFinalProject/src/reachability.txt`
* FORMAT: Tab-separated values (TSV)
* CONTENT: The dataset represents a directed graph indicating the transportation reachability of cities in the -
    United States and Canada.
*
* DESCRIPTION OF COLUMNS:
* FromNodeID: Represents the ID of the source city/node.
* ToNodeID: Represents the ID of the destination city/node.
* Weight: The weight or similarity of transportation reachability.
*
* INTERPRETATION:
* Each line in the dataset represents an edge in the directed graph.
* The edge goes from the city/node specified by `FromNodeID` to the city/node specified by `ToNodeID`.
* The Weight associated with each edge indicates the estimated travel time or similarity of transportation reachability-
    between the source and destination cities/nodes. More negative weight imply More dissimilar reachability.
*
* EXAMPLE:
* For example, the line `27 0 -757` indicates that there is a transportation connection from city/node with ID 27 to -
    city/node ID 0, with weight of -757. This means that the transportation reachability from city 27 to city 0 is -
    estimated to be -757, indicating some level of dissimilarity of possibly travel time.
* */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Class to represent a node in the graph
class Node {
    int id;

    // Constructor for Node class
    public Node(int id) {
        this.id = id;
    }
}

// Class to represent an edge in the graph
class Edge {
    int fromNodeId;
    int toNodeId;
    int weight;

    // Constructor for Edge class
    public Edge(int fromNodeId, int toNodeId, int weight) {
        this.fromNodeId = fromNodeId;
        this.toNodeId = toNodeId;
        this.weight = weight;
    }
}

// Class to represent a directed graph
class Graph {
    Map<Integer, List<Edge>> adjacencyList;

    // Constructor for Graph class
    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    // Method to add an edge to the graph
    public void addEdge(int fromNodeId, int toNodeId, int weight) {
        Edge edge = new Edge(fromNodeId, toNodeId, weight);

        /*
        * If FromNodeID is not in the Map, we're adding it.
        * Else we get the FromNodeID and add the new edge
        */
        if (!adjacencyList.containsKey(fromNodeId)) {
            adjacencyList.put(fromNodeId, new ArrayList<>());
        }
        adjacencyList.get(fromNodeId).add(edge);
    }

    // Method to get adjacency list of a node
    public List<Edge> getAdjacentEdges(int nodeId) {
        return adjacencyList.getOrDefault(nodeId, new ArrayList<>());
    }
}

public class GraphReader extends Graph{

    // Constructor that accepts a file path
    public GraphReader(String filePath) {
        super();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip comments and empty lines
                if (line.startsWith("#") || line.trim().isEmpty()) {
                    continue;
                }

                // Split the line by whitespace
                String[] parts = line.split("\\s+");

                // Parse the data
                int fromNodeId = Integer.parseInt(parts[0]);
                int toNodeId = Integer.parseInt(parts[1]);
                int weight = Math.abs(Integer.parseInt(parts[2]));

                // Add the edge to the graph (inherited method from Graph class)
                addEdge(fromNodeId, toNodeId, weight);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Path to the dataset file
        String filePath = "/Users/revanth/Desktop/PSA/PSAFinalProject/src/reachability1.txt";

        // Create a new graph
        GraphReader graph = new GraphReader(filePath);

        // Example: Print adjacency list of node 0
        List<Edge> adjacentEdges = graph.getAdjacentEdges(0);
//        System.out.println("Adjacent edges of node 0:");
//        for (Edge edge : adjacentEdges) {
//            System.out.println(edge.fromNodeId + " -> " + edge.toNodeId + ", Weight: " + edge.weight);
//        }

        System.out.println("\n---- ALL THE EDGES ----\n");
        for (int nodeId : graph.adjacencyList.keySet()) {
            adjacentEdges = graph.getAdjacentEdges(nodeId);
            System.out.println("Adjacent edges of node " + nodeId + ":");
            for (Edge edge : adjacentEdges) {
                System.out.println(edge.fromNodeId + " -> " + edge.toNodeId + ", Weight: " + edge.weight);
            }
        }

        System.out.println("\n---- Graph Traversal ---- ");
        BFS bfs = new BFS(filePath);

        System.out.print("BFS: ");
        bfs.bfsTraversal(0);
        System.out.println();
        DFS dfs = new DFS(filePath);
        System.out.print("DFS: ");
        dfs.dfsTraversal(0);


    }
    /*
    * TIME COMPLEXITY:
    * Time Complexity of the Reading the graph: O(n)
    * Printing, BFS, DFS: O(V + E)
    * i.e., O(n + V + E)
    *
    * SPACE COMPLEXITY:
    * O(V + E) -- > for adjacency matrix
    *
    * */
}

