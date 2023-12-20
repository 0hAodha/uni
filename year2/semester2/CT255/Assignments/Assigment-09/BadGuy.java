import java.awt.*;
import java.util.*;

public class BadGuy {
    /* list of nodes to which the algorithm has already found a route (i.e., one of its conencted neighbours has been expanded)
     * but have not themselves been expanded */ 
    LinkedList<Node> openlist = new LinkedList<Node>(); 

    // list of nodes that have been expanded and which therefore should not be revisited
    LinkedList<Node> closedlist = new LinkedList<Node>(); 

    Node[][] allnodes = new Node[40][40] ;  // array of all the nodes 

    Stack<Node> finalpath = new Stack<Node>();

	Image myImage;
	int x=0,y=0;
	boolean hasPath=false;

	public BadGuy( Image i ) {
		myImage=i;
		x = 30;
		y = 10;
	}
	
	public void reCalcPath(boolean map[][],int targx, int targy) {
        System.out.println();
        System.out.println("recalculating path");
        hasPath = false; 
        openlist.clear(); 
        closedlist.clear(); 
        finalpath.clear(); 

        // looping through map[][], generating each node, and marking each wall node as closed 
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                allnodes[i][j] = new Node(i, j);    // generating node 

                if (map[i][j]) {
                    allnodes[i][j].closed = true; 
                    allnodes[i][j].open = false; 

                    closedlist.add(allnodes[i][j]); 
                }
            }
        }

        // calculate f,g,h for the starting node and set to open 
        Node starting = allnodes[x][y]; 
        starting.g = 0; 
        starting.h = targx - x + targy - y; // manhattan distance
        starting.f = starting.g + starting.h; 
        starting.open = true;
        starting.closed = false;
        openlist.add(starting);


        // looping while a path has not been found 
        // end condition: if a neighbour is the target, or if there are no open nodes
        while (!hasPath) {
            // breaking if there are no open nodes 
            if (openlist.size() == 0) {
                break;
            }

            /* progress is made by identifying the most promising node in the open list (i.e., the one with lowest f value) and
             * expanding it by adding each of its connected neighbours to the open list, unless they are already closed. */

            // looping through open list to find most promising node 
            Node mostpromising = openlist.get(0); 
            for (int i = 1; i < openlist.size(); i++) {
                if (openlist.get(i).f < mostpromising.f) {
                    mostpromising = openlist.get(i);
                }
            }

            int mx = mostpromising.x; 
            int my = mostpromising.y; 

            // expanding the most promising node by adding each of its connected neighbours to the open list, unless they are already closed 
            // as nodes are added to the open list, their f, g, h, & parent values are recorded
            // the g value of a node is equal to the g value of its parent + the cost of moving from the parent to the node itself. t

            // as nodes are expanded, they are moved to the closed list
            mostpromising.open = false; 
            mostpromising.closed = true; 
            closedlist.add(mostpromising);
            // openlist.remove(mostpromising);

            // northwest neighbour
            if (mx-1 >= 0 && my-1 >= 0 && !allnodes[mx-1][my-1].closed) {
                // checking if this node is the target node, and breaking if so 
                if (mx-1 == targx && my-1 == targy) {
                    allnodes[mx-1][my-1].open = true; 
                    allnodes[mx-1][my-1].closed = false; 
                    closedlist.remove(allnodes[mx-1][my-1]);
                    openlist.add(allnodes[mx-1][my-1]);
                    break;
                }

                if (!allnodes[mx-1][my-1].open) {
                    allnodes[mx-1][my-1].g = mostpromising.g + 1;
                    allnodes[mx-1][my-1].h = targx - mx + targy - my; 
                    allnodes[mx-1][my-1].f = allnodes[mx-1][my-1].g + allnodes[mx-1][my-1].h;
                    allnodes[mx-1][my-1].open = true; 
                    allnodes[mx-1][my-1].closed = false; 
                    openlist.add(allnodes[mx-1][my-1]);
                }
            }

            // north neighbour
            if (my-1 >= 0 && !allnodes[mx][my-1].closed) {
                // checking if this node is the target node, and breaking if so 
                if (mx == targx && my-1 == targy) {
                    allnodes[mx][my-1].open = true; 
                    allnodes[mx][my-1].closed = false; 
                    closedlist.remove(allnodes[mx][my-1]);
                    openlist.add(allnodes[mx][my-1]);
                    break;
                }

                if (!allnodes[mx][my-1].open) {
                    allnodes[mx][my-1].g = mostpromising.g + 1;
                    allnodes[mx][my-1].h = targx - mx + targy - my; 
                    allnodes[mx][my-1].f = allnodes[mx][my-1].g + allnodes[mx][my-1].h;
                    allnodes[mx][my-1].open = true; 
                    allnodes[mx][my-1].closed = false; 
                    openlist.add(allnodes[mx][my-1]);
                }
            }

            // northeast neighbour
            if (mx+1 < 40 && my-1 >= 0 && !allnodes[mx+1][my-1].closed) {
                // checking if this node is the target node, and breaking if so 
                if (mx+1 == targx && my-1 == targy) {
                    allnodes[mx+1][my-1].open = true; 
                    allnodes[mx+1][my-1].closed = false; 
                    closedlist.remove(allnodes[mx+1][my-1]);
                    openlist.add(allnodes[mx+1][my-1]);
                    break;
                }

                if (!allnodes[mx+1][my-1].open && !allnodes[mx+1][my-1].closed) {
                    allnodes[mx+1][my-1].g = mostpromising.g + 1;
                    allnodes[mx+1][my-1].h = targx - mx+1 + targy - my; 
                    allnodes[mx+1][my-1].f = allnodes[mx+1][my-1].g + allnodes[mx+1][my-1].h;
                    allnodes[mx+1][my-1].open = true; 
                    allnodes[mx+1][my-1].closed = false; 
                    openlist.add(allnodes[mx+1][my-1]);
                }
            }

            // west neighbour
            if (mx-1 >= 0 && !allnodes[mx-1][my].closed) {
                // checking if this node is the target node, and breaking if so 
                if (mx-1 == targx && my == targy) {
                    allnodes[mx-1][my].open = true; 
                    allnodes[mx-1][my].closed = false; 
                    closedlist.remove(allnodes[mx-1][my]);
                    openlist.add(allnodes[mx-1][my]);
                    break;
                }

                if (!allnodes[mx-1][my].open && !allnodes[mx-1][my].closed) {
                    allnodes[mx-1][my].g = mostpromising.g + 1;
                    allnodes[mx-1][my].h = targx - mx + targy - my; 
                    allnodes[mx-1][my].f = allnodes[mx-1][my].g + allnodes[mx-1][my].h;
                    allnodes[mx-1][my].open = true; 
                    allnodes[mx-1][my].closed = false; 
                    openlist.add(allnodes[mx-1][my]);
                }
            }

            // east neighbour
            if (mx+1 < 40 && !allnodes[mx+1][my].closed) {
                // checking if this node is the target node, and breaking if so 
                if (mx+1 == targx && my == targy) {
                    allnodes[mx+1][my].open = true; 
                    allnodes[mx+1][my].closed = false; 
                    closedlist.remove(allnodes[mx+1][my]);
                    openlist.add(allnodes[mx+1][my]);
                    break;
                }

                if (!allnodes[mx+1][my].open) {
                    allnodes[mx+1][my].g = mostpromising.g + 1;
                    allnodes[mx+1][my].h = targx - mx+1 + targy - my; 
                    allnodes[mx+1][my].f = allnodes[mx+1][my].g + allnodes[mx+1][my].h;
                    allnodes[mx+1][my].open = true; 
                    allnodes[mx+1][my].closed = false; 
                    openlist.add(allnodes[mx+1][my]);
                }
            }

            // southwest neighbour
            if (mx-1 >= 0 && my+1 < 40 && !allnodes[mx-1][my+1].closed) {
                // checking if this node is the target node, and breaking if so 
                if (mx-1 == targx && my+1 == targy) {
                    allnodes[mx-1][my+1].open = true; 
                    allnodes[mx-1][my+1].closed = false; 
                    closedlist.remove(allnodes[mx-1][my+1]);
                    openlist.add(allnodes[mx-1][my+1]);
                    break;
                }

                if (!allnodes[mx-1][my+1].open) {
                    allnodes[mx-1][my+1].g = mostpromising.g + 1;
                    allnodes[mx-1][my+1].h = targx - mx + targy - my; 
                    allnodes[mx-1][my+1].f = allnodes[mx-1][my+1].g + allnodes[mx-1][my+1].h;
                    allnodes[mx-1][my+1].open = true; 
                    allnodes[mx-1][my+1].closed = false; 
                    openlist.add(allnodes[mx-1][my+1]);
                }
            }

            // south neighbour
            if (my+1 < 40 && !allnodes[mx][my+1].closed) {
                // checking if this node is the target node, and breaking if so 
                if (mx == targx && my+1 == targy) {
                    allnodes[mx][my+1].open = true; 
                    allnodes[mx][my+1].closed = false; 
                    closedlist.remove(allnodes[mx][my+1]);
                    openlist.add(allnodes[mx][my+1]);
                    break;
                }

                if (!allnodes[mx][my+1].open) { 
                    allnodes[mx][my+1].g = mostpromising.g + 1;
                    allnodes[mx][my+1].h = targx - mx + targy - my; 
                    allnodes[mx][my+1].f = allnodes[mx][my+1].g + allnodes[mx][my+1].h;
                    allnodes[mx][my+1].open = true; 
                    allnodes[mx][my+1].closed = false; 
                    openlist.add(allnodes[mx][my+1]);
                }
            }

            // southeast neighbour 
            if (mx+1 < 40 && my+1 < 40 && !allnodes[mx+1][my+1].closed) {
                // checking if this node is the target node, and breaking if so 
                if (mx+1 == targx && my+1 == targy) {
                    allnodes[mx+1][my+1].open = true; 
                    allnodes[mx+1][my+1].closed = false; 
                    closedlist.remove(allnodes[mx+1][my+1]);
                    openlist.add(allnodes[mx+1][my+1]);
                    break;
                }

                if (!allnodes[mx+1][my+1].open) {
                    allnodes[mx+1][my+1].g = mostpromising.g + 1;
                    allnodes[mx+1][my+1].h = targx - mx+1 + targy - my; 
                    allnodes[mx+1][my+1].f = allnodes[mx+1][my+1].g + allnodes[mx+1][my+1].h;
                    allnodes[mx+1][my+1].open = true; 
                    allnodes[mx+1][my+1].closed = false; 
                    openlist.add(allnodes[mx+1][my+1]);
                }
            }
        }

        // generate final path by pushing target onto stack, followed by its parent in closedlist, ..., followed by start node

        for (int i = openlist.size()-1; i >= 0; i--) {
            System.out.println("pushing x=" + openlist.get(i).x + " y =" + openlist.get(i).y);
            finalpath.push(openlist.get(i));
        }

        hasPath = true;
        return;
	}

	public void move(boolean map[][],int targx, int targy) {
		if (hasPath) {
            Node nextnode = finalpath.pop(); 
            System.out.println("next node x=" + nextnode.x + " y=" + nextnode.y);
            x = nextnode.x;
            y = nextnode.y;
		}
		else {
			// no path known, so just do a dumb 'run towards' behaviour
			int newx=x, newy=y;
			if (targx<x)
				newx--;
			else if (targx>x)
				newx++;
			if (targy<y)
				newy--;
			else if (targy>y)
				newy++;
            if ((newx < 40 && newx >= 0 && newy < 40 && newy >=0) && !map[newx][newy]) {
				x=newx;
				y=newy;
			}
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(myImage, x*20, y*20, null);
	}
}
