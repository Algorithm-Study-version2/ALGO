class Solution {
    public String removeDuplicates(String s, int k) {
        Deque<int[]> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (!stack.isEmpty() && stack.peekLast()[0] == c) {
                stack.peekLast()[1]++;
                if (stack.peekLast()[1] == k) {
                    stack.pollLast();
                }
            } else {
                stack.offerLast(new int[]{c, 1});
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int[] entry : stack) {
            char ch = (char) entry[0];
            int count = entry[1];
            while (count-- > 0) {
                sb.append(ch);
            }
        }

        return sb.toString();
    }
}