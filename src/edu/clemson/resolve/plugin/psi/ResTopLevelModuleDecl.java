package edu.clemson.resolve.plugin.psi;

/** A common interface for all language constructs declarable within the
 *  topmost module scope. This includes definitions, types, functions, global
 *  vars, etc.
 *  <p>
 *  If 'topmost module scope' seems hazy, you can intuitively think
 *  of these as anything we can declare between the 'name' of the module and
 *  the 'end' keyword.</p>
 */
public interface ResTopLevelModuleDecl extends ResCompositeElement {
}
