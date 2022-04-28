package com.yrnet.appweb.monitor.endpoint;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

import java.util.List;

/**
 * @author dengbp
 */
//@ErpEndPoint
public class ErpHttpTraceEndpoint {


    private final HttpTraceRepository repository;

    public ErpHttpTraceEndpoint(HttpTraceRepository repository) {
        this.repository = repository;
    }

    public FebsHttpTraceDescriptor traces() {
        return new FebsHttpTraceDescriptor(this.repository.findAll());
    }

    public static final class FebsHttpTraceDescriptor {

        private final List<HttpTrace> traces;

        private FebsHttpTraceDescriptor(List<HttpTrace> traces) {
            this.traces = traces;
        }

        public List<HttpTrace> getTraces() {
            return this.traces;
        }
    }
}
