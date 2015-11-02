package org.easybatch.tutorials.basic.wordcount;

import org.easybatch.core.mapper.RecordMapper;
import org.easybatch.core.record.StringRecord;

import java.util.Arrays;
import java.util.List;

/**
 * Mapper that splits each line into a list of words.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class LineTokenizer implements RecordMapper<StringRecord, List<String>> {

    public List<String> processRecord(StringRecord record) {
        String payload = record.getPayload();
        return Arrays.asList(payload.split(" "));
    }

}
