class Solution {
    func validPath(_ n: Int, _ edges: [[Int]], _ source: Int, _ destination: Int) -> Bool {
        if source == destination {
            return true
        }

        var graph = Array(repeating: [Int](), count: n)

        for edge in edges {
            let u = edge[0]
            let v = edge[1]
            graph[u].append(v)
            graph[v].append(u)
        }

        var visited = Array(repeating: false, count: n)
        var queue = [source]
        var front = 0
        visited[source] = true

        while front < queue.count {
            let current = queue[front]
            front += 1

            if current == destination {
                return true
            }

            for next in graph[current] {
                if !visited[next] {
                    visited[next] = true
                    queue.append(next)
                }
            }
        }

        return false
    }
    
    func allPathsSourceTarget(_ graph: [[Int]]) -> [[Int]] {
        let target = graph.count - 1
        var path = [0]
        var answer = [[Int]]()

        func dfs(_ node: Int) {
            if node == target {
                answer.append(path)
                return
            }

            for next in graph[node] {
                path.append(next)
                dfs(next)
                path.removeLast()
            }
        }

        dfs(0)
        return answer
    }

    func minScore(_ n: Int, _ roads: [[Int]]) -> Int {
        var graph = Array(repeating: [(Int, Int)](), count: n + 1)

        for road in roads {
            let a = road[0]
            let b = road[1]
            let dist = road[2]

            graph[a].append((b, dist))
            graph[b].append((a, dist))
        }

        var visited = Array(repeating: false, count: n + 1)
        var answer = Int.max

        func dfs(_ city: Int) {
            visited[city] = true

            for (next, dist) in graph[city] {
                answer = min(answer, dist)

                if !visited[next] {
                    dfs(next)
                }
            }
        }

        dfs(1)

        return answer
    }

    func islandPerimeter(_ grid: [[Int]]) -> Int {
        let rows = grid.count
        let cols = grid[0].count

        var visited = Array(repeating: Array(repeating: false, count: cols), count: rows)
        let directions = [(1, 0), (-1, 0), (0, 1), (0, -1)]

        func dfs(_ row: Int, _ col: Int) -> Int {
            if row < 0 || row >= rows || col < 0 || col >= cols {
                return 1
            }

            if grid[row][col] == 0 {
                return 1
            }

            if visited[row][col] {
                return 0
            }

            visited[row][col] = true

            var perimeter = 0

            for (dr, dc) in directions {
                perimeter += dfs(row + dr, col + dc)
            }

            return perimeter
        }

        for row in 0..<rows {
            for col in 0..<cols {
                if grid[row][col] == 1 {
                    return dfs(row, col)
                }
            }
        }

        return 0
    }

    func countCompleteComponents(_ n: Int, _ edges: [[Int]]) -> Int {
        var graph = Array(repeating: [Int](), count: n)

        for edge in edges {
            let u = edge[0]
            let v = edge[1]
            graph[u].append(v)
            graph[v].append(u)
        }

        var visited = Array(repeating: false, count: n)
        var answer = 0

        func dfs(_ node: Int, _ vertexCount: inout Int, _ edgeCount: inout Int) {
            visited[node] = true
            vertexCount += 1
            edgeCount += graph[node].count

            for next in graph[node] {
                if !visited[next] {
                    dfs(next, &vertexCount, &edgeCount)
                }
            }
        }

        for node in 0..<n {
            if visited[node] {
                continue
            }

            var vertexCount = 0
            var edgeCount = 0

            dfs(node, &vertexCount, &edgeCount)

            let actualEdges = edgeCount / 2
            let completeEdges = vertexCount * (vertexCount - 1) / 2

            if actualEdges == completeEdges {
                answer += 1
            }
        }

        return answer
    }
}