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

package org.easybatch.tutorials.advanced.jmx;

import org.easybatch.core.filter.RecordFilter;
import org.easybatch.core.filter.RecordNumberGreaterThanFilter;
import org.easybatch.core.filter.RecordNumberLowerThanFilter;
import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.flatfile.FlatFileRecordReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Arrays.asList;

/**
 * Main class to run the parallel JMX tutorial.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class ParallelJobsJmxTutorial {

    private static final int THREAD_POOL_SIZE = 2;

    public static void main(String[] args) throws Exception {

        // Create the  data source
        File dataSource = new File("src/main/resources/data/tweets.csv");

        // Build worker jobs
        // worker job 1: process records 1-3 and filters records 4+
        Job job1 = buildJob(dataSource, new RecordNumberGreaterThanFilter(3), "worker-job1");
        // worker job 2: process 4+ and filters records 1-3
        Job job2 = buildJob(dataSource, new RecordNumberLowerThanFilter(4), "worker-job2");

        //create a 2 threads pool to call worker jobs in parallel
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        executorService.invokeAll(asList(job1, job2));

        executorService.shutdown();

    }

    private static Job buildJob(File dataSource, RecordFilter recordFilter, String jobName) throws FileNotFoundException {
        return JobBuilder.aNewJob()
                .named(jobName)
                .reader(new FlatFileRecordReader(dataSource))
                .filter(recordFilter)
                .processor(new TweetSlowProcessor())
                .jmxMode(true)
                .build();
    }

}
