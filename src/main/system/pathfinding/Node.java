package main.system.pathfinding;


public class Node {
    public final int col;
    public final int row;
    Node parent;
    int gCost;
    int hCost;
    int fCost;
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }
}
