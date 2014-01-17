package readFile;

import java.util.ArrayList;

class Gnode {
	
	int number; 
	String name;
	ArrayList <String> phrase;
	Edge edges;
	Gnode nextNode;
	boolean visited;
	
	public Gnode(int num, String nam, ArrayList<String> phr, Edge edg, Gnode next, boolean vis){
		number = num;
		name = nam;
		phrase = phr;
		edges = edg; 
		nextNode = next; 
		visited = vis;
		
		
	}

	public Gnode(int num, String nam, ArrayList<String> phr, Object edg, //constructor to pass null 
			Object next, boolean vis) 
	{
		number = num;
		name = nam;
		phrase = phr;
		edges = null; 
		nextNode = null;
		visited = vis;
	}
}