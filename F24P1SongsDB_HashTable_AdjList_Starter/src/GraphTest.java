// -------------------------------------------------------------------------

import student.TestCase;

/**
 * Test {@link Graph}
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-05
 */
public class GraphTest
    extends TestCase
{
    //~ Fields ................................................................
    private Graph graph;
    private int initialNumberOfNodes = 5;
    private Node[] nodesInTheGraph;
    private int[][] edges = {{0, 1}, {0, 2}};

    //~Public  Methods ........................................................
    /** Create a graph and insert nodes and edges into it. */
    public void setUp()
    {
        this.nodesInTheGraph = new Node[this.initialNumberOfNodes];
        this.graph = new Graph(this.initialNumberOfNodes);
        for (int i = 0; i < this.initialNumberOfNodes; i++)
        {
            this.nodesInTheGraph[i] = this.graph.newNode();
        }
        for (int[] nodes: this.edges)
        {
            this.graph.addEdge(
                this.nodesInTheGraph[nodes[0]],
                this.nodesInTheGraph[nodes[1]]);
        }
    }

    /**
     * Test constructor by passing a negative capacity.
     */
    public void testConstructor()
    {
        try
        {
            this.graph = new Graph(-1);
            fail("Should throw an exception");
        }
        catch (IllegalArgumentException ex)
        {
            // Pass the test.
        }
    }

    /**
     * Test newNode() through:
     * 1. Add new nodes and check size and nodes existence.
     */
    public void testNewNode()
    {

        assertEquals(this.initialNumberOfNodes, this.graph.getSize());
        assertEquals(2 * this.initialNumberOfNodes, this.graph.getCapacity());
        for (Node node: this.nodesInTheGraph)
        {
            assertTrue(this.graph.hasNode(node));
        }

        // Add more nodes.
        int numOfNodesToAdd = 100;
        Node[] newNodes = new Node[numOfNodesToAdd];
        for (int i = 0; i < numOfNodesToAdd; i++) {
            newNodes[i] = this.graph.newNode();
            // Make sure inserted nodes still in the graph.
            for (Node node: newNodes)
            {
                if (node != null)
                {
                    assertTrue(this.graph.hasNode(node));
                }
            }
        }

    }

    /**
     * Test hasNode() through:
     * 1. Find nodes which do not exist.
     * 2. Find nodes out of boundaries.
     * Finding exist nodes is done in testNewNode().
     */
    public void testHasNode()
    {

        // Find nodes which do not exist.
        for (int i = 0; i < this.initialNumberOfNodes; i++)
        {
            assertFalse(this.graph.hasNode(new Node(i)));
        }

        // Find a node out of boundaries.
        assertFalse(this.graph.hasNode(new Node(this.graph.getCapacity())));
        assertFalse(this.graph.hasNode(new Node(-1)));

    }

    /**
     * Test addEdge() through:
     * 1. Add an edge connecting nodes which do not exist.
     * 2. Add an edge which already exist.
     * 3. Find exist edges.
     * 4. Add many edges.
     */
    public void testAddEdge()
    {

        // Add an edge connecting nodes which do not exist.
        assertFalse(this.graph.addEdge(this.nodesInTheGraph[0], new Node(1)));
        assertFalse(this.graph.addEdge(new Node(1), this.nodesInTheGraph[0]));
        assertFalse(this.graph.addEdge(new Node(0), new Node(1)));

        // Add an edge which already exist.
        assertFalse(this.graph.addEdge(
            this.nodesInTheGraph[this.edges[0][0]],
            this.nodesInTheGraph[this.edges[0][1]]));

        // Find exist edges.
        for (int[] edge: this.edges)
        {
            // It is an undirected graph.
            assertTrue(this.graph.hasEdge(
                this.nodesInTheGraph[edge[0]], this.nodesInTheGraph[edge[1]]));
            assertTrue(this.graph.hasEdge(
                this.nodesInTheGraph[edge[1]], this.nodesInTheGraph[edge[0]]));
        }

        // Add many edges.
        int numOfNodesToAdd = 100;
        Node[] newNodes = new Node[numOfNodesToAdd];
        for (int i = 0; i < numOfNodesToAdd; i++) {
            newNodes[i] = this.graph.newNode();
            for (Node node: newNodes)
            {
                if (node != null)
                {
                    assertTrue(this.graph.hasNode(node));
                }
            }
            if (i > 1)
            {
                assertTrue(this.graph.addEdge(newNodes[i], newNodes[i - 1]));
                assertTrue(this.graph.addEdge(newNodes[0], newNodes[i]));
                assertTrue(this.graph.hasEdge(newNodes[i], newNodes[i - 1]));
                assertTrue(this.graph.hasEdge(newNodes[0], newNodes[i]));
            }
        }

    }

    /**
     * Test hasEdge() through:
     * 1. Find an edge between nodes which do not exist.
     * 2. Find an edge which does not exist.
     * 3. Find an edge from a node to itself.
     * Finding exist edges is done in testAddEdge().
     */
    public void testHasEdge()
    {

        // Find an edge between nodes which do not exist.
        assertFalse(
            this.graph.hasEdge(this.nodesInTheGraph[this.edges[0][0]],
            new Node(this.nodesInTheGraph[this.edges[0][1]].getIndex())));
        assertFalse(
            this.graph.hasEdge(
                new Node(this.nodesInTheGraph[this.edges[0][1]].getIndex()),
                this.nodesInTheGraph[this.edges[0][0]]));
        assertFalse(this.graph.addEdge(new Node(0), new Node(1)));

        // Find an edge which does not exist.
        assertFalse(this.graph.hasEdge(
            this.nodesInTheGraph[1], this.nodesInTheGraph[2]));

        // Find an edge from a node to itself.
        // A vertex is always linked with itself.
        assertTrue(this.graph.hasEdge(this.nodesInTheGraph[0],
            this.nodesInTheGraph[1]));

    }

   /**
    * Test removeEdge() through:
    * 1. Remove an edge between nodes which do not exist.
    * 2. Remove an edge which do not exist.
    * 3. Remove an exist edge and check.
    * 4. Remove an edge from a node to itself.
    */
    public void testRemoveEdge()
    {

        // Remove an edge between nodes which do not exist.
        assertFalse(
            this.graph.removeEdge(this.nodesInTheGraph[this.edges[0][0]],
            new Node(this.nodesInTheGraph[this.edges[0][1]].getIndex())));
        assertFalse(
            this.graph.removeEdge(
                new Node(this.nodesInTheGraph[this.edges[0][1]].getIndex()),
                this.nodesInTheGraph[this.edges[0][0]]));
        assertFalse(this.graph.removeEdge(new Node(0), new Node(1)));

        // Remove an edge which does not exist.
        assertFalse(this.graph.removeEdge(
            this.nodesInTheGraph[1], this.nodesInTheGraph[2]));

        // Remove an exist edge and check.
        assertTrue(this.graph.removeEdge(
            this.nodesInTheGraph[this.edges[0][0]],
            this.nodesInTheGraph[this.edges[0][1]]));
        assertFalse(this.graph.hasEdge(
            this.nodesInTheGraph[this.edges[0][0]],
            this.nodesInTheGraph[this.edges[0][1]]));
        assertFalse(this.graph.hasEdge(
            this.nodesInTheGraph[this.edges[0][1]],
            this.nodesInTheGraph[this.edges[0][0]]));

        // Remove an edge from a node to itself.
        assertFalse(this.graph.removeEdge(
            this.nodesInTheGraph[this.edges[0][0]],
            this.nodesInTheGraph[this.edges[0][0]]));
    }

    /**
     * Test removeNode() through:
     * 1. Remove a node which does not exist.
     * 2. Find the node and edges which may connect to it.
     */
    public void testRemoveNode()
    {

        // Remove a node which does not exist.
        assertFalse(this.graph.removeNode(new Node(0)));

        assertTrue(this.graph.removeNode(this.nodesInTheGraph[0]));
        // Find the node and edges which may connect to it.
        assertFalse(this.graph.hasNode(this.nodesInTheGraph[0]));
        for (Node node: this.nodesInTheGraph)
        {
            assertFalse(this.graph.hasEdge(node, this.nodesInTheGraph[0]));
        }
        assertEquals(this.initialNumberOfNodes - 1, this.graph.getSize());

    }

    /**
     * Test buildConnectedComponent().
     */
    public void testBuildConnectedComponent()
    {

        boolean[] visited = new boolean[this.graph.getCapacity()];
        for (int i = 0; i < visited.length; i++)
        {
            visited[i] = false;
        }

        // Node 0 is connected with Node 1 and 2.
        assertEquals(3, this.graph.buildConnectedComponent(0, visited));
        assertTrue(visited[1]);
        assertTrue(visited[2]);

    }

    /**
     * Test print().
     */
    public void testPrint()
    {

        // 0-1-2 3 4
        assertEquals(
            "There are 3 connected components\n"
                + "The largest connected component has 3 elements",
            this.graph.print());

        this.graph.removeEdge(this.nodesInTheGraph[0], this.nodesInTheGraph[1]);
        // 0-2 1 3 4
        assertEquals(
            "There are 4 connected components\n"
                + "The largest connected component has 2 elements",
            this.graph.print());

        this.graph.removeNode(this.nodesInTheGraph[0]);
        // 1 2 3 4

        assertEquals(
            "There are 4 connected components\n"
                + "The largest connected component has 1 elements",
            this.graph.print());
    }

    /**
     * Test union() and find().
     */
    public void testUnionFind()
    {
        int[] roots = {0, 1, 2, 3, 4};
        this.graph.union(0, 1, roots);
        assertEquals(roots[0], roots[1]);
        assertEquals(this.graph.find(roots[0], roots),
            this.graph.find(roots[1], roots));
    }

}
