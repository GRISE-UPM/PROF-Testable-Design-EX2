# Changes made to the code

- Modified Class Document:
  - DocumentId is now provided using dep. injection by the    constructor -> The constructor has an int parameter to "customize" the way of getting that id (DocumentIdProvider.getInstance().getDocumentId() is supposed to be called when instantiating the documents).

- Modified Class DocumentIdProvider:
    - Hardcoded db driver class name is now a constant.
    - Modified Constructor:
        - Constructor is now protected -> Needed to extend the class creating "Doubles" for mocking purposes.
        - Extract protected method **loadProperties** -> Needed for mocking that part and testing that functionality.
        - Extract protected method **loadDBDriver** -> Needed for mocking that part and testing that functionality.
        - Extract protected method **createDBConnection** -> Needed for mocking that part and testing that functionality.
         - Extract protected method **getLastId** -> Needed for mocking that part and testing that functionality.

- Modified Class HandleDocumentsMainClass:
    - Changed new document statement, now its instantiated with the new constructor -> Document document = new Document(DocumentIdProvider.getInstance().getDocumentId());

- Created Class DocumentIdProviderDouble: Mocks the getDocumetId() method overriding it.

- Created Class DocumentIdProviderDoubleV2: Simulates a fail in the getLastId() method (More than one row on the Counters Table)