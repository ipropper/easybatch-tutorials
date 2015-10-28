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

package org.easybatch.tutorials.advanced.distributed;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.io.IOUtils;
import org.easybatch.core.dispatcher.AbstractRecordDispatcher;
import org.easybatch.core.dispatcher.RecordDispatchingException;
import org.easybatch.core.record.Header;
import org.easybatch.core.record.Record;
import org.easybatch.core.record.StringRecord;
import org.easybatch.tutorials.advanced.jms.JMSUtil;

import javax.jms.JMSException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static java.lang.String.format;

/**
 * Record dispatcher listening to incoming rest requests.
 * Each PUT request with a new record will be dispatched to a worker job.
 *
 * <strong>
 * This class is kept simple for demonstration purpose.
 * You should inject a list of JMS queues to dispatch records to instead of calling
 * the static {@link JMSUtil} class using a single queue.
 * </strong>
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class RestEndpointRecordDispatcher extends AbstractRecordDispatcher<Record> implements HttpHandler {

    private long recordNumber;

    @Override
    public void dispatchRecord(Record record) throws RecordDispatchingException {
        try {
            JMSUtil.sendStringRecord((StringRecord)record);
        } catch (JMSException e) {
            String message = format("Unable to dispatch record %s", record);
            throw new RecordDispatchingException(message, e);
        }
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        //should check if request == PUT && path = /api/orders ..
        InputStream requestBody = httpExchange.getRequestBody();
        String payload = IOUtils.toString(requestBody);
        try {
            Header header = new Header(++recordNumber, "REST API: /api/orders", new Date());
            dispatchRecord(new StringRecord(header, payload));
            httpExchange.sendResponseHeaders(200, 0);
        } catch (Exception e) {
            httpExchange.sendResponseHeaders(500, 0);
        } finally {
            httpExchange.close();
        }
    }
}
