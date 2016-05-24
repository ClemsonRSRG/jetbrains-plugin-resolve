## Thoughts/examples with `uses` clauses

Here's a simple facility named `T`:
```
Facility T;
	uses Standard_Booleans,
		U from cpsc372.dtwelch.test;
	...
end T;
```
the `out` folder hierarchy will look like this (once javac has run):

```
. out
  . facilities
    . Standard_Booleans.java
    . Standard_Booleans.class

  . concepts
    . boolean_template
      . Boolean_Template.java
      . Boolean_Template.class
      . Boolean_Impl.java
      . Boolean_Impl.class

  . cpsc372
    . dtwelch
      . test
        . U.java
        . U.class
```

I'm thinking that (in the compiler) we could keep track of which `AnnotatedModule`s are derived from files on
`RESOLVEPATH`, and if so, which directory in the path. Then all we have to do is write it out to that directory.

so if the path is not in RESOLVEPATH or RESOLVEROOT