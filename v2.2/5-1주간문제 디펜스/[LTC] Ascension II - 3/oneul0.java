class Solution {
    public int maximalRectangle(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] prefix = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                prefix[i][j] =
                    matrix[i - 1][j - 1] - '0'
                        + prefix[i - 1][j]
                        + prefix[i][j - 1]
                        - prefix[i - 1][j - 1];
            }
        }

        int answer = 0;
        for (int x1 = 0; x1 < n; x1++) {
            for (int y1 = 0; y1 < m; y1++) {
                for (int x2 = x1; x2 < n; x2++) {
                    for (int y2 = y1; y2 < m; y2++) {
                        int ones =
                            prefix[x2 + 1][y2 + 1]
                                - prefix[x1][y2 + 1]
                                - prefix[x2 + 1][y1]
                                + prefix[x1][y1];

                        int area = (x2 - x1 + 1) * (y2 - y1 + 1);

                        if (ones == area) {
                            answer = Math.max(answer, area);
                        }
                    }
                }
            }
        }

        return answer;
    }
}
//우하단 꼭짓점을 기준으로 하는 누적합
//우하단 꼭짓점(x,y)일때 x*y == 누적합이면 될까
//(0,0)~(n,m) 행렬에서 시작 지점을 (x1, y1), 끝 지점을 (x2, y2) 라고 했을 때
//(0,0), (x1-1,y2) / (0,0), (x2, y1-1) 부분을 빼고, (0,0), (x1-1, y1-1) 한번 더해줘야함