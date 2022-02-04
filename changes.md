# Changes made to the code

## 1. DocumentIdProvider

- Changed **DocumentIdProvider** constructor from *private* to *protected* (allows access from inside **DocumentIdProviderDouble**)

## 2. Document 
- Changed **Document** constructor to get a DocumentIdProvider object as method parameter


## 3. DocumentIdProviderDouble

- New class for for tests involving **DocumentIdProvider**

## 4. HandleDcocumentMainClass

- Changed how **Document** object is instanced.