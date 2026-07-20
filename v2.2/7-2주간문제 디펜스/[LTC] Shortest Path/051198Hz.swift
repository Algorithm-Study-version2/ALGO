class Solution {
    func networkDelayTime(_ times: [[Int]], _ n: Int, _ k: Int) -> Int {
        var graph = Array(repeating: [(Int, Int)](), count: n + 1)

        for edge in times {
            let from = edge[0]
            let to = edge[1]
            let cost = edge[2]
            graph[from].append((to, cost))
        }

        var dist = Array(repeating: Int.max, count: n + 1)
        dist[k] = 0

        var heap = Heap<(Int, Int)> { $0.1 < $1.1 }
        heap.push((k, 0))

        while let (node, cost) = heap.pop() {
            if cost > dist[node] {
                continue
            }

            for (next, weight) in graph[node] {
                let nextCost = cost + weight

                if nextCost < dist[next] {
                    dist[next] = nextCost
                    heap.push((next, nextCost))
                }
            }
        }

        var answer = 0

        for i in 1...n {
            if dist[i] == Int.max {
                return -1
            }
            answer = max(answer, dist[i])
        }

        return answer
    }

    func minimumWeight(_ n: Int, _ edges: [[Int]], _ src1: Int, _ src2: Int, _ dest: Int) -> Int {
        func dijkstra(_ start: Int, _ graph: [[(Int, Int)]]) -> [Int] {
            let INF = Int.max / 2

            var dist = Array(repeating: INF, count: graph.count)
            dist[start] = 0

            var pq = Heap<(Int, Int)> {
                $0.1 < $1.1
            }

            pq.push((start, 0))

            while let (node, cost) = pq.pop() {

                if cost > dist[node] {
                    continue
                }

                for (next, weight) in graph[node] {
                    let nextCost = cost + weight

                    if nextCost < dist[next] {
                        dist[next] = nextCost
                        pq.push((next, nextCost))
                    }
                }
            }
            return dist
        }


        var graph = Array(repeating: [(Int, Int)](), count: n)
        var reverse = Array(repeating: [(Int, Int)](), count: n)

        for edge in edges {
            let u = edge[0]
            let v = edge[1]
            let w = edge[2]

            graph[u].append((v, w))
            reverse[v].append((u, w))
        }

        let d1 = dijkstra(src1, graph)
        let d2 = dijkstra(src2, graph)
        let d3 = dijkstra(dest, reverse)

        let INF = Int.max / 2
        var answer = INF

        for i in 0..<n {
            if d1[i] == INF || d2[i] == INF || d3[i] == INF {
                continue
            }

            answer = min(answer, d1[i] + d2[i] + d3[i])
        }

        return answer == INF ? -1 : answer
    }

    func findTheCity(_ n: Int, _ edges: [[Int]], _ distanceThreshold: Int) -> Int {
        var graph = Array(repeating: [(Int, Int)](), count: n)

        for edge in edges {
            let u = edge[0]
            let v = edge[1]
            let w = edge[2]

            graph[u].append((v, w))
            graph[v].append((u, w))
        }

        var answer = -1
        var minReachable = Int.max

        for city in 0..<n {
            let dist = dijkstra(city, graph)

            var reachable = 0

            for next in 0..<n {
                if next != city && dist[next] <= distanceThreshold {
                    reachable += 1
                }
            }

            if reachable <= minReachable {
                minReachable = reachable
                answer = city
            }
        }

        return answer
    }

}

class Graph {
    private var graph: [[(Int, Int)]]

    init(_ n: Int, _ edges: [[Int]]) {
        graph = Array(repeating: [], count: n) 

        for edge in edges { 
            graph[edge[0]].append((edge[1], edge[2])) 
        }
    }
    
    func addEdge(_ edge: [Int]) {
        graph[edge[0]].append((edge[1], edge[2]))
    }

    func shortestPath(_ node1: Int, _ node2: Int) -> Int {
        let dist = dijkstra(node1)
        return dist[node2] == Int.max ? -1 : dist[node2]
    }

    private func dijkstra(_ start: Int) -> [Int] {
        let INF = Int.max

        var dist = Array(repeating: INF, count: graph.count)
        dist[start] = 0

        var heap = Heap<(Int, Int)> {
            $0.1 < $1.1
        }

        heap.push((start, 0))

        while let (node, cost) = heap.pop() {
            if cost > dist[node] {
                continue
            }

            for (next, weight) in graph[node] {
                let nextCost = cost + weight

                if nextCost < dist[next] {
                    dist[next] = nextCost
                    heap.push((next, nextCost))
                }
            }
        }

        return dist
    }
}

struct Heap<T> {
    private var elements = [T]()
    private let priority: (T, T) -> Bool

    init(priority: @escaping (T, T) -> Bool) {
        self.priority = priority
    }

    var isEmpty: Bool {
        elements.isEmpty
    }

    mutating func push(_ value: T) {
        elements.append(value)
        siftUp(elements.count - 1)
    }

    mutating func pop() -> T? {
        guard !elements.isEmpty else { return nil }

        if elements.count == 1 {
            return elements.removeLast()
        }

        let value = elements[0]
        elements[0] = elements.removeLast()
        siftDown(0)

        return value
    }

    private mutating func siftUp(_ index: Int) {
        var child = index

        while child > 0 {
            let parent = (child - 1) / 2

            if priority(elements[child], elements[parent]) {
                elements.swapAt(child, parent)
                child = parent
            } else {
                break
            }
        }
    }

    private mutating func siftDown(_ index: Int) {
        var parent = index

        while true {
            let left = parent * 2 + 1
            let right = left + 1
            var candidate = parent

            if left < elements.count &&
                priority(elements[left], elements[candidate]) {
                candidate = left
            }

            if right < elements.count &&
                priority(elements[right], elements[candidate]) {
                candidate = right
            }

            if candidate == parent {
                return
            }

            elements.swapAt(parent, candidate)
            parent = candidate
        }
    }
}