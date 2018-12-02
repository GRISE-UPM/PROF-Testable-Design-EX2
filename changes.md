# Changes made to the code

1.- Changed Document documentIdProvider acquisition to be set with dependency injection

2.- Changed Driver class to be injected in documentIdProvider to use dependency injection

It is not really a change, but I will explain why I did so many assertions in the test called "". 
It is because due to DocumentIdProvider being a singleton, if it is instantiated one time it can't be re-instantiated, so if I need to test the error checks during it's constructor I have to test that error before the normal use cases.
And as we can not expect the tests to be in any specific order, I put them together.
There would be possible to change the implementation of DocumentIdProvider to not be a singleton, but the code shouldn't be changed when testing it, at least if it is possible to test it without refactoring it