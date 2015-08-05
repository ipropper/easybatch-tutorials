package org.easybatch.tutorials.intermediate.chunks;

import org.easybatch.core.api.RecordProcessingException;
import org.easybatch.core.api.RecordProcessor;
import org.easybatch.core.reader.IterableMultiRecordReader;
import org.easybatch.core.record.MultiRecord;
import org.easybatch.core.writer.CollectionMultiRecordWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.easybatch.core.impl.EngineBuilder.aNewEngine;

/**
 * Main class to launch the chunk processing tutorial.
 *
 * This example shows the usage of {@link IterableMultiRecordReader} and {@link CollectionMultiRecordWriter}
 * to read and write records in chunks.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class Launcher {

    public static final int CHUNK_SIZE = 2;

    public static void main(String[] args) throws Exception {

        List<String> dataSource = Arrays.asList("foo", "bar", "baz", "toto", "titi");
        List<String> dataSink = new ArrayList<String>();

        aNewEngine()
                .reader(new IterableMultiRecordReader<String>(dataSource, CHUNK_SIZE))
                .processor(new MultiRecordProcessor())
                .writer(new CollectionMultiRecordWriter(dataSink))
                .build().call();

        System.out.println("dataSink = " + dataSink);
    }

    private static class MultiRecordProcessor implements RecordProcessor<MultiRecord, MultiRecord> {

        @Override
        public MultiRecord processRecord(MultiRecord multiRecord) throws RecordProcessingException {
            System.out.println(multiRecord);
            return multiRecord;
        }
    }
}
