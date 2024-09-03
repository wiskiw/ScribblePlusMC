package me.chrr.scribble.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdvancedTextHandler {

    public static int moveCursorByWords(String text, int wordOffset, int cursor) {
        if (wordOffset == 0) {
            return cursor;
        }

        boolean movingLeft = wordOffset < 0;
        int words = Math.abs(wordOffset);
        if (movingLeft) {
            return moveCursorByWordsToTheLeft(text, words, cursor);
        } else {
            return moveCursorByWordsToTheRight(text, words, cursor);
        }
    }

    private static int moveCursorByWordsToTheLeft(String text, int words, int cursor) {
        // Regex to match words and punctuation groups for moving cursor to the left
        String regex = "([\\p{L}\\p{M}\\p{N}_]+\\s*)|(\\p{P}+\\s*)";

        List<String> textGroups = findGroups(text, regex);

        int groupIndex = 0;
        int totalLength = 0;

        // Determine the group where the cursor is currently located
        while (totalLength < cursor && groupIndex < textGroups.size()) {
            totalLength += textGroups.get(groupIndex).length();
            groupIndex++;
        }

        // Calculate the new cursor position by moving left by the specified number of words
        int newCursorPosition = totalLength;
        for (int i = 0; i < words; i++) {
            groupIndex--;

            // If we've moved past the start of the text, return 0 (beginning of text)
            if (groupIndex < 0) {
                return 0;
            }

            // Subtract the length of the current group to move the cursor left
            newCursorPosition -= textGroups.get(groupIndex).length();
        }

        return newCursorPosition;
    }

    private static int moveCursorByWordsToTheRight(String text, int words, int cursor) {
        if (cursor >= text.length()) {
            return text.length();
        }

        // Regex to match words and punctuation groups for moving cursor to the right.
        String regex = "(\\s*[\\p{L}\\p{M}\\p{N}_]+)|(\\s*\\p{P}+)";
        List<String> textGroups = findGroups(text, regex);

        int currentIndex = 0;
        int currentLength = 0;

        // Find the index of the group where the cursor currently resides.
        while (true) {
            String group = textGroups.get(currentIndex);
            if (currentLength + group.length() > cursor) {
                break;
            }
            currentLength += group.length();
            currentIndex++;
        }

        int newCursorPosition = currentLength;

        // Move the cursor by the specified number of words (or groups).
        for (int i = 0; i < words; i++) {
            int targetIndex = currentIndex + i;

            // If the target index exceeds the available groups, return the text length.
            if (targetIndex >= textGroups.size()) {
                return text.length();
            }

            String targetGroup = textGroups.get(targetIndex);
            newCursorPosition += targetGroup.length();
        }

        return newCursorPosition;
    }


    private static List<String> findGroups(String text, String regexPattern) {
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(text);

        ArrayList<String> textGroups = new ArrayList<>();
        while (matcher.find()) {
            String value = matcher.group(0);
            textGroups.add(value);
        }

        return textGroups;
    }
}
