//Q1. Network Delay Time
class Solution {
    class Pair implements Comparable<Pair> {
        int to;
        int time;

        Pair(int to, int time) {
            this.to = to;
            this.time = time;
        }

        @Override
        public int compareTo(Pair o) {
            return Integer.compare(this.time, o.time);
        }
    }

    public int networkDelayTime(int[][] times, int n, int k) {
        List<List<Pair>> gr = new ArrayList<>();

        for (int i = 0; i <= n; i++) {
            gr.add(new ArrayList<>());
        }

        for (int[] time : times) {
            gr.get(time[0]).add(new Pair(time[1], time[2]));
        }

        return dijkstra(gr, n, k);
    }

    private int dijkstra(List<List<Pair>> gr, int n, int k) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[k] = 0;
        pq.offer(new Pair(k, 0));

        while (!pq.isEmpty()) {
            Pair cur = pq.poll();

            if (cur.time > dist[cur.to]) continue;

            for (Pair next : gr.get(cur.to)) {
                int newTime = cur.time + next.time;

                if (newTime < dist[next.to]) {
                    dist[next.to] = newTime;
                    pq.offer(new Pair(next.to, newTime));
                }
            }
        }

        int answer = 0;

        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) return -1;
            answer = Math.max(answer, dist[i]);
        }

        return answer;
    }
}

//Q2. Minimum Weighted Subgraph With the Required Paths
class Solution {

    class Pair implements Comparable<Pair> {
        int to;
        long cost;

        Pair(int to, long cost) {
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Pair other) {
            return Long.compare(this.cost, other.cost);
        }
    }

    private static final long INF = Long.MAX_VALUE / 4;

    public long minimumWeight(int n, int[][] edges, int src1, int src2, int dest) {
        List<List<Pair>> graph = new ArrayList<>();
        List<List<Pair>> reverseGraph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            reverseGraph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int weight = edge[2];

            graph.get(from).add(new Pair(to, weight));
            reverseGraph.get(to).add(new Pair(from, weight));
        }

        long[] dist1 = dijkstra(n, graph, src1);
        long[] dist2 = dijkstra(n, graph, src2);
        long[] distToDest = dijkstra(n, reverseGraph, dest);

        long answer = INF;

        for (int i = 0; i < n; i++) {
            if (dist1[i] == INF || dist2[i] == INF || distToDest[i] == INF) continue;

            answer = Math.min(answer, dist1[i] + dist2[i] + distToDest[i]);
        }

        return answer == INF ? -1 : answer;
    }

    private long[] dijkstra(int n, List<List<Pair>> graph, int start) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();

        long[] dist = new long[n];
        Arrays.fill(dist, INF);

        dist[start] = 0;
        pq.offer(new Pair(start, 0));

        while (!pq.isEmpty()) {
            Pair cur = pq.poll();

            if (cur.cost > dist[cur.to]) continue;

            for (Pair next : graph.get(cur.to)) {
                long newCost = cur.cost + next.cost;

                if (newCost < dist[next.to]) {
                    dist[next.to] = newCost;
                    pq.offer(new Pair(next.to, newCost));
                }
            }
        }

        return dist;
    }
}

//Q3. Design Graph With Shortest Path Calculator
class Graph {
    static final long INF = Long.MAX_VALUE / 4;

    final int n;
    final long[][] dist;

    public Graph(int n, int[][] edges) {
        this.n = n;
        this.dist = new long[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }

        // 초기 간선 등록
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int cost = edge[2];

            dist[from][to] = Math.min(dist[from][to], cost);
        }

        // 모든 정점 쌍의 최단 거리 계산
        for (int k = 0; k < n; k++) {
            for (int from = 0; from < n; from++) {
                if (dist[from][k] == INF) {
                    continue;
                }

                for (int to = 0; to < n; to++) {
                    if (dist[k][to] == INF) {
                        continue;
                    }

                    dist[from][to] = Math.min(
                        dist[from][to],
                        dist[from][k] + dist[k][to]
                    );
                }
            }
        }
    }

    public void addEdge(int[] edge) {
        int newFrom = edge[0];
        int newTo = edge[1];
        long newCost = edge[2];

        //기존 최단 거리보다 비싼 간선이면 갱신할 필요 없음
        if (dist[newFrom][newTo] <= newCost) {
            return;
        }

        //새 간선을 포함하는 모든 경로를 확인
        for (int from = 0; from < n; from++) {
            if (dist[from][newFrom] == INF) {
                continue;
            }

            for (int to = 0; to < n; to++) {
                if (dist[newTo][to] == INF) {
                    continue;
                }

                long newDistance =
                    dist[from][newFrom] + newCost + dist[newTo][to];

                dist[from][to] = Math.min(dist[from][to], newDistance);
            }
        }
    }

    public int shortestPath(int node1, int node2) {
        if (dist[node1][node2] == INF) {
            return -1;
        }

        return (int) dist[node1][node2];
    }
}

/**
 * Your Graph object will be instantiated and called as such:
 * Graph obj = new Graph(n, edges);
 * obj.addEdge(edge);
 * int param_2 = obj.shortestPath(node1,node2);
 */


//Q4. Find the City With the Smallest Number of Neighbors at a Threshold Distance
class Solution {

    class Pair {
        int node;
        int cost;

        Pair(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }
    }

    List<List<Pair>> gr = new ArrayList<>();
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {

        for (int i = 0; i < n; i++) {
            gr.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int weight = edge[2];

            gr.get(from).add(new Pair(to, weight));
            gr.get(to).add(new Pair(from, weight));
        }

        int answer = -1;
        int minCount = Integer.MAX_VALUE;

        for (int city = 0; city < n; city++) {
            int reachableCount = dijkstra(city, n, distanceThreshold);

            if (reachableCount <= minCount) {
                minCount = reachableCount;
                answer = city;
            }
        }

        return answer;
    }

    private int dijkstra(int start, int n, int distanceThreshold) {
        int[] distance = new int[n];
        Arrays.fill(distance, Integer.MAX_VALUE);

        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.cost, b.cost));

        distance[start] = 0;
        pq.offer(new Pair(start, 0));

        while (!pq.isEmpty()) {
            Pair current = pq.poll();

            int currentNode = current.node;
            int currentCost = current.cost;

            // 이미 더 짧은 경로가 처리된 경우
            if (currentCost > distance[currentNode]) continue;

            // 제한 거리를 초과하면 탐색할 필요가 없음
            if (currentCost > distanceThreshold) continue;

            for (Pair next : gr.get(currentNode)) {
                int nextNode = next.node;
                int nextCost = currentCost + next.cost;

                if (nextCost > distanceThreshold) {
                    continue;
                }

                if (nextCost < distance[nextNode]) {
                    distance[nextNode] = nextCost;
                    pq.offer(new Pair(nextNode, nextCost));
                }
            }
        }

        int count = 0;

        for (int city = 0; city < n; city++) {
            if (city != start && distance[city] <= distanceThreshold) {
                count++;
            }
        }

        return count;
    }
}