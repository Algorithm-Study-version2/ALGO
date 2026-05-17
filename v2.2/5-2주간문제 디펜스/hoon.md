# 5-2주간문제 디펜스 hoon 오답노트

# 2672. Number of Adjacent Elements With the Same Color

## 문제 유형

```
배열
```

## 틀린 부분

각 쿼리마다 배열 전체를 다시 확인할 필요가 없음

색이 바뀌는 위치 `idx` 주변의 관계만 변하므로, `idx-1`, `idx+1`만 확인하면 됨

## 핵심 아이디어

현재 같은 색으로 인접한 쌍의 개수를 `count`로 유지

색을 바꾸기 전에 기존 색 때문에 만들어져 있던 인접 쌍을 먼저 제거하고, 색을 바꾼 뒤 새 색으로 만들어지는 인접 쌍을 다시 더함

```
1. oldColor 기준으로 좌우 인접 쌍 제거
2. colors[idx]를 newColor로 업데이트
3. newColor 기준으로 좌우 인접 쌍 추가
4. answer[q] = count
```

기존 색이 없는 경우, 즉 `oldColor == 0`이면 제거할 인접 쌍이 없음

또한 배열 범위를 벗어나지 않도록 `idx > 0`, `idx + 1 < n` 조건을 반드시 확인해야 함

## 전체 코드

```java
class Solution {
    public int[] colorTheArray(int n, int[][] queries) {
        int[] colors = new int[n];
        int[] answer = new int[queries.length];

        int count = 0;

        for (int q = 0; q < queries.length; q++) {
            int idx = queries[q][0];
            int newColor = queries[q][1];
            int oldColor = colors[idx];

            if (oldColor != 0) {
                if (idx > 0 && colors[idx - 1] == oldColor) {
                    count--;
                }

                if (idx + 1 < n && colors[idx + 1] == oldColor) {
                    count--;
                }
            }

            colors[idx] = newColor;

            if (idx > 0 && colors[idx - 1] == newColor) {
                count++;
            }

            if (idx + 1 < n && colors[idx + 1] == newColor) {
                count++;
            }

            answer[q] = count;
        }

        return answer;
    }
}
```

---

# 2791. Count Paths That Can Form a Palindrome in a Tree

## 문제 유형

```
트리 / DFS / 비트마스크
```

## 틀린 부분

경로 문자열을 직접 만들거나, 매번 알파벳 개수를 세면 터짐

팰린드롬 가능 여부는 문자 개수 전체가 아니라 각 문자의 등장 횟수가 홀수인지 짝수인지만 알면 됨

## 핵심 아이디어

어떤 문자열을 재배열해서 팰린드롬으로 만들 수 있으려면 홀수 번 등장한 문자가 0개 또는 1개여야 함

각 노드까지의 루트 경로에서 문자의 홀짝 상태를 비트마스크로 저장함

두 노드의 mask를 XOR 했을 때 0이면 홀수 문자가 0개, 비트가 1개만 켜져 있으면 홀수 문자가 1개이면 팰린드롬 가능

`count`는 지금까지 나온 mask의 개수를 저장한다.

현재 노드를 처리할 때는 먼저 이전 mask들과 비교해서 정답에 더한 뒤, 현재 mask를 `count`에 추가한다.

또한 정답 개수가 커질 수 있으므로 `answer`는 `long`을 사용한다.

## 전체 코드

```java
import java.util.*;

class Solution {
    public long countPalindromePaths(List<Integer> parent, String s) {
        int n = parent.size();

        List<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            int p = parent.get(i);
            int bit = 1 << (s.charAt(i) - 'a');

            graph[p].add(new int[]{i, bit});
        }

        Map<Integer, Integer> count = new HashMap<>();
        Deque<int[]> stack = new ArrayDeque<>();

        long answer = 0;

        stack.push(new int[]{0, 0});

        while (!stack.isEmpty()) {
            int[] cur = stack.pop();

            int node = cur[0];
            int mask = cur[1];

            answer += count.getOrDefault(mask, 0);

            for (int i = 0; i < 26; i++) {
                int target = mask ^ (1 << i);
                answer += count.getOrDefault(target, 0);
            }

            count.put(mask, count.getOrDefault(mask, 0) + 1);

            for (int[] next : graph[node]) {
                int child = next[0];
                int bit = next[1];

                stack.push(new int[]{child, mask ^ bit});
            }
        }

        return answer;
    }
}
```

---

# 2472. Maximum Number of Non-overlapping Palindrome Substrings

## 문제 유형

```
문자열 / 팰린드롬 / 2차원 DP / 1차원 DP
```

## 헷갈린 부분

슬라이딩 윈도우처럼 인줄 알았는데 DP 문제였음

겹치지 않는 substring을 골라야 하므로, 어떤 구간을 마지막 팰린드롬으로 선택했을 때 그 이전 구간의 최댓값을 이어 붙이는 방식으로 생각해야 함

## 핵심 아이디어

먼저 `isPal[left][right]`로 모든 substring의 팰린드롬 여부 미리 계산

그 다음 `dp[end]`를 다음과 같이 정의

```
dp[end] = s[0 ~ end - 1]에서 고를 수 있는 non-overlapping palindrome substring의 최대 개수
```

마지막으로 선택하는 팰린드롬이 `s[start ~ end - 1]`라면, 이전 구간은 `s[0 ~ start - 1]`까지만 사용 가능

```
dp[end] = max(dp[end], dp[start] + 1)
```

`end`를 포함하지 않는 경우도 있으므로 먼저 `dp[end] = dp[end - 1]`로 둬야함

또한 길이가 최소 `k` 이상인 substring만 골라야 하므로 `start <= end - k` 범위만 확인

## 전체 코드

```java
class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();

        boolean[][] isPal = new boolean[n][n];

        for (int len = 1; len <= n; len++) {
            for (int left = 0; left + len - 1 < n; left++) {
                int right = left + len - 1;

                if (s.charAt(left) == s.charAt(right)) {
                    if (len <= 2) {
                        isPal[left][right] = true;
                    } else {
                        isPal[left][right] = isPal[left + 1][right - 1];
                    }
                }
            }
        }

        int[] dp = new int[n + 1];

        for (int end = 1; end <= n; end++) {
            dp[end] = dp[end - 1];

            for (int start = 0; start <= end - k; start++) {
                if (isPal[start][end - 1]) {
                    dp[end] = Math.max(dp[end], dp[start] + 1);
                }
            }
        }

        return dp[n];
    }
}
```

---

# 5. Longest Palindromic Substring

## 문제 유형

```
문자열 / 투 포인터 / 중심 확장
```

## 헷갈린 부분

"bb", "abba"처럼 짝수 길이 팰린드롬도 있으므로 중심을 두 가지로 확인해야 한다.

## 핵심 아이디어

팰린드롬은 가운데를 기준으로 좌우가 같다.

각 인덱스를 중심으로 잡고 양쪽으로 확장한다.

```
홀수 길이: expand(i, i)
짝수 길이: expand(i, i + 1)
```

확장이 끝났을 때 `left`, `right`는 이미 팰린드롬 범위를 한 칸 벗어난 상태라서 실제 길이는 다음과 같이 계산해야함

```java
right - left - 1
```

시작 위치

```java
start = i - (len - 1) / 2;
```

## 전체 코드

```java
class Solution {
    public String longestPalindrome(String s) {
        int start = 0;
        int maxLen = 1;

        for (int i = 0; i < s.length(); i++) {
            int len1 = expand(s, i, i);
            int len2 = expand(s, i, i + 1);

            int len = Math.max(len1, len2);

            if (len > maxLen) {
                maxLen = len;
                start = i - (len - 1) / 2;
            }
        }

        return s.substring(start, start + maxLen);
    }

    private int expand(String s, int left, int right) {
        while (
            left >= 0 &&
            right < s.length() &&
            s.charAt(left) == s.charAt(right)
        ) {
            left--;
            right++;
        }

        return right - left - 1;
    }
}
```

---

# 127. Word Ladder

## 문제 유형

```
그래프 / BFS / 최단 거리
```

## 틀린 부분

단어 하나하나를 노드로 보고, 한 글자만 다른 단어끼리 간선으로 연결된 그래프로 봐야 함

`wordSet`을 빈 Set으로 만들었음

```java
Set<String> wordSet = new HashSet<>();
```

이렇게 하면 `wordList`의 단어들이 들어가지 않기 때문에 아래 조건에서 항상 `true`가 됨

```java
if (!wordSet.contains(endWord)) return 0;
```

## 핵심 아이디어

단어를 그래프의 노드로 보고, 한 글자만 다른 단어끼리 연결된 것으로 생각한다.

최단 변환 횟수를 구해야 하므로 BFS를 사용한다.

```
hit -> hot -> dot -> dog -> cog
```

## 코드에서 조심할 부분

`wordList`를 반드시 `HashSet`에 넣고 시작해야 한다.

```java
Set<String> wordSet = new HashSet<>(wordList);
```

방문한 단어는 다시 큐에 들어가지 않도록 `wordSet`에서 제거한다.

```java
wordSet.remove(nextWord);
```

## 전체 코드

```java
import java.util.*;

class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);

        if (!wordSet.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new ArrayDeque<>();
        queue.offer(beginWord);

        int count = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int s = 0; s < size; s++) {
                String word = queue.poll();
                char[] chars = word.toCharArray();

                for (int i = 0; i < chars.length; i++) {
                    char original = chars[i];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) {
                            continue;
                        }

                        chars[i] = c;
                        String nextWord = new String(chars);

                        if (nextWord.equals(endWord)) {
                            return count + 1;
                        }

                        if (wordSet.contains(nextWord)) {
                            queue.offer(nextWord);
                            wordSet.remove(nextWord);
                        }
                    }

                    chars[i] = original;
                }
            }

            count++;
        }

        return 0;
    }
}
```

---

# 1458. Maximum Dot Product of Two Subsequences

## 문제 유형

```
DP / 2차원 DP
```

부분수열을 고르는 문제이므로 `nums1[i]`와 `nums2[j]`를 선택할지 말지 결정해야 함

또한 정답이 음수일 수도 있기 때문에 `dp`를 `0`으로 초기화하면 안됨

## 틀린 부분

아이디어를 못 떠올렸음

## 핵심 아이디어

`dp[i][j]`를 다음과 같이 정의

```
nums1의 앞 i개, nums2의 앞 j개를 봤을 때 만들 수 있는
non-empty subsequence의 최대 dot product
```

현재 두 값을 곱한 값을 `product`라고 하면 선택지를 이렇게 잡고

```
1. 현재 두 수만 선택해서 새로 시작
2. 이전 결과에 현재 두 수를 이어 붙이기
3. nums1의 현재 값을 버리기
4. nums2의 현재 값을 버리기
```

점화식 세우기

```java
dp[i][j] = max(
    product,
    dp[i - 1][j - 1] + product,
    dp[i - 1][j],
    dp[i][j - 1]
);
```

문제에서 non-empty subsequence를 요구하므로 아무것도 선택하지 않는 경우를 답으로 만들면 안됨

따라서 `dp`는 매우 작은 값으로 초기화

```java
int INF = -1_000_000_000;
```

## 전체 코드

```java
import java.util.*;

class Solution {
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;

        int INF = -1_000_000_000;

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int product = nums1[i - 1] * nums2[j - 1];

                int useBoth = Math.max(
                    product,
                    dp[i - 1][j - 1] + product
                );

                dp[i][j] = Math.max(
                    useBoth,
                    Math.max(dp[i - 1][j], dp[i][j - 1])
                );
            }
        }

        return dp[n][m];
    }
}
```