class Solution {
    public int maxProduct(int[] nums) {
        int max = nums[0];
        int curMax = nums[0];
        int curMin = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];

            if (num < 0) {
                int temp = curMax;
                curMax = curMin;
                curMin = temp;
            }

            curMax = Math.max(num, curMax * num);
            curMin = Math.min(num, curMin * num);

            max = Math.max(max, curMax);
        }

        return max;
    }
}