# Changes made to the code

+ **HandleDocuments/Document.java:** 
	- Atributos sin getters pasan a ser protected
	- Se sacan todas las operaciones fuera del contructor.
+ **HandleDocuments/HandleDocumentsmainClass.java:**
	- Se saca todas las en una metodo fuera del contructor se deberán
	- Atributos sin getters pasan a ser protected
	- Metodos privados pasan a ser protected y dejan de ser estáticos
	- Funcionalidades para implementar las funcionalidades del constructuror se implementan metodos protected.
	- Se ha creado un metodo auxiliar para la ejecución de queries, de tipo protected