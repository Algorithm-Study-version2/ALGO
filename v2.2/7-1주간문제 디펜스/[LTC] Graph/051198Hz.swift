class Solution {
    // Q1. Minimum Number of Vertices to Reach All Nodes
    func findSmallestSetOfVertices(_ n: Int, _ edges: [[Int]]) -> [Int] {
        var indegree = Array(repeating: 0, count: n)

        for edge in edges {
            indegree[edge[1]] += 1
        }

        var answer = [Int]()

        for i in 0..<n {
            if indegree[i] == 0 {
                answer.append(i)
            }
        }

        return answer
    }
    // Q2. Add Edges to Make Degrees of All Nodes Even
    func isPossible(_ n: Int, _ edges: [[Int]]) -> Bool {
        var degree = Array(repeating: 0, count: n + 1)
        var graph = Array(repeating: Set<Int>(), count: n + 1)

        for edge in edges {
            let u = edge[0]
            let v = edge[1]

            degree[u] += 1
            degree[v] += 1

            graph[u].insert(v)
            graph[v].insert(u)
        }

        var odd = [Int]()

        for i in 1...n {
            if degree[i] % 2 == 1 {
                odd.append(i)
            }
        }

        switch odd.count {
        case 0:
            return true

        case 2:
            let a = odd[0]
            let b = odd[1]

            if !graph[a].contains(b) {
                return true
            }

            for x in 1...n {
                if x != a && x != b &&
                    !graph[a].contains(x) &&
                    !graph[b].contains(x) {
                    return true
                }
            }

            return false

        case 4:
            let a = odd[0]
            let b = odd[1]
            let c = odd[2]
            let d = odd[3]

            func canConnect(_ u1: Int, _ v1: Int, _ u2: Int, _ v2: Int) -> Bool {
                return !graph[u1].contains(v1) &&
                       !graph[u2].contains(v2)
            }

            return canConnect(a, b, c, d) ||
                   canConnect(a, c, b, d) ||
                   canConnect(a, d, b, c)

        default:
            return false
        }
    }
}