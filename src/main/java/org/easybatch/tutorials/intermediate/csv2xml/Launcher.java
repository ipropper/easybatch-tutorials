package org.easybatch.tutorials.intermediate.csv2xml;

import com.thoughtworks.xstream.XStream;
import org.easybatch.core.api.RecordProcessingException;
import org.easybatch.core.api.RecordProcessor;
import org.easybatch.core.filter.HeaderRecordFilter;
import org.easybatch.core.writer.FileRecordWriter;
import org.easybatch.flatfile.DelimitedRecordMapper;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.tutorials.common.Tweet;
import org.easybatch.xml.XmlWrapperTagWriter;

import java.io.File;
import java.io.FileWriter;

import static org.easybatch.core.impl.EngineBuilder.aNewEngine;

/**
 * Main class to launch the CSV to XML tutorial.
 * <p/>
 * The goal is to read tweets from a CSV file and transform them to XML format.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class Launcher {

    public static void main(String[] args) throws Exception {

        File csvTweets = new File(Launcher.class.getResource("/org/easybatch/tutorials/basic/keyapis/tweets.csv").toURI());
        FileWriter xmlTweetsWriter = new FileWriter(new File("tweets.xml"));

        aNewEngine()
                .reader(new FlatFileRecordReader(csvTweets))
                .filter(new HeaderRecordFilter())
                .mapper(new DelimitedRecordMapper<Tweet>(Tweet.class, new String[]{"id", "user", "message"}))
                .processor(new TweetToXmlTransformer())
                .writer(new FileRecordWriter(xmlTweetsWriter))
                .jobEventListener(new XmlWrapperTagWriter(xmlTweetsWriter, "tweets"))
                .build().call();

    }

    private static class TweetToXmlTransformer implements RecordProcessor<Tweet, String> {

        private XStream xStream;

        public TweetToXmlTransformer() {
            xStream = new XStream();
            xStream.alias("tweet", Tweet.class);
        }

        @Override
        public String processRecord(Tweet tweet) throws RecordProcessingException {
            return xStream.toXML(tweet);
        }
    }
}
