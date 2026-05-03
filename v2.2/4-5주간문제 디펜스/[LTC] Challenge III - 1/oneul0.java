class Solution {
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for(int i = 0; i<s.length(); i++){
            char cur = s.charAt(i);
            if(cur == '(' || cur == '{' || cur == '['){
                stack.offerLast(cur);
            }
            else{
                if(stack.isEmpty()) return false;
                if(cur == ')' && stack.peekLast() != '(') return false;
                if(cur == ']' && stack.peekLast() != '[') return false;
                if(cur == '}' && stack.peekLast() != '{') return false;
                stack.pollLast();
            }
        }
        if(!stack.isEmpty()) return false;
        return true;
    }
}