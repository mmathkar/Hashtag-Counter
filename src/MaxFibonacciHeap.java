
 
import java.util.ArrayList;
import java.util.List;
/**
 *<p> The class MaxFibonacciHeap represents a Fibonacci heap data structure that can store key-value pairs where key is the Hash tag and its value 
 * is the frequency of the Hash tag.The class MaxFibonacciHeap has methods for performing various operations performed in a maximum Fibonacci heap.It performs 
 * various operations like insert,increase count,remove maximum on the instances of FibonacciNode class.
 * </p>
 **/
public class MaxFibonacciHeap 
{

    private FibonacciNode maxNode;
    private int heapSize;//stores the size of the fibonacci heap

    /**
     * This constructor initializes the maxNode to null and heap size to zero.
     */
    
    public MaxFibonacciHeap() {
        maxNode = null;
        heapSize = 0;
    }
       
    
    /** 
     * <p>This method inserts the node instance of FibonacciNode into the Fibonacci heap by creating a singleton tree and adding it to the
       doubly linked root list by calling addIntoList() function.maxNode pointer is updated with the maximum node in the new root list 
 which is returned by the addIntoList() function.The total number of nodes in the tree (i.e heapSize) is incremented and the pointer to the
 maximum value is updated if necessary.</p>
     *   
     * @param node node to be inserted into the Fibonacci heap
     * @return returns the node inserted
     */
    
    public FibonacciNode insert(FibonacciNode node) 
    {
        
        maxNode = addIntoList(maxNode, node);//add to root list and update maxNode
        heapSize++;
        return node;
    }
/**
 * <p>This function increases the count of the node to newKey.If theNode is not a root and new key is more than the frequency of the parent key, 
 * remove subtree rooted at theNode from its doubly linked sibling list by calling the cut() function and inserting theNode into the root level list.
 * cascadingCut() is then called on the parent of theNode.The parent of the node is then cut if its childCut is true, this continues for each ancestor
 * until a parent that is not marked(childCut=false) is encountered, which is then marked.
 * maxNode pointer is then updated if necessary.
 * .</p>
 * 
 * @param theNode the node in Fibonacci heap whose frequency is to be increased
 * @param newKey New increased frequency of theNode object
 */
    public void increaseCount(FibonacciNode theNode, int newKey) {
        
       
         FibonacciNode parent = theNode.parent;
         theNode.hashCount =newKey;       
        if (parent != null && theNode.hashCount>parent.hashCount) {
            cut(theNode, parent);
            cascadingCut(parent);
        }
        if (theNode.hashCount>maxNode.hashCount) {
            maxNode = theNode;
        }
    }
    
    /**
     *This method removes the subtree rooted at theNode from its doubly linked sibling list.It is then inserted into the top level list using 
      addIntoList() function.
     * If theNode is the only child of parent,child pointer of parent is made null.
     * Find the new maximum sibling of theNode so that we can point the child pointer of its parent to the new maximum child.
     * Decrease the parent's degree by 1 and make the child pointer of parent point to new maximum child
     * Remove theNode from the list and make the parent pointer null
     * Meld it into the root level list and update the maxNode pointer
     * Make the childCut of theNode false

     * @param theNode points to the element to be cut from the tree
     * @param parent points to the parent of the node to be cut
     */

    private void cut(FibonacciNode theNode, FibonacciNode parent) {
        
        
        FibonacciNode newMaxChild=theNode.next;
        FibonacciNode nextNode=theNode.next;
        if (theNode.next==theNode) //if theNode is the only child of parent,make the child pointer of parent to null
            parent.childNode=null;
        
        
        while(nextNode!=theNode) //to find the new maximum sibling of theNode so that we can point the child pointer of its parent
                                 //to the new maximum child
        {
          if(nextNode.hashCount>newMaxChild.hashCount)
           {       
                  newMaxChild=nextNode;
            }
          nextNode=nextNode.next;
        }
                      
         
        if(parent.degree<=1)  //if theNode is the only child of its parent,make the degree of parent 0 and remove the child pointer
        {
          parent.degree=0;
          parent.childNode=null;
        }
        
        else                   //decrease the parent's degree by 1 and make the child pointer of parent point to new maximum child
        {
            parent.degree--;
            parent.childNode=newMaxChild;
            newMaxChild.parent=parent;
        }
        
        removeNode(theNode);                        //remove theNode from the list
        theNode.parent=null;                        //remove parent pointer of the Node
        maxNode=addIntoList(maxNode, theNode); //meld theNode into the root level list and update maxNode
        theNode.childCut = false;                   //Make the childCut of theNode in the root list false
    }

    /**
     * <p>When theNode is cut out of its sibling list in a remove or increase key operation, follow path from parent of theNode to the root.
     * if the childCut value is True,call cut() and cascading cut recursively to cut from their sibling lists and insert into top-level list.
     * Once we reach a node for which childCut is false,we stop.For this node make the childCut to True.</p>     * 
     * @param theNode points to the node in Fibonacci heap on which the cascading cut is to be performed
     */
    private void cascadingCut(FibonacciNode theNode) {
        FibonacciNode parent = theNode.parent;
        if (parent != null) {
            if (theNode.childCut) {
                cut(theNode, parent);
                cascadingCut(parent);
            } else {
                theNode.childCut = true;
            }
        }
    }
    
    /**
     * <p>This function removes the maximum node pointed by the maxNode pointer.Here we remove the maxNode from the tree and reinsert the subtrees 
     * of the removed maxNode into the root level list. 
     * remove the parent pointers of all the children of maxNode which is to be removed.
     * if maxNode is the only node in the root list,set the nextRootListElement node to null.Otherwise store the next node data in it.
     *     and remove maxNode from the root list.
     * Merge the children of the max node with the root list by calling addIntoList() ,update maxNode
       if maxNode is not the only root list element call consolidate
       * </p>
     * @return returns the FibonacciNode having maximum count 
     */

    public FibonacciNode removeMax()
    {
        FibonacciNode removedMaxNode = maxNode;
        FibonacciNode nextRootListElement=null;
        
        //remove the parent pointers of all the children of maxNode which is to be removed.             
            if (removedMaxNode.childNode != null) 
            {
                FibonacciNode child = removedMaxNode.childNode;
                do {
                    child = child.next;
                    child.parent = null;
                } while (child != removedMaxNode.childNode);
            }
            
            
            //if maxNode is the only node in the root list,set the nextRootListElement node to null.Otherwise store the next node data in it.
            if(maxNode.next == maxNode)
                 nextRootListElement =  null ;
            else nextRootListElement=maxNode.next;
            

            removeNode(removedMaxNode);// Remove maxNode from the root list
            
            // Merge the children of the max node with the root list by calling addIntoList() ,update maxNode
            maxNode = addIntoList(nextRootListElement, removedMaxNode.childNode);
            heapSize--;
            
            //if maxNode is not the only root list element call consolidate
            if (nextRootListElement != null)
            {
                maxNode = nextRootListElement;
                consolidateTrees();
            }
             removedMaxNode.parent=null;  //set parent of maxnode to null
             removedMaxNode.childNode=null;
             removedMaxNode.degree=0;      
           return removedMaxNode;
    }

    /**
     * Performs pairwise combine operation on trees so that no two roots have same degree.
     * treeTable is used to keep track of trees by degree.
     * If there exists another node with the same degree, do a pairwise combine on them.Make the one
     * with smaller root a subtree of the larger one using linkingOperation().
     * Finally,the subtrees in the tree table are added into the root list and the maxNode is updated.
     **/ 
    
    
  private void consolidateTrees()
    {
        List<FibonacciNode> treeTable = new ArrayList<>();//tree table
        SpanList listNode = new SpanList(maxNode);        //for traversing the root list
        
        while (listNode.hasNextNode())
        {
            FibonacciNode thisNode = listNode.nextFiboNode();
            while (treeTable.size() <= thisNode.degree + 1)
                 treeTable.add(null);
            
          
            while (treeTable.get(thisNode.degree) != null)  // If there exists another node with the same degree, do a pairwise combine on them
            {
                if(thisNode.hashCount<=treeTable.get(thisNode.degree).hashCount)
                  {
                    FibonacciNode temp = thisNode;
                    thisNode = treeTable.get(thisNode.degree);
                    treeTable.set(thisNode.degree, temp);   //replaces current.deg position with temp node
                              
                   }
                linkingOperation(treeTable.get(thisNode.degree), thisNode);//pairwise combine the node in the tree table that has the same degree position 
                                                                            //as the current node.Make the one with smaller root a subtree of the larger one.
                treeTable.set(thisNode.degree, null);
                thisNode.degree++;                          //increment the degree of resulting tree
            }

            while (treeTable.size() <= thisNode.degree + 1)
            {
                treeTable.add(null);
            }
            treeTable.set(thisNode.degree, thisNode);       //store thisNode in the tree table at the degree position of thisNode
        }

        maxNode = null;
        for (int i = 0; i < treeTable.size(); i++) 
        {
            if (treeTable.get(i) != null) 
            {
                treeTable.get(i).next = treeTable.get(i);
                treeTable.get(i).prevNode = treeTable.get(i);
                maxNode = addIntoList(maxNode, treeTable.get(i));//the subtrees in the tree table are added into the root list and the maxNode is updated.
            }
        }
    }

    
 /**
  * This function deleting theNode from the Fibonacci Heap by removing the pointers connecting it to its adjacent nodes and connecting the nodes lying 
  * previous to it with the nextNode.The parent pointer of theNode is set to null.
  * @param theNode the Node to be deleted from Fibonacci Heap
  */
  private void removeNode(FibonacciNode theNode) 
    {
        FibonacciNode next = theNode.next;
        FibonacciNode previous = theNode.prevNode;
        
        previous.next = next;
        next.prevNode = previous;

        theNode.prevNode = theNode;
        theNode.next = theNode;
        
        theNode.parent=null;
    }

 /**
  * Combine two trees min and max by making the smaller root childNode of larger root(max).The min node is added into the sibling list of the
  * children of max node by calling addIntoList().The childNode pointer of the root max is then updated with the maximum child and the childCut value   
  * of min is set to False.Resulting tree has a rank of degree + 1.
  * @param min Subtree with smaller root
  * @param max Subtree with larger root
  */
 
   private void linkingOperation(FibonacciNode min, FibonacciNode max) 
   {
        removeNode(min);
        max.childNode = addIntoList(min, max.childNode);
        min.parent = max;
        min.childCut = false;
    }

  /**
   * This function merges two subtrees 'first' and 'second' in the root list and returns the max node 
   * If there is only one subtree,return it as the result.
   * @param first first subtree
   * @param second second subtree
   * @return returns the root with larger count value 
   */  
   
   public static FibonacciNode addIntoList(FibonacciNode first, FibonacciNode second)
   {
        
        if (first == null && second == null) {
            return null;
        }
        //if there is only one subtree,return it as the result
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        //
        FibonacciNode temp = first.next;  //merges first and second into the root list
        first.next = second.next;
        first.next.prevNode = first;
        second.next = temp;
        second.next.prevNode = second;

        if(first.hashCount>second.hashCount) //returns the subtree with maximum root
            return first;
        else return second;
    }
}


        
    

    // 
    