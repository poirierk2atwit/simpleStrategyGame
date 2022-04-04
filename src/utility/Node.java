//adapted from https://stackabuse.com/graphs-in-java-a-star-algorithm/ by Darinka Zobenica
package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

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
    // Hardcoded heuristic
    public int h; 
    public int[] pos;

    Node(int[] pos, int[] target){ 	
    	this.h = Math.abs(target[0] - pos[0]) + Math.abs(target[1] - pos[1]);
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
    
    public static Node aStar(int[] start, int[] target, int[][] mobilityMap){
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>();

        Node first = new Node(start, target);
        first.g = mobilityMap[start[0]][start[1]];
        first.f = first.g + first.heuristic();
        openList.add(first);

        while(!openList.isEmpty()){
            Node n = openList.peek();
            if(n.pos == target){
                return n;
            }
            
            if(!openList.contains(n) && !closedList.contains(n)){
            	for (int i = 0; i < 4; i++) {
            		int[] x = {1, 0, -1, 0};
            		int[] y = {0, 1, 0, -1};
            		try {
            			n.addBranch(mobilityMap[n.pos[0] + x[i]][n.pos[1] + y[i]], new Node(new int[]{n.pos[0] + x[i], n.pos[1] + y[i]}, target));
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
        	System.out.print("" + index + ":(" + n.pos[0] + "," + n.pos[1] + "), ");
        	n = n.parent;
            index++;
        }
        System.out.println("" + index + ":(" + n.pos[0] + "," + n.pos[1] + "), ");
    }
    
    public static ArrayList<int[]> getPath(Node target) {
    	ArrayList<int[]> coordiantes = new ArrayList<int[]>(); 
    	Node n = target;

        if(n==null) {
            return null;
        }
        
        while(n.parent != null) {
        	coordiantes.add(0, new int[] {n.pos[0], n.pos[1]});
        	n = n.parent;
        }
        return coordiantes;
    }
}