// Package declaration
package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Node Class
class Node {
    public int cityIndex;
    public Node parent;
    public int pathCost;

    // Constructor
    public Node(int cityIndex, Node parent, int pathCost) {
        this.cityIndex = cityIndex;
        this.parent = parent;
        this.pathCost = pathCost;
    }
}

// Custom Stack implementation
class Stack<E> {
    private static final int CAPACITY = 100; // Default stack capacity
    private E[] data;
    private int top = -1;

    // Constructor with default capacity
    public Stack() {
        this(CAPACITY);
    }

    // Constructor with custom capacity
    public Stack(int capacity) {
        data = (E[]) new Object[capacity];
    }

    // Returns the size of the stack
    // Time Complexity: O(1)
    public int size() {
        return top + 1;
    }

    // Checks if the stack is empty
    // Time Complexity: O(1)
    public boolean isEmpty() {
        return top == -1;
    }

    // Returns the top element of the stack
    // Time Complexity: O(1)
    public E top() {
        return data[top];
    }

    // Pushes an element onto the stack
    // Time Complexity: O(1)
    public void push(E element) throws IllegalStateException {
        if (top == data.length - 1) throw new IllegalStateException("Stack is full");
        data[++top] = element;
    }

    // Pops an element from the stack
    // Time Complexity: O(1)
    public E pop() {
        if (isEmpty()) return null;
        E temp = data[top];
        data[top--] = null;
        return temp;
    }
}

// Custom Queue Implementation
class Queue<E> {
    private static final int CAPACITY = 1000; // Default queue capacity
    private E[] data;
    private int front = 0;
    private int size = 0;

    // Constructor with default capacity
    public Queue() {
        this(CAPACITY);
    }

    // Constructor with custom capacity
    public Queue(int capacity) {
        data = (E[]) new Object[capacity];
    }

    // Returns the size of the queue
    // Time Complexity: O(1)
    public int size() {
        return size;
    }

    // Checks if the queue is empty
    // Time Complexity: O(1)
    public boolean isEmpty() {
        return size == 0;
    }

    // Returns the first element of the queue
    // Time Complexity: O(1)
    public E first() {
        if (isEmpty()) return null;
        return data[front];
    }

    // Adds an element to the queue
    // Time Complexity: O(1)
    public void enqueue(E e) throws IllegalStateException {
        if (size == data.length) throw new IllegalStateException("Queue is full");
        int avail = (front + size) % data.length;
        data[avail] = e;
        size++;
    }

    // Removes an element from the queue
    // Time Complexity: O(1)
    public E dequeue() {
        if (isEmpty()) return null;
        E temp = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        return temp;
    }
}

// Graph Class
class Graph {
    private int[][] adjacencyMatrix; // Graph represented as an adjacency matrix
    private String[] cities;         // List of cities
    private Map<String, Integer> cityIndexMap; // Mapping of city names to indices

    // Constructor
    public Graph(String[] cities, int[][] adjacencyMatrix) {
        this.cities = cities;
        this.adjacencyMatrix = adjacencyMatrix;
        this.cityIndexMap = new HashMap<>();
        for (int i = 0; i < cities.length; i++) {
            cityIndexMap.put(cities[i], i);
        }
    }

    // Get city index by name
    // Time Complexity: O(1)
    public int getCityIndex(String cityName) {
        return getOrElse(cityIndexMap,cityName, -1);
    }

    private <K, V> V getOrElse(Map<K, V> map, K key, V defaultValue) {
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    // DFS-based shortest path algorithm
    // Time Complexity: O(V^2) in the worst case
    public List<Node> findShortestPathDFS(String startCity, String endCity) {
        int start = getCityIndex(startCity);
        int end = getCityIndex(endCity);

        Stack<Node> open = new Stack<>(cities.length * cities.length);
        boolean[] visited = new boolean[cities.length];
        Node[] bestPathNodes = new Node[cities.length];

        open.push(new Node(start, null, 0));
        Node bestNode = null;

        while (!open.isEmpty()) {
            Node current = open.pop();

            if (visited[current.cityIndex] && bestPathNodes[current.cityIndex].pathCost <= current.pathCost) {
                continue;
            }

            visited[current.cityIndex] = true;
            bestPathNodes[current.cityIndex] = current;

            if (current.cityIndex == end && (bestNode == null || current.pathCost < bestNode.pathCost)) {
                bestNode = current;
            }

            for (int neighbor = 0; neighbor < cities.length; neighbor++) {
                int edgeWeight = adjacencyMatrix[current.cityIndex][neighbor];

                if (edgeWeight > 0 && edgeWeight < 99999) {
                    int newPathCost = current.pathCost + edgeWeight;
                    if (!visited[neighbor] || (bestPathNodes[neighbor] != null && newPathCost < bestPathNodes[neighbor].pathCost)) {
                        Node successor = new Node(neighbor, current, newPathCost);
                        open.push(successor);
                    }
                }
            }
        }

        return constructPath(bestNode);
    }

    // BFS-based shortest path algorithm
    // Time Complexity: O(V^2) in the worst case
    public List<Node> findShortestPathBFS(String startCity, String endCity) {
        int start = getCityIndex(startCity);
        int end = getCityIndex(endCity);

        Queue<Node> open = new Queue<>(cities.length * cities.length);
        Queue<Node> closed = new Queue<>(cities.length * cities.length);
      
        int[] pathCosts = new int[cities.length];
        Arrays.fill(pathCosts, Integer.MAX_VALUE);

        open.enqueue(new Node(start, null, 0));
        pathCosts[start] = 0;
        Node bestNode = null;

        while (!open.isEmpty()) {
            Node current = open.dequeue();

            if (current.cityIndex == end && (bestNode == null || current.pathCost < bestNode.pathCost)) {
                bestNode = current;
                continue;
            }

            for (int neighbor = 0; neighbor < cities.length; neighbor++) {
                int edgeWeight = adjacencyMatrix[current.cityIndex][neighbor];
                if (edgeWeight > 0 && edgeWeight < 99999) {
                    int newPathCost = current.pathCost + edgeWeight;
                    if (newPathCost < pathCosts[neighbor]) {
                        pathCosts[neighbor] = newPathCost;
                        Node successor = new Node(neighbor, current, newPathCost);
                        open.enqueue(successor);
                    }
                }
            }
        }

        return constructPath(bestNode);
    }

    // Constructs the path from the end node
    private List<Node> constructPath(Node endNode) {
        List<Node> path = new ArrayList<>();
        Node current = endNode;
        while (current != null) {
            path.add(0, current);
            current = current.parent;
        }
        return path;
    }
}

// CSV Parser
class CSVParser {
    public static Object[] parseCSV(String csvFilePath) {
        String[] cities = null;
        int[][] adjacencyMatrix = null;

        try {
            Scanner fileScanner = new Scanner(new File(csvFilePath));
            String header = fileScanner.nextLine();
            cities = header.split(",");
            cities = Arrays.copyOfRange(cities, 1, cities.length);

            List<String[]> rows = new ArrayList<>();
            while (fileScanner.hasNextLine()) {
                rows.add(fileScanner.nextLine().split(","));
            }
            fileScanner.close();

            int numCities = cities.length;
            adjacencyMatrix = new int[numCities][numCities];
            for (int i = 0; i < numCities; i++) {
                String[] row = rows.get(i);
                for (int j = 0; j < numCities; j++) {
                    try {
                        adjacencyMatrix[i][j] = Integer.parseInt(row[j + 1].trim());
                    } catch (NumberFormatException e) {
                        adjacencyMatrix[i][j] = 99999;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            return null;
        }

        return new Object[]{cities, adjacencyMatrix};
    }
}

// Main class to run the program
public class Run {
    public static void main(String[] args) {
        String csvFilePath = "Turkish cities.csv";
        Object[] graphData = CSVParser.parseCSV(csvFilePath);

        if (graphData == null) {
            System.out.println("Error loading graph data.");
            return;
        }

        String[] cities = (String[]) graphData[0];
        int[][] adjacencyMatrix = (int[][]) graphData[1];

        Graph graph = new Graph(cities, adjacencyMatrix);

        System.out.println("Available cities:");
        for (String city : cities) {
            System.out.print(city + " ");
        }
        System.out.println();

        Scanner inputScanner = new Scanner(System.in);

        String startCity;
        while (true) {
            System.out.print("Enter the start city: ");
            startCity = inputScanner.nextLine().trim();
            if (graph.getCityIndex(startCity) != -1) break;
            System.out.println("Invalid city name. Please try again.");
        }

        String endCity;
        while (true) {
            System.out.print("Enter the end city: ");
            endCity = inputScanner.nextLine().trim();
            if (graph.getCityIndex(endCity) != -1) break;
            System.out.println("Invalid city name. Please try again.");
        }

        long startTimeDFS = System.nanoTime();
        List<Node> pathDFS = graph.findShortestPathDFS(startCity, endCity);
        long endTimeDFS = System.nanoTime();

        long startTimeBFS = System.nanoTime();
        List<Node> pathBFS = graph.findShortestPathBFS(startCity, endCity);
        long endTimeBFS = System.nanoTime();

        displayResults("DFS", pathDFS, cities, (endTimeDFS - startTimeDFS));
        displayResults("BFS", pathBFS, cities, (endTimeBFS - startTimeBFS));

        inputScanner.close();
    }

    private static void displayResults(String algorithm, List<Node> path, String[] cities, long executionTime) {
        System.out.println("\n" + algorithm + " Results:");
        if (path == null || path.isEmpty()) {
            System.out.println("No path found.");
            return;
        }

        System.out.print("Path: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(cities[path.get(i).cityIndex]);
            if (i < path.size() - 1) System.out.print(" -> ");
        }
        System.out.println("\nPath Cost: " + path.get(path.size() - 1).pathCost);
        System.out.printf("Execution Time: %.3f ms%n", executionTime / 1_000_000.0);
    }
}
