class Solution {
    func minEdgeReversals(_ n: Int, _ edges: [[Int]]) -> [Int] {

        var graph = Array(repeating: [(Int, Int)](), count: n)

        for e in edges {
            let u = e[0],
                v = e[1]

            graph[u].append((v, 0))
            graph[v].append((u, 1))
        }

        var answer = Array(repeating: 0, count: n)

        func baseDFS(_ node: Int, _ parent: Int) {
            for (next, cost) in graph[node] {
                if next == parent { continue }

                answer[0] += cost
                baseDFS(next, node)
            }
        }

        baseDFS(0, -1)

        func rerootDFS(_ node: Int, _ parent: Int) {
            for (next, cost) in graph[node] {
                if next == parent { continue }

                answer[next] = answer[node] + (cost == 0 ? 1 : -1)

                rerootDFS(next, node)
            }
        }

        rerootDFS(0, -1)

        return answer
    }
}