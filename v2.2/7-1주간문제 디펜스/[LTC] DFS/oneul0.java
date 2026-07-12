//Q1. Find if Path Exists in Graph
class Solution {
    boolean[] visited;
    List<List<Integer>> gr = new ArrayList<>();
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        if(n == 1 || (source == destination)) return true;
        visited = new boolean[n];
        for(int i = 0; i<n; i++){
            gr.add(new ArrayList<>());
        }
        for(int[] edge : edges){
            gr.get(edge[0]).add(edge[1]);
            gr.get(edge[1]).add(edge[0]);
        }
        dfs(source, destination);
        return visited[destination];
    }
    private void dfs(int cur, int dest){
        if(cur == dest) return;

        for(int next : gr.get(cur)){
            if(!visited[next]){
                visited[next] = true;
                dfs(next, dest);
            }
        }
    }
}



//Q2. All Paths From Source to Target
class Solution {
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> answer = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        path.add(0);
        dfs(0, graph, path, answer);
        return answer;
    }

    private void dfs(int cur, int[][] graph, List<Integer> path, List<List<Integer>> answer){
        if(cur == graph.length-1){
            answer.add(new ArrayList<>(path));
            return;
        }

        for(int next : graph[cur]){
            path.add(next);
            dfs(next, graph, path, answer);
            path.remove(path.size()-1);
        }
    }
}

//Q3. Minimum Score of a Path Between Two Cities
class Solution {
    class Pair {
        int to;
        int cost;

        Pair(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    List<List<Pair>> gr;
    boolean[] visited;
    int minCost = Integer.MAX_VALUE;

    public int minScore(int n, int[][] roads) {
        gr = new ArrayList<>();
        visited = new boolean[n + 1];

        for (int i = 0; i <= n; i++) {
            gr.add(new ArrayList<>());
        }

        for (int[] road : roads) {
            int from = road[0];
            int to = road[1];
            int cost = road[2];

            gr.get(from).add(new Pair(to, cost));
            gr.get(to).add(new Pair(from, cost));
        }

        dfs(1);

        return minCost;
    }

    private void dfs(int cur) {
        visited[cur] = true;

        for (Pair next : gr.get(cur)) {
            minCost = Math.min(minCost, next.cost);

            if (!visited[next.to]) {
                dfs(next.to);
            }
        }
    }
}

//Q4. Island Perimeter
class Solution {
    int row, col;
    int perimeter = 0;
    int[][] arr;
    boolean[][] visited;

    int[] dx = {-1,1,0,0};
    int[] dy = {0,0,-1,1};
    public int islandPerimeter(int[][] grid) {
        row = grid.length+2;
        col = grid[0].length+2;

        arr = new int[row][col];
        visited = new boolean[row][col];

        for(int i=1; i<row-1; i++) {
            for(int j=1; j<col-1; j++) {
                arr[i][j] = grid[i-1][j-1];
            }
        }

        for(int i=0; i<row; i++) {
            for(int j=0; j<col; j++) {
                if(arr[i][j]==0 && !visited[i][j]) {
                    dfs(i, j);
                }
            }
        }

        return perimeter;
    }

    private void dfs(int cx, int cy) {
        visited[cx][cy] = true;

        for(int i=0; i<4; i++) {
            int nx = cx + dx[i];
            int ny = cy + dy[i];

            if (nx<0 || ny<0 || nx>=row || ny>=col) continue;

            if (arr[nx][ny] == 1) {
                perimeter++;
                continue;
            }

            if (!visited[nx][ny]) {
                dfs(nx, ny);
            }
        }
    }
}

//Q5. Count the Number of Complete Components
//그냥 사이클 찾는 문제가 아니라 그래프를 구성하는 각 요소끼리 연결되어 있어야 함
class Solution {
    boolean[] visited;
    int vertexCount;
    int edgeCount; //여기서 말하는 edge는 더 정확하게는 degree임 각 노드에 진입점이 몇 개인지
    List<List<Integer>> gr = new ArrayList<>();
    public int countCompleteComponents(int n, int[][] edges) {
        for(int i = 0; i<n; i++){
            gr.add(new ArrayList<>());
        }
        for(int[] edge : edges){
            gr.get(edge[0]).add(edge[1]);
            gr.get(edge[1]).add(edge[0]);
        }

        visited = new boolean[n];
        int answer = 0;
        for(int i  =0; i<n; i++){
            if(!visited[i]){
                vertexCount = 0;
                edgeCount = 0;


                dfs(i);

                if(edgeCount == vertexCount * ( vertexCount-1)){
                    answer++;
                }
            }
        }
        return answer;
    }

    public void dfs(int cur){
        visited[cur] = true;
        vertexCount++;
        edgeCount += gr.get(cur).size();

        for(int next : gr.get(cur)){
            if(!visited[next]){
                visited[next] = true;
                dfs(next);
            }
        }
    }
}