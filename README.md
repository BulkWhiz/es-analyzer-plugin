### Elasticsearch analysis plugin

this plugin is used to join every possible two-word combinations (pairs) in a document.

the plugin exports a token filter called `wordjoin_stemmer`.

basically this filter stems every token from the stream and joins the original word and the stemmed one with every subsequent word in the document. this is useful in e-commerce websites where documents are small and a user might search with two words joined as a single word.

Stemming happens on two steps
- the first is using [stanfordnlp/CoreNLP](https://github.com/stanfordnlp/CoreNLP) [Stemmer](https://github.com/stanfordnlp/CoreNLP/blob/master/src/edu/stanford/nlp/process/Stemmer.java) class with small modifications.
- the second step is applying custom stemming rules in the [CustomStemmer](https://github.com/BulkWhiz/es-analyzer-plugin/blob/master/src/main/java/io/bulkwhiz/index/utils/CustomStemmer.java) class.

to use the plugin please follow these steps:
- clone the repo on your local machine
- make sure you have maven installed on the machine 
- `mvn -Dtest=EsAnalysisTests test`. this will run tests to make sure everything is ok. you should always run tests if you change anything in the plugin
- `mvn package`. this will package the project and output a zip file under target/releases folder in the project directory
- `sudo /usr/share/elasticsearch/bin/elasticsearch-plugin install file:///home/ubuntu/es-analyzer-plugin-7.0.0.zip` install the plugin in elasticsearch using `elasticsearch-plugin` executable installed with elasticsearch. the path of this will vary depending on the way you installed elasic on the machine.
- if the plugin is already installed you will need to remove it first.

