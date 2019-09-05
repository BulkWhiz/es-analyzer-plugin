/*
 * Licensed to ElasticSearch and Shay Banon under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. ElasticSearch licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.bulkwhiz.index.analysis;

import junit.framework.Assert;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class EsAnalysisTests {


    @Test
    public void testTokenFilter() throws IOException {
        StringReader sr = new StringReader("nivea sun kids protection lotion conditioner");
        Analyzer analyzer = new StandardAnalyzer();
        WordJoinTokenFilter filter = new WordJoinTokenFilter(analyzer.tokenStream("f", sr));
        List<String> list = new ArrayList<String>();
        filter.reset();
        while (filter.incrementToken()) {
            CharTermAttribute ta = filter.getAttribute(CharTermAttribute.class);
            list.add(ta.toString());
            System.out.println(ta.toString());
        }
        Assert.assertEquals(81, list.size());
        Assert.assertTrue(list.contains("nivea"));
        Assert.assertTrue(list.contains("sun"));
        Assert.assertTrue(list.contains("niveasun"));
        Assert.assertTrue(list.contains("kids"));
        Assert.assertTrue(list.contains("kid"));
        Assert.assertTrue(list.contains("niveakid"));
        Assert.assertTrue(list.contains("niveakids"));
        Assert.assertTrue(list.contains("sunkid"));
        Assert.assertTrue(list.contains("sunkids"));
        Assert.assertTrue(list.contains("protection"));
        Assert.assertTrue(list.contains("protect"));
        Assert.assertTrue(list.contains("niveaprotect"));
        Assert.assertTrue(list.contains("niveaprotection"));
        Assert.assertTrue(list.contains("sunprotect"));
        Assert.assertTrue(list.contains("sunprotection"));
        Assert.assertTrue(list.contains("kidsprotect"));
        Assert.assertTrue(list.contains("kidsprotection"));
        Assert.assertTrue(list.contains("kidprotect"));
        Assert.assertTrue(list.contains("kidprotection"));
    }
}
