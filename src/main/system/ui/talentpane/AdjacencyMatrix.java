package main.system.ui.talentpane;

import java.util.Scanner;

public class AdjacencyMatrix {


    private final int[][] adjacency_matrix;


    public AdjacencyMatrix() {
        adjacency_matrix = new int[100][100];
        makeEdges();
    }

    public static void main(String[] args) {

        int v, e, count = 1, to = 0, from = 0;

        Scanner sc = new Scanner(System.in);

        AdjacencyMatrix graph;

        try {

            System.out.println("Enter the number of vertices: ");

            v = sc.nextInt();

            System.out.println("Enter the number of edges: ");

            e = sc.nextInt();


            graph = new AdjacencyMatrix();


            System.out.println("Enter the edges: <to> <from>");

            while (count <= e) {

                to = sc.nextInt();

                from = sc.nextInt();


                graph.makeEdge(to, from);

                count++;
            }


            System.out.println("The adjacency matrix for the given graph is: ");

            System.out.print("  ");

            for (int i = 1; i <= v; i++)

                System.out.print(i + " ");

            System.out.println();


            for (int i = 1; i <= v; i++) {

                System.out.print(i + " ");

                for (int j = 1; j <= v; j++)

                    System.out.print(graph.getEdge(i, j) + " ");

                System.out.println();
            }
        } catch (Exception E) {

            System.out.println("Somthing went wrong");
        }


        sc.close();
    }

    public void makeEdge(int to, int from) {
        adjacency_matrix[to][from] = 1;
    }

    public int getEdge(int to, int from) {
        return adjacency_matrix[to][from];
    }

    private void makeEdges() {
        makeEdge(2, 8);
        makeEdge(2, 9);
        makeEdge(9, 14);
        makeEdge(14, 10);
        makeEdge(10, 11);
        makeEdge(12, 11);
        makeEdge(8, 13);
        makeEdge(13, 12);

        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
        makeEdge(2, 8);
    }
}

