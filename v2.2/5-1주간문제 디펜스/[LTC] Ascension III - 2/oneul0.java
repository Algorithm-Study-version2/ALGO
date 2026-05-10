class Solution {
    class Pair implements Comparable<Pair> {
        int val, idx;

        Pair(int val, int idx) {
            this.val = val;
            this.idx = idx;
        }

        @Override
        public int compareTo(Pair o) {
            return Integer.compare(o.val, this.val);
        }
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        PriorityQueue<Pair> maxVals = new PriorityQueue<>();

        int n = nums.length;
        int[] output = new int[n-k+1];

        for (int i=0; i<k; i++) {
            maxVals.offer(new Pair(nums[i], i));
        }

        output[0] = maxVals.peek().val;

        for (int i=k; i<n; i++) {
            maxVals.offer(new Pair(nums[i], i));

            while (!maxVals.isEmpty() && maxVals.peek().idx <= i-k) {
                maxVals.poll();
            }

            output[i-k+1] = maxVals.peek().val;
        }

        return output;
    }
}