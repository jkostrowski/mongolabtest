package jko.mongo;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by JKO on 1/9/2015
 */
public class Benchmark {
    private static final Logger log = Logger.getLogger(Benchmark.class);


    public Map<String,Object> run() {
        Map<String,Object> map = new HashMap<String, Object>();

        try {
            MongoDAO dao = new MongoDAOImpl();
            dao.plainInsert();
            map.put("status", dao.getStatus());

        } catch (Exception e) {
            log.error( "Mongo DAO error", e );
            map.put("exception", e.getMessage());
        }

        map.put("collisions", "0");

        return map;
    }
}
