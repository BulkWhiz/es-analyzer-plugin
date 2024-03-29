package io.bulkwhiz.index.analysis;

import io.bulkwhiz.index.utils.CustomStemmer;
import io.bulkwhiz.index.utils.Stemmer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.commons.lang3.ArrayUtils;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedList;

public class WordJoinTokenFilter extends TokenFilter {

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);
    Stemmer stemmer = new Stemmer();
    CustomStemmer customStemmer = new CustomStemmer();
    LinkedList<AbstractMap.SimpleEntry<char[], char[]>> list = new LinkedList<>();
    LinkedList<char[]> output = new LinkedList<char[]>();

    public WordJoinTokenFilter(TokenStream in) {
        super(in);
    }
    
    @Override
    public final boolean incrementToken() throws IOException {
        while (!output.isEmpty()) {
            char[] buffer = output.poll();
            termAtt.setEmpty();
            termAtt.copyBuffer(buffer, 0, buffer.length);
            posIncrAtt.setPositionIncrement(0);
            return true;
        }
        if (!input.incrementToken()) {
            list.clear();
            output.clear();
            return false;
        } else {
            char[] buffer = termAtt.toString().toCharArray();
            String stemmed = stemmer.stem(termAtt.toString());
            char[] customStemmed = customStemmer.stem(stemmed);
            if(!Arrays.equals(buffer, customStemmed)) output.add(customStemmed);
            for (int counter = 0; counter < list.size(); counter++) {
                AbstractMap.SimpleEntry<char[], char[]> pair = list.get(counter);
                output.add(ArrayUtils.addAll(pair.getKey(), buffer));
                if(!Arrays.equals(buffer, customStemmed)) output.add(ArrayUtils.addAll(pair.getKey(), customStemmed));
                if(!Arrays.equals(pair.getKey(), pair.getValue())) output.add(ArrayUtils.addAll(pair.getValue(), buffer));
                if(!Arrays.equals(pair.getKey(), pair.getValue()) && !Arrays.equals(buffer, customStemmed))
                    output.add(ArrayUtils.addAll(pair.getValue(), customStemmed));
            }
            list.add(new AbstractMap.SimpleEntry<>(buffer, customStemmed));
            return true;
        }

    }

    @Override
    public final void end() throws IOException {
        super.end();
    }

    @Override
    public void reset() throws IOException {
        super.reset();
    }

}
