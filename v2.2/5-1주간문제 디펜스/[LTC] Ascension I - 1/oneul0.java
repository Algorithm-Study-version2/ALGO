class Solution {
    public String minRemoveToMakeValid(String s) {
        //스택에 인덱스만 넣기
        //set에 없애야할 괄호 인덱스 넣기
        Deque<Integer> stack = new ArrayDeque<>();
        Set<Integer> invalid = new HashSet<>();
        for(int i = 0; i<s.length(); i++){
            if(s.charAt(i) == '('){
                stack.offerLast(i);
            }
            else if(s.charAt(i) == ')'){
                if(stack.isEmpty()) invalid.add(i);
                stack.pollLast();
            }
        }
        while(!stack.isEmpty()){
            invalid.add(stack.pollLast());
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<s.length(); i++){
            if(invalid.contains(i)) continue;
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }
}