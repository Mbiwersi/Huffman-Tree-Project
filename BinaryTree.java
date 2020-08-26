
public class BinaryTree<T> {
//This class makes no reference to Huffman Encoding
//Add methods and instance variables for a general
//binary tree class
//general binary tree methods
	private Node root;
	
	public BinaryTree(T data){
		this.root = new Node(null,data, null);
	}
	
	//inner class node which are the parent, children and leaves of the BinaryTree
	public class Node {
		private Node left;
		private T data;
		private Node right;
		private Node(Node L, T d, Node R) {
			left = L;
			data = d;
			right = R;
		}
		
		//return the data stored in the Node
		public T getData() {
			return this.data;
		}
		
		//insert as left child of parent Node n
		public void insertLeft(Node n) {
			this.left = n;
		}
		
		//insert as right child of parent Node n
		public void insertRight(Node n) {
			this.right = n;
		}
		
		//boolean to determine if a Node is a leaf of the Huffman tree
		public boolean isLeaf() {
			if(this.left==null&&this.right==null) {
				return true;
			}
			return false;
		}
		
		//returns the Right child of a parent Node
		public Node getRight() {
			return this.right;
		}
		
		//returns the left child of a parent Node
		public Node getLeft() {
			return this.left;
		}
	}
	
	//boolean if the root is also a leaf
	public boolean isLeaf() {
		if(this.getRoot().isLeaf()) {
			return true;
		}
		return false;
	}
	
	//Assigning the left child of the root Node
	public void insertRootLeft(T data) {
		Node insert = new Node(null,data,null);
		this.root.insertLeft(insert);
	}
	
	//Assigning the right child of the root Node
	public void insertRootRight(T data) {
		Node insert = new Node(null,data,null);
		this.root.insertRight(insert);
	}
	
	//returns the root Node
	public Node getRoot() {
		return this.root;
	}
	
	//returns the data stored in the root Node
	public T getRootData() {
		return this.root.getData();
	}
	
	//returns the roots left child
	public Node getRootLeft() {
		return this.root.getLeft();
	}
	
	//return the roots right child
	public Node getRootRight() {
		return this.root.getRight();
	}
	
	//insert Node n into the tree
	public void insert(Node n) {
		Node parent = this.insertFindParent(this.root);
		if(parent.left==null) {
			parent.left = n;
		}
		else {
			parent.right = n;
		}
	}
	
	//recursive method to find the node to insert the child to
	private Node insertFindParent(Node n) {
		if(n.left==null&&n.right==null) {
			return n;
		}
		else if(n.left!=null&&n.right==null) {
			return n;
		}
		else {
			return this.insertFindParent(n.left);
		}
	}

	
}