// -------------------------------------------------------------------------
/**
 * A binary search tree which uses type G as key and store {@link Seminar} as
 * value.
 *
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-27
 *
 * @param <G> Class of the key used in the binary search tree.
 */
public class BST<G extends Comparable<G>>
{
    //~ Fields ................................................................
    /**
     * Node in the binary search tree.
     *
     *  @author Chang-Yu Huang
     *  @version 2024-09-24
     */
    class TreeNode
    {
        /** Key to be compared in the tree. */
        private G key;
        /** Stored data in the node. */
        private Seminar value;
        /** Left child of the node. */
        private TreeNode left;
        /** Right child of the node. */
        private TreeNode right;

        /**
         * Create a node of key and value.
         * @param key Key of the node.
         * @param value Value of the node.
         */
        public TreeNode(G key, Seminar value)
        {
            this.key = key;
            this.value = value;
        }

        /**
         * Get the key.
         * @return Key of the node.
         */
        G getKey()
        {
            return this.key;
        }

        /**
         * Get the value.
         * @return Value of the node.
         */
        Seminar getValue()
        {
            return this.value;
        }
    }

    /** Root of the tree. */
    private TreeNode root;
    /** Counter to count number of visited nodes in searchInRangeHelper. */
    private int counter;

    //~ Constructors ..........................................................
    /**
     * Create an empty tree.
     */
    public BST()
    {
        // Do nothing.
    }

    //~Public  Methods ........................................................

    /**
     * Return the root of the subtree after insertion.
     * @param currentRoot Root of the current subtree.
     * @param key Key of the new node.
     * @param value Value of the new node.
     * @return Root of the subtree after insertion.
     */
    TreeNode insertHelp(TreeNode currentRoot, G key, Seminar value)
    {
        // Root is null -> here is the insertion point.
        // Create a new node and return.
        if (currentRoot == null)
        {
            return new TreeNode(key, value);
        }
        // Find the insertion point.
        // Current key is greater or equal to the new key.
        if (currentRoot.key.compareTo(key) >= 0)
        {
            currentRoot.left = this.insertHelp(currentRoot.left, key, value);
        }
        else
        {
            currentRoot.right = this.insertHelp(currentRoot.right, key, value);
        }
        return currentRoot;

    }

    /**
     * Insert a node. Duplicated key is allowed.
     * @param key Key of the new node.
     * @param value Value of the new node.
     */
    public void insert(G key, Seminar value)
    {
        if (this.root == null)
        {
            this.root = new TreeNode(key, value);
        }
        else
        {
            insertHelp(this.root, key, value);
        }
    }

    /**
     * Find and return the node with the largest key in the subtree.
     * @param currentRoot Root of the subtree.
     * @return Element with the largest key.
     */
    TreeNode getMax(TreeNode currentRoot)
    {
        if (currentRoot == null)
        {
            return null;
        }
        if (currentRoot.right == null)
        {
            return currentRoot;
        }
        return this.getMax(currentRoot.right);
    }

    /**
     * Delete the largest element in the subtree and return the new root.
     * @param currentRoot Root of the subtree.
     * @return New root after the deletion.
     */
    TreeNode deleteMax(TreeNode currentRoot)
    {

        // Null check.
        if (currentRoot == null)
        {
            return null;
        }

        // Current root is the largest node in the subtree.
        if (currentRoot.right == null)
        {
            return currentRoot.left;
        }
        // Remove the largest element in the right subtree.
        currentRoot.right = this.deleteMax(currentRoot.right);
        return currentRoot;
    }

    /**
     * Delete the (key, value) node in the subtree and adjust the subtree.
     * @param currentRoot Root of the subtree.
     * @param key Key of the element to be deleted.
     * @param value Value of the element to be deleted.
     * @return Root of the subtree after adjustment.
     */
    TreeNode deleteHelp(TreeNode currentRoot, G key, Seminar value)
    {
        // Return if the subtree is empty.
        if (currentRoot == null)
        {
            return null;
        }

        // Find the element.
        if (currentRoot.key.compareTo(key) > 0)
        {
            currentRoot.left = this.deleteHelp(currentRoot.left, key, value);
        }
        else if (currentRoot.key.compareTo(key) < 0)
        {
            currentRoot.right = this.deleteHelp(currentRoot.right, key, value);
        }
        // Keys match. Check values.
        else if (currentRoot.value != value)
        {
            currentRoot.left = this.deleteHelp(currentRoot.left, key, value);
        }
        // Found.
        else
        {
            // Check children.
            if (currentRoot.left == null)
            {
                return currentRoot.right;
            }
            if (currentRoot.right == null)
            {
                return currentRoot.left;
            }
            // Replace currentRoot with previous key and value.
            TreeNode previous = this.getMax(currentRoot.left);
            currentRoot.key = previous.key;
            currentRoot.value = previous.value;
            currentRoot.left = this.deleteMax(currentRoot.left);
        }
        return currentRoot;
    }

    /**
     * Remove an element from the tree.
     * @param key Key of the element.
     * @param value Value of the element.
     */
    public void delete(G key, Seminar value)
    {
        this.root = this.deleteHelp(this.root, key, value);
    }

    /**
     * Find nodes by key in a subtree.
     * @param currentRoot Root of the subtree.
     * @param key Key to be searched.
     * @return Elements which match the key.
     */
    Seminar[] searchHelp(TreeNode currentRoot, G key)
    {
        // Base case.
        if (currentRoot == null)
        {
            return new Seminar[0];
        }

        // If current root doesn't match, search in the correspond subtree.
        if (currentRoot.key.compareTo(key) > 0)
        {
            return this.searchHelp(currentRoot.left, key);
        }
        if (currentRoot.key.compareTo(key) < 0)
        {
            return this.searchHelp(currentRoot.right, key);
        }

        // Current root matches. Insert it into the tail of result.
        Seminar[] foundInChildren = this.searchHelp(currentRoot.left, key);
        Seminar[] found = new Seminar[foundInChildren.length + 1];
        // Copy elements.
        for (int i = 0; i < foundInChildren.length; i++)
        {
            found[i] = foundInChildren[i];
        }
        found[foundInChildren.length] = currentRoot.value;
        return found;
    }

    /**
     * Search for elements which match the key in the tree.
     * @param key Key to be searched.
     * @return Matched elements.
     */
    public Seminar[] search(G key)
    {
        return this.searchHelp(this.root, key);
    }

    /**
     * Find elements whose key is in the range [lowerBound, upperBound] in the
     * subtree and insert to the back of an array.
     * @param currentRoot Root of the subtree.
     * @param lowerBound Lower bound of the range.
     * @param upperBound Upper bound of the range.
     * @param carrier Elements found previously.
     * @return Carrier + found elements.
     */
    Seminar[] searchInRangeHelp(TreeNode currentRoot,
        G lowerBound, G upperBound, Seminar[] carrier)
    {
        this.counter++;
        // Base case.
        if (currentRoot == null)
        {
            return carrier;
        }

        Seminar[] previous = carrier;
        Seminar[] result = carrier;
        // If current root is in the range.
        // In-order traversal.
        if (currentRoot.key.compareTo(lowerBound) >= 0)
        {
            previous = this.searchInRangeHelp(currentRoot.left, lowerBound,
                upperBound, carrier);
            result = previous;
        }
        if (currentRoot.key.compareTo(upperBound) <= 0
            && currentRoot.key.compareTo(lowerBound) >= 0)
        {
            result = new Seminar[previous.length + 1];
            for (int i = 0; i < previous.length; i++)
            {
                result[i] = previous[i];
            }
            result[previous.length] = currentRoot.value;
        }
        if (currentRoot.key.compareTo(upperBound) < 0)
        {
            return this.searchInRangeHelp(currentRoot.right, lowerBound,
                upperBound, result);
        }
        return result;
    }

    /**
     * Find all elements in the range [lowerBound, upperBound] in the tree.
     * @param lowerBound Lower bound of the range.
     * @param upperBound Upper bound of the range.
     * @return Elements in the range.
     */
    public Seminar[] searchInRange(G lowerBound, G upperBound)
    {
        this.counter = 0;
        return this.searchInRangeHelp(this.root, lowerBound,
            upperBound, new Seminar[0]);
    }

    /**
     * Return the number of visited nodes in the latest searchInRange().
     * @return Number of visited nodes in the latest searchInRange().
     */
    public int getCount()
    {
        return this.counter;
    }

    /**
     * Get the height of the subtree.
     * @param currentRoot Root of the subtree.
     * @return Height of the subtree.
     */
    int getHeight(TreeNode currentRoot)
    {
        if (currentRoot == null)
        {
            return 0;
        }
        return Math.max(this.getHeight(currentRoot.left),
            this.getHeight(currentRoot.right)) + 1;
    }

    /**
     * Package string with leading spaces and parentheses.
     * @param s String to be packaged.
     * @param numOfSpaces Number of leading spaces / 4.
     * @param withParenthese Whether to add parentheses aside s.
     * @return Packaged string.
     */
    String packageString(String s, int numOfSpaces, boolean withParenthese)
    {
        String result = "";
        for (int i = 0; i < numOfSpaces; i++)
        {
            result = result + "    ";
        }
        if (withParenthese)
        {
            return String.format(result + "(%s)", s);
        }
        return String.format(result + "%s", s);
    }

    /**
     * Get the number of nodes in the subtree.
     * @param currentRoot Root of the subtree.
     * @return Number of nodes in the subtree.
     */
    int getSize(TreeNode currentRoot)
    {
        if (currentRoot == null)
        {
            return 0;
        }
        return this.getSize(currentRoot.left) + this.getSize(currentRoot.right)
            + 1;
    }

    /**
     * Print the subtree in-orderedly.
     * @param currentRoot Root of the subtree.
     * @param level Level of currentRoot in the whole tree.
     * @param height Height of the whole tree.
     */
    void printHelp(TreeNode currentRoot, int level, int height)
    {
        // Base case.
        if (currentRoot == null)
        {
            System.out.println(this.packageString("null", height - level,
                true));
            return;
        }

        // Print the left child.
        this.printHelp(currentRoot.left, level + 1, height);
        System.out.println(this.packageString("\\", height - level, false));

        // Print current node.
        System.out.println(this.packageString(currentRoot.key.toString(),
            height - level, true));

        // Print the right child.
        System.out.println(this.packageString("/", height - level, false));
        this.printHelp(currentRoot.right, level + 1, height);
    }

    /**
     * Print the tree.
     */
    public void print()
    {
        if (this.root == null)
        {
            System.out.println("This tree is empty");
            return;
        }
        this.printHelp(this.root, 0, this.getHeight(this.root));
        System.out.println(String.format("Number of records: %d",
            this.getSize(this.root)));
    }

    /**
     * Get the root of the tree for testing.
     * @return Root of the BST.
     */
    TreeNode getRoot()
    {
        return this.root;
    }

}
