/*
 * The MIT License
 *
 *  Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

package org.easybatch.tutorials.intermediate.jdbc.load;

import org.easybatch.core.filter.BatchFilter;
import org.easybatch.core.mapper.BatchMapper;
import org.easybatch.core.filter.HeaderRecordFilter;
import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobReport;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileBatchReader;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.jdbc.*;
import org.easybatch.tutorials.common.DatabaseUtil;
import org.easybatch.tutorials.common.Tweet;
import org.easybatch.validation.BeanValidationRecordValidator;

import java.io.File;
import java.sql.Connection;

import static org.easybatch.core.job.JobBuilder.aNewJob;

/**
 * Main class to run the JDBC import data tutorial.
 *
 * The goal is to read tweets from a CSV file and load them in a relational database.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class Launcher {

    public static void main(String[] args) throws Exception {
        runTutorialInRecordMode();
        //runTutorialInBatchMode();
    }

    private static void runTutorialInRecordMode() throws Exception {
        // Load tweets from tweets.csv
        File tweets = new File("src/main/resources/data/tweets.csv");

        // Start embedded database server
        DatabaseUtil.startEmbeddedDatabase();

        // Setup the JDBC writer
        String[] fields = {"id", "user", "message"};
        String query = "INSERT INTO tweet VALUES (?,?,?);";
        Connection connection = DatabaseUtil.getConnection();

        // Build a batch job
        Job job = aNewJob()
                .skip(1)
                .reader(new FlatFileRecordReader(tweets))
                .mapper(new DelimitedRecordMapper(Tweet.class, fields))
                .validator(new BeanValidationRecordValidator())
                .writer(new JdbcRecordWriter(connection, query, new BeanPropertiesPreparedStatementProvider(Tweet.class, fields)))
                .jobListener(new JdbcConnectionListener(connection))
                .build();

        // Execute the job
        JobReport jobReport = JobExecutor.execute(job);
        System.out.println(jobReport);

        // Dump tweet table to check inserted data
        DatabaseUtil.dumpTweetTable();

        // Shutdown embedded database server and delete temporary files
        DatabaseUtil.cleanUpWorkingDirectory();
    }

    private static void runTutorialInBatchMode() throws Exception {

        // Load tweets from tweets.csv
        File tweets = new File("src/main/resources/data/tweets.csv");

        // Start embedded database server
        DatabaseUtil.startEmbeddedDatabase();

        String[] fields = {"id", "user", "message"};
        String query = "INSERT INTO tweet VALUES (?,?,?);";
        Connection connection = DatabaseUtil.getConnection();

        // Disable autocommit
        connection.setAutoCommit(false);

        // Build the job in batch mode
        int batchSize = 2;
        Job job = aNewJob()
                .reader(new FlatFileBatchReader(tweets, batchSize))
                .filter(new BatchFilter(new HeaderRecordFilter()))
                .mapper(new BatchMapper(new DelimitedRecordMapper(Tweet.class, fields)))
                .writer(new JdbcBatchWriter(connection, query, new BeanPropertiesPreparedStatementProvider(Tweet.class, fields)))
                .pipelineListener(new JdbcTransactionListener(connection)) // commit/rollback transaction after each batch
                .jobListener(new JdbcConnectionListener(connection)) // close JDBC connection after job end
                .build();

        // Execute the job
        JobReport jobReport = JobExecutor.execute(job);
        System.out.println(jobReport);

        // Dump tweet table to check inserted data
        DatabaseUtil.dumpTweetTable();

        // Shutdown embedded database server and delete temporary files
        DatabaseUtil.cleanUpWorkingDirectory();
    }

}
