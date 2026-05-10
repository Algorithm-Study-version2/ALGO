class Solution {
    func maximalRectangle(_ matrix: [[Character]]) -> Int {
        guard !matrix.isEmpty else { return 0 }

        let cols = matrix[0].count
        var heights = Array(repeating: 0, count: cols)
        var result = 0

        for row in matrix {
            for i in 0..<cols {
                if row[i] == "1" {
                    heights[i] += 1
                } else {
                    heights[i] = 0
                }
            }

            result = max(result, largestRectangle(heights))
        }

        return result
    }

    private func largestRectangle(_ heights: [Int]) -> Int {
        var stack = [Int]()
        var maxArea = 0
        let heights = heights + [0]

        for i in 0..<heights.count {
            while let last = stack.last, heights[last] > heights[i] {
                let height = heights[stack.removeLast()]
                let width = stack.isEmpty ? i : i - stack.last! - 1
                maxArea = max(maxArea, height * width)
            }
            stack.append(i)
        }

        return maxArea
    }
}