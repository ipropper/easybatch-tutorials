package org.easybatch.tutorials.advanced.cbrd.fruits;

import org.easybatch.core.processor.RecordProcessingException;
import org.easybatch.core.processor.RecordProcessor;
import org.easybatch.core.record.StringRecord;

public class FruitProcessor implements RecordProcessor<StringRecord, StringRecord> {

    public StringRecord processRecord(StringRecord record) throws RecordProcessingException {
        System.out.println("Processing fruit: " + record.getPayload());
        return record;
    }
}
