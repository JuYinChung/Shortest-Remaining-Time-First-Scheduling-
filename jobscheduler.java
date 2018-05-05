package RedBlackTree;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;

import RedBlackTree.RedBlackTree.Node;
import java.util.Arrays;
public class jobscheduler {
	static RedBlackTree tree=new RedBlackTree();
	static heapsort_ heap=new heapsort_();
	static RedBlackTree.Node min = tree.Node(-1);
	
	@SuppressWarnings("static-access")
	public static void main(String[] args0) throws IOException
	{

		String filePath = args0[0];
        FileInputStream fin = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader br = new BufferedReader(reader);
        String line,read; 
        Hashtable<Integer,String> hash=new Hashtable<Integer,String>();
		try {
			while((line = br.readLine())!=null )
			{
			    read = line;
			    String[] readArray= new String[2];
			    readArray=read.split(":"); 
			    int timestamp= Integer.parseInt(readArray[0]);
			    hash.put(timestamp, readArray[1]);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int timer=0;
		int standard=5;
		int previoustimer = 0;
	
		FileWriter fw = new FileWriter("output_file.txt");
		 
		        


		while(timer<1153000)
		{   
			// this part is used to read the time and the command is the txt file.
			//It is a long part because you have to split all the strings in the txt files,
			// and call the match command and execute them when reaching the right time
			if(hash.containsKey(timer)) 
			{
				
				String command=hash.get(timer);
				String[] command1=new String[2];
				String[] command2=new String[2];
				String[] command3=new String[1];
				int x1,x2;
				command1=command.split("\\(");
				if(command1[0].equals(" NextJob"))
				{	
					command3=command1[1].split("\\)");
					x1=Integer.parseInt(command3[0]);
					//System.out.print("NextJob:"+x1+"\n");
					String output= NextJob(x1)+"\r\n";
					fw.write(output);
					fw.flush();
					
				}
				else if(command1[0].equals(" PreviousJob"))
				{	
					command3=command1[1].split("\\)");
					x1=Integer.parseInt(command3[0]);
					String output=PreviousJob(x1)+"\r\n";
					fw.write(output);
					fw.flush();
				}
				else 
				{  
					if(command1[0].equals(" PrintJob"))
					{   
						String test=command1[1];
						if(!test.contains(","))
						{	
							command3=command1[1].split("\\)");
							x1=Integer.parseInt(command3[0]);
							//System.out.print("Print1Job:"+x1+"\n");
							String output=PrintJob(x1)+"\r\n";
							fw.write(output);
							fw.flush();
						}
						else
						{	
							command2=command1[1].split(",");
							command3=command2[1].split("\\)");
							x1=Integer.parseInt(command2[0]);
							x2=Integer.parseInt(command3[0]);
							//System.out.print("Print2Job:"+x1+","+x2+"\n");
							String output=PrintJob(x1,x2)+"\r\n";
							fw.write(output);
							fw.flush();
						}
					}
					else if(command1[0].equals(" Insert"))
					{	
						if(heap.heapsize==0)previoustimer=timer;
						command2=command1[1].split(",");
						command3=command2[1].split("\\)");
						x1=Integer.parseInt(command2[0]);
						x2=Integer.parseInt(command3[0]);
						RedBlackTree.Node a = tree.Node(x1);
						Insert(a,x2);
						if(timer-previoustimer>5)previoustimer=timer;
					}
				}
				
			}
		
			
			if(heap.num.length!=0) 
			{   if(timer<4000) System.out.print("previoustimer"+previoustimer+"timer"+timer+"\n");
				if(timer-previoustimer==1)
				{  
					
					min=heap.num[0];
					int i=0;
					while(i<heap.num.length&&heap.num[i].time==min.time)
					{
						i++;
					}
					i--;
					Random random=new Random();				
					if(i>0)min=heap.num[random.nextInt(i)];
					int total=heap.num[0].totalTime;
					
					standard=Math.min(5, min.totalTime-min.time);
					
					//System.out.print("FREE:min.key: "+min.key+" min.time: "+min.time+" min.total: "+min.totalTime+"\n");
				}
				if(standard==0&&min.totalTime-min.time<5) 
				{
					//System.out.print("DELETE JOBID: "+min.key+" min.time: "+min.time+" min.total: "+min.totalTime+"\n");
					Delete(min);
					previoustimer=timer;
				}
				if(timer-previoustimer==standard)
				{ 
					if(min.totalTime-min.time<5) 
					{
						
						Delete(min);
					}
					else if(min.time<min.totalTime)
					{	
						heap.increaseTime(min);
						
					}
					previoustimer=timer;
				
					//System.out.print("FINISH:min.key: "+min.key+" min.time: "+min.time+" min.total: "+min.totalTime+"\n");
				}
				
			}
			
			timer++;
		}
	
		fw.close();
	}

	private static void Delete(Node node) {

        tree.Delete(node);
		heap.delete(node);
		
	
	}

	private static String PrintJob(int ID1, int ID2) {
		ArrayList<Node> list=tree.RangeSearch(ID1,ID2);
		
		if(list.isEmpty())return "(0,0,0)"+"\n";
		else 
		{	
			StringBuffer s=new StringBuffer("");
			for(int i=0;i<list.size();i++)
			{   
				s.append("("+list.get(i).key+","+list.get(i).time+","+list.get(i).totalTime+")") ;
			}
			
			s.append("\n");
			return s.toString();
		}
		
	}

	private static String PreviousJob(int ID) 
	{
		Node x=tree.PreSearch(ID);
		if(x.key==-1)return "(0,0,0)"+"\n";
		else return "("+x.key+","+x.time+","+x.totalTime+")"+"\n";
	}

	private static String NextJob(int ID) 
	{
		Node x=tree.NextSearch(ID);
		if(x.key==-1)return "(0,0,0)"+"\n";
		else return "("+x.key+","+x.time+","+x.totalTime+")"+"\n";
	}

	private static String PrintJob(int ID) 
	{
		Node x=tree.Search(ID);
		if(x.key==-1)return "(0,0,0)"+"\n";
		else return "("+x.key+","+x.time+","+x.totalTime+")"+"\n";
	}

	private static void Insert(Node node, int totalTime) 
	{
	
		tree.Insert(node, totalTime);
		heap.insert(node);
		
	}
}
