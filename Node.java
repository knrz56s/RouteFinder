package routefinder.algorithm;

public class Node{
    private Node prev;
    private int col, row;
    private int gCost, hCost, fCost;
    boolean startNode, endNode, wallNode, roadNode, checkedNode, pathNode;

    public Node(int col, int row){
        this.col = col; //x-axis
        this.row = row; //y-axis
    }

    public int getCol(){
        return col;
    }

    public int getRow(){
        return row;
    }

    public void setGCost(int gCost){
        this.gCost = gCost;
    }

    public int getGCost(){
        return this.gCost;
    }

    public void setHCost(int hCost){
        this.hCost = hCost;
    }

    public int getHCost(){
        return this.hCost;
    }

    public void setFCost(int fCost){
        this.fCost = fCost;
    }

    public int getFCost(){
        return this.fCost;
    }

    public void setStartingNode(){
        this.startNode = true;
    }

    public void setEndingNode(){
        this.endNode = true;
    }

    public void setWallNode(){
        this.wallNode = true;
    }

    public void setCheckedNode(){
        this.checkedNode = true;
    }

    public void setRoadNode(){
        this.roadNode = true;
    }
    
    public void setPrev(Node parent){
        this.prev = parent;
    }

    public Node getPrev(){
        return this.prev;
    }

    public void setPathNode(){
        this.pathNode = true;
    }

    public boolean getPathNode(){
        return this.pathNode;
    }
}