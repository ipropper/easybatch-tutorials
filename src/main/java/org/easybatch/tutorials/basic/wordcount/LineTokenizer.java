package org.easybatch.tutorials.basic.wordcount;

import org.easybatch.core.mapper.RecordMapper;
import org.easybatch.core.record.Record;

import java.util.Arrays;
import java.util.List;

/**
 * Mapper that splits each line into a list of words.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class LineTokenizer implements RecordMapper<Record, List<String>> {

    public List<String> processRecord(Record record) {
        String payload = (String) record.getPayload();
        return Arrays.asList(payload.split(" "));
    }

}
