// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;

public class ResVisitor extends PsiElementVisitor {

  public void visitAddInfixExp(@NotNull ResAddInfixExp o) {
    visitExp(o);
  }

  public void visitAndInfixExp(@NotNull ResAndInfixExp o) {
    visitExp(o);
  }

  public void visitArgumentList(@NotNull ResArgumentList o) {
    visitCompositeElement(o);
  }

  public void visitAssignStatement(@NotNull ResAssignStatement o) {
    visitStatement(o);
  }

  public void visitBooleanLiteral(@NotNull ResBooleanLiteral o) {
    visitCompositeElement(o);
  }

  public void visitCallExp(@NotNull ResCallExp o) {
    visitExp(o);
  }

  public void visitChangingClause(@NotNull ResChangingClause o) {
    visitCompositeElement(o);
  }

  public void visitCloseIdentifier(@NotNull ResCloseIdentifier o) {
    visitCompositeElement(o);
  }

  public void visitConceptBlock(@NotNull ResConceptBlock o) {
    visitBlock(o);
  }

  public void visitConceptExtensionModuleDecl(@NotNull ResConceptExtensionModuleDecl o) {
    visitModuleDecl(o);
  }

  public void visitConceptModuleDecl(@NotNull ResConceptModuleDecl o) {
    visitModuleDecl(o);
  }

  public void visitConstraintsClause(@NotNull ResConstraintsClause o) {
    visitCompositeElement(o);
  }

  public void visitConventionsClause(@NotNull ResConventionsClause o) {
    visitCompositeElement(o);
  }

  public void visitCorrespondenceClause(@NotNull ResCorrespondenceClause o) {
    visitCompositeElement(o);
  }

  public void visitDecreasingClause(@NotNull ResDecreasingClause o) {
    visitCompositeElement(o);
  }

  public void visitElseStatement(@NotNull ResElseStatement o) {
    visitStatement(o);
  }

  public void visitEnsuresClause(@NotNull ResEnsuresClause o) {
    visitCompositeElement(o);
  }

  public void visitEntailsClause(@NotNull ResEntailsClause o) {
    visitCompositeElement(o);
  }

  public void visitExemplarDecl(@NotNull ResExemplarDecl o) {
    visitNamedElement(o);
  }

  public void visitExp(@NotNull ResExp o) {
    visitTypeOwner(o);
  }

  public void visitExtensionPairing(@NotNull ResExtensionPairing o) {
    visitCompositeElement(o);
  }

  public void visitFacilityBlock(@NotNull ResFacilityBlock o) {
    visitBlock(o);
  }

  public void visitFacilityDecl(@NotNull ResFacilityDecl o) {
    visitNamedElement(o);
  }

  public void visitFacilityModuleDecl(@NotNull ResFacilityModuleDecl o) {
    visitModuleDecl(o);
  }

  public void visitFieldDef(@NotNull ResFieldDef o) {
    visitNamedElement(o);
  }

  public void visitFieldVarDeclGroup(@NotNull ResFieldVarDeclGroup o) {
    visitCompositeElement(o);
  }

  public void visitIfStatement(@NotNull ResIfStatement o) {
    visitStatement(o);
  }

  public void visitImplBlock(@NotNull ResImplBlock o) {
    visitBlock(o);
  }

  public void visitImplModuleDecl(@NotNull ResImplModuleDecl o) {
    visitModuleDecl(o);
  }

  public void visitImplModuleParameters(@NotNull ResImplModuleParameters o) {
    visitModuleParameters(o);
  }

  public void visitInfixExp(@NotNull ResInfixExp o) {
    visitCompositeElement(o);
  }

  public void visitIntializationClause(@NotNull ResIntializationClause o) {
    visitCompositeElement(o);
  }

  public void visitLiteralExp(@NotNull ResLiteralExp o) {
    visitExp(o);
  }

  public void visitMaintainingClause(@NotNull ResMaintainingClause o) {
    visitCompositeElement(o);
  }

  public void visitMathAddInfixApplyExp(@NotNull ResMathAddInfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathAlternativeExp(@NotNull ResMathAlternativeExp o) {
    visitMathExp(o);
  }

  public void visitMathAlternativeItemExp(@NotNull ResMathAlternativeItemExp o) {
    visitMathExp(o);
  }

  public void visitMathAngle1OutfixApplyExp(@NotNull ResMathAngle1OutfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathAngleOutfixApplyExp(@NotNull ResMathAngleOutfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathApplicationInfixApplyExp(@NotNull ResMathApplicationInfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathAssertionExp(@NotNull ResMathAssertionExp o) {
    visitMathExp(o);
  }

  public void visitMathBarOutfixApplyExp(@NotNull ResMathBarOutfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathBooleanInfixApplyExp(@NotNull ResMathBooleanInfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathCartProdExp(@NotNull ResMathCartProdExp o) {
    visitMathExp(o);
  }

  public void visitMathCategoricalDefnDecl(@NotNull ResMathCategoricalDefnDecl o) {
    visitMathDefinitionDecl(o);
  }

  public void visitMathCupOutfixApplyExp(@NotNull ResMathCupOutfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathDblBarOutfixApplyExp(@NotNull ResMathDblBarOutfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathEqualityInfixApplyExp(@NotNull ResMathEqualityInfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathExp(@NotNull ResMathExp o) {
    visitCompositeElement(o);
  }

  public void visitMathIdentInfixApplyExp(@NotNull ResMathIdentInfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathImpliesInfixApplyExp(@NotNull ResMathImpliesInfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathIncomingUnaryApplyExp(@NotNull ResMathIncomingUnaryApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathInductiveDefnDecl(@NotNull ResMathInductiveDefnDecl o) {
    visitMathDefinitionDecl(o);
  }

  public void visitMathInfixApplyExp(@NotNull ResMathInfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathInfixDefnSig(@NotNull ResMathInfixDefnSig o) {
    visitCompositeElement(o);
  }

  public void visitMathJoiningInfixApplyExp(@NotNull ResMathJoiningInfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathLambdaExp(@NotNull ResMathLambdaExp o) {
    visitMathExp(o);
  }

  public void visitMathLiteralExp(@NotNull ResMathLiteralExp o) {
    visitMathExp(o);
  }

  public void visitMathMultInfixApplyExp(@NotNull ResMathMultInfixApplyExp o) {
    visitMathInfixApplyExp(o);
  }

  public void visitMathNestedExp(@NotNull ResMathNestedExp o) {
    visitMathExp(o);
  }

  public void visitMathOutfixDefnSig(@NotNull ResMathOutfixDefnSig o) {
    visitCompositeElement(o);
  }

  public void visitMathPostfixDefnSig(@NotNull ResMathPostfixDefnSig o) {
    visitCompositeElement(o);
  }

  public void visitMathPrefixApplyExp(@NotNull ResMathPrefixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathPrefixDefnSig(@NotNull ResMathPrefixDefnSig o) {
    visitCompositeElement(o);
  }

  public void visitMathPrefixGeneralizedCeilApplyExp(@NotNull ResMathPrefixGeneralizedCeilApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathPrefixGeneralizedSqbrApplyExp(@NotNull ResMathPrefixGeneralizedSqbrApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathQuantifiedExp(@NotNull ResMathQuantifiedExp o) {
    visitMathExp(o);
  }

  public void visitMathReferenceExp(@NotNull ResMathReferenceExp o) {
    visitMathExp(o);
    // visitReferenceExpBase(o);
  }

  public void visitMathRelationalInfixApplyExp(@NotNull ResMathRelationalInfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathSelectorExp(@NotNull ResMathSelectorExp o) {
    visitMathInfixApplyExp(o);
  }

  public void visitMathSetComprehensionExp(@NotNull ResMathSetComprehensionExp o) {
    visitMathExp(o);
  }

  public void visitMathSetElementsList(@NotNull ResMathSetElementsList o) {
    visitCompositeElement(o);
  }

  public void visitMathSetExp(@NotNull ResMathSetExp o) {
    visitMathExp(o);
  }

  public void visitMathSetInfixApplyExp(@NotNull ResMathSetInfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathSqBrOutfixApplyExp(@NotNull ResMathSqBrOutfixApplyExp o) {
    visitMathExp(o);
  }

  public void visitMathStandardDefnDecl(@NotNull ResMathStandardDefnDecl o) {
    visitMathDefinitionDecl(o);
  }

  public void visitMathSymbolName(@NotNull ResMathSymbolName o) {
    visitCompositeElement(o);
  }

  public void visitMathTheoremDecl(@NotNull ResMathTheoremDecl o) {
    visitNamedElement(o);
  }

  public void visitMathTypeAssertionExp(@NotNull ResMathTypeAssertionExp o) {
    visitMathExp(o);
  }

  public void visitMathVarDecl(@NotNull ResMathVarDecl o) {
    visitMathVarDeclGroup(o);
  }

  public void visitMathVarDeclGroup(@NotNull ResMathVarDeclGroup o) {
    visitCompositeElement(o);
  }

  public void visitMathVarDef(@NotNull ResMathVarDef o) {
    visitNamedElement(o);
  }

  public void visitModuleArgList(@NotNull ResModuleArgList o) {
    visitCompositeElement(o);
  }

  public void visitModuleSpec(@NotNull ResModuleSpec o) {
    visitCompositeElement(o);
  }

  public void visitMulInfixExp(@NotNull ResMulInfixExp o) {
    visitExp(o);
  }

  public void visitOpBlock(@NotNull ResOpBlock o) {
    visitBlock(o);
  }

  public void visitOperationDecl(@NotNull ResOperationDecl o) {
    visitOperationLikeNode(o);
  }

  public void visitOperationProcedureDecl(@NotNull ResOperationProcedureDecl o) {
    visitOperationLikeNode(o);
  }

  public void visitOrInfixExp(@NotNull ResOrInfixExp o) {
    visitExp(o);
  }

  public void visitParamDecl(@NotNull ResParamDecl o) {
    visitCompositeElement(o);
  }

  public void visitParamDef(@NotNull ResParamDef o) {
    visitNamedElement(o);
  }

  public void visitParameterMode(@NotNull ResParameterMode o) {
    visitCompositeElement(o);
  }

  public void visitParenExp(@NotNull ResParenExp o) {
    visitExp(o);
  }

  public void visitPrecisBlock(@NotNull ResPrecisBlock o) {
    visitBlock(o);
  }

  public void visitPrecisExtensionModuleDecl(@NotNull ResPrecisExtensionModuleDecl o) {
    visitModuleDecl(o);
  }

  public void visitPrecisModuleDecl(@NotNull ResPrecisModuleDecl o) {
    visitModuleDecl(o);
  }

  public void visitProcedureDecl(@NotNull ResProcedureDecl o) {
    visitCompositeElement(o);
  }

  public void visitRecordType(@NotNull ResRecordType o) {
    visitType(o);
  }

  public void visitRecordVarDeclGroup(@NotNull ResRecordVarDeclGroup o) {
    visitCompositeElement(o);
  }

  public void visitReferenceExp(@NotNull ResReferenceExp o) {
    visitExp(o);
    // visitReferenceExpBase(o);
  }

  public void visitRelationalInfixExp(@NotNull ResRelationalInfixExp o) {
    visitExp(o);
  }

  public void visitRequiresClause(@NotNull ResRequiresClause o) {
    visitCompositeElement(o);
  }

  public void visitSelectorExp(@NotNull ResSelectorExp o) {
    visitExp(o);
  }

  public void visitSimpleStatement(@NotNull ResSimpleStatement o) {
    visitStatement(o);
  }

  public void visitSpecModuleParameters(@NotNull ResSpecModuleParameters o) {
    visitModuleParameters(o);
  }

  public void visitStatement(@NotNull ResStatement o) {
    visitCompositeElement(o);
  }

  public void visitStringLiteral(@NotNull ResStringLiteral o) {
    visitCompositeElement(o);
  }

  public void visitSwapStatement(@NotNull ResSwapStatement o) {
    visitStatement(o);
  }

  public void visitType(@NotNull ResType o) {
    visitCompositeElement(o);
  }

  public void visitTypeImplInit(@NotNull ResTypeImplInit o) {
    visitCompositeElement(o);
  }

  public void visitTypeModelDecl(@NotNull ResTypeModelDecl o) {
    visitTypeLikeNodeDecl(o);
  }

  public void visitTypeParamDecl(@NotNull ResTypeParamDecl o) {
    visitNamedElement(o);
  }

  public void visitTypeReferenceExp(@NotNull ResTypeReferenceExp o) {
    visitReferenceExpBase(o);
  }

  public void visitTypeReprDecl(@NotNull ResTypeReprDecl o) {
    visitTypeLikeNodeDecl(o);
  }

  public void visitUnaryExp(@NotNull ResUnaryExp o) {
    visitExp(o);
  }

  public void visitUsesItemList(@NotNull ResUsesItemList o) {
    visitCompositeElement(o);
  }

  public void visitUsesSpec(@NotNull ResUsesSpec o) {
    visitNamedElement(o);
  }

  public void visitUsesString(@NotNull ResUsesString o) {
    visitCompositeElement(o);
  }

  public void visitVarDeclGroup(@NotNull ResVarDeclGroup o) {
    visitCompositeElement(o);
  }

  public void visitVarDef(@NotNull ResVarDef o) {
    visitNamedElement(o);
  }

  public void visitVarSpec(@NotNull ResVarSpec o) {
    visitCompositeElement(o);
  }

  public void visitWhileStatement(@NotNull ResWhileStatement o) {
    visitStatement(o);
  }

  public void visitBlock(@NotNull ResBlock o) {
    visitCompositeElement(o);
  }

  public void visitMathDefinitionDecl(@NotNull ResMathDefinitionDecl o) {
    visitCompositeElement(o);
  }

  public void visitModuleDecl(@NotNull ResModuleDecl o) {
    visitCompositeElement(o);
  }

  public void visitModuleParameters(@NotNull ResModuleParameters o) {
    visitCompositeElement(o);
  }

  public void visitNamedElement(@NotNull ResNamedElement o) {
    visitCompositeElement(o);
  }

  public void visitOperationLikeNode(@NotNull ResOperationLikeNode o) {
    visitCompositeElement(o);
  }

  public void visitReferenceExpBase(@NotNull ResReferenceExpBase o) {
    visitCompositeElement(o);
  }

  public void visitTypeLikeNodeDecl(@NotNull ResTypeLikeNodeDecl o) {
    visitCompositeElement(o);
  }

  public void visitTypeOwner(@NotNull ResTypeOwner o) {
    visitCompositeElement(o);
  }

  public void visitCompositeElement(@NotNull ResCompositeElement o) {
    visitElement(o);
  }

}
