import java.util.*;

/**
 * The {@code RedBlackTree} class represents an ordered symbol table of generic
 * key-value pairs.
 * This implements uses a left-leaning red-black Binary Search Tree.
 *
 * Created by SylvanasSun on 2017/6/1.
 *
 * @author SylvanasSun
 */
public class RedBlackTree<K extends Comparable<K>, V> implements Iterable<K> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    private class Node {
        private int size = 0;
        private boolean color = RED;
        private Node parent, left, right;
        private int orderStatus = 0;
        private K key;
        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.size = 1;
        }
    }

    /**
     * Return the number of key-value pairs of the this red black tree.
     */
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        return x != null ? x.size : 0;
    }

    /**
     * This red black tree is empty?
     *
     * @return if {@code true} represent is empty,{@code false} otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the number of this red black tree height.
     *
     * @return the number of this red black tree height
     */
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null)
            return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and {@code null} if  the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public V get(K key) {
        checkKeyIsNull(key, "called get(K key) function use the key is null.");
        return getValueAssociatedWithKey(key);
    }

    private V getValueAssociatedWithKey(K key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
            else
                return x.value;
        }
        return null;
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key}
     * and {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(K key) {
        checkKeyIsNull(key, "called contains(K key) function use the key is null.");
        return getValueAssociatedWithKey(key) != null;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key   the key
     * @param value the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(K key, V value) {
        checkKeyIsNull(key, "called put(K key,V value) function use the key is null.");
        if (value == null) {
            remove(key);
            return;
        }

        putNewNodeOrUpdate(key, value);
    }

    private void putNewNodeOrUpdate(K key, V value) {
        Node x = root;
        int cmp = 0;
        Node parent = null;
        while (x != null) {
            parent = x;
            cmp = key.compareTo(x.key);
            if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
            else {
                x.value = value;
                return;
            }
        }

        Node newNode = new Node(key, value);
        setColor(newNode, RED);
        newNode.parent = parent;
        if (parent != null) {
            if (cmp < 0)
                parent.left = newNode;
            else
                parent.right = newNode;
            setSize(parent);
            fixAfterInsertion(newNode);
        } else {
            root = newNode;
            setColor(root, BLACK);
        }
    }

    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public K min() {
        checkEmpty("called min() function the this red black tree is empty.");

        Node smallestNode = getSmallestNode();
        if (smallestNode == null)
            return null;
        else
            return smallestNode.key;
    }

    private Node getSmallestNode() {
        Node x = root;
        while (x.left != null)
            x = x.left;
        return x;
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public K max() {
        checkEmpty("called max() function the this red black tree is empty.");

        Node largestNode = getLargestNode();
        if (largestNode == null)
            return null;
        else
            return largestNode.key;
    }

    private Node getLargestNode() {
        Node x = root;
        while (x.right != null)
            x = x.right;
        return x;
    }

    /**
     * Removes the smallest key and associated value from the symbol table.
     * and return old value.
     *
     * @return @return the old value (if return {@code null} symbol table no contain the key)
     * @throws NoSuchElementException if the symbol table is empty
     */
    public V removeMin() {
        checkEmpty("called removeMin() function the red black tree is empty.");

        V smallestValue = getSmallestNode().value;
        if (smallestValue == null)
            return null;

        removeSmallestNode();
        return smallestValue;
    }

    private void removeSmallestNode() {
        Node smallestNode = getSmallestNode();
        Node replacement = smallestNode.right;
        removeSingleNode(smallestNode, replacement);
        smallestNode = null;
    }

    /**
     * Removes the largest key and associated value from the symbol table.
     * and return old value.
     *
     * @return @return the old value (if return {@code null} symbol table no contain the key)
     * @throws NoSuchElementException if the symbol table is empty
     */
    public V removeMax() {
        checkEmpty("called removeMax() function the red black tree is empty.");

        V largestValue = getLargestNode().value;
        if (largestValue == null)
            return null;

        removeLargestNode();
        return largestValue;
    }

    private void removeLargestNode() {
        Node largestNode = getLargestNode();
        Node replacement = largestNode.left;
        removeSingleNode(largestNode, replacement);
        largestNode = null;
    }

    /**
     * Return the kth smallest key in the symbol table.
     *
     * @param index the order statistic
     * @return the {@code k}th smallest key in the symbol table
     * @throws IllegalArgumentException unless {@code k} is between 0 and <em>n</em> - 1
     */
    public K select(int index) {
        if (index < 0 || index >= size())
            throw new IllegalArgumentException();

        Node x = getNodeWithIndex(index);
        if (x == null)
            return null;
        else
            return x.key;
    }

    private Node getNodeWithIndex(int index) {
        Node x = root;
        while (x != null) {
            int leftSize = size(x.left);
            if (leftSize > index)
                x = x.left;
            else if (leftSize < index) {
                x = x.right;
                index = index - leftSize - 1;
            } else {
                return x;
            }
        }
        return null;
    }

    /**
     * Return the number of keys in the symbol table strictly less than {@code key}.
     *
     * @param key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int rank(K key) {
        checkKeyIsNull(key, "called rank(K key) function use key is null.");

        return getIndexWithKey(root, key);
    }

    private int getIndexWithKey(Node x, K key) {
        if (x == null)
            return 0;

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return getIndexWithKey(x.left, key);
        else if (cmp > 0)
            return 1 + size(x.left) + getIndexWithKey(x.right, key);
        else
            return size(x.left);
    }


    /**
     * Returns the largest key in the symbol table less than or equals to {@code key}.
     *
     * @param key the key
     * @return the largest key in the symbol table less than or equals to {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     * @throws NoSuchElementException   if there is no such key
     */
    public K floor(K key) {
        checkKeyIsNull(key, "called floor(K key) function use the key is null.");
        checkEmpty("called floor(K key) function the this red black tree is empty.");

        Node x = getFloorNodeWithKey(key);
        if (x == null)
            return null;
        else
            return x.key;
    }

    private Node getFloorNodeWithKey(K key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp > 0) {
                if (x.right != null)
                    x = x.right;
                else
                    return x;
            } else if (cmp < 0) {
                if (x.left != null)
                    x = x.left;
                else {
                    Node t = x;
                    Node p = x.parent;
                    while (p != null && t == p.left) {
                        t = p;
                        p = p.parent;
                    }
                    return p;
                }
            } else {
                return x;
            }
        }
        return null;
    }

    /**
     * Returns the smallest key in the symbol table greater than or equals to {@code key}.
     *
     * @param key the key
     * @return the smallest key in the symbol table greater than or equals to {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     * @throws NoSuchElementException   if there is no such key
     */
    public K ceiling(K key) {
        checkKeyIsNull(key, "called ceiling(K key) function use key is null.");
        checkEmpty("called ceiling(K key) function the red black tree is empty.");

        Node x = getCeilingNodeWithKey(key);
        if (x == null)
            return null;
        else
            return x.key;
    }

    private Node getCeilingNodeWithKey(K key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                if (x.left != null)
                    x = x.left;
                else
                    return x;
            } else if (cmp > 0) {
                if (x.right != null)
                    x = x.right;
                else {
                    Node t = x;
                    Node p = x.parent;
                    while (p != null && t == p.right) {
                        t = p;
                        p = p.parent;
                    }
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is is in this symbol table) and return old value.
     *
     * @param key the key
     * @return the old value (if return {@code null} symbol table no contain the key)
     * @throws IllegalArgumentException if {@code key} is {@code null}
     * @throws NoSuchElementException   if the symbol table is empty
     */
    public V remove(K key) {
        checkKeyIsNull(key, "called remove(K key) function use the key is null.");
        checkEmpty("called remove(K key) function the this red black tree is empty.");

        V result = get(key);
        if (result == null)
            return null;
        removeNodeWithKey(root, key);
        return result;
    }

    private void removeNodeWithKey(Node x, K key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
            else {
                if (x.left != null && x.right != null) {
                    Node successor = successor(x);
                    x.key = successor.key;
                    x.value = successor.value;
                    x.size = x.size - 1;
                    x = successor;
                }
                Node replacement = x.left != null ? x.left : x.right;
                removeSingleNode(x, replacement);
                x = null;
            }
        }
    }

    private void removeSingleNode(Node x, Node replacement) {
        if (replacement != null) {
            replacementNotNull(x, replacement);
        } else if (x.parent == null) {
            root = null;
        } else {
            replacementIsNull(x);
        }
    }

    private void replacementNotNull(Node x, Node replacement) {
        Node xParent = x.parent;
        replacement.parent = xParent;
        if (xParent == null)
            root = replacement;
        else {
            if (x == xParent.left)
                xParent.left = replacement;
            else
                xParent.right = replacement;
            fixSize(xParent);
        }
        x.left = x.right = x.parent = null;
        if (x.color == BLACK)
            fixAfterDeletion(replacement);
    }

    private void replacementIsNull(Node x) {
        if (x.color == BLACK)
            fixAfterDeletion(x);

        Node xParent = x.parent;
        if (x == xParent.left)
            xParent.left = null;
        else
            xParent.right = null;
        fixSize(xParent);
    }

    private Node successor(Node x) {
        if (x == null) return null;
        Node xRight = x.right;
        if (xRight != null) {
            while (xRight.left != null)
                xRight = xRight.left;
            return xRight;
        } else {
            Node t = x;
            Node p = x.parent;
            while (p != null && t == p.right) {
                t = p;
                p = p.parent;
            }
            return p;
        }
    }

    private void checkKeyIsNull(K key, String message) {
        if (key == null)
            throw new IllegalArgumentException(message);
    }

    private void checkEmpty(String message) {
        if (isEmpty())
            throw new NoSuchElementException(message);
    }

    private void fixAfterInsertion(Node x) {
        while (x != null && x != root && colorOf(parentOf(x)) == RED) {
            if (parentOf(x) == grandpaOf(x).left) {
                x = parentIsLeftNode(x);
            } else {
                x = parentIsRightNode(x);
            }
            fixSize(x);
        }
        setColor(root, BLACK);
    }

    private void fixSize(Node x) {
        while (x != null) {
            setSize(x);
            x = x.parent;
        }
    }

    private void setSize(Node x) {
        if (x != null)
            x.size = 1 + size(x.left) + size(x.right);
    }

    private Node parentIsLeftNode(Node x) {
        Node xUncle = grandpaOf(x).right;

        if (colorOf(xUncle) == RED) {
            x = uncleColorIsRed(x, xUncle);
        } else {
            if (x == parentOf(x).right) {
                x = parentOf(x);
                rotateLeft(x);
            }
            rotateRight(grandpaOf(x));
        }
        return x;
    }

    private Node parentIsRightNode(Node x) {
        Node xUncle = grandpaOf(x).left;

        if (colorOf(xUncle) == RED) {
            x = uncleColorIsRed(x, xUncle);
        } else {
            if (x == parentOf(x).left) {
                x = parentOf(x);
                rotateRight(x);
            }
            rotateLeft(grandpaOf(x));
        }
        return x;
    }

    private Node uncleColorIsRed(Node x, Node xUncle) {
        setColor(parentOf(x), BLACK);
        setColor(xUncle, BLACK);
        setColor(grandpaOf(x), RED);
        x = grandpaOf(x);
        return x;
    }

    private void fixAfterDeletion(Node x) {
        while (x != null && x != root && colorOf(x) == BLACK) {
            if (x == parentOf(x).left) {
                x = successorIsLeftNode(x);
            } else {
                x = successorIsRightNode(x);
            }
        }
        setColor(x, BLACK);
    }

    private Node successorIsLeftNode(Node x) {
        Node brother = parentOf(x).right;

        if (colorOf(brother) == RED) {
            rotateLeft(parentOf(x));
            brother = parentOf(x).right;
        }

        if (colorOf(brother.left) == BLACK && colorOf(brother.right) == BLACK) {
            x = brotherChildrenColorIsBlack(x, brother);
        } else {
            if (colorOf(brother.right) == BLACK) {
                rotateRight(brother);
                brother = parentOf(x).right;
            }
            setColor(brother.right, BLACK);
            rotateLeft(parentOf(x));
            x = root;
        }
        return x;
    }

    private Node successorIsRightNode(Node x) {
        Node brother = parentOf(x).left;

        if (colorOf(brother) == RED) {
            rotateRight(parentOf(x));
            brother = parentOf(x).left;
        }

        if (colorOf(brother.left) == BLACK && colorOf(brother.right) == BLACK) {
            x = brotherChildrenColorIsBlack(x, brother);
        } else {
            if (colorOf(brother.left) == BLACK) {
                rotateLeft(brother);
                brother = parentOf(x).left;
            }
            setColor(brother.left, BLACK);
            rotateRight(parentOf(x));
            x = root;
        }
        return x;
    }

    private Node brotherChildrenColorIsBlack(Node x, Node brother) {
        setColor(brother, RED);
        x = parentOf(x);
        return x;
    }

    private void setColor(Node x, boolean color) {
        if (x != null)
            x.color = color;
    }

    private Node rotateLeft(Node x) {
        Node t = x.right;
        x.right = t.left;
        t.left = x;
        swapParent(x, t);
        swapColorAndSize(x, t);
        return t;
    }

    private Node rotateRight(Node x) {
        Node t = x.left;
        x.left = t.right;
        t.right = x;
        swapParent(x, t);
        swapColorAndSize(x, t);
        return t;
    }

    private void swapColorAndSize(Node x, Node t) {
        boolean temp = t.color;
        t.color = x.color;
        x.color = temp;
        setSize(x);
        setSize(t);
    }

    private void swapParent(Node x, Node t) {
        Node xParent = x.parent;
        t.parent = xParent;
        if (xParent == null)
            root = t;
        else {
            if (x == xParent.left)
                xParent.left = t;
            else
                xParent.right = t;
        }
        x.parent = t;
    }

    private boolean colorOf(Node x) {
        return x == null ? BLACK : x.color;
    }

    private Node parentOf(Node x) {
        return x == null ? null : x.parent;
    }

    private Node grandpaOf(Node x) {
        return x == null ? null : x.parent.parent;
    }

    @Override
    public Iterator<K> iterator() {
        return new RedBlackTreeIterator();
    }

    private class RedBlackTreeIterator implements Iterator<K> {
        private Queue<K> keyQueue;
        private Stack<Node> statusStack;
        private Node temp;

        RedBlackTreeIterator() {
            this.keyQueue = new ArrayDeque<K>();
            this.statusStack = new Stack<Node>();
            this.temp = root;
            inorder();
        }

        void inorder() {
            statusStack.push(temp);
            while (!statusStack.isEmpty()) {
                Node x = statusStack.peek();
                if (x.orderStatus == 0) {
                    if (x.left != null)
                        statusStack.push(x.left);
                    x.orderStatus = 1;
                } else if (x.orderStatus == 1) {
                    keyQueue.add(x.key);
                    x.orderStatus = 2;
                } else if (x.orderStatus == 2) {
                    if (x.right != null)
                        statusStack.push(x.right);
                    x.orderStatus = 3;
                } else if (x.orderStatus == 3) {
                    statusStack.pop();
                    x.orderStatus = 0;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !keyQueue.isEmpty();
        }

        @Override
        public K next() {
            if (!hasNext())
                throw new NullPointerException();
            return keyQueue.remove();
        }
    }

    public static void main(String[] args) {
        RedBlackTree<String, Integer> tree = new RedBlackTree<>();
        Scanner scanner = new Scanner(System.in);
        int value = 0;

        System.out.println("Please enter command!");
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("exit"))
                break;
            else if (command.equalsIgnoreCase("select")) {
                System.out.printf("Red Black Tree height = %d size = %d \n", tree.height(), tree.size());
                for (String key : tree) {
                    System.out.printf("key = %s value = %d", key, tree.get(key));
                    System.out.printf("\n");
                }
            } else if (command.equalsIgnoreCase("help")) {
                System.out.println("command : exit,select,get key,put key,remove key...");
            } else if (command.substring(0, 3).equalsIgnoreCase("get")) {
                String key = command.substring(4);
                System.out.printf("execute get, result = %s-%d\n", key, tree.get(key));
            } else if (command.substring(0, 3).equalsIgnoreCase("put")) {
                String key = command.substring(4);
                System.out.printf("execute put %s-%d ", key, value);
                tree.put(key, value++);
                System.out.printf("size = %d \n", tree.size());
            } else if (command.substring(0, 6).equalsIgnoreCase("remove")) {
                String key = command.substring(7);
                System.out.printf("execute remove %s-%d ", key, tree.remove(key));
                System.out.printf("size = %d \n", tree.size());
            } else {
                System.out.printf("Invalid command!\n");
            }
        }
    }

}
