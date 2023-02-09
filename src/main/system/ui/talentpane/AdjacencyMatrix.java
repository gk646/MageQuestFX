package main.system.ui.talentpane;

public class AdjacencyMatrix {


    private final int[][] adjacency_matrix;


    public AdjacencyMatrix() {
        adjacency_matrix = new int[100][100];
        makeEdges();
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

