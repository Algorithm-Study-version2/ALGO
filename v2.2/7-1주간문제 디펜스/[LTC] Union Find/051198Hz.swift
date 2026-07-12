class Solution {
    // Q1. Longest Consecutive Sequence
    func longestConsecutive(_ nums: [Int]) -> Int {
        private var parents = [Int: Int]()

        private func find( _ target: Int) -> Int {
            guard let parent = parents[target] else { return Int.max }

            if parent == target {
                return target
            }

            let maxParent = find(parent)
            parents[target] = maxParent

            return maxParent
        }

        private func union(_ g: Int, _ v: Int) {
            var g = find(g)
            var v = find(v)
            guard g != Int.max else { return }
            guard v != Int.max else { return }
            if g < v {
                swap(&g, &v)
            }
            parents[v] = g
        }

        var answer = 0

        for num in nums {
            parents[num, default: -1] = num
        }
        for num in nums {
            union(num+1, num)
        }
        for num in nums {
            union(num+1, num)
        }
        for num in nums {
            guard let parent = parents[num] else { continue }
            guard parent != Int.max else { continue }

            let lcsLength = parent - num + 1

            answer = answer < lcsLength ? lcsLength : answer
        }

        return answer
    }
    // Q2. Largest Component Size by Common Factor
    func largestComponentSize(_ nums: [Int]) -> Int {
        var parent = [Int]()
        var size = [Int]()

        func find(_ x: Int) -> Int {
            if parent[x] != x {
                parent[x] = find(parent[x])
            }
            return parent[x]
        }

        func union(_ a: Int, _ b: Int) {
            var ra = find(a)
            var rb = find(b)

            if ra == rb { return }

            if size[ra] < size[rb] {
                swap(&ra, &rb)
            }

            parent[rb] = ra
            size[ra] += size[rb]
        }

        let maxNum = nums.max()!

        parent = Array(0...maxNum)
        size = Array(repeating: 1, count: maxNum + 1)

        for num in nums {
            var x = num
            var factor = 2
            // 소인수분해 및 union
            while factor * factor <= x {
                if x % factor == 0 {
                    union(num, factor)

                    while x % factor == 0 {
                        x /= factor
                    }
                }
                factor += 1
            }

            if x > 1 {
                union(num, x)
            }
        }

        var count = [Int: Int]()
        var answer = 0

        for num in nums {
            let root = find(num)
            count[root, default: 0] += 1
            answer = max(answer, count[root]!)
        }

        return answer
    }
    // Q3. Process Restricted Friend Requests
    func friendRequests(_ n: Int, _ restrictions: [[Int]], _ requests: [[Int]]) -> [Bool] {
        var parent = Array(0..<n)

        func find(_ v: Int) -> Int {
            if parent[v] != v {
                parent[v] = find(parent[v])
            }
            return parent[v]
        }

        func union(_ g: Int, _ v: Int) {
            var g = find(g)
            var v = find(v)

            if g != v {
                parent[g] = v
            }
        }

        var answer = [Bool]()

        for request in requests {
            let g = find(request[0])
            let v = find(request[1])

            if g == v {
                answer.append(true)
                continue
            }

            var valid = true

            for restriction in restrictions {
                let x = restriction[0]
                let y = restriction[1]

                let rx = find(x)
                let ry = find(y)

                if (rx == g && ry == v) ||
                   (rx == v && ry == g) {
                    valid = false
                    break
                }
            }

            if valid {
                union(g, v)
                answer.append(true)
            } else {
                answer.append(false)
            }
        }

        return answer
    }
    // Q3. Maximum Path Quality of a Graph
    var graph = [[(Int, Int)]]()
    var values = [Int]()
    var visited = [Int]()
    var answer = 0
    var maxTime = 0

    func maximalPathQuality(_ values: [Int], _ edges: [[Int]], _ maxTime: Int) -> Int {
        self.values = values
        self.maxTime = maxTime

        let n = values.count
        graph = Array(repeating: [], count: n)

        for edge in edges {
            let u = edge[0]
            let v = edge[1]
            let t = edge[2]

            graph[u].append((v, t))
            graph[v].append((u, t))
        }

        visited = Array(repeating: 0, count: n)
        visited[0] = 1

        dfs(0, 0, values[0])

        return answer
    }

    func dfs(_ node: Int, _ time: Int, _ score: Int) {
        if node == 0 {
            answer = max(answer, score)
        }

        for (next, cost) in graph[node] {
            let nextTime = time + cost

            if nextTime > maxTime {
                continue
            }

            var nextScore = score

            if visited[next] == 0 {
                nextScore += values[next]
            }

            visited[next] += 1
            dfs(next, nextTime, nextScore)
            visited[next] -= 1
        }
    }
}