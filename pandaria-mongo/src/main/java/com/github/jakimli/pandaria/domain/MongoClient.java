package com.github.jakimli.pandaria.domain;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.mongodb.client.MongoClients.create;

@Component
public class MongoClient {

    private MongoDatabase database;

    public MongoClient(
            @Value("${mongo.db.name}") String name,
            @Value("${mongo.connection.string}") String connection) {
        database = create(connection).getDatabase(name);
    }

    public void insert(String collection, String document) {
        MongoCollection<Document> collect = database.getCollection(collection);

        if (collect == null) {
            database.createCollection(collection);
            collect = database.getCollection(collection);
        }

        collect.insertOne(Document.parse(document));
    }
}
