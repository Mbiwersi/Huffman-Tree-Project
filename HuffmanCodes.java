//Creates a Huffman tree from a text file and outputs the frequencies of each character
//as character codes or actual character(ie 32 = SPACE) and the path to each character in the tree starting from the root.
//PRE: 0 = left, 1 = Right.
//ie. The character SPACE would most likely be the most frequent character and output "32 000"
//Author: Michael Biwersi

import java.io.*;
import java.util.*;
public class HuffmanCodes {
	
	private int[] frequencys;
	private RandomAccessFile file;
	private PriorityQueue<Item> priorityQueue;
	private BinaryTree<Integer> huffTree;
	
	//Class that stores a sub tree and the priority of the tree
	private class Item implements Comparable<Item>{
		
		private BinaryTree<Integer> tree;
		private int priority;
		
		private Item(BinaryTree<Integer> t, int p) {
			this.tree = t;
			this.priority = p;
		}

		private BinaryTree<Integer> getTree(){
			return this.tree;
		}
		

		@Override
		public int compareTo(Item i) {
			// TODO Auto-generated method stub
			if(this.priority<i.priority) {
				return -1;
			}
			else if(this.priority>i.priority) {
				return 1;
			}
			return 0;
		}
	}
	
	public HuffmanCodes(String in) throws IOException {
		//Implements the main flow of your program
		//Add private methods and instance variables as needed
		this.file = new RandomAccessFile(in,"rw");
		this.frequencys = new int[128];
		this.priorityQueue = new PriorityQueue<Item>();
		this.fillFrequencys();
		this.fillQueue();
		this.huffTree = this.createHuffmanTree();
		this.printCodes();
 }
	//Class to help find the path of each leaf
	private class PathTree{
		
		private String path;
		private BinaryTree<Integer> tree;
		
		private PathTree(BinaryTree<Integer> t, String p) {
			this.path = p;
			this.tree = t;
		}
	}
	
	//outputs the character or character code and the path to said character in the Huffman tree
	//PRE: 0 = left child of root, 1 = right child of root.
	private void printCodes() {
		BinaryTree<Integer> current = this.huffTree;
		PathTree pathTree = new PathTree(current,"");
		if(current.getRoot().isLeaf()) {
			System.out.println(current.getRootData()+" is the only character in the tree");
		}
		else {
			PathTree left = this.splitLeft(pathTree);
			this.printHelper(left);
			PathTree right = this.splitRight(pathTree);
			this.printHelper(right);
		}
	}
	//Recursive method to find a leaf in post order then printing the character with the path
	private void printHelper(PathTree pathTree) {
		if(pathTree.tree.getRoot().isLeaf()) {
			//Outputs the actual character other with its character code
			if(pathTree.tree.getRootData()==127||(pathTree.tree.getRootData()>=0&&pathTree.tree.getRootData()<=32)) {
				System.out.println(pathTree.tree.getRootData().intValue()+" "+pathTree.path);
			}
			else {
			System.out.println((char)pathTree.tree.getRootData().intValue()+" "+pathTree.path);
			}
		}
		else {
			this.printHelper(this.splitLeft(pathTree));
			this.printHelper(this.splitRight(pathTree));
		}
	}
	
	//Makes a new tree with the root being the right of the original tree
	private PathTree splitRight(PathTree pathTree){
		BinaryTree<Integer> rtn = new BinaryTree<Integer>(pathTree.tree.getRootRight().getData());
		rtn.insert(pathTree.tree.getRootRight().getLeft());
		rtn.insert(pathTree.tree.getRootRight().getRight());
		PathTree retrn = new PathTree(rtn, pathTree.path+"1");
		return retrn;
	}
	
	//makes a new tree with the root being the left of the original tree
	private PathTree splitLeft(PathTree pathTree){
		BinaryTree<Integer> rtn = new BinaryTree<Integer>(pathTree.tree.getRootLeft().getData());
		rtn.insert(pathTree.tree.getRootLeft().getLeft());
		rtn.insert(pathTree.tree.getRootLeft().getRight());
		PathTree retrn = new PathTree(rtn, pathTree.path+"0");
		return retrn;
	}
	
	//Creates a BinarayTree an empty of Integers and inserts Items from the priority queue
	private BinaryTree<Integer> createHuffmanTree() {
		
		while(this.priorityQueue.size()>1) {
			BinaryTree<Integer> tree = new BinaryTree<Integer>(128);//creating a null node to assign children too from the PQ
			Item insertRight = this.priorityQueue.poll();
			Item insertLeft = this.priorityQueue.poll();
			Item pTree = new Item(tree, insertRight.priority+insertLeft.priority);
			pTree.getTree().insert(insertLeft.getTree().getRoot());
			pTree.getTree().insert(insertRight.getTree().getRoot());
			priorityQueue.add(pTree);
		}
		return priorityQueue.peek().getTree();
		
	}
	
	//reads a char from the text file and counts the number of times that character appears and stores
	//that number in an array with the according index of that character code
	private void fillFrequencys() throws IOException {
		file.seek(0);
		while(file.length()!=file.getFilePointer()) {
			char character = file.readChar();
			this.frequencys[(int)character] +=1;
		}
	}
	
	//fills a PriorityQueue with the frequency of every character that appears in the text file
	private void fillQueue() {
		for(int i=0; i<frequencys.length;i++) {
			if(this.frequencys[i]>0) {
			BinaryTree<Integer> insert = new BinaryTree<Integer>(i);
			Item item = new Item(insert,this.frequencys[i]);
			priorityQueue.add(item);
			}
		}
	}
	
	//helps the test driver have access to frequency
	public int[] getFrequencys() {
		return this.frequencys;
	}
	
	//helps the test driver have access to the priority queue
	public PriorityQueue<Item> getQueue(){
		return this.priorityQueue;
	}
	
	public static void main(String args[]) throws IOException {
		//args[0] is the name of the input file
		new HuffmanCodes(args[0]);
		
		
 }
}
