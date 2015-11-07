package org.easybatch.tutorials.basic.wordcount;

import org.easybatch.core.mapper.RecordMapper;
import org.easybatch.core.record.GenericRecord;
import org.easybatch.core.record.StringRecord;

import java.util.Arrays;

/**
 * Mapper that splits each line into a list of words.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class LineTokenizer implements RecordMapper<StringRecord, GenericRecord> {

    public GenericRecord processRecord(StringRecord record) {
        String payload = record.getPayload();
        return new GenericRecord<>(record.getHeader(), Arrays.asList(payload.split(" ")));
    }

}
