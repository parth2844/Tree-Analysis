import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class AVL {

    static class Node{
        int key;
        int height;
        Node left;
        Node right;
        public Node(int value){
            height = 1;
            key = value;
            left = null;
            right = null;
        }
    };

    static Node root;

    public void create()
    {
        root = null;
    }

    public void searchKey(int size, int[] inputArray)
    {
        long totalSearchTime=0;
        for(int i=0;i<size;i++){
            long start= System.nanoTime();
            searchRecursive(root,inputArray[i]);
            long end= System.nanoTime();
            if(totalSearchTime<end-start)
                totalSearchTime=end-start;
            //System.out.println(end-start);
        }
        System.out.println("Worst Search Time-"+totalSearchTime/1000);
    }

    public Node searchRecursive(Node root, int data)
    {
        if(root==null || root.key==data)
        {
            return root;
        }
        if(root.key>data)
        {
            return searchRecursive(root.left,data);
        }
        else
        {
            return searchRecursive(root.right,data);
        }
    }

    public int getHeight(Node root)
    {
        if(root==null)
        {
            return 0;
        }
        else
        {
            return root.height;
        }
    }

    Node rightRotate(Node root)
    {
        Node a = root.left;
        Node b = a.right;
        a.right = root;
        root.left = b;
        root.height = Math.max(getHeight(root.left),getHeight(root.right))+1;
        a.height = Math.max(getHeight(a.left),getHeight(a.right))+1;
        return a;
    }

    Node leftRotate(Node root)
    {
        Node a = root.right;
        Node b = a.left;
        a.left = root;
        root.right = b;
        root.height = Math.max(getHeight(root.left),getHeight(root.right))+1;
        a.height = Math.max(getHeight(a.left),getHeight(a.right))+1;
        return a;
    }

    int getBalanceFactor(Node root)
    {
        if(root==null)
        {
            return 0;
        }
        else
        {
            return getHeight(root.left)-getHeight(root.right);
        }
    }

    public void insert(int size,int[] inputArray)
    {
        long totalInputTime=0;
        for(int i=0;i<size;i++){
            long start= System.nanoTime();
            root=insertRotation(root,inputArray[i]);
            long end= System.nanoTime();
            if(totalInputTime<end-start)
                totalInputTime=end-start;
        }
        System.out.println("Worst Insertion Time-"+totalInputTime/1000);
    }

    Node insertRotation(Node root, int data)
    {
        if(root==null)
        {
            return (new Node(data));
        }
        if(data<root.key)
        {
            root.left = insertRotation(root.left,data);
        }
        else if(data>root.key)
        {
            root.right = insertRotation(root.right,data);
        }
        else
        {
            return root;
        }
        root.height = 1 + Math.max(getHeight(root.left),getHeight(root.right));
        int balance = getBalanceFactor(root);
        if(balance>1 && data<root.left.key)
        {
            return rightRotate(root);
        }
        if(balance<-1 && data>root.right.key)
        {
            return leftRotate(root);
        }
        if(balance>1 && data>root.left.key)
        {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        if(balance<-1 && data<root.right.key)
        {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }
        return root;
    }

    public Node findMinInorder(Node root)
    {
        Node currentValue = root;
        while(currentValue.left!=null)
        {
            currentValue = currentValue.left;
        }
        return currentValue;
    }

    public void deleteKey(int size, int[] inputArray)
    {
        long totalDeleteTime=0;
        for(int i=0;i<size;i++){
            long start= System.nanoTime();
            deleteRotation(root,inputArray[i]);
            long end= System.nanoTime();
            if(totalDeleteTime<end-start)
                totalDeleteTime=end-start;
        }
        System.out.println("Worst Delete Time-"+totalDeleteTime/1000);
    }

    public Node deleteRotation(Node root, int data)
    {
        if(root==null)
        {
            return root;
        }
        if(data<root.key)
        {
            root.left = deleteRotation(root.left,data);
        }
        else if(data>root.key)
        {
            root.right = deleteRotation(root.right,data);
        }
        else
        {
            if((root.left==null) || (root.right==null))
            {
                Node temp = null;
                if(temp==root.left)
                {
                    temp = root.right;
                }
                else
                {
                    temp = root.left;
                }
                if(temp==null)
                {
                    temp = root;
                    root = null;
                }
                else
                {
                    root = temp;
                }
            }
            else
            {
                Node temp = findMinInorder(root.right);
                root.key = temp.key;
                root.right = deleteRotation(root.right,temp.key);
            }
        }
        if(root==null)
        {
            return root;
        }
        root.height = Math.max(getHeight(root.left),getHeight(root.right))+1;
        int balance = getBalanceFactor(root);
        if(balance>1 && getBalanceFactor(root.left)>=0)
        {
            return rightRotate(root);
        }
        if(balance>1 && getBalanceFactor(root.left)<0)
        {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        if(balance<-1 && getBalanceFactor(root.right)<=0)
        {
            return leftRotate(root);
        }
        if(balance<-1 && getBalanceFactor(root.right)>0)
        {
            root.right = rightRotate(root.right);
            return leftRotate(root);
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
        AVL tree= new AVL();
        AVL tree1 = new AVL();
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


