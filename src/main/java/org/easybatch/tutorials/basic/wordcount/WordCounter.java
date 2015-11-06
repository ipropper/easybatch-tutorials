package org.easybatch.tutorials.basic.wordcount;

import org.easybatch.core.processor.ComputationalRecordProcessor;
import org.easybatch.core.record.GenericRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Processor that counts the number of occurrences of each word.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class WordCounter implements ComputationalRecordProcessor<GenericRecord<List<String>>, GenericRecord<List<String>>, Map<String, Integer>> {

    private Map<String, Integer> words = new HashMap<>();

    public Map<String, Integer> getComputationResult() {
        return words;
    }

    public GenericRecord<List<String>> processRecord(GenericRecord<List<String>> record) {
        List<String> tokens = record.getPayload();
        for (String token : tokens) {
            Integer count = words.get(token);
            count = (count == null) ? 1 : count + 1;
            words.put(token, count);
        }
        return record;
    }
}
