package comy.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TextDiffUtil {
    // 找出userText中不在refText中的单词（差异单词）
    public static Set<String> findDiffWords(String refText, String userText) {
        Set<String> refWords = Arrays.stream(refText.toLowerCase().split("\\s+"))
                                    .collect(Collectors.toSet());
        Set<String> userWords = new HashSet<>(Arrays.asList(userText.toLowerCase().split("\\s+")));
        userWords.removeAll(refWords);  // 剩下就是不同的单词
        return userWords;
    }
}
