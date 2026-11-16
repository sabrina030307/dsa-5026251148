package lw05.Prelab.Unguided;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class Main {
 
    static int n;
    static String[] names;
    static int[][] timeMatrix;
    static int[] bfsLevel;
    static int[] bfsPrev;
    static int[] dijkstraPrev;

    public static void addEdge(int u, int v, int time) {
        timeMatrix[u][v] = time;
        timeMatrix[v][u] = time;
    }
    public static ArrayList<Integer> bfs(int start, int n) {
        ArrayList<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[n];
        ArrayList<Integer> queue = new ArrayList<>(); 
        
        queue.add(start);
        visited[start] = true;

        bfsLevel[start] = 0;
        bfsPrev[start] = -1;

        while (!queue.isEmpty()) { 
            int u = queue.remove(0); 
            order.add(u);

            for (int v = 0; v < n; v++) { 
                if (timeMatrix[u][v] > 0 && !visited[v]) {
                    visited[v] = true;
                    bfsLevel[v] = bfsLevel[u] + 1;
                    bfsPrev[v] = u;
                    queue.add(v);
                }
            }
        }
        return order;
    }

    public static void printLevels(int n, ArrayList<Integer> bfsOrder) {
        int maxLevel = 0;
        for (int i = 0; i < n; i++) {
            if (bfsLevel[i] > maxLevel) maxLevel = bfsLevel[i];
        }
        
        for (int l = 0; l <= maxLevel; l++) {
            System.out.print("Level " + l + ": ");
            boolean first = true;
            for (int node : bfsOrder) {
                if (bfsLevel[node] == l) {
                    if (!first) System.out.print(", ");
                    System.out.print(names[node]);
                    first = false;
                }
            }
            System.out.println();
        }
    }

    public static int findTheNextNode(int[] dist, boolean[] visited, int n) {
        int min = Integer.MAX_VALUE;
        int minNode = -1;
        for (int i = 0; i < n; i++) { // Blank 1: Find unvisited node with minimum distance
            if (!visited[i] && dist[i] < min) {
                min = dist[i];
                minNode = i;
            }
        }
        return minNode;
    }

    public static void dijkstraAlg(int start, int n) {
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        for (int i = 0; i < n - 1; i++) {
            int u = findTheNextNode(dist, visited, n); 
            if (u == -1) break;
            visited[u] = true;

            for (int v = 0; v < n; v++) { 
                if (!visited[v] && timeMatrix[u][v] > 0 && dist[u] != Integer.MAX_VALUE && dist[u] + timeMatrix[u][v] < dist[v]) {
                    dist[v] = dist[u] + timeMatrix[u][v];
                    dijkstraPrev[v] = u;
                }
            }
        }
    }

    
    public static ArrayList<Integer> buildPath(int start, int dest, int[] prev) {
        ArrayList<Integer> path = new ArrayList<>();
        int curr = dest;
        while (curr != -1) { 
            path.add(0, curr);
            curr = prev[curr];
        }
        return path;
    }
    public static int pathTime(ArrayList<Integer> path) {
        int totalTime = 0;
        for (int i = 0; i < path.size() - 1; i++) { 
            totalTime += timeMatrix[path.get(i)][path.get(i + 1)];
        }
        return totalTime;
    }
      
    public static void printPath(ArrayList<Integer> path) {
        for (int i = 0; i < path.size(); i++) {
            System.out.print(names[path.get(i)]);
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    public static void printSummary(int start, int dest) {
        ArrayList<Integer> bfsPath = buildPath(start, dest, bfsPrev);
        ArrayList<Integer> dijkstraPath = buildPath(start, dest, dijkstraPrev);

        int bfsStops = bfsPath.size() - 1;
        int bfsT = pathTime(bfsPath);

        int djStops = dijkstraPath.size() - 1;
        int djT = pathTime(dijkstraPath);

        System.out.println("=== Fewest Stops Route from " + names[start] + " to " + names[dest] + " ===");
        printPath(bfsPath);
        System.out.println("Stops: " + bfsStops + " | Time: " + bfsT + " min");

        System.out.println("=== Fastest Route from " + names[start] + " to " + names[dest] + " ===");
        printPath(dijkstraPath);
        System.out.println("Stops: " + djStops + " | Time: " + djT + " min");

        System.out.println("=== Comparison ===");
        if (bfsPath.equals(dijkstraPath)) {
            System.out.println("Both routes are the same.");
        } else {
            int timeSaved = bfsT - djT;
            int extraStops = djStops - bfsStops;
            System.out.println("The fastest route saves " + timeSaved + " min but needs " + extraStops + " more stops.");
        }
    }
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("delivery.txt"));
            if (!scanner.hasNextInt()) 
            n = scanner.nextInt();

            names = new String[n];
            for (int i = 0; i < n; i++) {
                names[i] = scanner.next();
            }

            timeMatrix = new int[n][n];
            bfsLevel = new int[n];
            bfsPrev = new int[n];
            dijkstraPrev = new int[n];
            Arrays.fill(bfsPrev, -1);
            Arrays.fill(dijkstraPrev, -1);

            int e = scanner.nextInt();
            for (int i = 0; i < e; i++) {
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                int weight = scanner.nextInt();
                addEdge(u, v, weight);
            }

            int start = scanner.nextInt();
            int dest = scanner.nextInt();
            scanner.close();

            System.out.println("=== Hub Levels from " + names[start] + " ===");
            ArrayList<Integer> bfsOrder = bfs(start, n);
            printLevels(n, bfsOrder);

            dijkstraAlg(start, n);
            printSummary(start, dest);

        } catch (FileNotFoundException e) {
            System.out.println("Error: delivery.txt found.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }  
    }
}
