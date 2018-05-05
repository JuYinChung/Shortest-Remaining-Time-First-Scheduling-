package RedBlackTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipEntry;

import javax.naming.directory.SearchControls;

public class RedBlackTree {
	public class Node 
	{
		 int key = -1, color = 1;//BLACK=1, RED=0;
	     Node left = nil, right = nil, parent = nil;
	     int time=0;
	     int totalTime=-1;
	     Node(int key) 
	     {
	         this.key = key;
	     } 
	}
	public Node Node(int number) { return new Node(number); } 
	//Initialize Root
	final Node nil= new Node(-1);
	static Node x_parent;
	Node root = nil;
	static int count = 0;
	static int deletecount = 0;

	ArrayList<Node> list = new ArrayList<Node>();

	public Node PreSearch(int key) 
	{   
	    Node x=Search(key);
	    Node y=x.parent;
	    if(x==nil)
	    {	System.out.print("x_parent.key"+x_parent.key+"\n");
	    	if(x_parent.key>key)
	    		x=Search(x_parent.key);
	    	else 
	    		return x_parent;
	    }
		if(x.left!=nil)
			return maximum(x.left);
		else if(x==root)
			return nil;
		else if (x.key > x.parent.key)
			return x.parent;
		else 
			return nil;
	}
	public Node NextSearch(int key) 
	{   
	    Node x=Search(key);
	    Node y=x.parent;
	    System.out.print("x"+x.key+"\n");
	    if(x.key==-1)
	    {	System.out.print("x_parent.key"+x_parent.key+"\n");
		    if(x_parent.key!=-1) 
		    {
		    	if(x_parent.key<key)
		    	{
		    		x=Search(x_parent.key);
		    		
		    	}
		   
		    	else 
			    	return x_parent;
		    }
		    else return nil;
	    	
	    }
		if(x.right.key!=-1)
			return minimum(x.right);
		else if(x==root)
			return nil;
		else if (x.key < x.parent.key)
			return x.parent;
		else 
			return nil;
	}
	private Node maximum(Node x) {
		if(x.right!=nil)x=x.right;
		return x;
	}
	public Node Search(int key) 
	{	Node x=root;
		x_parent=root;
		while(x.key!=-1&&key!=x.key)
		{	
			if(key<x.key) 
			{
				x_parent=x;
				x=x.left;
			}
			else 
			{	
				x_parent=x;
				x=x.right;
			}
		}
		if(x==nil)return nil;
		else return x;
	}
	public ArrayList<Node> RangeSearch(int key,int key2) 
	{
		for(int i=key;i<=key2;i++)
		{
			Node x=Search(i);
			
			if(x.key!=-1) 
			{
				//System.out.print("range"+x.key+"\n");
				list.add(x);
			}
		}
		
		return list;   
	  
	}

	public void Insert(Node z,int totalTime) 
	{	count++;
		
		//System.out.print("Insert"+z.key+"\t"+count+"\n");
		z.totalTime=totalTime;	
		z.left=nil;
		z.right=nil;
		z.color=0;
		Node y=nil;
		Node x = root;
		while(x!=nil)
		{
			y = x;
			if(z.key<x.key)
			{
				x = x.left;
			}
			else
			{
				x = x.right;
			}
		}
		z.parent = y;
		if(y ==nil) 
		{
			root = z;
		}
		else if (z.key<y.key)
		{
			y.left = z;
		}
		else y.right = z;
		
		InsertFixup(z);
		//printTree(root);
	}
	public void Delete(Node node)
	{ 	
		deletecount++;
		count--;
		//System.out.print("delete"+node.key+"\t"+count+"\n");
		if(node==nil)return;
		Node y =node;
		Node x;
		int ycolor=y.color;
		if(node.left==nil)
		{
			x=node.right;
			rbTransplant(node,node.right);
		}
		else if(node.right==nil)
		{
			x=node.left;
			rbTransplant(node,node.left);
		}
		else 
		{
			y=minimum(node.right);
			ycolor=y.color;
			x=y.right;
			if(y.parent==node)
			{
				x.parent=y;
			}
			else 
			{
				rbTransplant(y,y.right);
				y.right=node.right;
				y.right.parent=y;
			}
			rbTransplant(node,y);
			y.left=node.left;
			y.left.parent=y;
			y.color=node.color;
		}
		if(ycolor==1)
		{
			DeleteFixup( x) ;
		}

	}
	
	private void rbTransplant(Node u, Node v) {
		// TODO Auto-generated method stub
		if(u.parent==nil) 
		{
			root=v;
		}
		else if (u==u.parent.left)
		{
			u.parent.left=v;
		}
		else u.parent.right=v;
		v.parent=u.parent;
	}
	private void DeleteFixup(Node current) 
	{
		while(current!=root&&current.color==1)
		{
			if(current==current.parent.left)
			{
				Node sibling=current.parent.right;
				if(sibling.color==0)
				{
					sibling.color=1;
					current.parent.color=0;
					LeftRotate(current.parent);
					sibling=current.parent.right;
				}
				
				//if(sibling.left!=null&&sibling.right!=null) 
				{
					if(sibling.left.color==1&&sibling.right.color==1)
					{
						sibling.color=0;
						current=current.parent;
					}
					else 
					{	
						if(sibling.right.color==1)
						{
							sibling.left.color=1;
							sibling.color=0;
							RightRotate(sibling);
							sibling=current.parent.right;
						}
						sibling.color=current.parent.color;
						current.parent.color=1;
						
						sibling.right.color=1;
						LeftRotate(current.parent);
						current=root;	
					}
				}
				
				
				
			}
			else
			{
				Node sibling=current.parent.left;
				if(sibling.color==0)
				{
					sibling.color=1;
					current.parent.color=0;
					RightRotate(current.parent);
					sibling=current.parent.left;
					
				}
				//if(sibling.left!=null&&sibling.right!=null) 
				{
					if(sibling.left.color==1&&sibling.right.color==1)
					{
						sibling.color=0;
						current=current.parent;
					}
					else 
					{
						//right is red and left is black
						if(sibling.left.color==1)
						{
							sibling.right.color=1;
							sibling.color=0;
							LeftRotate(sibling);
							sibling=current.parent.left;
						}
						//right is black and left is red
						sibling.color=current.parent.color;
						current.parent.color=1;
						sibling.left.color=1;
						RightRotate(current.parent);
						current=root;
					}
				}
				
				
			}
		}current.color=1;
	}
	
	public Node successor(Node z) {
		if(z.right!=nil)
		{	//System.out.print("min"+minimum(z.right)+"\n");
			return minimum(z.right);
		}
		Node y=z.parent;
		while(y!=nil&&z==y.right)
		{
			z=y;
			y=y.parent;
		}
		return y;
	}
	public Node minimum(Node x) {
		while(x.left.key!=-1)
		{
			x=x.left;
		}
		return x;
	}
	public void InsertFixup(Node z) {
		//System.out.print("InsertFizup\n");
		while(z.parent.color==0)
		{
			if(z.parent==z.parent.parent.left) 
			{
				Node y = z.parent.parent.right;
				if(y.color==0)
				{
					z.parent.color=1;
					y.color=1;
					z.parent.parent.color=0;
					z=z.parent.parent;
				}
				else if (z==z.parent.right)
				{
					z=z.parent;
					LeftRotate(z);
				}
				else 
				{
					z.parent.color=1;
					z.parent.parent.color=0;
					RightRotate(z.parent.parent);
				}
			}
			else 
			{
				Node y = z.parent.parent.left;
				if(y.color==0)
				{
					z.parent.color=1;
					y.color=1;
					z.parent.parent.color=0;
					z=z.parent.parent;
				}
				else 
				{
					if (z==z.parent.left)
					{
						z=z.parent;
						RightRotate(z);
					}
					
						z.parent.color=1;
						z.parent.parent.color=0;
						LeftRotate(z.parent.parent);
				}
				
			}
			
		}
		root.color=1;
		
	}
	public void RightRotate(Node z) {
		if(z.left==nil)return;
		Node y = z.left;
		z.left = y.right;
		if(y.right!=nil)y.right.parent = z;
		y.parent = z.parent;
		if(z.parent==nil) 
		{
			root = y;
		}
		else if(z==z.parent.right)
		{
			z.parent.right=y;
			
		}
		else z.parent.left =y;
		y.right=z;
		z.parent=y;
	}
	public void LeftRotate(Node z) {
		//System.out.print("LeftRotate\n");
		if(z.right==nil)return;
		Node y = z.right;
		z.right = y.left;
		if(y.left!=nil)
			y.left.parent = z;
		y.parent = z.parent;
		if(z.parent==nil) 
		{
			root = y;
		}
		else if(z==z.parent.left)
		{
			z.parent.left=y;
			
		}
		else 
			z.parent.right =y;
		y.left=z;
		z.parent=y;
	}
	public void printTree(Node node)
	{
		
	     if (node == nil) {
	         return;
	     }
	     printTree(node.left);
	     System.out.print(((node.color==0)?"Color: Red ":"Color: Black ")+"Key: "+node.key+" Parent: "+node.parent.key+"left"+node.left.key+"\n");
	     printTree(node.right);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test();

	}
	void test()
	{
		RedBlackTree test = new RedBlackTree();
		heapsort_ heap=new heapsort_();
		RedBlackTree.Node a=test.Node(5);
		RedBlackTree.Node aa=test.Node(6);
		RedBlackTree.Node aaa=test.Node(1);
		test.Insert(a,0);
		test.Insert(aa, 0);
		test.Insert(aaa,0);
	}
	
}

