# BNF information, tips and tricks

Getting up and running, working-with, and keeping track of all the Jetbrains plugin support tools can make your head spin when starting development of a language plugin. The purpose of this doc is to simply point out some helpful tools for working with grammarkit .bnf style grammars, and to specify a process for prepping and maintaining the resulting grammar and lexer.

## Starting out: some helpful/required tools

First off, before doing anything, here are some **extremely** helpful (e.g.: pretty much essential) tools for doing any language plugin development work.

### Grammarkit 

[Grammarkit](https://github.com/JetBrains/Grammar-Kit) is currently the only "official" Jetbrains-backed tool for custom language development. I'll leave the specifics and tutorials to the readme on the project homepage, but essentially think of this tool as a means of generating a parser that produces a reasonably expressive PSI (Program Structure Interface) tree -- which is really (from what I have gathered) the backbone of any non-trivial language plugin.

Install by going to `Preferences` (OSX) (or `Settings` on Windows) > `Plugins` > `Browse Repositories` Then search for "grammarkit"/"grammar", etc. Or just download and install the zip from the Jetbrains online plugin repo [here](https://plugins.jetbrains.com/plugin/6606).

### Psi viewer

Since you're likely going to spend most of your time typing in little sample programs and staring at the PSI structure that results, the [Psi viewer](https://plugins.jetbrains.com/plugin/227) plugin is essential (follow the same installation procedure as provided for grammarkit).

*pic*



*Mention parrts adaptor*

*howto generate lexer from jflex spec, howto generate parser from bnf (each without getting mired in errors)*


Psi Viewer, classes, our tree's organization in terms of bnf
