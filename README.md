# BsonMapper
a light wrapper for mongo-java-driver Bson to convert POJO to Bson or in reverse  
一个轻量级封装库实现POJO和BSON之间的转换  

## Menu
[中文](#中文)  
[English](#English)

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
    book.setAmount(30.21);
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
    MongoCollection<BsonDocument> testCol = testDatabase.getCollection("test_col", BsonDocument.class);
    Book book = new Book();
    book.setName("testBook");
    book.setAmount(30.21);
    book.setAuthor("welkin");
    book.setPageNum(125);
    book.setHasCover(true);
    BsonMapper bsonMapper = DefaultBsonMapper.defaultBsonMapper();
    testCol.insertOne(bsonMapper.writeToBsonDocument(book));
    
    BsonDocument first = testCol.find().first();
    Book bookFromDb = bsonMapper.readFrom(first, Book.class);
```
### 使用注解
在POJO上可以使用以下注解：
- `@BsonField`：可以指定映射到Bson时的名称以及是否为ObjectId
- `@BsonIgnore`：忽略该字段
- `@BsonArrayField`：如果是一个List或者Set，由于泛型擦除导致的问题，必须手动指定数组中存放的对象类型

### 问题与反馈
目前本项目处于初期阶段，刚刚完成基本功能。  
注意，可能会有bug，不可以应用到生产环境。  
个人能力有限，欢迎提出issues以及pull request。  

## English
### Quick Start  
1. Plan to upload it to the Maven central library, and you can download JAR package through Release to use  since it is under review.
2. Support JDK 6+
3. Two main interfaces:`BsonMapper` and `MongoBsonMapper`
4. A implementing class(`DefaultBsonMapper`) is provided and usage is as follows:
```java
  BsonMapper bsonMapper = DefaultBsonMapper.defaultBsonMapper();
  BsonTest bsonTest = bsonMapper.readFrom(bsonDocument1, BsonTest.class);
  
  BsonMapper bsonMapper = DefaultBsonMapper.defaultBsonMapper();
  BsonDocument bsonDocument = bsonMapper.writeToBsonDocument(getObject());
```

5. Besides the convertion between POJO and BsonDocument, BsonInput\BsonOutput and jsonString is also supported.
6. Easier usage of using it along with Mongo-java-driver is accessible.
This project is mainly in order to facilitate the use of native Mongo Java driver to access MongoDB data, since Mongo-java-driver package require a `codec` to achieve the conversion of document and POJO for every POJO when developer use it. With what I provided, you can directly do this:
```java
    MongoDatabase testDatabase = mongoClient.getDatabase("test");
    MongoCollection<Document> testCol = testDatabase.getCollection("test_col");
    Book book = new Book();
    book.setName("testBook");
    book.setAmount(30.21);
    book.setAuthor("welkin");
    book.setPageNum(125);
    book.setHasCover(true);
    MongoBsonMapper mongoBsonMapper = DefaultBsonMapper.defaultMongoBsonMapper();
    testCol.insertOne(mongoBsonMapper.writeToMongoDocument(book));
    
    BsonDocument first = testCol.find().first();
    Book bookFromDb = bsonMapper.readFrom(first, Book.class);
```
Or you can do conversion with BsonMapper, whose efficiency is higher than MongoBsonMapper when the object is too big.
```java
    MongoDatabase testDatabase = mongoClient.getDatabase("test");
    MongoCollection<BsonDocument> testCol = testDatabase.getCollection("test_col", BsonDocument.class);
    Book book = new Book();
    book.setName("testBook");
    book.setAmount(30.21);
    book.setAuthor("welkin");
    book.setPageNum(125);
    book.setHasCover(true);
    BsonMapper bsonMapper = DefaultBsonMapper.defaultBsonMapper();
    testCol.insertOne(bsonMapper.writeToBsonDocument(book));
    
    BsonDocument first = testCol.find().first();
    Book bookFromDb = bsonMapper.readFrom(first, Book.class);
```

### use annotation
You can use these annotations in POJO：
- `@BsonField`：define name of Bson field and if it is a ObjectId
- `@BsonIgnore`：will ignore the field when converting
- `@BsonArrayField`：if the field is a List or Set，must define component Type because of generic erase problem.

### Question and Feedback  
This project is now in its early stages with basic functions.  
Note that it can not be applied to the production environment in the risk of unpredictable bug.  
Since my limited capability, issues and pull requests are very welcome.  

