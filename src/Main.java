import java.util.*;

/**
 * Represents a node in a binary tree.
 *
 * @param <T> the type of data stored in the node
 */
class TreeNode<T> {
    T data;
    TreeNode<T> left;
    TreeNode<T> right;

    /**
     * Constructs a new TreeNode with the specified data.
     *
     * @param data the data to be stored in the node
     */
    public TreeNode(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

/**
 * Represents a binary tree.
 *
 * @param <T> the type of data stored in the tree
 */
class BinaryTree<T> {
    private TreeNode<T> root;

    /**
     * Returns the root node of the binary tree.
     *
     * @return the root node of the tree
     */
    public TreeNode<T> getRoot() {
        return root;
    }

    /**
     * Inserts a new node with the specified data into the binary tree.
     *
     * @param data the data to be inserted
     */
    public void insert(T data) {
        root = insert(root, data);
    }

    private TreeNode<T> insert(TreeNode<T> root, T data) {
        if (root == null) {
            return new TreeNode<>(data);
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> node = queue.poll();

            if (node.left == null) {
                node.left = new TreeNode<>(data);
                return root;
            } else {
                queue.add(node.left);
            }

            if (node.right == null) {
                node.right = new TreeNode<>(data);
                return root;
            } else {
                queue.add(node.right);
            }
        }

        return root;
    }

    /**
     * Deletes a node with the specified data from the binary tree.
     *
     * @param data the data to be deleted
     */
    public void delete(T data) {
        root = delete(root, data);
    }

    private TreeNode<T> delete(TreeNode<T> root, T data) {
        if (root == null) {
            return null;
        }

        if (root.data.equals(data)) {
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                TreeNode<T> minNode = findMinimum(root.right);
                root.data = minNode.data;
                root.right = delete(root.right, minNode.data);
            }
        } else {
            root.left = delete(root.left, data);
            root.right = delete(root.right, data);
        }

        return root;
    }

    private TreeNode<T> findMinimum(TreeNode<T> root) {
        if (root == null) {
            return null;
        }

        TreeNode<T> current = root;
        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    /**
     * Searches for a node with the specified data in the binary tree.
     *
     * @param data the data to be searched
     * @return true if the node is found, false otherwise
     */
    public boolean search(T data) {
        return search(root, data);
    }

    private boolean search(TreeNode<T> root, T data) {
        if (root == null) {
            return false;
        }

        if (root.data.equals(data)) {
            return true;
        }

        return search(root.left, data) || search(root.right, data);
    }

    /**
     * Draws the binary tree.
     */
    public void draw() {
        draw(root, 0);
    }

    private void draw(TreeNode<T> root, int level) {
        if (root == null) {
            System.out.println("Empty Tree");
            return;
        }

        draw(root.left, level + 1);

        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }

        System.out.println(root.data);

        draw(root.right, level + 1);
    }
}

/**
 * Main class to demonstrate the usage of the BinaryTree class.
 */
public class Main {
    public static void main(String[] args){
    }
}
