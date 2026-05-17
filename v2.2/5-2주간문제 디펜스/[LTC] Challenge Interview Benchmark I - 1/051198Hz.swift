class Solution {
    func longestPalindrome(_ s: String) -> String {
        let chars = Array(s)

        var start = 0
        var end = 0

        for i in chars.indices {
            let len1 = expand(chars, i, i)
            let len2 = expand(chars, i, i + 1)

            let len = max(len1, len2)

            // 펠린드롬 시작 끝 인덱스 계산
            if len > end - start + 1 {
                start = i - (len - 1) / 2
                end = i + len / 2
            }
        }

        return String(chars[start...end])
    }

    private func expand(_ chars: [Character], _ left: Int, _ right: Int) -> Int {
        var l = left
        var r = right

        while l >= 0,
            r < chars.count,
            chars[l] == chars[r]
        {
            l -= 1
            r += 1
        }

        return r - l - 1
    }
}

class SolutionAnother {
    func longestPalindrome(_ s: String) -> String {
        let chars = Array(s)
        let n = chars.count

        var pal = Array(
            repeating: Array(repeating: false, count: n),
            count: n
        )

        var maxLen = 1
        var start = 0

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

                    if pal[l][r], len > maxLen {
                        maxLen = len
                        start = l
                    }
                }
            }
        }

        return String(chars[start...<start+maxLen])
    }
}

