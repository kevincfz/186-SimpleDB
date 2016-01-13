import java.util.HashMap;
public class Solution {
    public String minWindow(String s, String t) {
        HashMap<Character, Integer> tChars = new HashMap<>();
        HashMap<Character, Integer> inWindow = new HashMap<>();
        int resultP1 = -1;
        int resultP2 = -1;
        int foundSoFar = 0;
        int p1 = 0;
        int p2 = 0;
        int min = Integer.MAX_VALUE;

        // set up the hashMaps
        for (int k = 0; k < t.length(); k += 1) {
            Character currentChar = t.charAt(k);
            if (!tChars.containsKey(currentChar)) {
                tChars.put(currentChar, 1);
            } else {
                tChars.put(currentChar, tChars.get(currentChar) + 1);
            }
        }

        while (p2 < s.length()) {
            char currentChar = t.charAt(p2);

            //correctly increment the character we encounter, if they are what we need
            if (tChars.contains(currentChar)) {
                if (inWindow.contains(currentChar)) {
                    inWindow.put(currentChar, inWindow.get(currentChar) + 1);
                    foundSoFar += 1;
                } else {
                    inWindow.put(currentChar, 1);
                }
            }

            if (foundSoFar == t.length()) {
                char p1Char = t.charAt(p1);
                while (!tChars.contains(p1Char)) {
                    p1 += 1;
                }

                while (inWindow.contains(p1Char) && inWindow.get(p1Char) > tChar.get(p1Char)) {
                    inWindow.put(p1Char, inWindow.get(p1Char) - 1);
                    p1 += 1;
                }
                if ((p2 - p1) < min) {
                    min  = p2 - p1;
                    resultP1 = p1;
                    resultP2 = p2;
                }

            }
            p2 += 1;
        }
        if (resultP1 = -1) {
            return "";
        }
        return S.substring(resultP1, resultP2 + 1);
    }
}