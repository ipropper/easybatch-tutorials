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

package org.easybatch.tutorials.intermediate.jdbc.load;

import org.easybatch.core.filter.HeaderRecordFilter;
import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobReport;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.jdbc.JdbcRecordWriter;
import org.easybatch.jdbc.PreparedStatementProvider;
import org.easybatch.tutorials.common.DatabaseUtil;
import org.easybatch.tutorials.common.Tweet;
import org.easybatch.validation.BeanValidationRecordValidator;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.easybatch.core.job.JobBuilder.aNewJob;

/**
 * Main class to run the JDBC import data tutorial.
 *
 * The goal is to read tweets from a CSV file and load them in a relational database.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class Launcher {

    public static void main(String[] args) throws Exception {

        //load tweets from tweets.csv
        File tweets = new File(Launcher.class.getResource("/org/easybatch/tutorials/basic/keyapis/tweets.csv").toURI());

        //Start embedded database server
        DatabaseUtil.startEmbeddedDatabase();

        //Setup the JDBC writer
        Connection connection = DatabaseUtil.getConnection();
        String query = "INSERT INTO tweet VALUES (?,?,?);";
        JdbcRecordWriter jdbcRecordWriter = new JdbcRecordWriter(connection, query, new PreparedStatementProvider() {
            @Override
            public void prepareStatement(PreparedStatement preparedStatement, Object record) throws SQLException {
                Tweet tweet = (Tweet) record;
                preparedStatement.setInt(1, tweet.getId());
                preparedStatement.setString(2, tweet.getUser());
                preparedStatement.setString(3, tweet.getMessage());
            }
        });

        // Build a batch job
        Job job = aNewJob()
                .reader(new FlatFileRecordReader(tweets))
                .filter(new HeaderRecordFilter())
                .mapper(new DelimitedRecordMapper(Tweet.class, new String[]{"id", "user", "message"}))
                .validator(new BeanValidationRecordValidator<Tweet>())
                .writer(jdbcRecordWriter)
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
