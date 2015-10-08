package edu.clemson.resolve.plugin.psi.impl;

import com.intellij.lang.ASTNode;
import edu.clemson.resolve.plugin.psi.ResRecordType;
import edu.clemson.resolve.plugin.psi.ResTypeReferenceExpression;
import org.jetbrains.annotations.Nullable;

/**
 * Created by daniel on 10/7/15.
 */
public class ResRecordTypeImpl
        extends
            AbstractResTypeImpl implements ResRecordType {

    public ResRecordTypeImpl(ASTNode node) {
        super(node);
    }
}
