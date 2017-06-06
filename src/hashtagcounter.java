/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.BufferedReader;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Mugdha
 */
public class hashtagcounter 
{
    public static void main(String args[])
    
    {         
        int count = 0;
        MaxFibonacciHeap ob=new MaxFibonacciHeap();
        Hashtable<String, FibonacciNode> hashtable;
        hashtable = new Hashtable<> ();
        String line;
        String key;
        Stack st=new Stack();//stores extracted maximum nodes for reinsertion into the Fibonacci heap
        FibonacciNode  poppedElem;
        
        String fileName=args[0];
        
        
        try
        {
            FileReader fileReader = new FileReader(fileName); // FileReader reads text files in the default encoding.
            BufferedWriter out;
             try (BufferedReader bufferedReader = new BufferedReader(fileReader)) // wrap FileReader in BufferedReader.
             {
                 out = new BufferedWriter(new FileWriter("C:\\Users\\Mugdha\\Documents\\NetBeansProjects\\FibonacciHeapProject\\src\\output_file.txt"));
                 String stop="stop";
                             
                 while(!stop.equalsIgnoreCase(line = bufferedReader.readLine())) //read lines until "STOP" is encountered
                 {
                     key=new String();
                     int query;
                     
                     if (line.startsWith("#"))       //if the line starts with # sign,i contains hashtag followed by its count.
                     {
                         Pattern MY_PATTERN = Pattern.compile("#([a-z_.]+) (\\d+)");
                         Matcher mat = MY_PATTERN.matcher(line);
                         
                         while (mat.find())         //Separate hashtag and its count
                         {
                             key=mat.group(1);      //key stores the hashtag key
                             count=Integer.parseInt(mat.group(2));  //count stores the frequency of the hashtag
                          }                         
                         
                        if(hashtable.containsKey(key))  //if the hashtable already has the key increment the value(keyHash) of the hashtag by the count
                         {

                             FibonacciNode n=hashtable.get(key);//retrieve the hashtag value stored in the hashtable
                             int keyHash =(int)n.getVal();      //get the count of the hashtag stored in the node n in the Fibonacci heap by calling getVal()
                             ob.increaseCount(n,keyHash+count); //increase the count of the hashtag present in the node n by the new count

                         }
                        else
                         {
                             //if the hashtable does not have the hashtag key read,create a new node with the hashtag key and its count
                             FibonacciNode fibNode=new FibonacciNode(key,count);
                             ob.insert(fibNode);        //insert the node created into Fibonacci heap
                             hashtable.put(key,fibNode);//add the (hashtag key,Node)pair into the hashtable
                         }
                         
                     }
                     
                     else if(line.matches("\\d+")) //if the line read has integer value,it is stored as query.n most popular hashtags are 
                                                   //extracted from the heap.
                     {
                         
                         query=Integer.parseInt(line);
                         String sep="";
                         while(query!=0)
                         {
                             FibonacciNode ex=ob.removeMax();  //extract the maximum node stored at the root of the Max Fibonacci Heap
                             st.push(ex);                      //push the node extracted into stack for reinsertion into the Fibonacci heap once all max nodes are printed                                                             
                             hashtable.remove(ex.getKey());    //remove the key node pair stored in the hashtable for the maximum key extracted from the Fibonacci heap
                             //System.out.print(sep+ex.getKey());
                             out.append(sep);                                
                             out.append(ex.getKey());           //add the extracted node's hashtag key to the output file
                             sep=",";
                             query--;
                          }  
                         //System.out.println("");  
                         out.newLine();
                         
                         //Once all the n popular hashtags are printed,reinsert them into the Fibonacci heap and hashtable
                         do
                         {
                             poppedElem=(FibonacciNode)st.pop();
                             ob.insert(poppedElem);
                             hashtable.put(poppedElem.getKey(),poppedElem);
                         }while(!st.empty());
                                               
                     }
                     
                 }
             }
               out.close();
            
            }
        catch(FileNotFoundException ex)
        {
            System.out.println("Unable to open '" +fileName + "'");                
        }
        catch(IOException ex) 
        {
            System.out.println("Unable to read file '"+ fileName + "'");                  
           
        }       
      
}
    }





        
       
        
    

    
    

