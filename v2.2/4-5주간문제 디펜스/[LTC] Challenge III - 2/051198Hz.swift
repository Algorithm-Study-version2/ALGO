class Solution {
    struct CountableCharacter {
        let c: Character
        let count: Int
    }
    func removeDuplicates(_ s: String, _ k: Int) -> String {
        var stack = [CountableCharacter]()

        for c in s {
            guard let last = stack.last else {
                stack.append(CountableCharacter(c: c, count: 1))
                continue
            }
            if last.c == c {
                if last.count == k - 1 {
                    for _ in 0..<k - 1 {
                        stack.removeLast()
                    }
                    continue
                }
                stack.append(CountableCharacter(c: c, count: last.count + 1))
                continue
            }
            stack.append(CountableCharacter(c: c, count: 1))
        }

        return String(stack.map { $0.c })
    }
}