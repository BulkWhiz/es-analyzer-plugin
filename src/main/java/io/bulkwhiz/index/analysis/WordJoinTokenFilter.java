package io.bulkwhiz.index.analysis;

import io.bulkwhiz.index.utils.Stemmer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionLengthAttribute;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class WordJoinTokenFilter extends TokenFilter {

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final KeywordAttribute keywordAttr = addAttribute(KeywordAttribute.class);
    private final PositionLengthAttribute posLenAtt = addAttribute(PositionLengthAttribute.class);
    private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);
    Stemmer stemmer = new Stemmer();
    LinkedList<char[]> list = new LinkedList<char[]>();

    
    public WordJoinTokenFilter(TokenStream in) {
        super(in);
    }
    
    @Override
    public final boolean incrementToken() throws IOException {
        while (!list.isEmpty()) {
            char[] buffer = list.poll();
            termAtt.setEmpty();
            termAtt.copyBuffer(buffer, 0, buffer.length);
            posIncrAtt.setPositionIncrement(0);
            return true;
        }

        if (!input.incrementToken()) {
            return false;
        } else {
            char[] buffer = termAtt.buffer();
            char[] stemmed = stemmer.stem(termAtt.toString()).toCharArray();
            if(buffer != stemmed) list.add(stemmed);
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
