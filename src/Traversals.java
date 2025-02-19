import java.util.*;

public class Traversals {

  /**
   * Returns the sum of the values of all leaf nodes in the given tree of
   * integers.
   * A leaf node is defined as a node with no children.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the sum of leaf node values, or 0 if the tree is null
   */
  public static int sumLeafNodes(TreeNode<Integer> node) {
    if (node == null)
      return 0;

    if (node.left == null && node.right == null) {
      return node.value;
    }

    return sumLeafNodes(node.left) + sumLeafNodes(node.right);
  }

  /**
   * Counts the number of internal nodes (non-leaf nodes) in the given tree of
   * integers.
   * An internal node has at least one child.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the count of internal nodes, or 0 if the tree is null
   */
  public static int countInternalNodes(TreeNode<Integer> node) {
    if (node == null)
      return 0;
    if (node.left == null && node.right == null)
      return 0;

    return 1 + countInternalNodes(node.left) + countInternalNodes(node.right);
  }

  /**
   * Creates a string by concatenating the string representation of each node's
   * value
   * in a post-order traversal of the tree. For example, if the post-order
   * visitation
   * encounters values "a", "b", and "c" in that order, the result is "abc".
   * If node is null, returns an empty string.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a post-order traversal string, or an empty string if the tree is null
   */
  public static <T> String buildPostOrderString(TreeNode<T> node) {
    if (node == null)
      return "";
    StringBuilder result = new StringBuilder();

    //recurse the left and right subtrees
    result.append(buildPostOrderString(node.left));
    result.append(buildPostOrderString(node.right));

    //append the current node's value
    result.append(node.value);

    return result.toString();
  }

  /**
   * Collects the values of all nodes in the tree level by level, from top to
   * bottom.
   * If node is null, returns an empty list.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a list of node values in a top-to-bottom order, or an empty list if
   *         the tree is null
   */
  public static <T> List<T> collectLevelOrderValues(TreeNode<T> node) {
    List<T> list = new ArrayList<T>();
    if (node == null)
      return list;

    //queue is best for level order traversal
    Queue<TreeNode<T>> queue = new LinkedList<>();
    queue.add(node);

    while (!queue.isEmpty()) {
      //remove front node
      TreeNode<T> current = queue.poll();
      //store node's value in the list
      list.add(current.value);

      //add children to the queue
      if (current.left != null)
        queue.add(current.left);
      if (current.right != null)
        queue.add(current.right);
    }

    return list;
  }

  /**
   * Counts the distinct values in the given tree.
   * If node is null, returns 0.
   *
   * @param node the node of the tree
   * @return the number of unique values in the tree, or 0 if the tree is null
   */
  public static int countDistinctValues(TreeNode<Integer> node) {
    if (node == null)
      return 0;
    Set<Integer> uniques = new HashSet<>();

    Queue<TreeNode<Integer>> queue = new LinkedList<>();
    queue.add(node);

    while (!queue.isEmpty()) {
      TreeNode<Integer> current = queue.poll();
      //add values to HashSet (sets will only hold unique values)
      uniques.add(current.value);

      if (current.left != null)
        queue.add(current.left);
      if (current.right != null)
        queue.add(current.right);
    }

    return uniques.size();
  }

  /**
   * Determines whether there is at least one root-to-leaf path in the tree
   * where each successive node's value is strictly greater than the previous
   * node's value.
   * If node is null, returns false.
   *
   * @param node the node of the tree
   * @return true if there exists a strictly increasing root-to-leaf path, false
   *         otherwise
   */
  public static boolean hasStrictlyIncreasingPath(TreeNode<Integer> node) {
    if (node == null)
      return false;

    //if it's a leaf node, return true (it's a valid path)
    if (node.left == null && node.right == null)
      return true;

    //recurse left subtree (if left child has greater value)
    if (node.left != null && node.left.value > node.value) {
      if (hasStrictlyIncreasingPath(node.left))
        return true;
    }

    //recurse right subtree (if right child has greater value)
    if (node.right != null && node.right.value > node.value) {
      if (hasStrictlyIncreasingPath(node.right))
        return true;
    }

    //if no increasing path found, return false
    return false;
  }

  // OPTIONAL CHALLENGE
  /**
   * Checks if two trees have the same shape. Two trees have the same shape
   * if they have exactly the same arrangement of nodes, irrespective of the node
   * values.
   * If both trees are null, returns true. If one is null and the other is not,
   * returns false.
   *
   * @param nodeA the node of the first tree
   * @param nodeB the node of the second tree
   * @param <T>   the type of values stored in the trees
   * @return true if the trees have the same shape, false otherwise
   */
  public static <T> boolean haveSameShape(TreeNode<T> nodeA, TreeNode<T> nodeB) {
    //base case 1: Both nodes are null → trees are identical in shape
    if (nodeA == null && nodeB == null)
      return true;

    //base Case 2: One is null but the other isn't → different shapes
    if (nodeA == null || nodeB == null)
      return false;

    //recurse: Check left and right subtrees
    return haveSameShape(nodeA.left, nodeB.left) && haveSameShape(nodeA.right, nodeB.right);
  }

  // OPTIONAL CHALLENGE
  // Very challenging!
  // Hints:
  // List.copyOf may be helpful
  // Consider adding a helper method
  // Consider keeping the current path in a separate variable
  // Consider removing the current node from the current path after the node's
  // subtrees have been traversed.
  /**
   * Finds all paths from the root to every leaf in the given tree.
   * Each path is represented as a list of node values from root to leaf.
   * The paths should be added pre-order.
   * If node is null, returns an empty list.
   * 
   * Example:
   *
   * 1
   * / \
   * 2 3
   * / \ \
   * 4 5 6
   * 
   * Expected output:
   * [[1, 2, 4], [1, 2, 5], [1, 3, 6]]
   * 
   * @param node the root node of the tree
   * @return a list of lists, where each inner list represents a root-to-leaf path
   *         in pre-order
   */
  public static <T> List<List<T>> findAllRootToLeafPaths(TreeNode<T> node) {
    List<List<T>> result = new ArrayList<>();
    if (node == null)
      return result;

    // start recursion with empty path
    findPaths(node, new ArrayList<>(), result);
    return result;
  }

  private static <T> void findPaths(TreeNode<T> node, List<T> currentPath, List<List<T>> result) {
    if (node == null)
      return;

    // add current node to path
    currentPath.add(node.value);

    // if it's leaf node save the path
    if (node.left == null && node.right == null) {
      result.add(new ArrayList<>(currentPath));
    } else {
      // recurse left and right subtrees
      findPaths(node.left, currentPath, result);
      findPaths(node.right, currentPath, result);
    }

    // remove last node before returning
    currentPath.remove(currentPath.size() - 1);
  }
}
