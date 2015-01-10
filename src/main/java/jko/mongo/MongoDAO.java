package jko.mongo;

import com.mongodb.WriteResult;

public interface MongoDAO {
    int   loopInsert();
    void  plainInsert();

    WriteResult getStatus();
}
