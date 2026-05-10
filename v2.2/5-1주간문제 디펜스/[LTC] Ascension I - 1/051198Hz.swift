import Foundation

class Solution {
    func minRemoveToMakeValid(_ s: String) -> String {
        var sArray = Array(s)
        var stack = [Int]()
        var indicesToRemove = Set<Int>()

        for (i, char) in sArray.enumerated() {
            if char == "(" {
                stack.append(i)
            } else if char == ")" {
                if stack.isEmpty {
                    indicesToRemove.insert(i)
                } else {
                    stack.removeLast()
                }
            }
        }

        while !stack.isEmpty {
            indicesToRemove.insert(stack.removeLast())
        }

        var result = ""
        for i in 0..<sArray.count {
            if !indicesToRemove.contains(i) {
                result.append(sArray[i])
            }
        }

        return result
    }
}