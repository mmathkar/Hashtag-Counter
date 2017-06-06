
import java.util.*;

/**
 * <p>This SpanList is used to simplify the consolidateTrees() method. It works by
      gathering a list of the nodes in the list in the constructor since the
    nodes can change during consolidation.</p>
 * 
 */
public class SpanList {

        private Queue<FibonacciNode> queue = new LinkedList<>();
        /**
         * Add the nodes in the the list who are siblings of the first node
         * @param first first node in the list
         */
        public SpanList(FibonacciNode first) 
        {
            if (first == null)
            {
                return;
            }

            FibonacciNode thisNode = first;
            do {
                queue.add(thisNode);
                thisNode = thisNode.next;
            } while (first != thisNode);
        }
        
        /**
         * returns next Fibonacci Node element in the list
         * @return returns  the next FibonacciNode
         */

        public FibonacciNode nextFiboNode() {
            return queue.poll();
        }
        /**
         * 
         * @return returns true if there is a next node in the Fibonacci heap
         */
        public boolean hasNextNode()        
        {
            return queue.peek() != null;
        }

        

   }

