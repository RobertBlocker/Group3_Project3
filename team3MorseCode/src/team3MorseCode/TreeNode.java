package team3MorseCode;

/** A binary tree node */
public class TreeNode {
    public char val;
    public TreeNode left, right;
    public TreeNode(char x) { val = x; }
    public TreeNode(char x, TreeNode leftChild, TreeNode rightChild) {
        val = x;  left = leftChild;  right = rightChild;
    }
}
