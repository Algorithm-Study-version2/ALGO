class Solution {
    func minOperations(_ n: Int) -> Int {
        var n = n
        var count = 0

        while n > 0 {
            if n == 1 {
                count += 1
                break
            }

            if n & 1 == 0 {
                n >>= 1
            } else {
                if (n & 3) == 1 {
                    n -= 1
                } else {
                    n += 1
                }
                count += 1
            }
        }

        return count
    }
}