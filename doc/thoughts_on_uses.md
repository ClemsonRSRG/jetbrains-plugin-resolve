## Thoughts/examples with `uses` clauses

Here's a simple facility named `T`:
```
Facility T;
	uses (
	    Standard_Booleans,
	    U from cpsc372::dtwelch::test,    --test is ok because it contains .resolve files..   cpsc372.dtwelch isn't because it doesn't
		X from cpsc372::msitara::meh;
    );
	...
end T;
```
(what goes after the `from`s are actually specifying `libDirectory`s'

*Clients are responsible for choosing a 'sufficiently high' package (first dir that contains .resolve files...)
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
    . msitara
      . meh
        . V.java
        . V.class
    . Users                         //this project is not on stdlibs or resolvepath
      . daniel
        . documents
          . some_nonconformal_proj
            . X.java
            . X.class
```

I'm thinking that (in the compiler) we could keep track of which `AnnotatedModule`s are derived from files on
`RESOLVEPATH`, and if so, which directory in the path. Then all we have to do is write it out to that directory.

If -lib is set to the containing directory only

In the compiler, each annotated module keeps track of the following (in the spirit of the above example):

LIBDIR should really be called "pkgDir", and users must specify it on the commandline of the tool....

package normally needs to be specified by users... but in the case where the user is using the IDE, maybe I can just
refer to the module that the user is within... that's the package...

#### Standard_Booleans:
* rawpath=Users/daniel/Documents/resolve-lite/src/facilities/Standard_Booleans.resolve
* libdir=
* relativizedlibdir: `facilities`

####



