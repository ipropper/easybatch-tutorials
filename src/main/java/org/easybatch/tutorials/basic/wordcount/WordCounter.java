package org.easybatch.tutorials.basic.wordcount;

import org.easybatch.core.processor.ComputationalRecordProcessor;
import org.easybatch.core.record.GenericRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Processor that counts the number of occurrences of each word.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class WordCounter implements ComputationalRecordProcessor<GenericRecord, GenericRecord, Map<String, Integer>> {

    private Map<String, Integer> words = new HashMap<>();

    public Map<String, Integer> getComputationResult() {
        return words;
    }

    @SuppressWarnings("unchecked")
    public GenericRecord processRecord(GenericRecord record) {
        List<String> tokens = (List<String>) record.getPayload();
        for (String token : tokens) {
            Integer count = words.get(token);
            count = (count == null) ? 1 : count + 1;
            words.put(token, count);
        }
        return record;
    }
}
