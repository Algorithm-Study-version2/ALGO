class Solution {
    func colorTheArray(_ n: Int, _ queries: [[Int]]) -> [Int] {
        var color = Array(repeating: 0, count: n)
        var pairCount = 0
        var result = [Int]()

        for querry in queries {
            let i = querry[0]
            let newColor = querry[1]

            if i - 1 >= 0, color[i - 1] != 0, color[i - 1] == color[i] {
                pairCount = max(pairCount - 1, 0)
            }
            if i + 1 <= n - 1, color[i + 1] != 0, color[i + 1] == color[i] {
                pairCount = max(pairCount - 1, 0)
            }

            color[i] = newColor

            if i - 1 >= 0, color[i - 1] != 0, color[i - 1] == color[i] {
                pairCount += 1
            }
            if i + 1 <= n - 1, color[i + 1] != 0, color[i + 1] == color[i] {
                pairCount += 1
            }

            result.append(pairCount)
        }

        return result
    }
}