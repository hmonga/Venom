import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import venom.*;

public class VenomTest {

    /*
     * This is a Java Test Class, which uses the JUnit package to create
     * and run tests. You do NOT have to submit this class.
     * 
     * You can fill in these tests in order to evaluate your code. Writing tests
     * is a crucial skill to have as a developer.
     * 
     * How to run?
     * - MAKE SURE you are in the right directory. On the right side of your VS Code Explorer, you should see:
     *  Venom
     *      lib
     *      src
     *      test
     *      input files
     * NOT:
     * Venom/CS112/Another Folder Name
     *  Venom
     *      ...
     * Open the INNERMOST Venom (case sensitive) using File -> Open Folder.
     * - Right click VenomTest.java in the VS Code explorer and select "Run Tests"
     */


    @Test
    public void testInsertSymbioteHost() {
        Venom test = new Venom();

        // Tests a few cases with inserting single nodes (not all-inclusive)

        SymbioteHost root1 = new SymbioteHost("Eddie Brock", 21, 24, false);
        // Is the root inserted?
        test.insertSymbioteHost(root1);
        // This will throw an AssertionError if the root is null
        assertNotNull(test.getRoot());
        // This will throw an AssertionError if the root is not Eddie Brock
        assertEquals(root1.getName(), test.getRoot().getName());

        SymbioteHost root2 = new SymbioteHost("Eddie Brock", 22, 24, false);
        test.insertSymbioteHost(root2);
        // This will throw an AssertionError if the root is not correctly updated with the new info 
        assertEquals(root2.getName(), test.getRoot().getName());
        // This will throw an AssertionError if the root has children. Only one unique key has been inserted so far.
        assertNull(root2.getLeft());
        assertNull(root2.getRight());
        // This will throw an AssertionError if the root is not correctly updated with the new info 
        assertEquals(root2.getSymbioteCompatibility(), test.getRoot().getSymbioteCompatibility()); // is the value correctly updated?

        SymbioteHost firstLeft = new SymbioteHost("Anne Weying", 66, 1, true);
        test.insertSymbioteHost(firstLeft);
        SymbioteHost ptr = test.getRoot();
        ptr = ptr.getLeft();
        // This will throw an AssertionError if the new node is not correct inserted to left of the root
        assertNotNull(ptr);
        // This will throw an AssertionError if the new node to the left of the root is not Anne Weying
        assertEquals(firstLeft.getName(), ptr.getName());

        SymbioteHost nestedRight = new SymbioteHost("Dylan Brock", 78, 9, false);
        test.insertSymbioteHost(nestedRight);
        // use same ptr from before
        ptr = ptr.getRight();
        // This will throw an AssertionError if the new node is not correct inserted to Root->Left->Right
        assertNotNull(ptr);
        // This will throw an AssertionError if the new nested node Root->Left->Right is not Dylan Brock
        assertEquals(nestedRight.getName(), ptr.getName());
    }

    @Test
    public void testBuildTree() {
        // WRITE YOUR OWN CODE to complete this test
       // Instantiate a new Venom object
       Venom test = new Venom();
       // Call buildTree on the testInput.in file
       test.buildTree("testInput.in");


       SymbioteHost root = test.getRoot();
       SymbioteHost leftChild = root.getLeft();
       SymbioteHost rightChild = root.getRight();
       SymbioteHost leftRightRightChild = leftChild.getRight().getRight();


       assertNotNull("Root should not be null", root);
       assertEquals("Mac Gargan", root.getName());


        // Check root's left child, and its children
       assertNotNull("Root's left child should not be null", leftChild);
       assertEquals("Anne Weying" , leftChild.getName());


       //check left's right child
       SymbioteHost leftRightChild = leftChild.getRight();
       assertNotNull("Left's right child should not be null", leftRightChild);
       assertEquals("Eddie Brock", leftRightChild.getName());


       //check left's right's right child
       assertNotNull("Left's right's right child should not be null", leftRightRightChild);
       assertEquals("Flash Thompson", leftRightRightChild.getName());
     
       // Check root's right child, and its children


       //check right child
       assertNotNull("Root's right child should not be null", rightChild);
    }

    @Test
    public void testFindMostSuitable() {
        // Write your own code to implement this test

        // Check for the most suitable host (use calculateSuitability method), use
        // preorder traversal to get an ordering

        Venom test = new Venom();
        test.buildTree("testInput.in");
        SymbioteHost mostSuitable = test.findMostSuitable();
        assertNotNull("Most suitable host should not be null", mostSuitable);   
        assertEquals("Anne Weying", mostSuitable.getName());

    }

    @Test
    public void testFindHostsWithAntibodies() {
        // Write your own code to implement this test

        // Use inorder traversal for ordering

        // Remove this line once you have written this test.
       Venom test = new Venom(); 
        test.buildTree("testInput.in"); 
        ArrayList<SymbioteHost> people = test.findHostsWithAntibodies();
        assertNotNull("People should not be null", people);
        assertEquals(3, people.size());
    }

    @Test
    public void testFindHostsWithinSuitabilityRange() {
        Venom test = new Venom(); 
        test.buildTree("testInput.in");
        ArrayList<SymbioteHost> hostsInRange = test.findHostsWithinSuitabilityRange(0, 100);
        assertNotNull("Hosts in range should not be null", hostsInRange);
        assertEquals(2, hostsInRange.size());
    }
    
    @Test
    public void testDeleteSymbioteHost() {
        Venom test = new Venom();
        test.buildTree("testInput.in");
        //one child
        test.deleteSymbioteHost("Wade Winston");
        test.printTree();
        //two children
        test.deleteSymbioteHost("Mac Gargan");
        test.printTree();
        //no children
        test.deleteSymbioteHost("Flash Thompson");
        test.printTree();

    }
}