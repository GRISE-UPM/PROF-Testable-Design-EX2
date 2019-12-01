# Changes made to the code

- Refactored hardcoded collaborators to use dependency injection instead. Dependencies are now passed at construction time and used when needed.
  - `TemplateFactory` no longer uses `static` methods.
  - `Document` saves at construction time an instance of `TemplateFactory` and `DocumentIdProvider` to make testing and dependency modification easier.
  - Singleton left unchanged but constructor is no longer private to make subclassing easier during testing.
- Refactor internal methods that access external resources to make testing easier and allow for easier modification.
  - Methods meant to be overriden during testing start with an underscore (`_`).
- Update argument positions so that they match the ones given as example in the problem statement.
- Better documentation (not really good either).
