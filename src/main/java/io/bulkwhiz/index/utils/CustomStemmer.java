package io.bulkwhiz.index.utils;

import java.util.HashMap;

public class CustomStemmer {

    HashMap<String, String> customStems;

    public CustomStemmer() {
        customStems = new HashMap<String, String>();
        customStems.put("freshen", "fresh");
        customStems.put("dried", "dry");
        customStems.put("women", "woman");
        customStems.put("wipe", "wip");
        customStems.put("scrubber", "scrub");
    }

    public char[] stem(String s) {
        String stemmed = customStems.get(s);
        if(stemmed != null) return stemmed.toCharArray();
        else return s.toCharArray();
    }
}
