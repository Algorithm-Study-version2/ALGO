/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     public var val: Int
 *     public var left: TreeNode?
 *     public var right: TreeNode?
 *     public init() { self.val = 0; self.left = nil; self.right = nil; }
 *     public init(_ val: Int) { self.val = val; self.left = nil; self.right = nil; }
 *     public init(_ val: Int, _ left: TreeNode?, _ right: TreeNode?) {
 *         self.val = val
 *         self.left = left
 *         self.right = right
 *     }
 * }
 */
class Solution {
    let MOD = 1_000_000_007
    var total = 0
    var maxProduct = 0

    func maxProduct(_ root: TreeNode?) -> Int {
        total = getSum(root)
        dfs(root)
        return Int(maxProduct % MOD)
    }

    func getSum(_ node: TreeNode?) -> Int {
        guard let node = node else { return 0 }
        return node.val + getSum(node.left) + getSum(node.right)
    }

    func dfs(_ node: TreeNode?) -> Int {
        guard let node = node else { return 0 }

        let leftSum = dfs(node.left)
        let rightSum = dfs(node.right)

        let treeSum = node.val + leftSum + rightSum

        let subtreeProduct = treeSum * (total - treeSum)
        maxProduct = max(maxProduct, subtreeProduct)

        return treeSum
    }
}