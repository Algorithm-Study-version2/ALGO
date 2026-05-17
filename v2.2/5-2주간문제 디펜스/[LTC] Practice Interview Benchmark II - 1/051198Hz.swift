class Solution {
    func maxArea(_ height: [Int]) -> Int {
        var left = 0
        var right = height.count - 1
        var result = Int.min

        while left < right {
            let area = min(height[left], height[right]) * (right - left)
            result = max(result, area)

            if height[left] > height[right] {
                right -= 1
                continue
            }
            left += 1
        }

        return result
    }
}