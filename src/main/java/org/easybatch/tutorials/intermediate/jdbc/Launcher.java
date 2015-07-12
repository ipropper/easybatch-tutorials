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

package org.easybatch.tutorials.intermediate.jdbc;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.easybatch.core.api.Record;
import org.easybatch.integration.apache.common.csv.ApacheCommonCsvRecord;
import org.easybatch.integration.apache.common.csv.ApacheCommonCsvRecordReader;
import org.easybatch.jdbc.JdbcRecordWriter;
import org.easybatch.jdbc.PreparedStatementProvider;
import org.easybatch.tutorials.common.DatabaseUtil;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;
import static org.easybatch.core.impl.EngineBuilder.aNewEngine;

/**
 * Main class to run the JDBC tutorial.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class Launcher {

    public static void main(String[] args) throws Exception {

        //load tweets from tweets.csv
        File tweets = new File(Launcher.class.getResource("/org/easybatch/tutorials/basic/keyapis/tweets.csv").toURI());

        //Start embedded database server
        DatabaseUtil.startEmbeddedDatabase();

        //Setup the Apache Commons CSV reader
        CSVParser parser = new CSVParser(new FileReader(tweets), CSVFormat.DEFAULT.withHeader("id", "user", "message"));
        ApacheCommonCsvRecordReader recordReader = new ApacheCommonCsvRecordReader(parser);

        //Setup the JDBC writer
        Connection connection = DatabaseUtil.getConnection();
        String query = "INSERT INTO tweet VALUES (?,?,?);";
        JdbcRecordWriter jdbcRecordWriter = new JdbcRecordWriter(connection, query, new PreparedStatementProvider() {
            @Override
            public void prepareStatement(PreparedStatement preparedStatement, Record record) throws SQLException {
                CSVRecord csvRecord = ((ApacheCommonCsvRecord) record).getPayload();
                preparedStatement.setInt(1, parseInt(csvRecord.get("id")));
                preparedStatement.setString(2, csvRecord.get("user"));
                preparedStatement.setString(3, csvRecord.get("message"));
            }
        });

        // Build and run a batch engine
        aNewEngine()
                .reader(recordReader)
                .skip(1)
                .processor(jdbcRecordWriter)
                .build().call();

        // Dump tweet table to check inserted data
        DatabaseUtil.dumpTweetTable();

        // Shutdown embedded database server and delete temporary files
        DatabaseUtil.cleanUpWorkingDirectory();

    }

}