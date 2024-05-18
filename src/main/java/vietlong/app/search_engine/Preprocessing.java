package vietlong.app.search_engine;
import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.tartarus.snowball.ext.PorterStemmer;

public class Preprocessing {
    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList("hasn't", "as", "aren't", "re", "weren't", "couldn't", "haven", "between", "she's", "o", "any", "be", "itself", "what", "very", "your", "himself", "mightn", "does", "than", "some", "ll", "their", "don't", "but", "isn", "most", "i", "am", "if", "can", "under", "shouldn't", "same", "themselves", "my", "doing", "d", "up", "not", "you", "you'll", "he", "theirs", "nor", "hasn", "them", "his", "by", "aren", "do", "where", "she", "ain", "has", "into", "needn", "didn", "you've", "they", "while", "myself", "shouldn", "through", "below", "been", "shan", "with", "only", "doesn't", "at", "should've", "are", "hers", "whom", "a", "over", "m", "against", "both", "no", "then", "down", "now", "these", "off", "will", "were", "you're", "who", "ourselves", "during", "you'd", "s", "this", "yourself", "above", "of", "me", "that", "further", "because", "there", "each", "needn't", "more", "being", "our", "so", "weren", "for", "doesn", "once", "the", "it's", "mustn't", "when", "here", "is", "won't", "we", "again", "did", "all", "ve", "an", "him", "on", "or", "yours", "other", "why", "out", "mightn't", "those", "don't", "haven't", "which", "had", "herself", "about", "before", "was", "after", "having", "t", "how", "won", "too", "from", "isn't", "it", "didn't", "ours", "in", "y", "wouldn't", "own", "just", "wasn", "until", "few", "should", "to", "yourselves", "its", "and", "such", "wasn't", "couldn't", "hadn't", "ma", "mustn", "hadn", "her", "have", "shan't", "that'll", "wouldn"));

    public static List<String> tokenize(String text) {
        // Tokenization
        List<String> tokens = Arrays.asList(text.toLowerCase().split("\\W+"));

        // Lowercasing and removing non-alphanumeric characters
        tokens = tokens.stream()
                .map(token -> token.replaceAll("[^a-zA-Z0-9]", ""))
                .collect(Collectors.toList());

        // Removing stopwords
        tokens = tokens.stream()
                .filter(token -> !STOP_WORDS.contains(token))
                .collect(Collectors.toList());

        // Stemming
        PorterStemmer stemmer = new PorterStemmer();
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            stemmer.setCurrent(token);
            stemmer.stem();
            tokens.set(i, stemmer.getCurrent());
        }

        return tokens;


    }
}
