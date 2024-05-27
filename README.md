# AirlineTravelReachabilityNetwork
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Data Structures](https://img.shields.io/badge/Data%20Structures-0078D4?style=for-the-badge&logo=data&logoColor=white)

## Project Overview

This project focuses on integrating various graph algorithms, including Breadth-First Search (BFS), Depth-First Search (DFS), and Dijkstra’s algorithm, to create an efficient airline travel reachability network. The aim is to enhance travel planning efficiency between cities by providing accurate and optimized routes.

## Features

BFS Implementation: Used for finding the shortest path in terms of the number of edges.
DFS Implementation: Used for traversing or searching tree or graph data structures.
Dijkstra’s Algorithm: Used for finding the shortest path between nodes in a graph, which may represent, for example, road networks.

## DATA SET DESCRIPTION
**FILE PATH:** `reachability.txt`
**FORMAT:** Tab-separated values (TSV)
**CONTENT:** The dataset represents a directed graph indicating the transportation reachability of cities in the United States and Canada.
### DESCRIPTION OF COLUMNS:
FromNodeID: Represents the ID of the source city/node.
ToNodeID: Represents the ID of the destination city/node.
Weight: The weight or similarity of transportation reachability.
### INTERPRETATION:
* Each line in the dataset represents an edge in the directed graph.
* The edge goes from the city/node specified by `FromNodeID` to the city/node specified by `ToNodeID`.
* The Weight associated with each edge indicates the estimated travel time or similarity of transportation reachability between the source and destination cities/nodes. More negative weight imply More dissimilar reachability.

### EXAMPLE:
* For example, the line `27 0 -757` indicates that there is a transportation connection from city/node with ID 27 to city/node ID 0, with weight of -757. This means that the transportation reachability from city 27 to city 0 is estimated to be -757, indicating some level of dissimilarity of possibly travel time.
