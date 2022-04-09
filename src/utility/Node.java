//adapted from https://stackabuse.com/graphs-in-java-a-star-algorithm/ by Darinka Zobenica
package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import utility.Pair.Path;

/**
 * Node class by Darinka Zobenica adapted for a 2D array of integers 
 * without diagonal movement and without an initial node web.
 * 
 * @author poirierk2
 */
public class Node implements Comparable<Node> {
    // Parent in the path
    public Node parent = null;

    public List<Edge> neighbors;

    // Evaluation functions
    public int f = Integer.MAX_VALUE;
    public int g = Integer.MAX_VALUE;
    public int weight;
    // Hardcoded heuristic
    public int h; 
    public Pair pos;

    Node(Pair pos, Pair target){ 	
    	this.h = Math.abs(target.x() - pos.x()) + Math.abs(target.y() - pos.y());
    	this.pos = pos;
    	this.neighbors = new ArrayList<>();
    }

    @Override
    public int compareTo(Node n) {
          return Integer.compare(this.f, n.f);
    }

    public static class Edge {
          Edge(int weight, Node node){
                this.weight = weight;
                this.node = node;
          }

          public int weight;
          public Node node;
    }

    public void addBranch(int weight, Node node){
          Edge newEdge = new Edge(weight, node);
          neighbors.add(newEdge);
    }

    public int heuristic(){
          return this.h;
    }
    
    public static Node aStar(Pair start, Pair target, int[][] mobilityMap){
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Node[][] nodes = new Node[mobilityMap.length][mobilityMap[0].length];

        Node first = nodes[start.x()][start.y()] = new Node(start, target);
        first.g = start.getFrom(mobilityMap);
        first.f = first.g + first.heuristic();
        openList.add(first);

        while(!openList.isEmpty()){
            Node n = openList.peek();
            if(n.pos.equals(target)){
                return n;
            }
            
            if(n.neighbors.size() == 0){
            	int[] offX = {1, 0, -1, 0};
        		int[] offY = {0, 1, 0, -1};
            	for (int i = 0; i < 4; i++) {
            		Pair offset = new Pair(offX[i], offY[i]);
            		try {
            			if (!(n.pos.getFrom(nodes, offset) == null)) {
            				n.addBranch(n.pos.getFrom(mobilityMap, offset), (Node) n.pos.getFrom(nodes, offset));
            			} else {
            				nodes[n.pos.x() + offX[i]][n.pos.y() + offY[i]] = new Node(n.pos.combine(offset), target);
            				n.addBranch(n.pos.getFrom(mobilityMap, offset), (Node) n.pos.getFrom(nodes, offset));
            			}
            		} catch (Exception e) {
            		
            		}
            	}
            }

            for(Node.Edge edge : n.neighbors){
                Node m = edge.node;
                int totalWeight = n.g + edge.weight;

                if(!openList.contains(m) && !closedList.contains(m)){
                    m.parent = n;
                    m.g = totalWeight;
                    m.f = m.g + m.heuristic();
                    m.weight = edge.weight;
                    openList.add(m);
                } else {
                    if(totalWeight < m.g){
                        m.parent = n;
                        m.g = totalWeight;
                        m.f = m.g + m.heuristic();

                        if(closedList.contains(m)){
                            closedList.remove(m);
                            openList.add(m);
                        }
                    }
                }
            }

            openList.remove(n);
            closedList.add(n);
        }
        return null;
    }

    public static void printPath(Node target){
    	Node n = target;

        if(n==null) {
        	System.out.println("No Path");
            return;
        }
        
        int index = 0;   
        while(n.parent != null) {
        	System.out.print("" + index + ":(" + n.pos.x() + "," + n.pos.y() + "), ");
        	n = n.parent;
            index++;
        }
        System.out.println("" + index + ":(" + n.pos.x() + "," + n.pos.y() + "), ");
    }
    
    public static Path getPath(Node target) {
    	Path path = new Path(); 
    	Node n = target;

        if(n==null) {
            return null;
        }
        
        while(n.parent != null) {
        	path.add(0, new Pair (n.pos, n.weight));
        	n = n.parent;
        }
        return path;
    }
    
    public static int moveCost(Path path) {
    	int output = 0;
    	for (int i = 0; i < path.size(); i++) {
    		output += path.get(i).getAux();
    	}
    	return output;
    }
}