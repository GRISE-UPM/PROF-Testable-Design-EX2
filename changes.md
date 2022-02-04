# Changes made to the code

## 1. Document
- Changed **Document** constructor to get a DocumentIdProvider object as method parameter

## 2. DocumentIdProvider

- Changed **DocumentIdProvider** constructor from *private* to *protected* (allows access from inside **DocumentIdProviderDouble**)
- Removed *ENVIRON* variable and replaced it with System.getProperty("user.dir") to simplify testing
- Changed **DocumentIdProvider** constructor and split the content into new methods: 
  - *loadProperties*
  - *loadDDBBDriver*
  - *initDBConnection*
  - *getDocumentFromCountersTable*
  - *getLastId*

- Made *statement* and *resultSet* private class variables 
- Made **initDBConnection** method receive *url*, *username*, *password* as parameters  

## 3. DocumentIdProviderDouble

- New class for tests involving **DocumentIdProvider**
- Override **getDocumentId** to throw exception if documentId < 0
- Override **getLastId** to throw exception (CORRUPTED_COUNTER error)

## 4. HandleDcocumentMainClass

- Changed how **Document** object is instanced.