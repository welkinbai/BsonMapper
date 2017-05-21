# BsonMapper
a light wrapper for mongo-java-driver Bson to convert POJO to Bson or in reverse

## 中文
### 快速开始
1. 计划上传至Maven中央库，目前在审核中，可先通过Release下载JAR包使用
2. 支持JDK6+
3. 主要有两个接口：`BsonMapper`和`MongoBsonMapper`
4. 提供了实现类`DefaultBsonMapper`，用法如下：
```java
  BsonMapper bsonMapper = DefaultBsonMapper.defaultBsonMapper();
  BsonTest bsonTest = bsonMapper.readFrom(bsonDocument1, BsonTest.class);
  
  BsonMapper bsonMapper = DefaultBsonMapper.defaultBsonMapper();
  BsonDocument bsonDocument = bsonMapper.writeToBsonDocument(getObject());
```

5. 除了在POJO和BsonDocument之间转换外，还支持在与`BsonInput\BsonOutput`以及`jsonString`之间转换
6. 和Mongo java driver共用来简化操作

本项目主要是为了方便使用原生的Mongo java driver来存取MongoDB的数据，然而Mongo-java-driver包非常智障地使用`codec`，这就要求用户在使用的时候要为每个POJO编写codec来实现document到POJO之间的转换。
使用本项目可以直接如下操作：
```java
    MongoDatabase testDatabase = mongoClient.getDatabase("test");
    MongoCollection<Document> testCol = testDatabase.getCollection("test_col");
    Book book = new Book();
    book.setName("testBook");
    book.setAmount(30.21 + Math.random());
    book.setAuthor("welkin");
    book.setPageNum(125);
    book.setHasCover(true);
    MongoBsonMapper mongoBsonMapper = DefaultBsonMapper.defaultMongoBsonMapper();
    testCol.insertOne(mongoBsonMapper.writeToMongoDocument(book));
    
    BsonDocument first = testCol.find().first();
    Book bookFromDb = bsonMapper.readFrom(first, Book.class);
```
还可使用`BsonMapper`来进行转换，在对象特别大的时候，效率高于`MongoBsonMapper`:
```java
    MongoDatabase testDatabase = mongoClient.getDatabase("test");
    MongoCollection<Document> testCol = testDatabase.getCollection("test_col");
    Book book = new Book();
    book.setName("testBook");
    book.setAmount(30.21 + Math.random());
    book.setAuthor("welkin");
    book.setPageNum(125);
    book.setHasCover(true);
    MongoBsonMapper mongoBsonMapper = DefaultBsonMapper.defaultMongoBsonMapper();
    testCol.insertOne(mongoBsonMapper.writeToMongoDocument(book)); 
    
    Document first = testCol.find().first();
    Book bookFromDb = mongoBsonMapper.readFrom(first, Book.class);
```
### 问题与反馈
目前本项目处于初期阶段，刚刚完成基本功能。
注意，可能会有bug，不可以应用到生产环境。
个人能力有限，欢迎提出issues以及pull request。

## English

