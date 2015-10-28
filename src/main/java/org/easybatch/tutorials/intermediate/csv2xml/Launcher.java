package org.easybatch.tutorials.intermediate.csv2xml;

import org.easybatch.core.filter.HeaderRecordFilter;
import org.easybatch.core.writer.FileRecordWriter;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.tutorials.common.Tweet;
import org.easybatch.xml.XmlRecordMarshaller;
import org.easybatch.xml.XmlWrapperTagWriter;

import java.io.File;
import java.io.FileWriter;

import static org.easybatch.core.job.JobBuilder.aNewJob;

/**
 * Main class to launch the CSV to XML tutorial.
 * <p>
 * The goal is to read tweets from a CSV file and transform them to XML format.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class Launcher {

    public static void main(String[] args) throws Exception {

        File csvTweets = new File(Launcher.class.getResource("/org/easybatch/tutorials/basic/keyapis/tweets.csv").toURI());
        FileWriter xmlTweetsWriter = new FileWriter(new File("tweets.xml"));

        aNewJob()
                .reader(new FlatFileRecordReader(csvTweets))
                .filter(new HeaderRecordFilter())
                .mapper(new DelimitedRecordMapper(Tweet.class, new String[]{"id", "user", "message"}))
                .marshaller(new XmlRecordMarshaller(Tweet.class))
                .writer(new FileRecordWriter(xmlTweetsWriter))
                .jobListener(new XmlWrapperTagWriter(xmlTweetsWriter, "tweets"))
                .call();

    }

}
