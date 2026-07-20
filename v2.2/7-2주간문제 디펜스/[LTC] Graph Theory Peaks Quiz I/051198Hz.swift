class Solution {
    func canVisitAllRooms(_ rooms: [[Int]]) -> Bool {
        var visited = Array(repeating: false, count: rooms.count)
        var queue = [0]
        var index = 0
        
        visited[0] = true
        var count = 1
        
        while index < queue.count {
            let room = queue[index]
            index += 1
            
            for next in rooms[room] {
                if visited[next] {
                    continue
                }
                
                visited[next] = true
                count += 1
                queue.append(next)
            }
        }
        
        return count == rooms.count
    }
}