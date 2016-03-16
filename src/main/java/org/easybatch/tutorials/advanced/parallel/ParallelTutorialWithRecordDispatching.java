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

package org.easybatch.tutorials.advanced.parallel;

import org.easybatch.core.dispatcher.PoisonRecordBroadcaster;
import org.easybatch.core.dispatcher.RoundRobinRecordDispatcher;
import org.easybatch.core.filter.HeaderRecordFilter;
import org.easybatch.core.filter.PoisonRecordFilter;
import org.easybatch.core.job.Job;
import org.easybatch.core.reader.BlockingQueueRecordReader;
import org.easybatch.core.record.Record;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.tutorials.common.Tweet;
import org.easybatch.tutorials.common.TweetProcessor;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.Arrays.asList;
import static org.easybatch.core.job.JobBuilder.aNewJob;

/**
* Main class to run the parallel jobs tutorial with record dispatching.
 *
* @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
*/
public class ParallelTutorialWithRecordDispatching {

    private static final int THREAD_POOL_SIZE = 3;

    public static void main(String[] args) throws Exception {

        // Input file tweets.csv
        File tweets = new File("src/main/resources/data/tweets.csv");

        // Create queues
        BlockingQueue<Record> queue1 = new LinkedBlockingQueue<>();
        BlockingQueue<Record> queue2 = new LinkedBlockingQueue<>();

        // Create a round robin record dispatcher to distribute records to worker jobs
        RoundRobinRecordDispatcher<Record> roundRobinRecordDispatcher =
                                        new RoundRobinRecordDispatcher<>(asList(queue1, queue2));

        // Build a master job to read records from the data source and dispatch them to worker jobs
        Job masterJob = aNewJob()
                .named("master-job")
                .reader(new FlatFileRecordReader(tweets))
                .filter(new HeaderRecordFilter())
                .mapper(new DelimitedRecordMapper(Tweet.class, "id", "user", "message"))
                .dispatcher(roundRobinRecordDispatcher)
                .jobListener(new PoisonRecordBroadcaster<>(asList(queue1, queue2)))
                .build();

        // Build worker jobs
        Job workerJob1 = buildWorkerJob(queue1, "worker-job1");
        Job workerJob2 = buildWorkerJob(queue2, "worker-job2");

        // Create a thread pool to call master and worker jobs in parallel
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        // Submit workers to executor service
        executorService.invokeAll(asList(masterJob, workerJob1, workerJob2));

        // Shutdown executor service
        executorService.shutdown();

    }

    public static Job buildWorkerJob(BlockingQueue<Record> queue, String jobName) {
        return aNewJob()
                .named(jobName)
                .reader(new BlockingQueueRecordReader<>(queue))
                .filter(new PoisonRecordFilter())
                .processor(new TweetProcessor())
                .build();
    }

}
