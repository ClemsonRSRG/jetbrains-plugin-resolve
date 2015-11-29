# IntelliJ Idea Plugin for RESOLVE (lite)

An [IntelliJ](https://www.jetbrains.com/idea/) 13.x, 14.x plugin for the
RESOLVE specification and programming language.

**update:** I've halted development of this for the time being:

see this new [repo](https://github.com/antlr/jetbrains):

*All I have is documentation at this point in this repository, but I will be adding code as I can generalize what I've done. I started out using the internal "PSI" trees that Intellij is based upon but I don't like their parsing and lexing mechanism. That makes it hard for me to use their built-in PSI trees, which are fine, but still different than what ANTLR wants to build. The ANTLR plug-in itself was my first and it converted ANTLR trees to PSI trees but it required a very complicated adapter from ANTLR recognizers to what intellij needs. It is also very fragile and I can't get it to work right so I built my StringTemplate plug-in using ANTLR trees and recognizers exclusively. His language is semi-difficult because it is actually two languages. At the outermost level it is a group file (of templates) but each template definition has an embedded language of StringTemplate itself. When I can, I will abstract out the generic code that makes it easy to build ANTLR-based language plug-ins for jetbrains stuff.*

