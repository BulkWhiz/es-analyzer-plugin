package io.bulkwhiz.plugin.analysis;

import io.bulkwhiz.index.analysis.WordJoinTokenFilterFactory;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.HashMap;
import java.util.Map;

public class EsAnalysisPlugin extends Plugin implements AnalysisPlugin {

//    @Override
//    public Map<String, AnalysisModule.AnalysisProvider<CharFilterFactory>> getCharFilters() {
//        Map<String, AnalysisModule.AnalysisProvider<CharFilterFactory>> extra = new HashMap<>();
//        return extra;
//    }

//    @Override
//    public Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> getTokenizers() {
//        Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> extra = new HashMap<>();
//
//        return extra;
//    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<org.elasticsearch.index.analysis.TokenFilterFactory>> getTokenFilters() {
        Map<String, AnalysisModule.AnalysisProvider<org.elasticsearch.index.analysis.TokenFilterFactory>> extra = new HashMap<>();
        extra.put("wordjoin_stemmer", WordJoinTokenFilterFactory::new);
        return extra;
    }

//    @Override
//    public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
//        return Collections.singletonMap("example", Example::new);
//    }

}
