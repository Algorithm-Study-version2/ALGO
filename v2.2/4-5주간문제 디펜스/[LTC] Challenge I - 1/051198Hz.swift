class Solution {
    func isValid(_ s: String) -> Bool {
        var stack = [Character]()

        for c in s {
            if ["(", "{", "["].contains(c) {
                stack.append(c)
            }
            if c == ")" {
                guard let last = stack.last else { return false }
                guard last == "(" else { return false }
                stack.removeLast()
            }
            if c == "}" {
                guard let last = stack.last else { return false }
                guard last == "{" else { return false }
                stack.removeLast()
            }
            if c == "]" {
                guard let last = stack.last else { return false }
                guard last == "[" else { return false }
                stack.removeLast()
            }
        }

        return stack.count == 0 ? true : false
    }
}