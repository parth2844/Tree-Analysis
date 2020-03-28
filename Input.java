import java.io.FileNotFoundException;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Random;
public class Input {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
        //The size of the input required is assigned
        int size=10000;
        Random rand = new Random();
        PrintWriter writer = new PrintWriter("input4.txt", "UTF-8");
        writer.println(size);
        //Random unique values are generated and stored in a text file
        for(int i=0;i<size;i++) {
            int x=rand.nextInt(100000);
            if(map.get(x)==null) {
                map.put(x, 1);
                writer.println(x);
            }
            else
                i--;
        }
        writer.close();
    }
}
