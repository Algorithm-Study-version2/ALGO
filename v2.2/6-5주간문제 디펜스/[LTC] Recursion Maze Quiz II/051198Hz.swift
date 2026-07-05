class Solution {
    func reorderList(_ head: ListNode?) {
        guard let head = head else { return }

        var slow = head
        var fast = head

        while fast.next != nil, fast.next?.next != nil {
            slow = slow.next!
            fast = fast.next!.next!
        }

        var second = slow.next
        slow.next = nil

        var prev: ListNode? = nil
        while second != nil {
            let next = second!.next
            second!.next = prev
            prev = second
            second = next
        }

        var first: ListNode? = head
        var secondHalf = prev

        while secondHalf != nil {
            let tmp1 = first?.next
            let tmp2 = secondHalf?.next

            first?.next = secondHalf
            secondHalf?.next = tmp1

            first = tmp1
            secondHalf = tmp2
        }
    }

    func allPathsSourceTarget(_ graph: [[Int]]) -> [[Int]] {
        var result = [[Int]]()
        var path = [Int]()

        func backtracking(_ node: Int) {
            path.append(node)

            if node == graph.count - 1 {
                result.append(path)
                path.removeLast()
                return
            }

            for next in graph[node] {
                backtracking(next)
            }

            path.removeLast()
        }

        backtracking(0)
        return result
    }
}