/*
 * The MIT License
 *
 *  Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

package org.easybatch.tutorials.advanced.distributed;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.io.IOUtils;
import org.easybatch.core.record.Batch;
import org.easybatch.core.record.Header;
import org.easybatch.core.record.Record;
import org.easybatch.core.record.StringRecord;
import org.easybatch.core.writer.RecordWriter;
import org.easybatch.tutorials.advanced.jms.JMSUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Record dispatcher listening to incoming rest requests.
 * Each PUT request with a new record will be dispatched to a worker job.
 *
 * <strong>
 * This class is kept simple for demonstration purpose.
 * You should inject a list of JMS queues to which dispatch records instead of calling
 * the static {@link JMSUtil} class with a single queue.
 * </strong>
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class RestEndpointRecordDispatcher implements RecordWriter, HttpHandler {

    private long recordNumber;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        //should check if request == PUT && path = /api/orders ..
        InputStream requestBody = httpExchange.getRequestBody();
        String payload = IOUtils.toString(requestBody);
        try {
            Header header = new Header(++recordNumber, "REST API: /api/orders", new Date());
            writeRecords(new Batch(new StringRecord(header, payload)));
            httpExchange.sendResponseHeaders(200, 0);
        } catch (Exception e) {
            httpExchange.sendResponseHeaders(500, 0);
        } finally {
            httpExchange.close();
        }
    }

    @Override
    public void open() throws Exception {

    }

    @Override
    public void writeRecords(Batch batch) throws Exception {
        for (Record record : batch) {
            JMSUtil.sendStringRecord((StringRecord)record);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
