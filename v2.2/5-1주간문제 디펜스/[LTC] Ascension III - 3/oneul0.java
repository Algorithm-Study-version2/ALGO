class Solution {
    class Node {
        int node, cost;
        Node(int node, int cost){
            this.node = node;
            this.cost = cost;
        }
    }
    List<List<Node>> gr = new ArrayList<>();
    int[] answer;
    public int[] minEdgeReversals(int n, int[][] edges) {
        answer = new int[n];
        for(int i = 0; i<n; i++){
            gr.add(new ArrayList<>());
        }
        for(int[] edge : edges){
            gr.get(edge[0]).add(new Node(edge[1], 0));
            gr.get(edge[1]).add(new Node(edge[0], 1));
        }


        answer[0] = getConnCount(0, -1);
        reRoot(0,-1);
        return answer;
    }

    public int getConnCount(int node, int parent){
        int total = 0;
        for(Node next : gr.get(node)){
            int to = next.node;
            int cost = next.cost;
            if(to==parent) continue;
            total += cost;
            total += getConnCount(to, node);
        }
        return total;
    }

    public void reRoot(int node, int parent){
        for(Node next : gr.get(node)){
            int to = next.node;
            int cost = next.cost;
            if(to == parent) continue;
            if(cost == 0) answer[to] = answer[node] + 1;
            else answer[to] = answer[node] -1;

            reRoot(to, node);
        }
    }
}

//각 노드에서 출발해서 모든 노드로 닿기위해 필요한 뒤집기 수
//원본 그래프는 변하지 않음
//