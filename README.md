## 🇹🇷 Turkish Cities Shortest Path Finder

A Java-based application that calculates the shortest path between Turkish cities using Breadth-First Search (BFS) and Depth-First Search (DFS) algorithms. The project features a custom graph implementation and reads distance data from a CSV file.

## 🚀 Features

* Custom Data Structures

  * Hand-coded Stack and Queue implementations

  * Core logic avoids using java.util collections

* Graph Representation

  * Stores distances between 17 major Turkish cities using an Adjacency Matrix

* Dual Search Algorithms

  * DFS: Deep exploration with path-cost tracking and pruning

  * BFS: Layer-based exploration optimized for shortest distance discovery

* Performance Metrics

  * Compares execution time (ms)

  * Compares total path cost (km)

## 🛠️ Technologies Used

* Language: Java 8+

* Data Format: CSV

* Input Handling: java.util.Scanner

## 📂 Project Structure
```text
ShortestPath-main/
├── Run.java                # Main entry point, graph logic, custom DS
└── Turkish cities.csv      # Adjacency matrix of distances between cities
```
## 📋 Prerequisites

* Java Development Kit (JDK 8 or higher)

* Ensure Turkish cities.csv is in the same directory as Run.java or the compiled .class files

## ⚙️ Installation & Running
* 1. Compile the Program
```bash
javac test/Run.java
```
* 2. Run the Application
```bash
java test.Run
```
## 🎮 Usage

When the program starts, it will:

1. Display a list of available cities

2. Ask you to enter a Start City

3. Ask you to enter an End City

4. Output:
   * Path taken
   * Total distance (km)
   * Execution time for DFS and BFS

## 📌 Example Output
```yaml
Enter the start city: Istanbul
Enter the end city: Antalya

DFS Results:
Path: Istanbul -> Ankara -> Konya -> Antalya
Path Cost: 1018
Execution Time: 0.450 ms

BFS Results:
Path: Istanbul -> Antalya
Path Cost: 690
Execution Time: 0.120 ms
```
## 🧠 Algorithm Logic
### Adjacency Matrix

  * The graph is constructed from Turkish cities.csv

  * 99999 represents no direct connection between two cities

### Shortest Path Strategy
#### DFS Approach

  * Uses a custom Stack

  * Explores deeply along each branch

  * Tracks the lowest-cost path found so far

  * Prunes branches that are already more expensive than the best known path

#### BFS Approach

  * Uses a custom Queue

  * Maintains a pathCosts[] array (similar in spirit to Dijkstra’s early logic)

  * If a shorter path to a node is found later, BFS re-evaluates that node
