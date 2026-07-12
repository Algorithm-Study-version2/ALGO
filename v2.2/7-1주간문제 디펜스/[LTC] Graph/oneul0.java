//Q1. Minimum Number of Vertices to Reach All Nodes
class Solution {
    public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
        int[] inbound = new int[n];
        List<Integer> result = new ArrayList<>();

        for(List<Integer> edge : edges){
            inbound[edge.get(1)]++;
        }

        //DAG 상의 어떤 노드가 indegree가 있으면 반드시 다른 노드로부터 도달 가능
        for(int i = 0; i<n; i++){
            if(inbound[i] == 0){
                result.add(i);
            }
        }
        return result;
    }
}

//Q2. Add Edges to Make Degrees of All Nodes Even
//짝수 노드는 그대로 두고 홀수 노드가 몇 개인지 먼저 찾아서 해결 가능
//홀수 노드가 만약 2개라면 그 2개 노드를 잇는 간선을 만들어서 짝수로 만들기 가능
//4개라면 그 노드들 사이에 연결이 2개 생기면 가능
//간선을 2개까지만 더 그을 수 있으므로 이외의 경우는 불가능
class Solution {
    Set<String> set;
    public boolean isPossible(int n, List<List<Integer>> edges) {
        int[] degree = new int[n+1];
        set = new HashSet<>();

        List<List<Integer>> gr = new ArrayList<>();

        for(int i = 0; i<=n; i++){
            gr.add(new ArrayList<>());
        }
        for(List<Integer> edge : edges){
            gr.get(edge.get(0)).add(edge.get(1));
            gr.get(edge.get(1)).add(edge.get(0));
            degree[edge.get(0)]++;
            degree[edge.get(1)]++;
            int a = Math.min(edge.get(0), edge.get(1));
            int b = Math.max(edge.get(0), edge.get(1));
            set.add(a + ":" + b);
        }

        //홀수 개수인 노드 찾기
        List<Integer> nodes = new ArrayList<>();
        for(int i =1; i<=n; i++){
            if(degree[i] % 2 == 1){
                nodes.add(i);
            }
        }

        if(nodes.size() == 0) {
            return true;
        }
        else if(nodes.size() == 2) {
            int a = Math.min(nodes.get(0), nodes.get(1));
            int b = Math.max(nodes.get(0), nodes.get(1));
            if (!hasEdge(a, b)) return true;

            for (int i = 1; i <= n; i++) {
                if (i == a || i == b) continue;

                if (!hasEdge(a, i) && !hasEdge(b, i)) {
                    return true;
                }
            }

            return false;
        }
        else if(nodes.size() == 4){
            int a = Math.min(nodes.get(0), nodes.get(1));
            int b = Math.max(nodes.get(0), nodes.get(1));
            int c = Math.min(nodes.get(2), nodes.get(3));
            int d = Math.max(nodes.get(2), nodes.get(3));

            if (!hasEdge(a,b) && !hasEdge(c,d)) return true;
            if (!hasEdge(a,c) && !hasEdge(b,d)) return true;
            if (!hasEdge(a,d) && !hasEdge(b,c)) return true;
        }

        return false;
    }

    private boolean hasEdge(int a, int b){
        int minVal = Math.min(a, b);
        int maxVal = Math.max(a,b);
        return set.contains(minVal+":"+maxVal);
    }
}

//Q3. Maximum Path Quality of a Graph
//중요 조건
//한 노드를 여러번 방문할 수 있지만 품질은 중첩되지 않음
//그래프를 순회해서 최대 품질을 만드는 문제
class Solution {
    int answer;
    int[] values;
    int[] visitCount;
    List<int[]>[] gr;
    int maxTime;

    public int maximalPathQuality(int[] values, int[][] edges, int maxTime) {
        this.values = values;
        this.maxTime = maxTime;

        int n = values.length;
        gr = new ArrayList[n];
        visitCount = new int[n];

        for (int i = 0; i < n; i++) {
            gr[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int time = edge[2];

            gr[u].add(new int[]{v, time});
            gr[v].add(new int[]{u, time});
        }

        visitCount[0] = 1;
        dfs(0, 0, values[0]);

        return answer;
    }

    private void dfs(int node, int time, int quality) {
        if (node == 0) {
            answer = Math.max(answer, quality);
        }

        for (int[] edge : gr[node]) {
            int next = edge[0];
            int cost = edge[1];

            if (time + cost > maxTime) {
                continue;
            }

            boolean firstVisit = visitCount[next] == 0;

            visitCount[next]++;

            dfs(next, time + cost, quality + (firstVisit ? values[next] : 0));

            visitCount[next]--;
        }
    }
}