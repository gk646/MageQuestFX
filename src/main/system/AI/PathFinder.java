package main.system.AI;

import gameworld.maps.OverWorld;
import main.MainGame;

import java.util.ArrayList;

public class PathFinder {
    MainGame mg;
    Node[][] nodes;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(MainGame mg) {
        this.mg = mg;
        instantiateNodes();
    }

    public void instantiateNodes() {
        nodes = new Node[OverWorld.worldSize.x][OverWorld.worldSize.y];
        for (int i = 0; i < OverWorld.worldSize.x; i++) {
            for (int b = 0; b < OverWorld.worldSize.y; b++) {
                nodes[i][b] = new Node(i, b);

            }
        }

    }

    public void resetNodes() {
        for (int i = 0; i < OverWorld.worldSize.x; i++) {
            for (int b = 0; b < OverWorld.worldSize.y; b++) {
                nodes[i][b].open = false;
                nodes[i][b].checked = false;
                nodes[i][b].solid = false;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();
        startNode = nodes[startCol][startRow];
        currentNode = startNode;
        goalNode = nodes[goalCol][goalRow];
        for (int i = 0; i < OverWorld.worldSize.x; i++) {
            for (int b = 0; b < OverWorld.worldSize.y; b++) {
                int tileNum = OverWorld.worldData[i][b];
                if (mg.wRender.tileStorage[tileNum].collision) {
                    nodes[i][b].solid = true;
                }
                //Set cost
                getCost(nodes[i][b]);

            }
        }

    }

    public void getCost(Node node) {
        //G cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;
        //H cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        //F cost
        node.fCost = node.gCost + node.hCost;

    }

    public boolean search() {
        while (!goalReached && step < 2000) {

            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.checked = true;
            openList.remove(currentNode);


            if (row - 1 >= 0) {
                openNode(nodes[col][row - 1]);
            }
            if (col - 1 >= 0) {
                openNode(nodes[col - 1][row]);
            }
            if (row + 1 < OverWorld.worldSize.y) {
                openNode(nodes[col][row + 1]);
            }
            if (col + 1 < OverWorld.worldSize.x) {
                openNode(nodes[col + 1][row]);
            }

            int bestNodesIndex = 0;
            int bestNodesfCost = 999;

            for (int i = 0; i < openList.size(); i++) {

                //check f cost
                if (openList.get(i).fCost < bestNodesfCost) {
                    bestNodesIndex = i;
                    bestNodesfCost = openList.get(i).fCost;
                }
                //F cost equal check
                else if (openList.get(i).fCost == bestNodesfCost) {
                    if (openList.get(i).gCost < openList.get(bestNodesIndex).gCost)
                        bestNodesIndex = i;
                }
            }
            if (openList.size() == 0) {
                break;
            }

            currentNode = openList.get(bestNodesIndex);

            if(currentNode == goalNode){
                goalReached = true;
                trackPath();
            }
        }
        return true;
    }
    public void trackPath(){
        Node current = goalNode;

        while(current != startNode){
            pathList.add(0,current);
            current = current.parent;
        }
    }

    public void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
    public void searchPath(int goalCol, int goalRow){

    }
}
