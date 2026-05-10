class Solution {
    public int trap(int[] height) {
        int answer = 0;
        Deque<Integer> st = new ArrayDeque<>();
        //높이 배열을 순차적으로 순회한다
        for(int i = 0; i<height.length; i++){
            //스택에 요소가 있고 && 현재 만난 높이가 스택의 가장 위 막대보다 높으면 : 물이 고일 수 있음
            //현재 왼쪽, 오른쪽의 물이 고일 수 있는 부분 전체를 대상으로 진행함
            while(!st.isEmpty() && height[i] > height[st.peek()]){
                int top = st.pop(); // stack의 top -> 바닥 높이
                if(st.isEmpty()) break;
                int dist = i - st.peek() -1;
                int water = Math.min(height[i], height[st.peek()]) - height[top];
                answer += dist*water;
            }
            st.push(i);
        }
        return answer;
    }
}