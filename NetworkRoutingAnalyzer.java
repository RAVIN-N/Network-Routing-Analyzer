import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class NetworkRoutingAnalyzer {

    static final int INF = 9999;

    static void dijkstra(int[][] graph, int source, int destination,
                          String[] routers) {

        int n = graph.length;
        int[] distance = new int[n];
        boolean[] visited = new boolean[n];
        int[] parent = new int[n];

        Arrays.fill(distance, INF);
        Arrays.fill(parent, -1);

        distance[source] = 0;

        for (int count = 0; count < n - 1; count++) {
            int u = -1;
            int min = INF;

            for (int i = 0; i < n; i++) {
                if (!visited[i] && distance[i] < min) {
                    min = distance[i];
                    u = i;
                }
            }

            if (u == -1) {
                break;
            }

            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (graph[u][v] != 0
                        && !visited[v]
                        && distance[u] + graph[u][v] < distance[v]) {

                    distance[v] = distance[u] + graph[u][v];
                    parent[v] = u;
                }
            }
        }

        System.out.println("\n========== DIJKSTRA RESULT ==========");
        System.out.println("Source      : " + routers[source]);
        System.out.println("Destination : " + routers[destination]);

        if (distance[destination] == INF) {
            System.out.println("No route available.");
            return;
        }

        printPath(parent, destination, routers);
        System.out.println("\nTotal Routing Cost : " + distance[destination]);
        System.out.println("Time Complexity     : O(V²)");
    }

    static void printPath(int[] parent, int destination,
                          String[] routers) {

        ArrayList<String> path = new ArrayList<>();
        int current = destination;

        while (current != -1) {
            path.add(routers[current]);
            current = parent[current];
        }

        Collections.reverse(path);

        System.out.print("Shortest Path       : ");

        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));

            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
    }

    static void bellmanFord(int[][] graph, int source, int destination,
                            String[] routers) {

        int n = graph.length;
        int[] distance = new int[n];
        int[] parent = new int[n];

        Arrays.fill(distance, INF);
        Arrays.fill(parent, -1);

        distance[source] = 0;

        for (int k = 0; k < n - 1; k++) {
            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {

                    if (graph[u][v] != 0
                            && distance[u] != INF
                            && distance[u] + graph[u][v] < distance[v]) {

                        distance[v] = distance[u] + graph[u][v];
                        parent[v] = u;
                    }
                }
            }
        }

        System.out.println("\n========== BELLMAN-FORD RESULT ==========");
        System.out.println("Source      : " + routers[source]);
        System.out.println("Destination : " + routers[destination]);

        if (distance[destination] == INF) {
            System.out.println("No route available.");
            return;
        }

        printPath(parent, destination, routers);
        System.out.println("\nTotal Routing Cost : " + distance[destination]);
        System.out.println("Time Complexity     : O(VE)");
    }

    static void floydWarshall(int[][] graph, String[] routers) {

        int n = graph.length;
        int[][] distance = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (i == j) {
                    distance[i][j] = 0;
                } else if (graph[i][j] != 0) {
                    distance[i][j] = graph[i][j];
                } else {
                    distance[i][j] = INF;
                }
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    if (distance[i][k] != INF
                            && distance[k][j] != INF
                            && distance[i][k] + distance[k][j] < distance[i][j]) {

                        distance[i][j] =
                                distance[i][k] + distance[k][j];
                    }
                }
            }
        }

        System.out.println("\n========== FLOYD-WARSHALL ==========");
        System.out.println("\nAll-Pairs Shortest Path:");

        System.out.print("       ");

        for (String router : routers) {
            System.out.printf("%6s", router);
        }

        System.out.println();

        for (int i = 0; i < n; i++) {
            System.out.printf("%6s", routers[i]);

            for (int j = 0; j < n; j++) {
                if (distance[i][j] == INF) {
                    System.out.printf("%6s", "-");
                } else {
                    System.out.printf("%6d", distance[i][j]);
                }
            }

            System.out.println();
        }

        System.out.println("\nTime Complexity : O(V³)");
    }

    static void displayNetwork(int[][] graph, String[] routers) {

        System.out.println("\n========== NETWORK ==========");

        System.out.print("        ");

        for (String router : routers) {
            System.out.printf("%6s", router);
        }

        System.out.println();

        for (int i = 0; i < graph.length; i++) {
            System.out.printf("%6s", routers[i]);

            for (int j = 0; j < graph.length; j++) {
                if (i == j) {
                    System.out.printf("%6s", "-");
                } else {
                    System.out.printf("%6d", graph[i][j]);
                }
            }

            System.out.println();
        }
    }

    static void compareAlgorithms() {

        System.out.println("\n========== ALGORITHM COMPARISON ==========");

        System.out.printf("%-20s %-20s%n",
                "Algorithm", "Time Complexity");

        System.out.println("--------------------------------------------");

        System.out.printf("%-20s %-20s%n",
                "Dijkstra", "O(V²)");

        System.out.printf("%-20s %-20s%n",
                "Bellman-Ford", "O(VE)");

        System.out.printf("%-20s %-20s%n",
                "Floyd-Warshall", "O(V³)");

        System.out.println("--------------------------------------------");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String[] routers = {"A", "B", "C", "D", "E"};

        int[][] graph = {
                {0, 4, 2, 0, 0},
                {4, 0, 0, 3, 0},
                {2, 0, 0, 1, 5},
                {0, 3, 1, 0, 2},
                {0, 0, 5, 2, 0}
        };

        int choice = 0;

        System.out.println("================================================");
        System.out.println("          NETWORK ROUTING ANALYZER");
        System.out.println("================================================");

        do {
            System.out.println("\n--------------- MENU ----------------");
            System.out.println("1. Display Network");
            System.out.println("2. Dijkstra Shortest Route");
            System.out.println("3. Bellman-Ford Shortest Route");
            System.out.println("4. Floyd-Warshall Analysis");
            System.out.println("5. Compare Algorithms");
            System.out.println("6. Exit");
            System.out.println("-------------------------------------");

            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("No valid menu choice was provided. Exiting.");
                break;
            }

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    displayNetwork(graph, routers);
                    break;

                case 2:
                    runShortestPath(sc, graph, routers, true);
                    break;

                case 3:
                    runShortestPath(sc, graph, routers, false);
                    break;

                case 4:
                    floydWarshall(graph, routers);
                    break;

                case 5:
                    compareAlgorithms();
                    break;

                case 6:
                    System.out.println(
                            "\nThank you for using Network Routing Analyzer!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 6);

        sc.close();
    }

    static void runShortestPath(Scanner sc, int[][] graph,
                                String[] routers, boolean useDijkstra) {

        System.out.print("Enter Source Router (A-E): ");

        if (!sc.hasNext()) {
            System.out.println("No source router provided.");
            
            return;
        }

        char sourceChar = sc.next().toUpperCase().charAt(0);

        System.out.print("Enter Destination Router (A-E): ");

        if (!sc.hasNext()) {
            System.out.println("No destination router provided.");
            return;
        }

        char destinationChar = sc.next().toUpperCase().charAt(0);

        int source = sourceChar - 'A';
        int destination = destinationChar - 'A';

        if (source < 0 || source >= routers.length
                || destination < 0 || destination >= routers.length) {

            System.out.println("Invalid router!");
            return;
        }

        if (useDijkstra) {
            dijkstra(graph, source, destination, routers);
        } else {
            bellmanFord(graph, source, destination, routers);
        }
    }
}
