class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int[] re : reservedSeats) {
            int row = re[0];
            int seat = re[1];

            map.put(row, add(map.getOrDefault(row, 0), seat));
        }

        long count = (long)(n - map.size()) * 2;

        for (int mask : map.values()) {
            count += groupCount(mask);
        }

        return (int) count;
    }

    public int add(int mask, int pos) {
        return mask | (1 << pos);
    }

    public boolean isOn(int mask, int pos) {
        return (mask & (1 << pos)) != 0;
    }

    public boolean chkLeft(int mask) {
        for (int i = 2; i <= 5; i++) {
            if (isOn(mask, i)) return false;
        }
        return true;
    }

    public boolean chkRight(int mask) {
        for (int i = 6; i <= 9; i++) {
            if (isOn(mask, i)) return false;
        }
        return true;
    }

    public boolean chkMiddle(int mask) {
        for (int i = 4; i <= 7; i++) {
            if (isOn(mask, i)) return false;
        }
        return true;
    }

    public int groupCount(int mask) {
        int count = 0;

        boolean left = chkLeft(mask);
        boolean right = chkRight(mask);

        count += left ? 1 : 0;
        count += right ? 1 : 0;

        if (count == 0) {
            count += chkMiddle(mask) ? 1 : 0;
        }

        return count;
    }
}