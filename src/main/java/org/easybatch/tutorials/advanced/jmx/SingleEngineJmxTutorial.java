/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package org.easybatch.tutorials.advanced.jmx;

import org.easybatch.core.api.Engine;
import org.easybatch.core.api.Report;
import org.easybatch.core.impl.EngineBuilder;
import org.easybatch.core.reader.StringRecordReader;

import static org.easybatch.core.util.Utils.LINE_SEPARATOR;

/**
 * Main class to run the JMX tutorial.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class SingleEngineJmxTutorial {

    public static void main(String[] args) throws Exception {

        // Create the String data source
        String dataSource =
                "1,foo,easy batch rocks! #EasyBatch" + LINE_SEPARATOR +
                "2,bar,@foo I do confirm :-)" + LINE_SEPARATOR +
                "3,baz,@foo @bar what are you talking about? Am I in trouble?" + LINE_SEPARATOR +
                "4,foo,@baz yes you are in trouble!" + LINE_SEPARATOR +
                "5,bar,@baz It's about easy batch. See in here: http://www.easybatch.org cc @md_benhassine" + LINE_SEPARATOR +
                "6,baz,@foo @bar @md_benhassine Oh damn that's really easy!" + LINE_SEPARATOR +
                "7,md_benhassine,Thank you all! your feedback is welcome :-)" + LINE_SEPARATOR +
                "8,foo,@md_benhassine Have you some benchmarks out there?" + LINE_SEPARATOR +
                "9,md_benhassine,@foo yep check them out here: https://github.com/EasyBatch/easybatch-benchmarks" + LINE_SEPARATOR +
                "10,foo,@md_benhassine I'll see there thx!";

        // Build a batch engine
        Engine engine = new EngineBuilder()
                .reader(new StringRecordReader(dataSource))
                .processor(new TweetSlowProcessor())
                .enableJMX(true)
                .build();

        // Run the batch engine
        Report report = engine.call();

        System.out.println("report = " + report);

    }

}