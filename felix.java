import java.io.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class Solution {
	
	//internal graph class
	static class Graph {
		int V; //number of vertices in graph
		ArrayList<Integer> adj[]; //graph is an array of adjacency lists
		int indegree[]; //list for keeping track of in-degrees
		
		//constructor
		public Graph(int V) {
			this.V = V;
			//define the size of array as number of vertices
			adj = new ArrayList[V+1];
			indegree = new int[V+1];
			//create a new list for each vertex such that adjacent nodes can be stored
			for (int i = 1; i <= V; i++) {
				adj[i] = new ArrayList<Integer>();
			}
		}
	}
	
	//add edge to a directed graph
	static void addEdge(Graph graph, int from, int to) {
		graph.adj[from].add(to);
	}
	
	//calculate indegrees at all vertices
	static void indegree(Graph graph) {
		for (int i = 1; i <= graph.V; i++) {
            ArrayList<Integer> temp = (ArrayList<Integer>) graph.adj[i];
            for (int node : temp) {
                graph.indegree[node]++;
            }
		}
	}
    
	static ArrayList<Integer> lexTopSort(Graph graph) {
        
		PriorityQueue<Integer> zeroVertices = new PriorityQueue<Integer>();
		for (int i = 1; i <= graph.V; i++) {
			if (graph.indegree[i] == 0) {
				zeroVertices.add(i);
			}
		}
		
		ArrayList<Integer> orderedVertices = new ArrayList<Integer>();
		while (zeroVertices.size() != 0) {
            int nextVertex = zeroVertices.poll();
            orderedVertices.add(nextVertex);
            for (int i = 0; i < graph.adj[nextVertex].size(); i++){
                int neighbor = graph.adj[nextVertex].get(i);
                graph.indegree[neighbor]--;
                if (graph.indegree[neighbor] == 0) {
                    zeroVertices.add(neighbor);
                }
            }
        }
        return orderedVertices;
	}
    
	
	public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			//read in size of n
			String[] arr = in.readLine().split(" ");
			int n = Integer.parseInt(arr[0]);
			int m = Integer.parseInt(arr[1]);
			Graph graph = new Graph(n);
			for (int i = 0; i < m; i++) {
				String[] relations = in.readLine().split(" ");
				addEdge(graph, Integer.parseInt(relations[0]), Integer.parseInt(relations[1]));
            }
			//calculate each indegree
			indegree(graph);
            ArrayList<Integer> answer = lexTopSort(graph);
            if (answer.size() < n) {
                System.out.print("-1");
            } else {
                for (int i = 0; i < answer.size(); i++) {
                    System.out.print(answer.get(i) + " ");
                }
            }
		} catch (IOException e) {
			System.err.println("IO Exception");
		}
    }
}