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

    func findKthBit(_ n: Int, _ k: Int) -> Character {
        if n == 1 {
            return "0"
        }

        let length = (1 << n) - 1
        let mid = (length + 1) / 2

        if k == mid {
            return "1"
        }

        if k < mid {
            return findKthBit(n - 1, k)
        }

        let symmetry = length - k + 1
        let bit = findKthBit(n - 1, symmetry)

        return bit == "0" ? "1" : "0"
    }

    func decodeString(_ s: String) -> String {
        let chars = Array(s)
        var index = 0

        func parse() -> String {
            var result = ""

            while index < chars.count {
                switch chars[index] {
                case "]":
                    return result

                case "0"..."9":
                    var count = 0
                    while chars[index].isNumber {
                        count = count * 10 + chars[index].wholeNumberValue!
                        index += 1
                    }

                    index += 1
                    let decoded = parse()
                    index += 1

                    result += String(repeating: decoded, count: count)

                default:
                    result.append(chars[index])
                    index += 1
                }
            }

            return result
        }

        return parse()
    }
}