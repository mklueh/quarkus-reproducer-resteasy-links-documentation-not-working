package com.example;

import io.quarkus.resteasy.reactive.links.InjectRestLinks;
import io.quarkus.resteasy.reactive.links.RestLink;
import io.quarkus.resteasy.reactive.links.RestLinkType;
import jakarta.ws.rs.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

import static jakarta.ws.rs.core.MediaType.*;
import static org.jboss.resteasy.reactive.common.util.RestMediaType.*;

@Path("/records")
@Produces({APPLICATION_JSON, APPLICATION_HAL_JSON})
public class RecordsResource {
    @Data
    public static class Record {
        private String name;

        public static Record name(String name) {
            var test = new Record();
            test.setName(name);
            return test;
        }
    }

    @Data
    public static class TestRecord {
        private String name;

        public static TestRecord name(String name) {
            var test = new TestRecord();
            test.setName(name);
            return test;
        }
    }

    private static final Map<Integer, Record> recordDb = Map.of(
            1, Record.name("a"),
            2, Record.name("b"),
            3, Record.name("c"),
            4, Record.name("d")
    );


    private static final Map<Integer, TestRecord> testRecordDb = Map.of(
            1, TestRecord.name("a"),
            2, TestRecord.name("b"),
            3, TestRecord.name("c"),
            4, TestRecord.name("d")
    );

    @GET
    @InjectRestLinks
    @RestLink(rel = "list")
    public List<Record> getAll() {
        return recordDb.values().stream().toList();
    }

    @GET
    @Path("/without-type-instance/{id}")
    @RestLink(rel = "get-by-slug-or-id")
    public TestRecord injectNothing(@PathParam("id") int id) {
        return testRecordDb.get(id);
    }

    @GET
    @Path("/with-type-instance/{id}")
    @InjectRestLinks(RestLinkType.INSTANCE)
    @RestLink(entityType = TestRecord.class)
    public TestRecord injectTypeInstance(@PathParam("id") int id) {
        return testRecordDb.get(id);
    }
}
