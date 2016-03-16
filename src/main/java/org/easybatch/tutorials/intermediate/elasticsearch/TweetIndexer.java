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

package org.easybatch.tutorials.intermediate.elasticsearch;

import org.easybatch.core.processor.RecordProcessor;
import org.easybatch.core.record.StringRecord;
import org.elasticsearch.client.Client;

/**
 * Processor that indexes tweets in elastic search.
 *
 * For production use, prefer elastic search <a href="http://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/bulk.html">bulk API</a> for better performance.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class TweetIndexer implements RecordProcessor<StringRecord, StringRecord>{

    private Client client;

    public TweetIndexer(Client client) {
        this.client = client;
    }

    @Override
    public StringRecord processRecord(StringRecord record) {
        String tweet = record.getPayload();
        //index the tweet in the twitter index
        client.prepareIndex("twitter", "tweet")
                .setSource(tweet)
                .execute()
                .actionGet();

        return record;
    }

}
