package org.easybatch.tutorials.intermediate.chunks;

import org.easybatch.core.api.RecordProcessingException;
import org.easybatch.core.api.RecordProcessor;
import org.easybatch.core.reader.IterableMultiRecordReader;
import org.easybatch.core.record.MultiRecord;

import java.util.Arrays;
import java.util.List;

import static org.easybatch.core.impl.EngineBuilder.aNewEngine;

/**
 * Main class to launch the chunk processing tutorial.
 *
 * This example shows the usage of {@link IterableMultiRecordReader} that reads records in chunks from an iterable data source
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class Launcher {

    public static final int CHUNK_SIZE = 2;

    public static void main(String[] args) throws Exception {

        List<String> dataSource = Arrays.asList("foo", "bar", "baz", "toto", "titi");

        aNewEngine()
                .reader(new IterableMultiRecordReader<String>(dataSource, CHUNK_SIZE))
                .processor(new MultiRecordProcessor())
                .build().call();
    }

    private static class MultiRecordProcessor implements RecordProcessor<MultiRecord, MultiRecord> {

        @Override
        public MultiRecord processRecord(MultiRecord multiRecord) throws RecordProcessingException {
            System.out.println(multiRecord);
            return multiRecord;
        }
    }
}
