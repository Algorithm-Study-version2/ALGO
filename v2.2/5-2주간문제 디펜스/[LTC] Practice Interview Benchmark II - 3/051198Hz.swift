final class Solution {

    func maxPalindromes(_ s: String, _ k: Int) -> Int {
        let chars = Array(s)
        let n = chars.count

        var pal = Array(
            repeating: Array(repeating: false, count: n),
            count: n
        )
        
        for i in 0..<n {
            pal[i][i] = true
        }

        if n >= 2 {
            for len in 2...n {
                for l in 0...(n - len) {
                    let r = l + len - 1

                    if chars[l] == chars[r] {
                        if len == 2 {
                            pal[l][r] = true
                        } else {
                            pal[l][r] = pal[l + 1][r - 1]
                        }
                    }
                }
            }
        }

        var dp = Array(repeating: 0, count: n + 1)

        for i in stride(from: n - 1, through: 0, by: -1) {
            dp[i] = dp[i + 1]

            if i + k - 1 < n {
                for j in (i + k - 1)..<n {
                    if pal[i][j] {
                        dp[i] = max(
                            dp[i],
                            1 + dp[j + 1]
                        )
                        break
                    }
                }
            }
        }

        return dp[0]
    }
}