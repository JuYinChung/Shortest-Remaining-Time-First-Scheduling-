
package RedBlackTree;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import RedBlackTree.RedBlackTree.Node;



public class heapsort_ {
	static int heapsize=0;
	static Node[] nums= new Node[2000];
	static Node[] num= {};
	static RedBlackTree tree=new RedBlackTree();
	public static void main(String[] args0)
	{
		test();
	}
	void test()
	{
		RedBlackTree.Node a = tree.Node(5);
		RedBlackTree.Node b = tree.Node(6);
		RedBlackTree.Node c = tree.Node(18);
		RedBlackTree.Node d = tree.Node(2);
		RedBlackTree.Node e = tree.Node(1);
		a.time=10;
		b.time=12;
		d.time=5;
		c.time=1;
		e.time=2;
		insert(a);
		insert(b);
		insert(c);
	}
	public static void build_maxheap(Node[] a)
	{
		heapsize=a.length-1;
		for(int i=(a.length/2);i>=0;i--)
		{
			heapify(a,i);
		}
	}
	public static void heapify(Node[] nums,int i) 
	{	
		//System.out.print("Start Heapify  \n");
		int largest;
		int left=left(i);
		int right=right(i);
		if(left<=heapsize&&nums[left].time>nums[i].time) 
		{
			largest=left;
		}
		else largest=i;
		if(right<=heapsize&&nums[right].time>nums[largest].time )
		{
			
			largest=right;
		}
		if(largest!=i) {
			Node x=nums[i];
			nums[i]=nums[largest];
			nums[largest]=x;
			heapify(nums,largest);
		}
		
	}
	public static int parent(int index) 
	{
		return index-1/2;
	}
	
	public static int left(int index) 
	{
		return index*2+1;
	}

	public static int right(int index) 
	{
		return index*2+2;
	}
	public static void heapsort(Node[] nums) 
	{	
		
		build_maxheap(nums);
		for(int i=nums.length-1;i>=0;i--)
		{
			Node x=nums[0];
			nums[0]=nums[i];
			nums[i]=x;
			heapsize=heapsize-1;
			heapify(nums,0);
		}
		
	}

	public static void heap_increasekey(Node node, int i)
	{ 
	    i=i-1;
		if(i==0) 
		{
			nums[i]=node;
		}
		else
		{   

			nums[i]=node;
			while(i>=0&&nums[parent(i)].time<nums[i].time)
			{
				Node x=nums[parent(i)];
				nums[parent(i)]=nums[i];
				nums[i]=x;
				i=parent(i);
			}
		}
	
	}
	public static void insert(Node node) 
	{
		heapsize=heapsize+1;
		//System.out.print("heapsize"+heapsize+"\n");
		num=new Node[heapsize];
		for(int i=0;i<heapsize;i++)
		{
			num[i]=nums[i];
		}
		num[heapsize-1]=node;
		//System.out.print("exetime after insert:"+node.time+"\n");
		
		heap_increasekey(node,heapsize);
		int adjustheapsize=heapsize;
		heapsort(num);
		heapsize=adjustheapsize;
		
		//System.out.print(Arrays.toString(num)+"\n");
	}
	public static void increaseTime(Node node) 
	{
		node.time=node.time+5;
		int adjust=heapsize;
		heapsort(num);
		heapsize=adjust;
		//System.out.print("exetime after increasetime\n");

	
	}
	public static void delete(Node node)
	{
		
		int j=0;
		for(int i=0;i<num.length;i++)
		{
			if(num[i]==node)j=i;
		}
		num[j].time=-1;
		while (j-1 >= 0 && num[j-1].time > num[j].time)
	    {
			Node x=num[j-1];
			num[j-1]=num[j];
			num[j]=x;
			j--;
	    }
		
		Node[] numdelete=new Node[num.length-1];
		for(int h=0;h<num.length-1;h++) 
		{
			numdelete[h]=num[h+1];
		}
		num=new Node[numdelete.length];
		num=numdelete;
		for(int i=0;i<numdelete.length;i++)
		{
			nums[i]=num[i];
		}
		
		heapsize--;

		
	}
	
}
