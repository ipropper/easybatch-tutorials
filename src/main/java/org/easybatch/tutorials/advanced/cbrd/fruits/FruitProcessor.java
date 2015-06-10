package org.easybatch.tutorials.advanced.cbrd.fruits;

import org.easybatch.core.api.RecordProcessingException;
import org.easybatch.core.api.RecordProcessor;
import org.easybatch.core.record.StringRecord;

public class FruitProcessor implements RecordProcessor<StringRecord, StringRecord> {

    public StringRecord processRecord(StringRecord record) throws RecordProcessingException {
        System.out.println("Processing fruit: " + record.getPayload());
        return record;
    }
}
