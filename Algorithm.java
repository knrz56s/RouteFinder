package routefinder.algorithm;
import java.util.ArrayList;

public class Algorithm{
    private int[][] imageArray;
    private int maxRow, maxCol;
    private Node[][] node;
    private Node startNode, endNode, currentNode;

    private ArrayList<Node> nodeList = new ArrayList<>();
    private ArrayList<Node> visitedList = new ArrayList<>();

    private boolean pathFound = false;
    private int step;

    public Algorithm(int[][] imageArray){
        this.imageArray = imageArray;
        this.maxRow = imageArray[0].length;
        this.maxCol = imageArray.length;
        node = new Node[maxCol][maxRow];
    }

    public void convertIntToMaze(Node startNode, Node endNode){
        int row = 0;
        int col = 0;
        while(col < maxCol && row < maxRow){
            
            if(startNode.getCol() == col && startNode.getRow() == row){
                this.startNode = startNode;
                node[col][row] = startNode;
                currentNode = startNode;
            }
            else if(endNode.getCol() == col && endNode.getRow() == row){
                this.endNode = endNode;
                node[col][row] = endNode;
            }
            else{
                node[col][row] = new Node(col, row);
                if(imageArray[col][row] == 0) node[col][row].setWallNode();
            }
            
            row++;
            
            if(row == maxRow){
                row = 0;
                col++;
            }
        }
        setCostNode();
    }

    private void setCostNode(){
        int col = 0;
        int row = 0;

        while(col < maxCol && row < maxRow){
            getCost(node[col][row]);
            row++;

            if(row == maxRow){
                row = 0;
                col++;
            }
        }
    }

    private void getCost(Node node){
        int xDist = Math.abs(node.getCol() - startNode.getCol());
        int yDist = Math.abs(node.getRow() - startNode.getRow());
        node.setGCost(xDist + yDist);

        xDist = Math.abs(node.getCol() - endNode.getCol());
        yDist = Math.abs(node.getRow() - endNode.getRow());
        node.setHCost(xDist + yDist);

        node.setFCost(node.getGCost() + node.getHCost());
    }

    public void searchPath(){
        step = 0;
        while(pathFound == false && step < (maxRow * maxCol)){
            int col = currentNode.getCol();
            int row = currentNode.getRow();

            currentNode.setCheckedNode();
            visitedList.add(currentNode);
            nodeList.remove(currentNode);

            if(col - 1 >= 0) checkNode(node[col - 1][row]); //up
            if(row + 1 < maxRow && col - 1 >= 0) checkNode(node[col - 1][row + 1]); //upper-right
            if(row + 1 < maxRow) checkNode(node[col][row + 1]); //right
            if(row + 1 < maxRow && col + 1 < maxCol) checkNode(node[col + 1][row + 1]); //lower-right
            if(col + 1 < maxCol) checkNode(node[col + 1][row]); //down
            if(row - 1 >= 0 && col + 1 < maxCol) checkNode(node[col + 1][row - 1]); //lower-left
            if(row - 1 >= 0) checkNode(node[col][row - 1]); //left
            if(row - 1 >= 0 && col - 1 >= 0) checkNode(node[col - 1][row - 1]); //upper-left

            int bestNodeIndex = 0;
            int bestNodeFCost = Integer.MAX_VALUE;

            for(int i = 0; i < nodeList.size(); i++){
                if(nodeList.get(i).getFCost() < bestNodeFCost){
                    bestNodeIndex = i;
                    bestNodeFCost = nodeList.get(i).getFCost();
                }

                else if(nodeList.get(i).getFCost() == bestNodeFCost){
                    if(nodeList.get(i).getGCost() < nodeList.get(bestNodeIndex).getGCost()){
                    bestNodeIndex = i;
                    }
                }
            }

            if(nodeList.size() > 0) currentNode = nodeList.get(bestNodeIndex);
            if(currentNode == endNode){
                pathFound = true;
            }
            step++;
        }
        if(pathFound){
            backtrack();
            for(int y = 0; y < maxCol; y++){
                for(int x = 0; x < maxRow; x++){
                    if(node[y][x].getPathNode()) imageArray[y][x] = 2;
                }
            }
        }
    }

    private void checkNode(Node node){
        if(node.roadNode == false && node.checkedNode == false && node.wallNode == false){
            node.setRoadNode();
            node.setPrev(currentNode);
            nodeList.add(node);
        }
    }

    private void backtrack(){
        Node current = endNode;

        while(current != startNode){
            current = current.getPrev();
            if(current != startNode){
                current.setPathNode();
            }
        }
    }
    
    public boolean checkPathFound(){
        return pathFound;
    }

    public int[][] pathResult(){
        return imageArray;
    }
}