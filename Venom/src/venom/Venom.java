package venom;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * The Venom class represents a binary search tree of SymbioteHost objects.
 * Venom is a sentient alien symbiote with a liquid-like form that requires a
 * host to bond with for its survival. The host is granted superhuman abilities
 * and the symbiote gains a degree of autonomy. The Venom class contains methods
 * that will help venom find the most compatible host. You are Venom.
 * 
 * @author Ayla Muminovic
 * @author Shane Haughton
 * @author Elian D. Deogracia-Brito
 */
public class Venom {
    private SymbioteHost root;

    /**
     * DO NOT EDIT THIS METHOD
     * 
     * Default constructor.
     */
    public Venom() {
        root = null;
    }

    /**
     * This method is provided to you
     * Creates an array of SymbioteHost objects from a file. The file should
     * contain the number of people on the first line, followed by the name,
     * compatibility, stability, and whether they have antibodies on each
     * subsequent line.
     * 
     * @param filename the name of the file
     * @return an array of SymbioteHosts (should not contain children)
     */
    public SymbioteHost[] createSymbioteHosts(String filename) {
        // DO NOT EDIT THIS METHOD
        StdIn.setFile(filename);
        int numOfPeople = StdIn.readInt();
        SymbioteHost[] people = new SymbioteHost[numOfPeople];
        for (int i = 0; i < numOfPeople; i++) {
            StdIn.readLine();
            String name = StdIn.readLine();
            int compatibility = StdIn.readInt();
            int stability = StdIn.readInt();
            boolean hasAntibodies = StdIn.readBoolean();
            SymbioteHost newPerson = new SymbioteHost(name, compatibility, stability, hasAntibodies, null, null);
            people[i] = newPerson;
        }
        return people;
    }

    /**
     * Inserts a SymbioteHost object into the binary search tree.
     * 
     * @param symbioteHost the SymbioteHost object to insert
     */
    public void insertSymbioteHost(SymbioteHost symbioteHost) {
        // WRITE YOUR CODE HERE
        if(root == null){                                                           //base case if root is null
            root = symbioteHost;                                                  //set root to symbioteHost
            return; 
        }

        SymbioteHost ptr  = root;                                               //set a pointer to root

        while(ptr != null){                                                 // while ptr is not null
            int cmp = symbioteHost.getName().compareTo(ptr.getName());       //compare the name of symbioteHost to the name of ptr
            if(cmp < 0){
                if(ptr.getLeft() == null){
                    ptr.setLeft(symbioteHost);
                    break; 
                }
                ptr = ptr.getLeft(); 
            }
            else if(cmp > 0){
                if(ptr.getRight() == null){
                    ptr.setRight(symbioteHost);
                    break;
                }
                ptr = ptr.getRight();
            }
            else{                                                                    //if the root itself is the one we are looking for 
                ptr.setSymbioteCompatibility(symbioteHost.getSymbioteCompatibility());
                ptr.setMentalStability(symbioteHost.getMentalStability());
                ptr.setHasAntibodies(symbioteHost.hasAntibodies());
                break; 
            }
        }

}

    /**
     * Builds a binary search tree from an array of SymbioteHost objects.
     * 
     * @param filename filename to read
     */
    public void buildTree(String filename) {
        // WRITE YOUR CODE HERE
        SymbioteHost[] hosts = createSymbioteHosts(filename);
        for(SymbioteHost host : hosts){
            insertSymbioteHost(host);
        }
    }

    /**
     * Finds the most compatible host in the tree. The most compatible host
     * is the one with the highest suitability.
     * PREorder traversal is used to traverse the tree. The host with the highest suitability
     * is returned. If the tree is empty, null is returned.
     * 
     * USE the calculateSuitability method on a SymbioteHost instance to get
     * a host's suitability.
     * 
     * @return the most compatible SymbioteHost object
     */

     private void preorder(SymbioteHost root, Queue<SymbioteHost> q){
        if(root == null){
            return;
        }
        q.enqueue(root);
        preorder(root.getLeft(), q); 
        preorder(root.getRight(), q);  
    }

    public SymbioteHost findMostSuitable() {
        // WRITE YOUR CODE HERE
        Queue<SymbioteHost> q = new Queue<SymbioteHost>(); 
        preorder(root, q);                          //call preorder

        SymbioteHost mostSuitable = null;  //mostSuitable to null return this at end
        SymbioteHost hostPtr = null;           //host temporary pointer
        int suitableMax = 0;               // suitablity rate 
        while (!q.isEmpty()) {            
            hostPtr = q.dequeue();            //dequeue to check first element and continue to rest until queue is empty
            int suitability = hostPtr.calculateSuitability();      //calculate suitability of the first element and to rest of the elements
            if (suitability > suitableMax) {              //if it is greate update it 
                suitableMax = suitability;             
                mostSuitable = hostPtr;
            }
        }
         return mostSuitable; // UPDATE this line, provided so code compiles
    }

    /**
     * Finds all hosts in the tree that have antibodies. INorder traversal is used to
     * traverse the tree. The hosts that have antibodies are added to an
     * ArrayList. If the tree is empty, null is returned.
     * 
     * @return an ArrayList of SymbioteHost objects that have antibodies
     */

    private void inorder(SymbioteHost root, Queue<SymbioteHost> p){
        if (root == null){
            return; 
        }
        inorder(root.getLeft(), p); 
        p.enqueue(root); 
        inorder(root.getRight(), p);
    }
    public ArrayList<SymbioteHost> findHostsWithAntibodies() {
        // WRITE YOUR CODE HERE
        Queue<SymbioteHost> antibodies = new Queue<SymbioteHost>(); 
        inorder(root, antibodies); 
        SymbioteHost hostPtr = null;
        ArrayList<SymbioteHost> hosts = new ArrayList<SymbioteHost>();
        while(!antibodies.isEmpty()){
            hostPtr = antibodies.dequeue();
            if(hostPtr.hasAntibodies()){
                hosts.add(hostPtr);
            }
        }
        return hosts;  // UPDATE this line, provided so code compiles
    }

    /**
     * Finds all hosts in the tree that have a suitability between the given
     * range. The range is inclusive. Level order traversal is used to traverse the tree. The
     * hosts that fall within the range are added to an ArrayList. If the tree
     * is empty, null is returned.
     * 
     * @param minSuitability the minimum suitability
     * @param maxSuitability the maximum suitability
     * @return an ArrayList of SymbioteHost objects that fall within the range
     */
    private void levelOrder(SymbioteHost root, Queue<SymbioteHost> q){
       Queue<SymbioteHost> queue = new Queue<SymbioteHost>();
       queue.enqueue(root);
         while(!queue.isEmpty()){
              SymbioteHost current = queue.dequeue();
              q.enqueue(current);
              if(current.getLeft() != null){
                queue.enqueue(current.getLeft());
              }
              if(current.getRight() != null){
                queue.enqueue(current.getRight());
              }
         }
    }

    public ArrayList<SymbioteHost> findHostsWithinSuitabilityRange(int minSuitability, int maxSuitability) {
            ArrayList<SymbioteHost> hosts = new ArrayList<SymbioteHost>();
            Queue<SymbioteHost> queue = new Queue<SymbioteHost>();
            levelOrder(root, queue);
            while(!queue.isEmpty()){
                SymbioteHost current = queue.dequeue();
                if(current.calculateSuitability() >= minSuitability && current.calculateSuitability() <= maxSuitability){
                    hosts.add(current);
                }
            }

        return hosts; // UPDATE this line, provided so code compiles
    }

    /**
     * Deletes a node from the binary search tree with the given name.
     * If the node is not found, nothing happens.
     * 
     * @param name the name of the SymbioteHost object to delete
     */
    private SymbioteHost min(SymbioteHost root){
        SymbioteHost ptr = root;
        while(ptr.getLeft() != null){
            ptr = ptr.getLeft();
        }
        return ptr;
    }
    private SymbioteHost delete(SymbioteHost root, String name){
        if(root == null){         //base case
            return root;
        }
        int cmp = name.compareTo(root.getName());    //compare the name of the root to the name of the host
        if(cmp < 0){          
            root.setLeft(delete(root.getLeft(), name));   // if the name is less than the root's name, set the left = to the left of the root
        }
        else if(cmp > 0){
            root.setRight(delete(root.getRight(), name));  // if the name is greater than the root's name, set the right = to the right of the root
        }
        else{
            if(root.getLeft() == null){         //if the left of the root is null, return the right of the root
                return root.getRight(); 
            }
            else if(root.getRight() == null){  //if the right of the root is null, return the left of the root
                return root.getLeft();
            }
            SymbioteHost t = root;    
            root = min(t.getRight());    
            root.setRight(delete(t.getRight(), root.getName()));    //set the right of the root to the right of the root
            root.setLeft(t.getLeft());      //set the right of the root to the right of the root
        }
       
        return root;
    }

    public void deleteSymbioteHost(String name) {
        // WRITE YOUR CODE HERE 
        root = delete(root, name);
     }

    /**
     * Challenge - worth zero points
     *
     * Heroes have arrived to defeat you! You must clean up the tree to
     * optimize your chances of survival. You must remove hosts with a
     * suitability between 0 and 100 and hosts that have antibodies.
     * 
     * Cleans up the tree by removing nodes with a suitability of 0 to 100
     * and nodes that have antibodies (IN THAT ORDER).
     */
    public void cleanupTree() {
        // WRITE YOUR CODE HERE
        
        }
    

    /**
     * Gets the root of the tree.
     * 
     * @return the root of the tree
     */
    public SymbioteHost getRoot() {
        return root;
    }

    /**
     * Prints out the tree.
     */
    public void printTree() {
        if (root == null) {
            return;
        }

        // Modify no. of '\t' based on depth of node
        printTree(root, 0, false, false);
    }

    private void printTree(SymbioteHost root, int depth, boolean isRight, boolean isLeft) {
        System.out.print("\t".repeat(depth));

        if (isRight) {
            System.out.print("|-R- ");
        } else if (isLeft) {
            System.out.print("|-L- ");
        } else {
            System.out.print("+--- ");
        }

        if (root == null) {
            System.out.println("null");
            return;
        }

        System.out.println(root);

        if (root.getLeft() == null && root.getRight() == null) {
            return;
        }

        printTree(root.getLeft(), depth + 1, false, true);
        printTree(root.getRight(), depth + 1, true, false);
    }
}
