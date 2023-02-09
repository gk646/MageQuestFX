package main.system.ui.talentpane;

import java.util.ArrayList;

public class AdjacencyMatrix {


    private final int[][] adjacency_matrix;
    public static final ArrayList<int[]> edge_list = new ArrayList<>();


    public AdjacencyMatrix() {
        adjacency_matrix = new int[100][100];
        makeEdges();
    }

    public void makeEdge(int to, int from) {
        adjacency_matrix[to][from] = 1;
        int[] edge = new int[2];
        edge[0] = to;
        edge[1] = from;
        edge_list.add(edge);
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
        makeEdge(11, 10);
        makeEdge(12, 11);
        makeEdge(8, 13);
        makeEdge(12, 13);
        makeEdge(13, 12);
        makeEdge(11, 12);
        makeEdge(3, 16);
        makeEdge(16, 17);
        makeEdge(17, 18);
        makeEdge(18, 19);
        makeEdge(11, 15);
        makeEdge(13, 20);
        makeEdge(20, 21);
        makeEdge(21, 22);
        makeEdge(19, 20);
        makeEdge(5, 6);
        makeEdge(6, 23);
        makeEdge(23, 24);
        makeEdge(24, 25);
        makeEdge(25, 26);
        makeEdge(26, 27);
        makeEdge(3, 27);
        makeEdge(25, 28);
        makeEdge(28, 41);
        makeEdge(41, 42);
        makeEdge(42, 43);
        makeEdge(43, 44);
        makeEdge(44, 45);
        makeEdge(42, 49);
        makeEdge(43, 49);
        makeEdge(49, 50);
        makeEdge(50, 51);
        makeEdge(51, 52);
        makeEdge(49, 54);
        makeEdge(54, 53);
        makeEdge(53, 52);
        makeEdge(52, 38);
        makeEdge(52, 37);
        makeEdge(37, 36);
        makeEdge(36, 35);
        makeEdge(35, 34);

        makeEdge(31, 30);
        makeEdge(30, 34);
        makeEdge(34, 35);
        makeEdge(35, 36);
        makeEdge(36, 37);
        makeEdge(37, 38);
        makeEdge(38, 39);
        makeEdge(39, 40);
        makeEdge(40, 55);
        makeEdge(55, 58);
        makeEdge(59, 57);
        makeEdge(55, 59);
        makeEdge(56, 59);
        makeEdge(57, 58);
        makeEdge(57, 59);
        makeEdge(39, 55);
        makeEdge(32, 33);
        makeEdge(33, 34);
    }
}

