package org.easybatch.tutorials.intermediate.batches;

import static org.easybatch.core.job.JobBuilder.aNewJob;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.easybatch.core.filter.HeaderRecordFilter;
import org.easybatch.core.filter.BatchFilter;
import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.mapper.BatchMapper;
import org.easybatch.core.marshaller.BatchMarshaller;
import org.easybatch.core.processor.RecordProcessingException;
import org.easybatch.core.processor.RecordProcessor;
import org.easybatch.core.reader.IterableBatchReader;
import org.easybatch.core.record.Batch;
import org.easybatch.core.writer.CollectionBatchWriter;
import org.easybatch.core.writer.StandardOutputBatchWriter;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.DelimitedRecordMarshaller;
import org.easybatch.flatfile.FlatFileBatchReader;
import org.easybatch.tutorials.common.Tweet;

/**
 * Main class to launch the batch processing tutorial.
 *
 * This example shows how to read, filter, map, marshal, process and write records in batches.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class Launcher {

    public static final int BATCH_SIZE = 2;

    public static void main(String[] args) throws Exception {

        /*
         * Example 1 : read records in batches from a list
         */
        List<String> dataSource = Arrays.asList("foo", "bar", "baz", "toto", "titi");
        List<String> dataSink = new ArrayList<>();

        Job job = aNewJob()
                .reader(new IterableBatchReader(dataSource, BATCH_SIZE))
                .processor(new BatchProcessor())
                .writer(new CollectionBatchWriter(dataSink))
                .build();

        JobExecutor.execute(job);

        System.out.println("dataSink = " + dataSink);
        System.out.println("***********************");
        
        /*
         * Example 2 : read records in batches form a flat file
         */
        File tweets = new File("src/main/resources/data/tweets.csv");
        String[] fields = {"id", "user", "message"};
        job = aNewJob()
                .reader(new FlatFileBatchReader(tweets, BATCH_SIZE))
                .filter(new BatchFilter(new HeaderRecordFilter()))
                .processor(new BatchProcessor())
                .mapper(new BatchMapper(new DelimitedRecordMapper(Tweet.class, fields)))
                .marshaller(new BatchMarshaller(new DelimitedRecordMarshaller(Tweet.class, fields)))
                .writer(new StandardOutputBatchWriter())
                .build();

        JobExecutor.execute(job);
    }

    private static class BatchProcessor implements RecordProcessor<Batch, Batch> {

        @Override
        public Batch processRecord(Batch batch) throws RecordProcessingException {
            System.out.println(batch);
            return batch;
        }
    }
}
