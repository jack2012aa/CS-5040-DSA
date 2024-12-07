// -------------------------------------------------------------------------
/**
 * A graph representing in an adjacency list.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-17
 */
public class Graph
{
    //~ Fields ................................................................
    /** Array to store vertices in the graph. */
    private DoubleLinkedList[] vertices;
    /** Array to store empty slots in vertex which can be used in insertion. */
    private int[] emptySlots;
    /** The index of the not-null top of the emptySlots. */
    private int stackPointer;
    /** Number of vertices in the graph. */
    private int numberOfNodes;

    //~ Constructors ..........................................................
    /**
     * Create a Graph whose capacity is appointed.
     * @param initialCapacity The initial capacity of vertex in the graph.
     * @throws IllegalArgumentException If initialCapacity < 1.
     */
    public Graph(int initialCapacity) throws IllegalArgumentException
    {
        if (initialCapacity < 1)
        {
            String errorMessage = String.format(
                "initialCapacity should be greater than 0. Got %d.",
                initialCapacity);
            throw new IllegalArgumentException(errorMessage);
        }
        this.vertices = new DoubleLinkedList[initialCapacity];
        this.emptySlots = new int[initialCapacity];
        this.stackPointer = initialCapacity - 1;
        this.numberOfNodes = 0;

        // Add slots into emptySlots.
        for (int i = 0; i < initialCapacity; i++)
        {
            emptySlots[initialCapacity - 1 - i] = i;
        }
    }

    //~Public  Methods ........................................................

    /**
     * Insert a new Node into the graph and return it.
     * @return the new node in the graph.
     */
    public Node newNode()
    {

        // Find an empty index.
        int index = this.emptySlots[this.stackPointer--];
        Node node = new Node(index);
        this.vertices[index] = new DoubleLinkedList(node);

        // Expand the array if it is full.
        if (++this.numberOfNodes == this.vertices.length)
        {
            this.expand();
        }

        return node;

    }

    /**
     * Check is the node in the graph.
     * @param node The node to be checked.
     * @return True if the node exist, else false/
     */
    public boolean hasNode(Node node) {
        if (node.getIndex() >= this.vertices.length
            || node.getIndex() < 0
            || this.vertices[node.getIndex()] == null)
        {
            return false;
        }
        return this.vertices[node.getIndex()].getHead() == node;
    }

    /**
     * Add an edge between two node.
     * If the edge exists or vertex doesn't exist, return false.
     * @param node1 A vertex in one side of the edge.
     * @param node2 A vertex in another side of the edge.
     * @return True if insert successfully, else false.
     */
    public boolean addEdge(Node node1, Node node2)
    {

        if (!this.hasNode(node1) || !this.hasNode(node2))
        {
            return false;
        }

        if (this.hasEdge(node1, node2))
        {
            return false;
        }

        // node1 -> node2
        this.vertices[node1.getIndex()].addNodeToRear(node2);
        // node2 -> node1
        this.vertices[node2.getIndex()].addNodeToRear(node1);
        return true;

    }

    /**
     * Check is there an edge between node1 and node2.
     * If the vertex doen't exist, return false.
     * @param node1 A vertex in one side of the edge.
     * @param node2 A vertex in another side of the edge.
     * @return True if the edge exist, else false.
     */
    public boolean hasEdge(Node node1, Node node2)
    {
        if (!this.hasNode(node1) || !this.hasNode(node2))
        {
            return false;
        }

        // By maintaining undirected edge thoroughly
        // we can check only one of the direction of edge here.
        return this.vertices[node1.getIndex()].hasNode(node2);
    }

    /**
     * Remove the edge between node1 and node2.
     * Removing an edge from a node to itself is unavailable.
     * If the vertex or edge doesn't exist, return false.
     * @param node1 A vertex in one side of the edge.
     * @param node2 A vertex in another side of the edge.
     * @return True if the edge is removed, else false.
     */
    public boolean removeEdge(Node node1, Node node2)
    {

        if (!this.hasNode(node1) || !this.hasNode(node2)
            || node1 == node2)
        {
            return false;
        }

        // Remove node1 -> node2 and node2 -> node1.
        // By maintaining undirected edge thoroughly
        // we can check only one of the direction of edge here.
        this.vertices[node1.getIndex()].removeNode(node2);
        return this.vertices[node2.getIndex()].removeNode(node1);

    }

    /**
     * Remove the vertex and all edged connect to it.
     * @param node The vertex to be removed.
     * @return True if the vertex is removed, else false.
     */
    public boolean removeNode(Node node)
    {

        DoubleLinkedList removedVertex = this.vertices[node.getIndex()];
        if (removedVertex.getHead() != node)
        {
            return false;
        }

        // Remove edges.
        for (Node neighbor: removedVertex)
        {
            this.vertices[neighbor.getIndex()].removeNode(node);
        }
        this.numberOfNodes--;
        this.emptySlots[++this.stackPointer] = node.getIndex();
        this.vertices[node.getIndex()] = null;
        return true;

    }

    /**
     * Expand the capacity of the vertex twice.
     */
    public void expand()
    {

        DoubleLinkedList[] cloneVertex = this.vertices.clone();
        this.vertices = new DoubleLinkedList[2 * this.vertices.length];
        this.emptySlots = new int[this.vertices.length];
        for (int i = 0; i < this.vertices.length / 2; i++)
        {
            this.vertices[i] = cloneVertex[i];
        }
        for (int i = this.vertices.length - 1; i >= this.vertices.length / 2;
            i--)
        {
            this.emptySlots[++this.stackPointer] = i;
        }

    }

    /**
     * Return the capacity of vertex.
     * @return The capacity of vertex.
     */
    public int getCapacity()
    {
        return this.vertices.length;
    }

    /**
     * Return number of nodes in the vertex.
     * @return The number of nodes in the vertex.
     */
    public int getSize()
    {
        return this.numberOfNodes;
    }

    /**
     * Build the connected component of the node.
     * This function won't check whether the node is in the graph.
     * @param index The index of a node in the connected component.
     * @param visited An array storing whether the node in this.vertex
     * is visited.
     * @return Size of the connected component.
     */
    int buildConnectedComponent(int index, boolean[] visited)
    {

        int size = 0;
        // Build a stack for DFS().
        int[] stack = new int[visited.length];
        int localStackPointer = -1;
        // Push the starting node.
        stack[++localStackPointer] = index;
        visited[index] = true;
        // While stack not empty.
        while (localStackPointer != -1)
        {
            // Pop the top node.
            DoubleLinkedList currentVertex =
                this.vertices[stack[localStackPointer--]];
            size++;
            for (Node neighbor: currentVertex)
            {
                if (!visited[neighbor.getIndex()])
                {
                    stack[++localStackPointer] = neighbor.getIndex();
                    visited[neighbor.getIndex()] = true;
                }
            }
        }
        return size;

    }

//    /**
//     * Print and return the number of connected components and the largest
//     * component size.
//     * I think using deep first search is easier to understand since this is
//     * an undirected graph.
//     * @return The number of connected components and the largest component
//     * size.
//     */
//    public String myPrint()
//    {
//
//        int numOfComponent = 0;
//        int maxComponentSize = 0;
//        boolean[] visited = new boolean[this.vertices.length];
//
//        // Use a DFS to count number of components.
//        for (int i = 0; i < this.vertices.length; i++)
//        {
//            if (!visited[i] && this.vertices[i] != null)
//            {
//                numOfComponent++;
//                maxComponentSize = Math.max(maxComponentSize,
//                    this.buildConnectedComponent(i, visited));
//            }
//        }
//
//        String result = String.format(
//            "There are %d connected components\n"
//            + "The largest connected component has %d elements",
//            numOfComponent, maxComponentSize);
//        System.out.println(result);
//        return result;
//
//    }

    /**
     * Join two trees.
     * @param a Index of a node in a tree.
     * @param b Index of a node in another tree.
     * @param roots Roots of the tree.
     */
    public void union(int a, int b, int[] roots)
    {
        int rootA = this.find(a, roots);
        int rootB = this.find(b, roots);
        if (rootA != rootB)
        {
            roots[rootA] = rootB;
        }
    }

    /**
     * Return the root of the node.
     * @param index Index of the node.
     * @param roots Roots of the tree.
     * @return Index of the root.
     */
    public int find(int index, int[] roots)
    {
        int current = index;
        while (roots[current] != current) {
            current = roots[current];
        }
        return current;
    }

    /**
     * Print and return the number of connected components and the largest
     * component size.
     * @return The number of connected components and the largest component
     * size.
     */
    public String print()
    {

        // Initialize roots.
        // Roots of connected components.

        int[] roots = new int[this.vertices.length];
        for (int i = 0; i < roots.length; i++)
        {
            if (this.vertices[i] == null)
            {
                roots[i] = -1;
            }
            else
            {
                roots[i] = i;
            }
        }

        // Iterate vertices.
        for (DoubleLinkedList vertex: this.vertices)
        {
            // Skip nulls.
            if (vertex != null)
            {
                int indexA = vertex.getHead().getIndex();
                // Iterate edges.
                for (Node node: vertex)
                {
                    int indexB = node.getIndex();
                    this.union(indexA, indexB, roots);
                }
            }
        }

        // Count components and size.
        int numOfComponent = 0;
        int maxComponentSize = 0;
        int[] componentSizes = new int[this.vertices.length];
        for (int i: roots)
        {
            if (i == -1)
            {
                continue;
            }
            // Final root.
            int root = this.find(i, roots);
            if (componentSizes[root] == 0)
            {
                numOfComponent++;
            }
            componentSizes[root]++;
            if (componentSizes[root] > maxComponentSize)
            {
                maxComponentSize = componentSizes[root];
            }
        }

        String result = String.format(
            "There are %d connected components\n"
            + "The largest connected component has %d elements",
            numOfComponent, maxComponentSize);
        System.out.println(result);
        return result;

    }


}
