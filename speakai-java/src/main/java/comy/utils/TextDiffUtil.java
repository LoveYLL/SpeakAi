package comy.utils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
public class TextDiffUtil {
    public static Set<String> findMissingWords(String refText, String userText) {
        Set<String> refWords = Arrays.stream(refText.toLowerCase().split("\\s+"))
                .collect(Collectors.toSet());
        Set<String> userWords = new HashSet<>(Arrays.asList(userText.toLowerCase().split("\\s+")));
        refWords.removeAll(userWords);
        return refWords;
    }
}