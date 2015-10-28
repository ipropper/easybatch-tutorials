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

package org.easybatch.tutorials.intermediate.jdbc.extract;

import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobReport;
import org.easybatch.core.writer.FileRecordWriter;
import org.easybatch.flatfile.DelimitedRecordMarshaller;
import org.easybatch.jdbc.JdbcConnectionListener;
import org.easybatch.jdbc.JdbcRecordMapper;
import org.easybatch.jdbc.JdbcRecordReader;
import org.easybatch.tutorials.common.DatabaseUtil;
import org.easybatch.tutorials.common.Tweet;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;

import static org.easybatch.core.job.JobBuilder.aNewJob;

/**
 * Main class to run the JDBC export data tutorial.
 *
 * The goal is to read tweets from a relational database and export them to a flat file.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class Launcher {

    public static void main(String[] args) throws Exception {

        // Output file
        FileWriter tweets = new FileWriter(new File("tweets.csv"));
        
        //Start embedded database server
        DatabaseUtil.startEmbeddedDatabase();
        DatabaseUtil.populateTweetTable();

        // get a connection to the database
        Connection connection = DatabaseUtil.getConnection();

        // Build a batch job
        Job job = aNewJob()
                .reader(new JdbcRecordReader(connection, "select * from tweet"))
                .mapper(new JdbcRecordMapper(Tweet.class, new String[]{"id", "user", "message"}))
                .marshaller(new DelimitedRecordMarshaller(Tweet.class, new String[]{"id", "user", "message"}))
                .writer(new FileRecordWriter(tweets))
                .jobListener(new JdbcConnectionListener(connection))
                .build();
        
        // Execute the job
        JobReport jobReport = JobExecutor.execute(job);
        System.out.println(jobReport);

        // Shutdown embedded database server and delete temporary files
        DatabaseUtil.cleanUpWorkingDirectory();

    }

}
