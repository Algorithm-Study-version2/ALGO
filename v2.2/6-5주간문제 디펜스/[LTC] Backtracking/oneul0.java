//Q1. Combinations
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> comb = new ArrayList<>();
        backtrack(n, k, 1, comb, result);

        return result;
    }

    private void backtrack(int n, int k, int start, List<Integer> comb, List<List<Integer>> result) {
        if (comb.size()==k) {
            result.add(new ArrayList<>(comb));
            return;
        }

        for (int i=start; i<=n; i++) {
            comb.add(i);
            backtrack(n, k, i+1, comb, result);
            comb.remove(comb.size()-1);
        }
    }
}

//Q2. Restore IP Addresses
//길이 정해져있으니까 길이 맞춰서 되는 경우 모두 구하기
class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        List<String> parts = new ArrayList<>();

        backtrack(s, 0, parts, result);

        return result;
    }

    private void backtrack(String s, int index, List<String> parts, List<String> result) {
        if (parts.size() == 4) {
            if (index == s.length()) {
                result.add(String.join(".", parts));
            }
            return;
        }

        for (int len = 1; len <= 3; len++) {
            if (index + len > s.length()) break;

            String part = s.substring(index, index + len);

            if (!isValid(part)) continue;

            parts.add(part);
            backtrack(s, index + len, parts, result);
            parts.remove(parts.size() - 1);
        }
    }

    private boolean isValid(String part) {
        if (part.length() > 1 && part.charAt(0) == '0') return false;

        int value = Integer.parseInt(part);

        return value >= 0 && value <= 255;
    }
}



//Q3. The k-th Lexicographical String of All Happy Strings of Length n
//사전순으로 문자열 만들고
//k번째 문자열 나오면 반환
class Solution {
    public String getHappyString(int n, int k) {
        StringBuilder path = new StringBuilder();
        int[] count = new int[1];
        String[] answer = new String[1];

        backtrack(n, k, count, answer, path);

        return answer[0] == null ? "" : answer[0];
    }

    private void backtrack(int n, int k, int[] count, String[] answer, StringBuilder path) {
        if (answer[0] != null) return;

        if (path.length() == n) {
            count[0]++;

            if (count[0] == k) {
                answer[0] = path.toString();
            }

            return;
        }

        char[] chars = {'a', 'b', 'c'};

        for (char ch : chars) {
            int len = path.length();

            if (len > 0 && path.charAt(len - 1) == ch) {
                continue;
            }

            path.append(ch);
            backtrack(n, k, count, answer, path);
            path.deleteCharAt(path.length() - 1);
        }
    }
}