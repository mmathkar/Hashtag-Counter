/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * FibonacciNode object is used to store data in Fibonacci heap
 * 
 * @author Mugdha
 */
public class FibonacciNode
{
    
    
         String hashKey;
         int hashCount;
         
         int degree;
         /**
          *  <li>parent    :This node is a pointer to the parent of FibonacciNode object.</li>
          *  <li>childNode :This node is a pointer to one of the children of the FibonacciNode instance.</li>
          *  <li>prevNode  :This node is a pointer to the left sibling of the FibonacciNode instance.</li>
          *  <li>next      :This node is a pointer to the right sibling of the FibonacciNode instance.</li>
          */
         FibonacciNode parent,childNode,prevNode,next;
         /**
          * childCut   :True if node has lost a child since it became a child of its current parent.
                        Set to false by remove min, which is the only operation that makes one node a child of another.
                            
          */
         boolean childCut;
       
         /**
          * Creates a Fibonacci heap node with hashKey=null.
          */
        public FibonacciNode() {
            hashKey = null;
        }
        /**
         * Creates a Fibonacci Node initialized with hashKey.The next and previous node pointers set to the this node.
         * @param key the hashtag key to be stored
         */

        public FibonacciNode(String key) {
            this.hashKey = key;
            next = this;
            prevNode = this;
        }
        
        /**
         * Creates a Fibonacci heap node initialized with a key and value.
         * @param key hashtag to be stored in FibonacciHeap node
         * @param value frequency of the hashtag 
         */
        public FibonacciNode(String key,int value) {
            this.hashKey = key;
            this.hashCount=value;
            next = this;
            prevNode = this;
        }
        /**
         * Returns the count of the hashtag stored in the FibonacciHeap Node 
         * @return hashtag frequency for the node
         */
        
        public int getVal() {
            return hashCount;
        }
        /**
         * Returns the hashtag stored in the Fibonacci Heap Node
         * @return hashtag key for the node
         */
        
        public String getKey() {
            return hashKey;
        }

    }