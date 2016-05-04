# Progress Report: Designing a *Fully Featured RESOLVE IDE for Engineering Verified Software

### Abstract

Integrated Development Environments (IDEs) have made large strides in recent years towards making code easier to write,
navigate though, and ultimately maintain in projects of all sizes -- large to small. Through features such as
syntactic and semantic error annotations, context-based keyword and reference completion, and a host of other
transformational "housekeeping" capabilities (such as refactor/rename support), IDEs have significantly reduced much of
the burden in arriving at -- and ultimately maintaining -- syntactically and semantically "competent" code. This
progress report explores the integration of these existing features along with formal verification related features into
a commercial class IDE built on top of the IntelliJ IDEA platform (*footnote* maybe just put "built in.." into the
footnote as well?) for RESOLVE -- an integrated programming and mathematical specification language.

### Introduction and high level goals

The usefulness and central role of IDEs in developing and maintaining software projects of all
sizes is -- at this point -- undisputed. Looking beyond however simply helping users write conventionally designed
"competent" code, in order to keep a handle on software complexity while also increasing quality of resultant
software, current IDEs stand to benefit from taking up support for languages that allow users to abstractly model
components, verify implementations correct with respect to specifications, and ultimately make any such verified code
available in easily accessible, bite-sized component-packages for reuse in future projects.

In expanding the role of IDEs to encompass this lofty goal, the question then naturally becomes how to effectively
incorporate the RESOLVE modeling, programming, and verification methodology into an IDE in a way that leverages the
many positive qualities of existing, modern IDEs while also meshing well with the iterative approach [cite] we've found
both students and average users to take towards reasoning about, and ultimately verifying the correctness of their
code. This report takes several initial steps towards exploring this question. The rest of the paper is organized as
follows: section ...

TALK ABOUT CHALLENGES

### IDE design and organization

The organization of the actual IDE is

### Current features

As is typical of IDEs, there are many disparate units of functionality blended together within the editor interface
discussed in section 2. Thus, in the proceeding sections we showcase some of these features, and illustrate their
usefulness in writing RESOLVE specifications, theories, and executable code.

#### "Live template" support

Live templates is an extensible mechanism that allow users to predefine frequently occurring fragments of code, and insert
them into appropriate contexts in their programs. A template itself can be thought of as a 'document with holes' which users,
at the time of instantiation, are prompted with filling in. The actual act of inserting a template into a valid
context is performed by typing a label/keystroke (determined by the author of the template) then repeatedly visiting and
filling in any blank fields (holes) defined. In this section we illustrate two general ways live templates are leveraged
by the IDE.

##### Mathematical glyphs (unicode support)

A long standing design objective in RESOLVE is in making sure specifications and their underlying mathematics actually
look like math -- with the expectation that they'll be familiar and easily recognizable to 'by-trade' mathematicians
(not just 'cs-pseudo-mathematicians'). This effort however has been seriously hobbled by the extremely limited range of
ascii characters.

extensible 
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