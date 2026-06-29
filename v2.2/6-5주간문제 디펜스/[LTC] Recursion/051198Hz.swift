class Solution {
    func mergeTwoLists(_ list1: ListNode?, _ list2: ListNode?) -> ListNode? {
        if list1 == nil && list2 == nil {
            return nil
        }
        guard let list1 = list1 else {
            let next = mergeTwoLists(list2?.next, nil)
            list2?.next = next
            return list2
        }
        guard let list2 = list2 else { 
            let next = mergeTwoLists(list1.next, nil)
            list1.next = next
            return list1
        }
        if list1.val < list2.val {
            let nextNode = mergeTwoLists(list1.next, list2)
            list1.next = nextNode
            return list1
        }
        if list2.val <= list1.val {
            let nextNode = mergeTwoLists(list1, list2.next)
            list2.next = nextNode
            return list2
        }
        
        return nil
    }
}