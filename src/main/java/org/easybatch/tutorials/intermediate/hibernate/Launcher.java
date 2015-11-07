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
import org.easybatch.extensions.hibernate.HibernateRecordWriter;
import org.easybatch.extensions.hibernate.HibernateSessionListener;
import org.easybatch.extensions.hibernate.HibernateTransactionListener;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.tutorials.common.DatabaseUtil;
import org.easybatch.tutorials.common.Tweet;
import org.easybatch.validation.BeanValidationRecordValidator;
import org.hibernate.Session;

import java.io.File;

import static org.easybatch.core.job.JobBuilder.aNewJob;

/**
* Main class to run the Hibernate tutorial.
 *
* @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
*/
public class Launcher {

    public static void main(String[] args) throws Exception {

        // Load tweets from tweets.csv
        File tweets = new File("src/main/resources/data/tweets.csv");

        // Start embedded database server
        DatabaseUtil.startEmbeddedDatabase();
        DatabaseUtil.initializeSessionFactory();

        Session session = DatabaseUtil.getSessionFactory().openSession();

        // Build and run a batch job
        aNewJob()
                .reader(new FlatFileRecordReader(tweets))
                .filter(new HeaderRecordFilter())
                .mapper(new DelimitedRecordMapper(Tweet.class, "id", "user", "message"))
                .validator(new BeanValidationRecordValidator<Tweet>())
                .writer(new HibernateRecordWriter(session))
                .pipelineListener(new HibernateTransactionListener(session))
                .jobListener(new HibernateSessionListener(session))
                .call();

        // Dump tweet table to check inserted data
        DatabaseUtil.dumpTweetTable();

        // Shutdown embedded database server and delete temporary files
        DatabaseUtil.closeSessionFactory();
        DatabaseUtil.cleanUpWorkingDirectory();

        /*
         * Load data in batch mode sample:

         int batchSize = 2;
         aNewJob()
                .reader(new FlatFileBatchReader(tweets, batchSize))
                .filter(new BatchFilter(new HeaderRecordFilter()))
                .mapper(new BatchMapper(new DelimitedRecordMapper(Tweet.class, "id", "user", "message")))
                .writer(new HibernateBatchWriter(session))
                .pipelineListener(new HibernateTransactionListener(session)) // commit/rollback transaction after each batch
                .jobListener(new HibernateSessionListener(session)) // close session after job end
                .call();

         */

    }

}
