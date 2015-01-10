package jko.mongo;

import com.mongodb.*;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//

public class MongoDAOImpl implements MongoDAO {
    private static final Logger log = Logger.getLogger(MongoDAOImpl.class);

    private DBCollection c1, c2;
    private WriteResult status;

//    public MongoDAOImpl(SimpleMongoDbFactory mongoDbFactory) {
//        mongoDbFactory.setWriteConcern( WriteConcern.JOURNALED );
//
//        c1 = mongoDbFactory.getDb().getCollection( "c1" );
//        c2 = mongoDbFactory.getDb().getCollection( "c2" );
//    }

    // new MongoClientURI( "mongodb://user1:sws11wed@ds049170.mongolab.com:49170/jko" ) );


    public MongoDAOImpl() throws Exception {
//        MongoClientURI uri =  new MongoClientURI("mongodb://user1:sws11wed@ds049170.mongolab.com:49170/jko" );
//        MongoClient mongo = new MongoClient(
//                new ServerAddress( "ds049170.mongolab.com", 49170 ),
//                Arrays.asList( uri.getCredentials() ),
//                MongoClientOptions.builder()
//                    .minConnectionsPerHost(100)
//                    .connectionsPerHost(100)
//                    .writeConcern(WriteConcern.JOURNALED)
//                    .threadsAllowedToBlockForConnectionMultiplier(1)
//                    .build()
//                );

        MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://user1:sws11wed@ds049170.mongolab.com:49170/jko" ));

        c1 = (mongo.getDB("jko").getCollection("foo"));
        c2 = (mongo.getDB("jko").getCollection("bar"));

    }


    @Override
    public void plainInsert() {
        status = c1.insert(new BasicDBObject("x", 1000));
   }

    @Override
    public int loopInsert() {
        int collisions = 0;
        while (true) {
            try {
                DBCursor cursor = c2.find().sort( new BasicDBObject("_id", -1 ) ).limit( 1 );
                int id = cursor.hasNext() ? (Integer) cursor.next().get( "_id" ) + 1 : 0;
                status = c2.insert(new BasicDBObject("_id", id).append("x", 2000));
                break;
            }
            catch (MongoException e) {
                if (e.getCode() != 11000) {
                    log.error("ERROR: message:" + e.getMessage());
                    break;
                }
                else {
                    collisions += 1;
                }
            }
        }
        return collisions;
    }

    public WriteResult getStatus() {
        return status;
    }

}
