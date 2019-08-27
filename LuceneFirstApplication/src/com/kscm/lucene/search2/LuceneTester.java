package com.kscm.lucene.search2;

import java.io.IOException;

import com.kscm.lucene.common.LuceneConstants;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TopDocs;

public class LuceneTester {
    String indexDir = "/Users/annappar/Oracle/Tech/Search/ApacheLucene/Index";
    String dataDir = "/Users/annappar/Oracle/Tech/Search/ApacheLucene/Data";
    Searcher searcher;

    public static void main(String[] args) {
        LuceneTester tester;
        try {
            tester = new LuceneTester();
            tester.searchUsingBooleanQuery("record1.txt","record1");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void searchUsingBooleanQuery(String searchQuery1,
                                         String searchQuery2)throws IOException, ParseException {
        searcher = new Searcher(indexDir);
        long startTime = System.currentTimeMillis();

        //create a term to search file name
        Term term1 = new Term(LuceneConstants.FILE_NAME, searchQuery1);
        //create the term query object
        Query query1 = new TermQuery(term1);

        Term term2 = new Term(LuceneConstants.FILE_NAME, searchQuery2);
        //create the term query object
        Query query2 = new PrefixQuery(term2);

        BooleanQuery query = new BooleanQuery();
        query.add(query1,BooleanClause.Occur.MUST_NOT);
        query.add(query2,BooleanClause.Occur.MUST);

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
