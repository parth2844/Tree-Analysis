import javax.swing.tree.TreeNode;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BST {
    //Node Structure
    static class Node{
        int key;
        Node left,right;
        public Node(int k){
            key=k;
            left=null;
            right=null;
        }
    }
    static Node root;

    public BST(){
        root=null;
    }


    static void insert(int size, int[] inputArray){
        long totalInputTime=0;
        for(int i=0;i<size;i++){
            //System.out.print(inputArray[i]+" ");
            long start= System.nanoTime();
            root=insertKey(root,inputArray[i]);
            long end= System.nanoTime();
            if(totalInputTime<end-start)
                totalInputTime=end-start;
        }
        System.out.println("Worst Insertion Time-"+totalInputTime);
    }
    static Node insertKey(Node root, int key){

        if (root == null) {
            root = new Node(key);
            return root;
        }

        if (key < root.key)
            root.left = insertKey(root.left, key);
        else if (key > root.key)
            root.right = insertKey(root.right, key);

        return root;
    }

    static void searchKey(int size, int[] inputArray){
        long totalSearchTime=0;
        for(int i=0;i<size;i++){
            long start= System.nanoTime();
            search(root,inputArray[i]);
            long end= System.nanoTime();
            if(totalSearchTime<end-start)
                totalSearchTime=end-start;
        }
        System.out.println("Worst Search Time-"+totalSearchTime/1000);
    }

    static Node search(Node root, int key)
    {
        if (root==null || root.key==key) {
            return root;
        }

        if (root.key > key)
            return search(root.left, key);

        return search(root.right, key);
    }

    static void deleteKey(int size, int[] inputArray){
        long totalDeleteTime=0;
        for(int i=0;i<size;i++){
            long start= System.nanoTime();
            delete(root,inputArray[i]);
            long end= System.nanoTime();
            if(totalDeleteTime<end-start)
                totalDeleteTime=end-start;
        }
        System.out.println("Worst Delete Time-"+totalDeleteTime/1000);
    }

    static int findPreInorder(Node rootNode)
    {
        int min = rootNode.key;
        while(rootNode.left!=null)
        {
            min = rootNode.left.key;
            rootNode = rootNode.left;
        }
        return min;
    }
    static Node delete(Node root, int key){
        if(root==null){
            return root;
        }
        if(key<root.key){
            root.left = delete(root.left,key);
        }
        else if(key>root.key){
            root.right = delete(root.right,key);
        }
        else{
            if(root.left==null){
                return root.right;
            }
            else if(root.right==null){
                return root.left;
            }
            else{
                root.key = findPreInorder(root.right);
                root.right = delete(root.right,root.key);
            }
        }
        return root;
    }

    public void printLevelOrder()
    {
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty())
        {
            Node temp = q.poll();
            System.out.print(temp.key + " ");
            if(temp.left!=null)
            {
                q.add(temp.left);
            }
            if(temp.right!=null)
            {
                q.add(temp.right);
            }
        }
    }
    public static void main(String args[]) throws IOException {
        BST tree= new BST();
        BST tree1 = new BST();
        File file = new File("input1.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int size=Integer.parseInt(br.readLine());
        int []inputArray= new int[size];
        for(int i=0;i<size;i++)
            inputArray[i]=Integer.parseInt(br.readLine());
        if(true) {
            //For random order
            System.out.println("Random Order");
            //Insertion
            tree.insert(size, inputArray);
            //tree.printLevelOrder();
            //System.out.println();
            //Search
            tree.searchKey(size, inputArray);
            //tree.printLevelOrder();
            //System.out.println();
            //Delete
            tree.deleteKey(size, inputArray);
            //tree.printLevelOrder();
            //System.out.println();
        }
        else {
            //For Sorted Order
            Arrays.sort(inputArray);
            System.out.println("Sorted Order");
            //Insertion
            tree1.insert(size, inputArray);
            //tree1.printLevelOrder();
            //System.out.println();
            //Search
            tree1.searchKey(size, inputArray);
            //tree1.printLevelOrder();
            //System.out.println();
            //Delete
            tree1.deleteKey(size, inputArray);
            //tree1.printLevelOrder();
        }
    }
}

