/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package org.easybatch.tutorials.intermediate.hibernate;

import org.easybatch.core.filter.HeaderRecordFilter;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.integration.hibernate.HibernateRecordWriter;
import org.easybatch.integration.hibernate.HibernateTransactionJobListener;
import org.easybatch.integration.hibernate.HibernateTransactionStepListener;
import org.easybatch.tutorials.common.Tweet;
import org.easybatch.validation.BeanValidationRecordValidator;
import org.hibernate.Session;

import java.io.File;

import static org.easybatch.core.impl.EngineBuilder.aNewEngine;

/**
* Main class to run the data loading tutorial.
 *
* @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
*/
public class Launcher {

    public static final int COMMIT_INTERVAL = 2;

    public static void main(String[] args) throws Exception {

        //load tweets from tweets.csv
        File tweets = new File(Launcher.class
                .getResource("/org/easybatch/tutorials/basic/keyapis/tweets.csv").toURI());

        //Start embedded database server
        DatabaseUtil.startEmbeddedDatabase();
        DatabaseUtil.initializeSessionFactory();

        Session session = DatabaseUtil.getSessionFactory().openSession();

        // Build and run a batch engine
        aNewEngine()
                .reader(new FlatFileRecordReader(tweets))
                .filter(new HeaderRecordFilter())
                .mapper(new DelimitedRecordMapper<Tweet>(Tweet.class, new String[]{"id", "user", "message"}))
                .validator(new BeanValidationRecordValidator<Tweet>())
                .processor(new HibernateRecordWriter(session))
                .recordProcessorEventListener(new HibernateTransactionStepListener(session, COMMIT_INTERVAL))
                .jobEventListener(new HibernateTransactionJobListener(session, true))
                .build().call();

        // Dump tweet table to check inserted data
        DatabaseUtil.dumpTweetTable();

        // Shutdown embedded database server and delete temporary files
        DatabaseUtil.closeSessionFactory();
        DatabaseUtil.cleanUpWorkingDirectory();

    }

}