# Progress Report: Designing a *Fully Featured RESOLVE IDE for Engineering Verified Software

### Abstract

Integrated Development Environments (IDEs) have made large strides in recent years towards making code easier to write,
navigate though, and ultimately maintain in projects of all sizes -- large and small. Through features such as
syntactic and semantic error annotations, context-based keyword and reference completions, as well as a host of other
transformational "housekeeping" capabilities (such as refactor/rename support), IDEs have significantly reduced much of
the burden in arriving at -- and ultimately *maintaining* -- syntactically and semantically "competent" code. This
progress report explores the integration of these and additional formal verification related features into an IDE built
on top of the IntelliJ IDEA platform (*footnote* maybe just put "built in.." into the footnote as well?) for RESOLVE --
an integrated programming and mathematical specification language.

### Introduction and high level goals

The usefulness and importance of IDEs as a central workbench for developing and maintaining software projects of all
sizes is -- at this point -- undisputed. Beyond however simply helping users achieve syntactically and semantically
valid code, in order to keep a handle on growing complexity and increase the quality of software, IDEs will ultimately
need to take up support for languages that allow users to abstractly model components, verify implementations correct
with respect to the specifications, and ultimately make any such verified code available in
libraries/component-packages to be reused as a foundation in future projects.

The question naturally then becomes how to effectively incorporate the RESOLVE modeling, programming, and verification
methodology into an IDE in a way that meshes well with the iterative approach [cite] observed reasoning process. This
report takes several initial steps towards exploring this question. The rest paper is organized as follows: section ...

TALK ABOUT CHALLENGES

### IDE design and organization

The organization of the actual IDE is

### Current features

As is typical of IDEs, there are many disparate units of functionality blended together within the editor interface
discussed in section 2. Thus, in the proceeding sections we showcase some of these features, and illustrate their
usefulness in writing RESOLVE specifications, theories, and executable code.

#### "Live template" support

Live templates are a mechanism that allow users to predefine frequently occuring fragments of code, and insert them
into user defined contexts within the code. A template itself can be thought of as a 'document with holes' which users,
at the time of instantiation, are tasked with filling in. Many such templates come packaged standard IDE for languages
and are often marketed as a time-saver

##### Mathematical glyphs (unicode support)

Maintaining the mathematical appearance of the mathematics used to verify software written in RESOLVE math units and specifications has, and remains one of the primary

that these efforts have been seriously hobbled by the

A long standing design objective in RESOLVE has been invested in making specifications, and their underlying mathematics
actually look like math. This effort however has been seriously hobbeled by the inability
In the context of RESOLVE, specifications and their appearance
One especially useful wmof this feature in RESOLVE is encoding non- noticeable
The incorporation of a feature referred to as *live templates* makes the incorporation of user defined mathematical
glyphs easier than learning (and remembering) arbitrary unicode key-combinations. Rather, to insert a particular symbol,
users

##### Language constructs


#### Keyword & reference completion

*Todo for both mathematical and programmatic contexts.

#### Integration with the RESOLVE compiler

The IDE interfaces directly with the RESOLVE compiler and communicates annotions back and forth which in then
placed over the appropriates tokens on the internal IntelliJ Psi tree


##### Semantic error annotations
*TODO
(requires integration with the resolve compiler)

##### Code generation
*TODO

#### Limited reference completion

#### Standard and user extensible library support

### Future features

#### Verification oriented

#### Design by contract oriented

#### 'Simple' one-off features


#### Verification condition reporting

###





Understandability, ease of use, and modern navigational features come together to make for a promising environment for
developing modular, verified components.

The usefulness of IDEs for
The past several we have seen

Toolchain for developing verified code in RESOLVE

Developing resolve code from the developer perspective. Though, these are also tools that upper class students are no
doubt unfamiliar with. And having our language easily integrateable into this existing framework

While the system being discussed is in development, we also discuss several verification-oriented features currently
under consideration such as handing the presentation of Verification Conditions (VCs) as well as

The system being discussed is in development
We also explore the
using the Jetbrains platform,