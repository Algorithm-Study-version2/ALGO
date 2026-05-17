final class Solution {

    func countPalindromePaths(_ parent: [Int], _ s: String) -> Int {
        let n = parent.count
        let chars = Array(s)

        var graph = Array(repeating: [(Int, Int)](), count: n)

        for node in 1..<n {
            let p = parent[node]

            let bit =
                1
                << Int(
                    chars[node].asciiValue!
                        - Character("a").asciiValue!)

            graph[p].append((node, bit))
        }

        var masks = Array(repeating: 0, count: n)

        func dfs(_ node: Int, _ mask: Int) {
            masks[node] = mask

            for (next, bit) in graph[node] {
                dfs(next, mask ^ bit)
            }
        }

        dfs(0, 0)

        var freq = [Int: Int]()
        var answer = 0

        for mask in masks {
            answer += freq[mask, default: 0]

            for i in 0..<26 {
                let target = mask ^ (1 << i)
                answer += freq[target, default: 0]
            }

            freq[mask, default: 0] += 1
        }

        return answer
    }
}