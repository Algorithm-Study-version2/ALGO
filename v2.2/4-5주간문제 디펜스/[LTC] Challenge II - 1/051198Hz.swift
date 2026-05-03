class Solution {
    func subarraySum(_ nums: [Int], _ k: Int) -> Int {
        var map = [0: 1]
        var prefix = 0
        var result = 0

        for num in nums {
            prefix += num

            if let count = map[prefix - k] {
                result += count
            }

            map[prefix, default: 0] += 1
        }

        return result
    }
}