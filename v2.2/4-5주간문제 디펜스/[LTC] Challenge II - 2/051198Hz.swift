/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     public var val: Int
 *     public var next: ListNode?
 *     public init() { self.val = 0; self.next = nil; }
 *     public init(_ val: Int) { self.val = val; self.next = nil; }
 *     public init(_ val: Int, _ next: ListNode?) { self.val = val; self.next = next; }
 * }
 */
class Solution {
    func mergeKLists(_ lists: [ListNode?]) -> ListNode? {
        var heap = MinHeap()

        // 초기값 넣기
        for node in lists {
            if let node = node {
                heap.push(node)
            }
        }

        let dummy = ListNode(0)
        var current = dummy

        while let node = heap.pop() {
            current.next = node
            current = node

            if let next = node.next {
                heap.push(next)
            }
        }

        return dummy.next
    }
}

struct MinHeap {
    var heap: [ListNode] = []

    mutating func push(_ node: ListNode) {
        heap.append(node)
        siftUp(heap.count - 1)
    }

    mutating func pop() -> ListNode? {
        guard !heap.isEmpty else { return nil }

        heap.swapAt(0, heap.count - 1)
        let node = heap.removeLast()
        siftDown(0)
        return node
    }

    mutating func siftUp(_ i: Int) {
        var child = i
        var parent = (child - 1) / 2

        while child > 0 && heap[child].val < heap[parent].val {
            heap.swapAt(child, parent)
            child = parent
            parent = (child - 1) / 2
        }
    }

    mutating func siftDown(_ i: Int) {
        var parent = i

        while true {
            let left = parent * 2 + 1
            let right = parent * 2 + 2
            var smallest = parent

            if left < heap.count && heap[left].val < heap[smallest].val {
                smallest = left
            }
            if right < heap.count && heap[right].val < heap[smallest].val {
                smallest = right
            }

            if smallest == parent { break }
            heap.swapAt(parent, smallest)
            parent = smallest
        }
    }
}