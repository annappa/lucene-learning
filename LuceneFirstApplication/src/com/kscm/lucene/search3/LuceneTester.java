package com.kscm.lucene.search3;

import java.io.IOException;

import com.kscm.lucene.common.LuceneConstants;
import com.kscm.lucene.index1.Indexer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;

public class LuceneTester {
    String indexDir = "/Users/annappar/Oracle/Tech/Search/ApacheLucene/Index";
    String dataDir = "/Users/annappar/Oracle/Tech/Search/ApacheLucene/Data";
    Indexer indexer;
    Searcher searcher;

    public static void main(String[] args) {
        LuceneTester tester;
        try {
            tester = new LuceneTester();
            tester.sortUsingRelevance("record3.txt");
            tester.sortUsingIndex("record3.txt");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void sortUsingRelevance(String searchQuery)
            throws IOException, ParseException {
        searcher = new Searcher(indexDir);
        long startTime = System.currentTimeMillis();

        //create a term to search file name
        Term term = new Term(LuceneConstants.FILE_NAME, searchQuery);
        //create the term query object
        Query query = new FuzzyQuery(term);
        searcher.setDefaultFieldSortScoring(true, false);
        //do the search
        TopDocs hits = searcher.search(query,Sort.RELEVANCE);
        long endTime = System.currentTimeMillis();

        System.out.println(hits.totalHits +
                " documents found. Time :" + (endTime - startTime) + "ms");
        for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.getDocument(scoreDoc);
            System.out.print("Score: "+ scoreDoc.score + " ");
            System.out.println("File: "+ doc.get(LuceneConstants.FILE_PATH));
        }
        searcher.close();
    }

    private void sortUsingIndex(String searchQuery)
            throws IOException, ParseException {
        searcher = new Searcher(indexDir);
        long startTime = System.currentTimeMillis();
        //create a term to search file name
        Term term = new Term(LuceneConstants.FILE_NAME, searchQuery);
        //create the term query object
        Query query = new FuzzyQuery(term);
        searcher.setDefaultFieldSortScoring(true, false);
        //do the search
        TopDocs hits = searcher.search(query,Sort.INDEXORDER);
        long endTime = System.currentTimeMillis();

        System.out.println(hits.totalHits +
                " documents found. Time :" + (endTime - startTime) + "ms");
        for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.getDocument(scoreDoc);
            System.out.print("Score: "+ scoreDoc.score + " ");
            System.out.println("File: "+ doc.get(LuceneConstants.FILE_PATH));
        }
        searcher.close();
    }
}
