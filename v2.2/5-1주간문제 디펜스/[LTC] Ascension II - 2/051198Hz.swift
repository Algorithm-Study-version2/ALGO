class Solution {
    func trap(_ height: [Int]) -> Int {
        var left = 0
        var right = height.count - 1

        var leftMax = 0
        var rightMax = 0
        var result = 0

        while left < right {
            if height[left] < height[right] {
                if height[left] >= leftMax {
                    leftMax = height[left]
                } else {
                    result += leftMax - height[left]
                }
                left += 1
            } else {
                if height[right] >= rightMax {
                    rightMax = height[right]
                } else {
                    result += rightMax - height[right]
                }
                right -= 1
            }
        }

        return result
    }
}