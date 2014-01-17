package readFile;
import java.io.*;
import java.util.*;

public class WebSearch {
	public static void main(String [] args) throws IOException{
		BufferedReader userinput = new BufferedReader(new InputStreamReader(System.in));
		ReadFile rf =  new ReadFile(args[0]); 					// going to call the ReadFile class with whatever "args[0]" is equal to
		Gnode adjLL;
		Gnode[] masterArr= new Gnode[rf.getNumberOfWebpages()]; // creating new array of Gnodes (adjacency linked list)
		String choice;
		String origin;
		String destination;
		int flag=0;
		int counter = 0;
		int result;
		int dos;
		int dosflag;
		int mark,mark2;

// Create Adjacency Linked List from args[0]
		
		for(int i = 0; i<masterArr.length; i++){//create an array of Gnodes WITHOUT linked list connections 
			int num = i;
			String name1 = rf.getPageTitle(num); //saves the name of the Node
			ArrayList<String> phrases1 = rf.getKeyPhrases(i); //saves the list of phrases
			Gnode node = new Gnode(num, name1, phrases1, null, null, false); //must call Gnode to create a node within 
			masterArr[i] = node; 
			
		} 
		for (int i = 0; i<masterArr.length; i++){ //goes through all elements in the array
			Edge edg = new Edge();
			masterArr[i].edges=edg;
			ArrayList<Integer> temp = rf.getOutLinks(i);
			for (int j=0; j<rf.getNumberOfOutLinks(i); j++){ //for each element in the array, set Edge
				Edge tempedg = new Edge();
				edg.node=masterArr[temp.get(j)];
				if (j==rf.getNumberOfOutLinks(i)-1)
					edg=edg.next;
				else{
					edg.next=tempedg;
					edg=edg.next;
				}
			}
		}
		for ( int i=0; i<(rf.getNumberOfWebpages()-1);i++){
			masterArr[i].nextNode=masterArr[i+1];
		}
		adjLL=masterArr[0];
		
// Start Output
		
		System.out.println("Welcome to WebSearch!");
		while(true){
			System.out.println();
			System.out.println("Choose an option or type 'quit' to quit:");
			System.out.println("1. Print Graph");
			System.out.println("2. Degrees of Separation");
			System.out.println();
			
			choice=userinput.readLine(); 
			System.out.println();
			
			adjLL=masterArr[0];
			if (choice.equals("1")){ //takes care of printing graph choice
				
// Start Graph Representation
				System.out.println("Printing Graph...");
				while (adjLL!=null){
					System.out.print(adjLL.number+" (");
					for (int i=0; i<adjLL.phrase.size(); i++){
						System.out.print(adjLL.phrase.get(i).trim());
						if (i==adjLL.phrase.size()-1)
							System.out.print("): ");
						else
							System.out.print(", ");
					}
					
					Edge tempadjLLedges;
					tempadjLLedges = adjLL.edges;
					while (tempadjLLedges!=null){
						if (flag==1)
							System.out.print(",");
						System.out.print(tempadjLLedges.node.number);
						tempadjLLedges=tempadjLLedges.next; 
						flag=1;
					}
					flag=0;
					adjLL=adjLL.nextNode;
					System.out.println();
				}			
			}
			else if (choice.equals("2")){
				
// Start Degrees of Separation
				System.out.print("Title of origin page > ");
				origin = userinput.readLine();
				counter=0;
				while(!origin.equals(masterArr[counter].name)){
					counter++;
				}
				Gnode startNode = masterArr[counter];				
				counter = 0;
				System.out.print("Title of destination page > ");
				destination = userinput.readLine();
				while(!destination.equals(masterArr[counter].name)){
					counter++;
				}
				Gnode endNode = masterArr[counter];		

				result=0;
				Queue<Gnode> queue = new LinkedList<Gnode>();
				Queue<Integer> count = new LinkedList<Integer>();
				Queue<Integer> count2 = new LinkedList<Integer>();
				queue.add(startNode);
				dos=0;
				dosflag=0;
				mark=0;
				mark2=0;
				
				//System.out.println(result+""+ dos);

				if (origin == destination)
					result=0;
				else if (origin!=destination){
					while (!queue.isEmpty()){
						Gnode temp;
						temp=queue.remove();
						Edge temp2= new Edge();
						temp2=temp.edges;
						while (temp2!=null){
							if (endNode == temp2.node){
								dosflag=1;
							}
							if (temp2.node.visited==false){
								temp2.node.visited=true;
								queue.add(temp2.node);
								dos++;
							}
							temp2=temp2.next;
						}
						count.add(dos);
						if (dosflag==1)
							break;
						dos=0;
					}
					//while (!count.isEmpty()){
						mark=count.remove();
						//mark2=0;
						result=bfs(count,mark);

						//mark=0;
						//count2.add(1);
					//}
					//while(!count2.isEmpty()){
					//	result++;
					//	count2.remove();
					//}
					//result++;
				}
				if (dosflag==0)
					result=-1;
				for (int i=0;i<8;i++)
					masterArr[i].visited=false;
				//System.out.println(result+""+dos);
				System.out.println("Degrees of separation between pages "+origin+" and "+destination+": "+result);
			}
			else if (choice.equals("quit")){ //quit option
				System.exit(0);
			}
		}	
	}
	static public int bfs(Queue<Integer> thru,int X){
		int sum=0;
		int recur=0;
		int hold=0;
		int tick=0;
		for(int i=0;i<X;i++){
			if (!thru.isEmpty()){
				tick=thru.remove();
				if (tick==0)
					hold++;
				else
					recur=tick+recur;
			}
		}
		if (hold-recur>=0)
			sum=1;
		else
			sum=1+bfs(thru,recur);
		return sum;
	}
}