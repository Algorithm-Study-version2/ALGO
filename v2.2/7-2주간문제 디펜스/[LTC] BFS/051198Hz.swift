class Solution {
    func highestPeak(_ isWater: [[Int]]) -> [[Int]] {
        let m = isWater.count
        let n = isWater[0].count

        var height = Array(
            repeating: Array(repeating: -1, count: n),
            count: m
        )

        var queue = [(Int, Int)]()
        var head = 0

        for i in 0..<m {
            for j in 0..<n {
                if isWater[i][j] == 1 {
                    height[i][j] = 0
                    queue.append((i, j))
                }
            }
        }

        let directions = [
            (1, 0),
            (-1, 0),
            (0, 1),
            (0, -1)
        ]

        while head < queue.count {
            let (x, y) = queue[head]
            head += 1

            for (dx, dy) in directions {
                let nx = x + dx
                let ny = y + dy

                guard nx >= 0, nx < m,
                      ny >= 0, ny < n else {
                    continue
                }

                if height[nx][ny] != -1 {
                    continue
                }

                height[nx][ny] = height[x][y] + 1
                queue.append((nx, ny))
            }
        }
        return height
    }

    func watchedVideosByFriends(_ watchedVideos: [[String]], _ friends: [[Int]], _ id: Int, _ level: Int) -> [String] {
        let n = friends.count
        var visited = Array(repeating: false, count: n)
        var queue = [id]
        visited[id] = true

        var currentLevel = 0

        while currentLevel < level {
            let size = queue.count

            for _ in 0..<size {
                let person = queue.removeFirst()

                for friend in friends[person] {
                    if !visited[friend] {
                        visited[friend] = true
                        queue.append(friend)
                    }
                }
            }

            currentLevel += 1
        }

        var frequencies = [String: Int]()

        for person in queue {
            for video in watchedVideos[person] {
                frequencies[video, default: 0] += 1
            }
        }

        return frequencies
            .sorted {
                if $0.value == $1.value {
                    return $0.key < $1.key
                }
                return $0.value < $1.value
            }
            .map { $0.key }
    }

    func shortestAlternatingPaths(_ n: Int, _ redEdges: [[Int]], _ blueEdges: [[Int]]) -> [Int] {
        var graph = Array(repeating: [(Int, Int)](), count: n)
        
        // 빨강 0
        for edge in redEdges { 
            graph[edge[0]].append((edge[1], 0))
        }
        // 파랑 1
        for edge in blueEdges { 
            graph[edge[0]].append((edge[1], 1))
        }

        var answer = Array(repeating: -1, count: n)

        var visited = Array(repeating: Array(repeating: false, count: 2), count: n)
        // 현재 정점, 직전 간선 색상, 타고온 간선의 총 거리
        var queue = [(Int, Int, Int)]()

        queue.append((0, 0, 0))
        queue.append((0, 1, 0))

        visited[0][0] = true
        visited[0][1] = true

        var head = 0

        while head < queue.count {
            let (node, lastColor, dist) = queue[head]
            head += 1

            if answer[node] == -1 || answer[node] > dist {
                answer[node] = dist
            }

            for (next, color) in graph[node] {
                if color == lastColor {
                    continue
                }

                if visited[next][color] {
                    continue
                }

                visited[next][color] = true
                queue.append((next, color, dist + 1))
            }
        }

        return answer
    }

    func numBusesToDestination(_ routes: [[Int]], _ source: Int, _ target: Int) -> Int {
        if source == target {
            return 0
        }

        // 정류장 -> 해당 정류장을 지나는 버스들
        var stopToBuses = [Int: [Int]]()

        for (bus, route) in routes.enumerated() {
            for stop in route {
                stopToBuses[stop, default: []].append(bus)
            }
        }

        var queue = [(bus: Int, count: Int)]()
        var index = 0

        var visitedBus = Set<Int>()
        var visitedStop = Set<Int>()

        // source에서 탈 수 있는 모든 버스
        for bus in stopToBuses[source] ?? [] {
            queue.append((bus, 1))
            visitedBus.insert(bus)
        }
        visitedStop.insert(source)

        while index < queue.count {
            let (bus, count) = queue[index]
            index += 1

            // 현재 버스로 갈 수 있는 모든 정류장 탐색
            for stop in routes[bus] {
                if stop == target {
                    return count
                }

                if visitedStop.contains(stop) {
                    continue
                }
                visitedStop.insert(stop)

                // 해당 정류장에서 갈아탈 수 있는 버스들
                for nextBus in stopToBuses[stop] ?? [] {
                    if visitedBus.contains(nextBus) {
                        continue
                    }

                    visitedBus.insert(nextBus)
                    queue.append((nextBus, count + 1))
                }
            }
        }

        return -1
    }

}