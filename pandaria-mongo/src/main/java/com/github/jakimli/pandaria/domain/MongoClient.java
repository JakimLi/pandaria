package com.github.jakimli.pandaria.domain;

import com.mongodb.BasicDBList;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.mongodb.client.MongoClients.create;
import static org.bson.Document.parse;

@Component
public class MongoClient {

    private MongoDatabase database;

    public MongoClient(
            @Value("${mongo.db.name}") String name,
            @Value("${mongo.db.connection}") String connection) {
        database = create(connection).getDatabase(name);
    }

    public void insert(String collection, String document) {
        collection(collection).insertOne(parse(document));
    }

    public BasicDBList findAll(String collection) {
        return toList(collection(collection).find().iterator());
    }

    private MongoCollection<Document> collection(String collection) {
        return database.getCollection(collection);
    }

    private BasicDBList toList(MongoCursor<Document> iterator) {
        BasicDBList list = new BasicDBList();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }
}
