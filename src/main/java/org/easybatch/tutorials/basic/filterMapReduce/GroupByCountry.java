package org.easybatch.tutorials.basic.filterMapReduce;

import org.easybatch.core.processor.ComputationalRecordProcessor;
import org.easybatch.core.record.Record;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A processor that groups persons by country.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class GroupByCountry implements ComputationalRecordProcessor<Record, Record, Map<String, Set<Person>>> {

    private  Map<String, Set<Person>> personsByCountry = new HashMap<>();

    @Override
    public Record processRecord(Record record) {
        Person person = (Person) record.getPayload();
        String country = person.getCountry();
        if (!personsByCountry.containsKey(country)) {
            personsByCountry.put(country, new HashSet<Person>());
        }
        personsByCountry.get(country).add(person);
        return record;
    }

    @Override
    public Map<String, Set<Person>> getComputationResult() {
        return personsByCountry;
    }

}
