//Q1. Longest Consecutive Sequence
//정렬을 안 쓰고 유니온 파인드로 풀기는 DSU 클래스 만들어서 푸는 풀이를 추천하던데
//억DSU 같아서 Set 기반 선형 탐색으로 풀었음..
class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> numSet = new HashSet<>();

        for(int num : nums){
            numSet.add(num);
        }

        int longest = 0;

        for(int num : numSet){
            if(!numSet.contains(num-1)){
                int cur = num;
                int length = 1;

                while(numSet.contains(cur+1)){
                    cur++;
                    length++;
                }

                longest = Math.max(longest, length);
            }
        }

        return longest;
    }
}

//Q2. Largest Component Size by Common Factor
//전 문제에서 dsu 연습 안한거 후회했던 문제
//각 숫자를 소인수분해하고 같은 소인수를 가진 숫자들의 인덱스를 유니온 파인드로 합침
//그렇게해서 나온 각 집합의 원소 개수를 구하고 가장 큰 집합 크기 반환
class Solution {
    static class DSU {
        int[] parent;
        int[] size;

        DSU(int n) {
            parent = new int[n];
            size = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        void union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);

            if (rootA == rootB) return;

            if (size[rootA] < size[rootB]) {
                int temp = rootA;
                rootA = rootB;
                rootB = temp;
            }

            parent[rootB] = rootA;
            size[rootA] += size[rootB];
        }
    }

    public int largestComponentSize(int[] nums) {
        int n = nums.length;
        DSU dsu = new DSU(n);

        // 소인수 -> 그 소인수를 처음 가진 nums의 인덱스
        Map<Integer, Integer> factorOwner = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int x = nums[i];

            for (int f = 2; f * f <= x; f++) {
                if (x % f == 0) {
                    connectByFactor(factorOwner, dsu, f, i);

                    while (x % f == 0) {
                        x /= f;
                    }
                }
            }

            if (x > 1) {
                connectByFactor(factorOwner, dsu, x, i);
            }
        }

        int[] count = new int[n];
        int answer = 0;

        for (int i = 0; i < n; i++) {
            int root = dsu.find(i);
            count[root]++;
            answer = Math.max(answer, count[root]);
        }

        return answer;
    }

    private void connectByFactor(Map<Integer, Integer> factorOwner, DSU dsu, int factor, int index) {
        if (!factorOwner.containsKey(factor)) {
            factorOwner.put(factor, index);
        } else {
            dsu.union(index, factorOwner.get(factor));
        }
    }

}

//Q3. Process Restricted Friend Requests
//요구사항이 복잡하다고 느꼈던 문제이지만 실제 관계를 생각해보니 이해할 수 있었다
class Solution {
    int[] parent;
    public boolean[] friendRequests(int n, int[][] restrictions, int[][] requests) {
        boolean[] result = new boolean[requests.length];
        parent = new int[n];

        for(int i = 0; i<n; i++){
            parent[i] = i;
        }

        for(int i = 0; i<requests.length; i++){
            int u = requests[i][0];
            int v = requests[i][1];

            int rootU = find(u);
            int rootV = find(v);

            if(rootU == rootV){
                result[i] = true;
                continue;
            }

            boolean blocked = false;

            for(int[] res : restrictions){
                int x = res[0];
                int y = res[1];

                int rootX = find(x);
                int rootY = find(y);

                //금지 관계의 한쪽이 u 그룹에 있고, 다른 한쪽이 v 그룹에 있다면
                if((rootX == rootU && rootY == rootV) ||
                    (rootX == rootV && rootY == rootU)) {
                    blocked = true;
                    break;
                }
            }

            //친구요청이 막혔으면 false 아니라면 true
            if(blocked){
                result[i] = false;
            }
            else{
                union(u,v);
                result[i] = true;
            }
        }
        return result;
    }

    //친구가 속한 그룹의 대표를 찾는다
    private int find(int x){
        if(parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    //친구요청이 성공하면 두 그룹을 합친다
    private void union(int a, int b){
        a = find(a);
        b = find(b);

        if(a != b){
            parent[b] = a;
        }
    }




}