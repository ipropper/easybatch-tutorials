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

package org.easybatch.tutorials.intermediate.mongodb.load;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.easybatch.core.filter.HeaderRecordFilter;
import org.easybatch.extensions.mongodb.MongoDBRecordWriter;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.tutorials.common.Tweet;
import org.easybatch.validation.BeanValidationRecordValidator;

import java.io.File;

import static org.easybatch.core.job.JobBuilder.aNewJob;

/**
 * Main class to run MongoDB tutorial.
 *
 * <strong>Pre requisite: mongod should be up and running on default port (27017)</strong>
 * 
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class Launcher {

    public static void main(String[] args) throws Exception {

        // create a mongo client
        MongoClient mongoClient = new MongoClient();
        DBCollection tweetsCollection = mongoClient.getDB("test").getCollection("tweets");

        //load tweets from tweets.csv
        File tweets = new File("src/main/resources/data/tweets.csv");

        aNewJob()
                .reader(new FlatFileRecordReader(tweets))
                .filter(new HeaderRecordFilter())
                .mapper(new DelimitedRecordMapper(Tweet.class, "id", "user", "message"))
                .validator(new BeanValidationRecordValidator<Tweet>())
                .processor(new TweetToDBObjectTransformer())
                .writer(new MongoDBRecordWriter(tweetsCollection))
                .call();

        mongoClient.close();

    }
}
