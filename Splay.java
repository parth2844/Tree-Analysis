import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Splay {

    static class Node
    {
        int key;
        Node left;
        Node right;

        Node(int key)
        {
            this.key = key;
            this.left = null;
            this.right = null;
        }
    }

    Node root;

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

    public Node searchRecursive(Node root, int val) {
        return splay(root, val);
    }

    public void insert(int size, int[] inputArray)
    {
        long totalInputTime=0;
        for(int i=0;i<size;i++){
            long start= System.nanoTime();
            root=insertRecursive(root,inputArray[i]);
            long end= System.nanoTime();
            totalInputTime+=end-start;
        }
        System.out.println("Insertion-"+(double) totalInputTime/(double)size);
    }

    public Node insertRecursive(Node root, int data)
    {
        if (root == null)
        {
            root = new Node(data);
        }
        else if (data<root.key)
        {
            root.left = insertRecursive(root.left,data);
        }
        else if (data>root.key)
        {
            root.right = insertRecursive(root.right,data);
        }
        return splay(root,data);
    }

    private Node rightRotate(Node root)
    {
        Node newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;
        return newRoot;
    }

    private Node leftRotate(Node root)
    {
        Node newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;
        return newRoot;
    }

    public Node splay(Node root, int key)
    {
        if (root==null || root.key==key)
        {
            return root;
        }
        if (root.key>key)
        {
            if (root.left==null)
            {
                return root;
            }
            else if (root.left.key>key)
            {
                root.left.left = splay(root.left.left, key);
                root = rightRotate(root);
            }
            else if (root.left.key<key)
            {
                root.left.right = splay(root.left.right, key);
                if (root.left.right!=null)
                {
                    root.left = leftRotate(root.left);
                }
            }
            return (root.left==null)?root:rightRotate(root);
        }
        else
        {
            if (root.right==null)
            {
                return root;
            }
            else if (root.right.key>key)
            {
                root.right.left = splay(root.right.left,key);
                if (root.right.left != null)
                {
                    root.right = rightRotate(root.right);
                }
            }
            else if (root.right.key<key)
            {
                root.right.right = splay(root.right.right,key);
                root = leftRotate(root);
            }
            return (root.right==null)?root:leftRotate(root);
        }
    }

    public void deleteKey(int size, int[] inputArray)
    {
        long totalDeleteTime=0;
        for(int i=0;i<size;i++){
            long start= System.nanoTime();
            deleteRecursive(root,inputArray[i]);
            long end= System.nanoTime();
            if(totalDeleteTime<end-start)
                totalDeleteTime=end-start;
        }
        System.out.println("Worst Delete Time-"+totalDeleteTime/1000);
    }

    private Node deleteRecursive(Node root, int key)
    {
        if (root==null)
        {
            return root;
        }
        root = splay(root,key);
        if (key==root.key)
        {
            if (root.left==null)
            {
                return root.right;
            }
            else if (root.right==null)
            {
                return root.left;
            }
            Node child = root.left;
            while(child.right!=null)
            {
                child = child.right;
            }
            root.key = child.key;
            root.left = deleteRecursive(root.left,child.key);
        }
        return root;
    }

    /*public void printLevelOrder() {
        List<List<String>> tree = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        Queue<Node> q = new LinkedList<>();
        if(root == null)
        {
            System.out.println("Tree is Empty");
            return;
        }
        q.add(root);
        temp.add(String.keyOf(root.key));
        tree.add(new ArrayList<>(temp));
        while(true)
        {
            temp.clear();
            int size = q.size();
            for(int i = 0; i < size; i++)
            {
                Node node = q.remove();
                if(node.left != null)
                {
                    q.add(node.left);
                    temp.add(String.keyOf(node.left.key));
                }
                else
                {
                    temp.add("null");
                }

                if(node.right != null)
                {
                    q.add(node.right);
                    temp.add(String.keyOf(node.right.key));
                }
                else
                {
                    temp.add("null");
                }
            }
            if(!q.isEmpty())
            {
                tree.add(new ArrayList<>(temp));
            }
            else
            {
                break;
            }
        }
        System.out.println(tree);
    }

     */
    public static void main(String args[]) throws IOException {
        Splay tree= new Splay();
        Splay tree1 = new Splay();
        File file = new File("input1.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int size=Integer.parseInt(br.readLine());
        int []inputArray= new int[size];
        for(int i=0;i<size;i++)
            inputArray[i]=Integer.parseInt(br.readLine());

        if(false) {
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
