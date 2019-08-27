package com.kscm.lucene.search1;

import java.io.IOException;

import com.kscm.lucene.common.LuceneConstants;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.TopDocs;

public class LuceneTester {
    String indexDir = "/Users/annappar/Oracle/Tech/Search/ApacheLucene/Index";
    String dataDir = "/Users/annappar/Oracle/Tech/Search/ApacheLucene/Data";
    Searcher searcher;

    public static void main(String[] args) {
        LuceneTester tester;
        try {
            tester = new LuceneTester();
            tester.searchUsingPrefixQuery("record1");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void searchUsingPrefixQuery(String searchQuery)
            throws IOException, ParseException {
        searcher = new Searcher(indexDir);
        long startTime = System.currentTimeMillis();

        //create a term to search file name
        Term term = new Term(LuceneConstants.FILE_NAME, searchQuery);
        //create the term query object
        Query query = new PrefixQuery(term);
        //do the search
        TopDocs hits = searcher.search(query);
        long endTime = System.currentTimeMillis();

        System.out.println(hits.totalHits +
                " documents found. Time :" + (endTime - startTime) + "ms");
        for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.getDocument(scoreDoc);
            System.out.println("File: "+ doc.get(LuceneConstants.FILE_PATH));
        }
        searcher.close();
    }
}
