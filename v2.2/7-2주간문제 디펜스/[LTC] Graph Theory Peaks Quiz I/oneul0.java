//Q1. Keys and Rooms
//bfs
class Solution {

    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int n = rooms.size();

        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<>();

        visited[0] = true;
        queue.offer(0);

        int count = 1;

        while (!queue.isEmpty()) {
            int currentRoom = queue.poll();

            for (int key : rooms.get(currentRoom)) {
                if (visited[key]) {
                    continue;
                }

                visited[key] = true;
                count++;
                queue.offer(key);
            }
        }

        return count == n;
    }
}