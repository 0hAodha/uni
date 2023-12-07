public class Node {
    public int x; 
    public int y; 

    public int g;       // cost of getting from the starting node to this node 
    public int h;       // estimated heuristic cost of getting from this node to the target node 
    public int f;       // sum of g & h, the algorithm's best current estimate as to the total cost of travelling from the starting location to the target location via this node 
    public boolean closed = false; 
    public boolean open = false;

    public Node(int x, int y) {
        this.x = x; 
        this.y = y;
    }
}
