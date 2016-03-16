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

package org.easybatch.tutorials.intermediate.mongodb.extract;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.easybatch.core.writer.FileRecordWriter;
import org.easybatch.extensions.mongodb.MongoDBRecordMapper;
import org.easybatch.extensions.mongodb.MongoDBRecordReader;
import org.easybatch.extensions.xstream.XstreamRecordMarshaller;
import org.easybatch.xml.XmlWrapperTagWriter;

import java.io.FileWriter;

import static org.easybatch.core.job.JobBuilder.aNewJob;

/**
 * Main class to export tweets from MongoDB to an XML file.
 *
 * <strong>Pre requisite: mongod should be up and running on default port (27017)</strong>
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class Launcher {

    public static void main(String[] args) throws Exception {

        // Create a mongo client
        MongoClient mongoClient = new MongoClient();
        DBCollection tweetsCollection = mongoClient.getDB("test").getCollection("tweets");

        // Create output file tweets.xml
        FileWriter tweets = new FileWriter("tweets.xml");

        // Build and run the batch job
        aNewJob()
                .reader(new MongoDBRecordReader(tweetsCollection, new BasicDBObject()))
                .mapper(new MongoDBRecordMapper<>(Tweet.class))
                .processor(new XstreamRecordMarshaller("tweet", Tweet.class))
                .writer(new FileRecordWriter(tweets))
                .jobListener(new XmlWrapperTagWriter(tweets, "tweets"))
                .call();

        System.out.println("Successfully exported tweets.");

        mongoClient.close();
    }
}
