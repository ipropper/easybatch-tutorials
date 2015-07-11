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

package org.easybatch.tutorials.advanced.quartz;

import org.easybatch.core.api.Engine;
import org.easybatch.core.impl.EngineBuilder;
import org.easybatch.core.reader.StringRecordReader;
import org.easybatch.integration.quartz.BatchJobScheduler;
import org.easybatch.tutorials.basic.helloworld.TweetProcessor;

import java.util.Date;

import static org.easybatch.core.util.Utils.LINE_SEPARATOR;

/**
 * Main class to run the Hello World tutorial repeatedly every minute using easy batch - quartz integration module.<br/>
 *
 * The {@link org.easybatch.integration.quartz.BatchJobScheduler} API lets you schedule easy batch executions as follows :
 * <ul>
 * <li>At a fixed point of time using {@link org.easybatch.integration.quartz.BatchJobScheduler#scheduleAt(Engine, java.util.Date)}</li>
 * <li>Repeatedly with predefined interval using {@link org.easybatch.integration.quartz.BatchJobScheduler#scheduleAtWithInterval(Engine, java.util.Date, int)}</li>
 * <li>Using unix cron-like expression with {@link org.easybatch.integration.quartz.BatchJobScheduler#scheduleCron(Engine, String)}</li>
 * </ul>
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class Launcher {

    public static void main(String[] args) throws Exception {

        // Create the data source
        String dataSource =
                "1,foo,easy batch rocks! #EasyBatch" + LINE_SEPARATOR +
                "2,bar,@foo I do confirm :-)";

        // Build a batch engine
        Engine engine = new EngineBuilder()
                .reader(new StringRecordReader(dataSource))
                .processor(new TweetProcessor())
                .build();

        // Schedule the engine to start now and run every 10 seconds
        BatchJobScheduler scheduler = BatchJobScheduler.getInstance();
        scheduler.scheduleAtWithInterval(engine, new Date(), 10);
        scheduler.start();

        System.out.println("Hit enter to stop the application");
        System.in.read();
        scheduler.stop();
    }

}