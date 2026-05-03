/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    final long MOD = (long)(1e9 + 7);
    List<Long> subtreeSums = new ArrayList<>();
    public int maxProduct(TreeNode root) {
        long total = getTotalSum(root);
        long answer = 0;
        for(long sub : subtreeSums){
            answer = Math.max(answer, sub * (total - sub));
        }
        return (int)(answer%MOD);
    }
    public long getTotalSum(TreeNode node){
        if(node == null) return 0;
        long sum = node.val + getTotalSum(node.left) + getTotalSum(node.right);
        subtreeSums.add(sum);
        return sum;
    }
}