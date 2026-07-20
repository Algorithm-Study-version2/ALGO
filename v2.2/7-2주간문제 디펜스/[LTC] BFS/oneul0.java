//Q1. Map of Highest Peak
class Solution {
	int n, m;
	public int[][] highestPeak(int[][] isWater) {
		n = isWater.length;
		m = isWater[0].length;
		int[][] height = new int[n][m];
		Queue<int[]> q = new ArrayDeque<>();
		boolean[][] visited =new boolean[n][m];
		for(int i = 0; i<n; i++){
			for(int j = 0; j<m; j++){
				if(isWater[i][j] == 1){
					q.offer(new int[]{i,j,0});
					visited[i][j] = true;
				}
			}
		}
		bfs(q, visited, height);
		return height;
	}
	private void bfs(Queue<int[]> q, boolean[][] visited, int[][] height){
		int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
		while(!q.isEmpty()){
			int[] cur = q.poll();

			for(int i = 0; i<4; i++){
				int nx = cur[0] + dx[i];
				int ny = cur[1] + dy[i];

				if(nx<0 || nx>=n || ny<0 || ny>=m || visited[nx][ny]) continue;
				q.offer(new int[]{nx,ny,cur[2]+1});
				visited[nx][ny] = true;
				height[nx][ny] = cur[2]+1;
			}
		}
	}
}

//Q2. Get Watched Videos by Your Friends
class Solution {
	class Pair {
		String name;
		int count;

		Pair(String name, int count) {
			this.name = name;
			this.count = count;
		}
	}

	public List<String> watchedVideosByFriends(List<List<String>> watchedVideos, int[][] friends, int id, int level) {
		int n = friends.length;

		List<Integer> people = bfs(friends, id, n, level);

		Map<String, Integer> countMap = new HashMap<>();

		for (int person : people) {
			for (String video : watchedVideos.get(person)) {
				countMap.put(video, countMap.getOrDefault(video, 0)+1);
			}
		}

		List<Pair> videoCounts = new ArrayList<>();

		for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
			videoCounts.add(new Pair(entry.getKey(), entry.getValue()));
		}

		videoCounts.sort((a, b) -> {
			if (a.count != b.count) {
				return Integer.compare(a.count, b.count);
			}

			return a.name.compareTo(b.name);
		});

		List<String> answer = new ArrayList<>();

		for (Pair pair : videoCounts) {
			answer.add(pair.name);
		}

		return answer;
	}

	private List<Integer> bfs(int[][] friends, int start, int n, int level) {
		Queue<int[]> queue = new ArrayDeque<>();
		boolean[] visited = new boolean[n];
		List<Integer> people = new ArrayList<>();

		queue.offer(new int[]{start, 0});
		visited[start] = true;

		while (!queue.isEmpty()) {
			int[] current = queue.poll();
			int person = current[0];
			int distance = current[1];

			if (distance == level) {
				people.add(person);
				continue;
			}

			for (int next : friends[person]) {
				if (!visited[next]) {
					visited[next] = true;
					queue.offer(new int[]{next, distance+1});
				}
			}
		}

		return people;
	}
}

//Q3. Shortest Path with Alternating Colors
//색깔 같은지만 판단(boolean)하니까 이전과 같은지를 판단할 수 없어서 color 자료형 변경
class Solution {
	class Edge {
		int to;
		int color; // 1: red, 2: blue

		Edge(int to, int color) {
			this.to = to;
			this.color = color;
		}
	}

	public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
		List<List<Edge>> graph = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}

		for (int[] edge : redEdges) {
			graph.get(edge[0]).add(new Edge(edge[1], 1));
		}

		for (int[] edge : blueEdges) {
			graph.get(edge[0]).add(new Edge(edge[1], 2));
		}

		int[] answer = new int[n];
		Arrays.fill(answer, -1);
		answer[0] = 0;

		Queue<int[]> queue = new ArrayDeque<>();

		queue.offer(new int[]{0, 0, 1}); // node, dist, color
		queue.offer(new int[]{0, 0, 2});

		boolean[][] visited = new boolean[n][3]; //node, color
		visited[0][1] = true;
		visited[0][2] = true;

		while (!queue.isEmpty()) {
			int[] current = queue.poll();

			int node = current[0];
			int distance = current[1];
			int lastColor = current[2];

			for (Edge next : graph.get(node)) {
				if (next.color == lastColor) continue;

				if (visited[next.to][next.color]) continue;

				visited[next.to][next.color] = true;

				int nextDistance = distance + 1;

				if (answer[next.to] == -1) {
					answer[next.to] = nextDistance;
				} else {
					answer[next.to] = Math.min(answer[next.to], nextDistance);
				}

				queue.offer(new int[]{next.to, nextDistance, next.color});
			}
		}

		return answer;
	}
}

//Q4. Bus Routes
//버스랑 정류장 관계가 어떻게 되는지 이해하는데 시간이 걸렸던 문제
//따라서 주어진대로 정류장 번호(인덱스)를 지나는 버스 번호 목록을 만들어서 참조할 수 있도록 함
class Solution {
	class Pair {
		int stop;
		int busCount;

		Pair(int stop, int busCount) {
			this.stop = stop;
			this.busCount = busCount;
		}
	}

	// 정류장 번호 -> 해당 정류장을 지나는 버스 번호 목록
	Map<Integer, List<Integer>> stopToBuses = new HashMap<>();
	public int numBusesToDestination(int[][] routes, int source, int target) {
		if (source == target) return 0;

		for (int bus = 0; bus < routes.length; bus++) {
			for (int stop : routes[bus]) {
				stopToBuses.computeIfAbsent(stop, key -> new ArrayList<>()).add(bus);
			}
		}

		return bfs(source, target, routes);
	}

	private int bfs(int source, int target, int[][] routes) {
		Queue<Pair> queue = new ArrayDeque<>();
		boolean[] visitedBuses = new boolean[routes.length];
		Set<Integer> visitedStops = new HashSet<>();

		queue.offer(new Pair(source, 0));
		visitedStops.add(source);

		while (!queue.isEmpty()) {
			Pair cur = queue.poll();

			// 현재 정류장을 지나는 버스
			List<Integer> buses = stopToBuses.getOrDefault(cur.stop,Collections.emptyList());

			for (int bus : buses) {
				// 이미 노선 전체를 확인한 버스라면
				if (visitedBuses[bus]) continue;

				visitedBuses[bus] = true;
				int nextBusCount = cur.busCount + 1;

				// 이 버스가 지나는 모든 정류장으로 이동 가능
				for (int nextStop : routes[bus]) {
					if (nextStop == target) return nextBusCount;

					if (visitedStops.add(nextStop)) {
						queue.offer(new Pair(nextStop, nextBusCount));
					}
				}
			}
		}

		return -1;
	}
}