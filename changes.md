# Changes made to the code

## DocumentIdProvider;

- Se ha quitado el private de los atributos ENVIRON, documentID e instance para poder ser accesibles desde fuera de la clase.
- Igualmente quitado el private del constructor, y puesto public para el siguiente punto.
- Se ha creado el testDouble de esta clase, en el package de test.
- El asunto es que el constructor de esta clase es complejo, e incluye casi toda la lógica de la clase. Para simplificarlo,
  se han creado métodos para cada una de las cosas que realiza relativas a la base de datos, con el objetivo de aislarlas y
  poder cambiarlas para ser testables, sin necesidad de acceder a la BD, en el double.
- Como resultado de esto, se han tenido que incluir dos variables para que el statement y el resultSet puedan ser testables.
  Además, en getLastObjectID se añade un parámetro para poder realizar la penúltima prueba en el double.


## Document:

- Ya que no hay getters para los atributos del documento, se quitan también aquí los privates.
- En el constructor de document, el id se obtiene ahora con un objeto de la clase double en vez de con la original, así como el
  provider, que pasa a usarse el double.
  
  
## DocumentIdProviderDouble:

- El test double del provider, que sobreescribe los métodos necesarios para hacerlos testables (mayoritariamente todo lo rela-
  tivo a la base de datos). El objetivo es que este double permita testear todo lo requerido en el enunciado sin dejar de hacer funcionar
  la clase original y pudiendo acceder a sus variables y métodos.