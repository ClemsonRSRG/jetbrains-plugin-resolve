// This is a generated file. Not intended for manual editing.
package edu.clemson.resolve.jetbrains.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static edu.clemson.resolve.jetbrains.ResTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class ResParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, EXTENDS_SETS_);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == ADD_INFIX_EXP) {
      r = Exp(b, 0, 2);
    }
    else if (t == AND_INFIX_EXP) {
      r = Exp(b, 0, 0);
    }
    else if (t == ARGUMENT_LIST) {
      r = ArgumentList(b, 0);
    }
    else if (t == ASSIGN_STATEMENT) {
      r = AssignStatement(b, 0);
    }
    else if (t == BOOLEAN_LITERAL) {
      r = BooleanLiteral(b, 0);
    }
    else if (t == CALL_EXP) {
      r = Exp(b, 0, 5);
    }
    else if (t == CHANGING_CLAUSE) {
      r = ChangingClause(b, 0);
    }
    else if (t == CLOSE_IDENTIFIER) {
      r = CloseIdentifier(b, 0);
    }
    else if (t == CONCEPT_BLOCK) {
      r = ConceptBlock(b, 0);
    }
    else if (t == CONCEPT_EXTENSION_MODULE_DECL) {
      r = ConceptExtensionModuleDecl(b, 0);
    }
    else if (t == CONCEPT_MODULE_DECL) {
      r = ConceptModuleDecl(b, 0);
    }
    else if (t == CONSTRAINTS_CLAUSE) {
      r = ConstraintsClause(b, 0);
    }
    else if (t == CONVENTIONS_CLAUSE) {
      r = ConventionsClause(b, 0);
    }
    else if (t == CORRESPONDENCE_CLAUSE) {
      r = CorrespondenceClause(b, 0);
    }
    else if (t == DECREASING_CLAUSE) {
      r = DecreasingClause(b, 0);
    }
    else if (t == ELSE_STATEMENT) {
      r = ElseStatement(b, 0);
    }
    else if (t == ENSURES_CLAUSE) {
      r = EnsuresClause(b, 0);
    }
    else if (t == ENTAILS_CLAUSE) {
      r = EntailsClause(b, 0);
    }
    else if (t == EXEMPLAR_DECL) {
      r = ExemplarDecl(b, 0);
    }
    else if (t == EXP) {
      r = Exp(b, 0, -1);
    }
    else if (t == EXTENSION_PAIRING) {
      r = ExtensionPairing(b, 0);
    }
    else if (t == FACILITY_BLOCK) {
      r = FacilityBlock(b, 0);
    }
    else if (t == FACILITY_DECL) {
      r = FacilityDecl(b, 0);
    }
    else if (t == FACILITY_MODULE_DECL) {
      r = FacilityModuleDecl(b, 0);
    }
    else if (t == FIELD_DEF) {
      r = FieldDef(b, 0);
    }
    else if (t == FIELD_VAR_DECL_GROUP) {
      r = FieldVarDeclGroup(b, 0);
    }
    else if (t == IF_STATEMENT) {
      r = IfStatement(b, 0);
    }
    else if (t == IMPL_BLOCK) {
      r = ImplBlock(b, 0);
    }
    else if (t == IMPL_MODULE_DECL) {
      r = ImplModuleDecl(b, 0);
    }
    else if (t == IMPL_MODULE_PARAMETERS) {
      r = ImplModuleParameters(b, 0);
    }
    else if (t == INTIALIZATION_CLAUSE) {
      r = IntializationClause(b, 0);
    }
    else if (t == LITERAL_EXP) {
      r = LiteralExp(b, 0);
    }
    else if (t == MAINTAINING_CLAUSE) {
      r = MaintainingClause(b, 0);
    }
    else if (t == MATH_ADD_INFIX_APPLY_EXP) {
      r = MathExp(b, 0, 5);
    }
    else if (t == MATH_ALTERNATIVE_EXP) {
      r = MathAlternativeExp(b, 0);
    }
    else if (t == MATH_ALTERNATIVE_ITEM_EXP) {
      r = MathAlternativeItemExp(b, 0);
    }
    else if (t == MATH_ANGLE_1_OUTFIX_APPLY_EXP) {
      r = MathAngle1OutfixApplyExp(b, 0);
    }
    else if (t == MATH_ANGLE_OUTFIX_APPLY_EXP) {
      r = MathAngleOutfixApplyExp(b, 0);
    }
    else if (t == MATH_APPLICATION_INFIX_APPLY_EXP) {
      r = MathExp(b, 0, 3);
    }
    else if (t == MATH_ASSERTION_EXP) {
      r = MathAssertionExp(b, 0);
    }
    else if (t == MATH_BAR_OUTFIX_APPLY_EXP) {
      r = MathBarOutfixApplyExp(b, 0);
    }
    else if (t == MATH_BOOLEAN_INFIX_APPLY_EXP) {
      r = MathExp(b, 0, -1);
    }
    else if (t == MATH_CART_PROD_EXP) {
      r = MathCartProdExp(b, 0);
    }
    else if (t == MATH_CATEGORICAL_DEFN_DECL) {
      r = MathCategoricalDefnDecl(b, 0);
    }
    else if (t == MATH_CUP_OUTFIX_APPLY_EXP) {
      r = MathCupOutfixApplyExp(b, 0);
    }
    else if (t == MATH_DBL_BAR_OUTFIX_APPLY_EXP) {
      r = MathDblBarOutfixApplyExp(b, 0);
    }
    else if (t == MATH_EQUALITY_INFIX_APPLY_EXP) {
      r = MathExp(b, 0, 1);
    }
    else if (t == MATH_EXP) {
      r = MathExp(b, 0, -1);
    }
    else if (t == MATH_IDENT_INFIX_APPLY_EXP) {
      r = MathExp(b, 0, 7);
    }
    else if (t == MATH_IMPLIES_INFIX_APPLY_EXP) {
      r = MathExp(b, 0, 0);
    }
    else if (t == MATH_INCOMING_UNARY_APPLY_EXP) {
      r = MathIncomingUnaryApplyExp(b, 0);
    }
    else if (t == MATH_INDUCTIVE_DEFN_DECL) {
      r = MathInductiveDefnDecl(b, 0);
    }
    else if (t == MATH_INFIX_DEFN_SIG) {
      r = MathInfixDefnSig(b, 0);
    }
    else if (t == MATH_JOINING_INFIX_APPLY_EXP) {
      r = MathExp(b, 0, 10);
    }
    else if (t == MATH_LAMBDA_EXP) {
      r = MathLambdaExp(b, 0);
    }
    else if (t == MATH_LITERAL_EXP) {
      r = MathLiteralExp(b, 0);
    }
    else if (t == MATH_MULT_INFIX_APPLY_EXP) {
      r = MathExp(b, 0, 6);
    }
    else if (t == MATH_NESTED_EXP) {
      r = MathNestedExp(b, 0);
    }
    else if (t == MATH_OUTFIX_DEFN_SIG) {
      r = MathOutfixDefnSig(b, 0);
    }
    else if (t == MATH_POSTFIX_DEFN_SIG) {
      r = MathPostfixDefnSig(b, 0);
    }
    else if (t == MATH_PREFIX_APPLY_EXP) {
      r = MathExp(b, 0, 11);
    }
    else if (t == MATH_PREFIX_DEFN_SIG) {
      r = MathPrefixDefnSig(b, 0);
    }
    else if (t == MATH_PREFIX_GENERALIZED_CEIL_APPLY_EXP) {
      r = MathExp(b, 0, 13);
    }
    else if (t == MATH_PREFIX_GENERALIZED_SQBR_APPLY_EXP) {
      r = MathExp(b, 0, 12);
    }
    else if (t == MATH_QUANTIFIED_EXP) {
      r = MathQuantifiedExp(b, 0);
    }
    else if (t == MATH_REFERENCE_EXP) {
      r = MathReferenceExp(b, 0);
    }
    else if (t == MATH_RELATIONAL_INFIX_APPLY_EXP) {
      r = MathExp(b, 0, 2);
    }
    else if (t == MATH_SELECTOR_EXP) {
      r = MathExp(b, 0, 15);
    }
    else if (t == MATH_SET_COMPREHENSION_EXP) {
      r = MathSetComprehensionExp(b, 0);
    }
    else if (t == MATH_SET_ELEMENTS_LIST) {
      r = MathSetElementsList(b, 0);
    }
    else if (t == MATH_SET_EXP) {
      r = MathSetExp(b, 0);
    }
    else if (t == MATH_SET_INFIX_APPLY_EXP) {
      r = MathExp(b, 0, 4);
    }
    else if (t == MATH_SQ_BR_OUTFIX_APPLY_EXP) {
      r = MathSqBrOutfixApplyExp(b, 0);
    }
    else if (t == MATH_STANDARD_DEFN_DECL) {
      r = MathStandardDefnDecl(b, 0);
    }
    else if (t == MATH_SYMBOL_NAME) {
      r = MathSymbolName(b, 0);
    }
    else if (t == MATH_THEOREM_DECL) {
      r = MathTheoremDecl(b, 0);
    }
    else if (t == MATH_TYPE_ASSERTION_EXP) {
      r = MathExp(b, 0, 15);
    }
    else if (t == MATH_VAR_DECL) {
      r = MathVarDecl(b, 0);
    }
    else if (t == MATH_VAR_DECL_GROUP) {
      r = MathVarDeclGroup(b, 0);
    }
    else if (t == MATH_VAR_DEF) {
      r = MathVarDef(b, 0);
    }
    else if (t == MODULE_ARG_LIST) {
      r = ModuleArgList(b, 0);
    }
    else if (t == MODULE_SPEC) {
      r = ModuleSpec(b, 0);
    }
    else if (t == MUL_INFIX_EXP) {
      r = Exp(b, 0, 3);
    }
    else if (t == OP_BLOCK) {
      r = OpBlock(b, 0);
    }
    else if (t == OPERATION_DECL) {
      r = OperationDecl(b, 0);
    }
    else if (t == OPERATION_PROCEDURE_DECL) {
      r = OperationProcedureDecl(b, 0);
    }
    else if (t == OR_INFIX_EXP) {
      r = Exp(b, 0, -1);
    }
    else if (t == PARAM_DECL) {
      r = ParamDecl(b, 0);
    }
    else if (t == PARAM_DEF) {
      r = ParamDef(b, 0);
    }
    else if (t == PARAMETER_MODE) {
      r = ParameterMode(b, 0);
    }
    else if (t == PAREN_EXP) {
      r = ParenExp(b, 0);
    }
    else if (t == PRECIS_BLOCK) {
      r = PrecisBlock(b, 0);
    }
    else if (t == PRECIS_EXTENSION_MODULE_DECL) {
      r = PrecisExtensionModuleDecl(b, 0);
    }
    else if (t == PRECIS_MODULE_DECL) {
      r = PrecisModuleDecl(b, 0);
    }
    else if (t == PROCEDURE_DECL) {
      r = ProcedureDecl(b, 0);
    }
    else if (t == RECORD_TYPE) {
      r = RecordType(b, 0);
    }
    else if (t == RECORD_VAR_DECL_GROUP) {
      r = RecordVarDeclGroup(b, 0);
    }
    else if (t == REFERENCE_EXP) {
      r = ReferenceExp(b, 0);
    }
    else if (t == RELATIONAL_INFIX_EXP) {
      r = Exp(b, 0, 1);
    }
    else if (t == REQUIRES_CLAUSE) {
      r = RequiresClause(b, 0);
    }
    else if (t == SELECTOR_EXP) {
      r = Exp(b, 0, 5);
    }
    else if (t == SIMPLE_STATEMENT) {
      r = SimpleStatement(b, 0);
    }
    else if (t == SPEC_MODULE_PARAMETERS) {
      r = SpecModuleParameters(b, 0);
    }
    else if (t == STATEMENT) {
      r = Statement(b, 0);
    }
    else if (t == STRING_LITERAL) {
      r = StringLiteral(b, 0);
    }
    else if (t == SWAP_STATEMENT) {
      r = SwapStatement(b, 0);
    }
    else if (t == TYPE) {
      r = Type(b, 0);
    }
    else if (t == TYPE_IMPL_INIT) {
      r = TypeImplInit(b, 0);
    }
    else if (t == TYPE_MODEL_DECL) {
      r = TypeModelDecl(b, 0);
    }
    else if (t == TYPE_PARAM_DECL) {
      r = TypeParamDecl(b, 0);
    }
    else if (t == TYPE_REFERENCE_EXP) {
      r = TypeReferenceExp(b, 0);
    }
    else if (t == TYPE_REPR_DECL) {
      r = TypeReprDecl(b, 0);
    }
    else if (t == UNARY_EXP) {
      r = UnaryExp(b, 0);
    }
    else if (t == USES_ITEM) {
      r = UsesItem(b, 0);
    }
    else if (t == USES_ITEM_LIST) {
      r = UsesItemList(b, 0);
    }
    else if (t == VAR_DECL_GROUP) {
      r = VarDeclGroup(b, 0);
    }
    else if (t == VAR_DEF) {
      r = VarDef(b, 0);
    }
    else if (t == VAR_SPEC) {
      r = VarSpec(b, 0);
    }
    else if (t == WHILE_STATEMENT) {
      r = WhileStatement(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return ModuleDecl(b, l + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(RECORD_TYPE, TYPE),
    create_token_set_(MATH_VAR_DECL, MATH_VAR_DECL_GROUP),
    create_token_set_(MATH_MULT_INFIX_APPLY_EXP, MATH_SELECTOR_EXP),
    create_token_set_(ASSIGN_STATEMENT, ELSE_STATEMENT, IF_STATEMENT, SIMPLE_STATEMENT,
      STATEMENT, SWAP_STATEMENT, WHILE_STATEMENT),
    create_token_set_(ADD_INFIX_EXP, AND_INFIX_EXP, CALL_EXP, EXP,
      LITERAL_EXP, MUL_INFIX_EXP, OR_INFIX_EXP, PAREN_EXP,
      REFERENCE_EXP, RELATIONAL_INFIX_EXP, SELECTOR_EXP, UNARY_EXP),
    create_token_set_(MATH_ADD_INFIX_APPLY_EXP, MATH_ALTERNATIVE_EXP, MATH_ALTERNATIVE_ITEM_EXP, MATH_ANGLE_1_OUTFIX_APPLY_EXP,
      MATH_ANGLE_OUTFIX_APPLY_EXP, MATH_APPLICATION_INFIX_APPLY_EXP, MATH_ASSERTION_EXP, MATH_BAR_OUTFIX_APPLY_EXP,
      MATH_BOOLEAN_INFIX_APPLY_EXP, MATH_CART_PROD_EXP, MATH_CUP_OUTFIX_APPLY_EXP, MATH_DBL_BAR_OUTFIX_APPLY_EXP,
      MATH_EQUALITY_INFIX_APPLY_EXP, MATH_EXP, MATH_IDENT_INFIX_APPLY_EXP, MATH_IMPLIES_INFIX_APPLY_EXP,
      MATH_INCOMING_UNARY_APPLY_EXP, MATH_JOINING_INFIX_APPLY_EXP, MATH_LAMBDA_EXP, MATH_LITERAL_EXP,
      MATH_MULT_INFIX_APPLY_EXP, MATH_NESTED_EXP, MATH_PREFIX_APPLY_EXP, MATH_PREFIX_GENERALIZED_CEIL_APPLY_EXP,
      MATH_PREFIX_GENERALIZED_SQBR_APPLY_EXP, MATH_QUANTIFIED_EXP, MATH_REFERENCE_EXP, MATH_RELATIONAL_INFIX_APPLY_EXP,
      MATH_SELECTOR_EXP, MATH_SET_COMPREHENSION_EXP, MATH_SET_EXP, MATH_SET_INFIX_APPLY_EXP,
      MATH_SQ_BR_OUTFIX_APPLY_EXP, MATH_TYPE_ASSERTION_EXP),
  };

  /* ********************************************************** */
  // '(' ExpArgumentList? ')'
  public static boolean ArgumentList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArgumentList")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, ArgumentList_1(b, l + 1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, ARGUMENT_LIST, r, p, null);
    return r || p;
  }

  // ExpArgumentList?
  private static boolean ArgumentList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArgumentList_1")) return false;
    ExpArgumentList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ':=' Exp
  public static boolean AssignStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AssignStatement")) return false;
    if (!nextTokenIs(b, COLON_EQUALS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _LEFT_, null);
    r = consumeToken(b, COLON_EQUALS);
    p = r; // pin = 1
    r = r && Exp(b, l + 1, -1);
    exit_section_(b, l, m, ASSIGN_STATEMENT, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // true | false
  public static boolean BooleanLiteral(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BooleanLiteral")) return false;
    if (!nextTokenIs(b, "<boolean literal>", FALSE, TRUE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<boolean literal>");
    r = consumeToken(b, TRUE);
    if (!r) r = consumeToken(b, FALSE);
    exit_section_(b, l, m, BOOLEAN_LITERAL, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !('is')
  static boolean CategoricalSigListRec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CategoricalSigListRec")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !CategoricalSigListRec_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // ('is')
  private static boolean CategoricalSigListRec_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CategoricalSigListRec_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IS);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'changing' ExpArgumentList ';'
  public static boolean ChangingClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ChangingClause")) return false;
    if (!nextTokenIs(b, CHANGING)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, CHANGING);
    p = r; // pin = 1
    r = r && report_error_(b, ExpArgumentList(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, CHANGING_CLAUSE, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // identifier
  public static boolean CloseIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CloseIdentifier")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, CLOSE_IDENTIFIER, r);
    return r;
  }

  /* ********************************************************** */
  // ConceptItem*
  public static boolean ConceptBlock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConceptBlock")) return false;
    Marker m = enter_section_(b, l, _NONE_, "<concept block>");
    int c = current_position_(b);
    while (true) {
      if (!ConceptItem(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ConceptBlock", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, CONCEPT_BLOCK, true, false, ItemsRecover_parser_);
    return true;
  }

  /* ********************************************************** */
  // 'Concept' 'Extension' identifier SpecModuleParameters? 'for' ModuleSpec ';'
  // UsesItemList?
  // RequiresClause?
  // ConceptBlock
  // 'end' identifier ';'
  public static boolean ConceptExtensionModuleDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConceptExtensionModuleDecl")) return false;
    if (!nextTokenIs(b, CONCEPT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, CONCEPT);
    r = r && consumeToken(b, EXTENSION);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, IDENTIFIER));
    r = p && report_error_(b, ConceptExtensionModuleDecl_3(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, FOR)) && r;
    r = p && report_error_(b, ModuleSpec(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, SEMICOLON)) && r;
    r = p && report_error_(b, ConceptExtensionModuleDecl_7(b, l + 1)) && r;
    r = p && report_error_(b, ConceptExtensionModuleDecl_8(b, l + 1)) && r;
    r = p && report_error_(b, ConceptBlock(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, END)) && r;
    r = p && report_error_(b, consumeToken(b, IDENTIFIER)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, CONCEPT_EXTENSION_MODULE_DECL, r, p, null);
    return r || p;
  }

  // SpecModuleParameters?
  private static boolean ConceptExtensionModuleDecl_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConceptExtensionModuleDecl_3")) return false;
    SpecModuleParameters(b, l + 1);
    return true;
  }

  // UsesItemList?
  private static boolean ConceptExtensionModuleDecl_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConceptExtensionModuleDecl_7")) return false;
    UsesItemList(b, l + 1);
    return true;
  }

  // RequiresClause?
  private static boolean ConceptExtensionModuleDecl_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConceptExtensionModuleDecl_8")) return false;
    RequiresClause(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TypeModelDecl
  //     | OperationDecl
  //     | MathStandardDefnDecl
  //     | ConstraintsClause
  static boolean ConceptItem(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConceptItem")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = TypeModelDecl(b, l + 1);
    if (!r) r = OperationDecl(b, l + 1);
    if (!r) r = MathStandardDefnDecl(b, l + 1);
    if (!r) r = ConstraintsClause(b, l + 1);
    exit_section_(b, l, m, null, r, false, ConceptItemRecover_parser_);
    return r;
  }

  /* ********************************************************** */
  // !('Type'|'Operation'|'Definition'|'constraints'|'Constraints'|'end')
  static boolean ConceptItemRecover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConceptItemRecover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !ConceptItemRecover_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // 'Type'|'Operation'|'Definition'|'constraints'|'Constraints'|'end'
  private static boolean ConceptItemRecover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConceptItemRecover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FAMILY_TYPE);
    if (!r) r = consumeToken(b, OPERATION);
    if (!r) r = consumeToken(b, DEFINITION);
    if (!r) r = consumeToken(b, CONSTRAINTS);
    if (!r) r = consumeToken(b, "Constraints");
    if (!r) r = consumeToken(b, END);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'Concept' identifier SpecModuleParameters? ';'
  // UsesItemList?
  // RequiresClause?
  // ConceptBlock
  // 'end' identifier ';'
  public static boolean ConceptModuleDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConceptModuleDecl")) return false;
    if (!nextTokenIs(b, CONCEPT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, CONCEPT);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, IDENTIFIER));
    r = p && report_error_(b, ConceptModuleDecl_2(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, SEMICOLON)) && r;
    r = p && report_error_(b, ConceptModuleDecl_4(b, l + 1)) && r;
    r = p && report_error_(b, ConceptModuleDecl_5(b, l + 1)) && r;
    r = p && report_error_(b, ConceptBlock(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, END)) && r;
    r = p && report_error_(b, consumeToken(b, IDENTIFIER)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, CONCEPT_MODULE_DECL, r, p, null);
    return r || p;
  }

  // SpecModuleParameters?
  private static boolean ConceptModuleDecl_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConceptModuleDecl_2")) return false;
    SpecModuleParameters(b, l + 1);
    return true;
  }

  // UsesItemList?
  private static boolean ConceptModuleDecl_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConceptModuleDecl_4")) return false;
    UsesItemList(b, l + 1);
    return true;
  }

  // RequiresClause?
  private static boolean ConceptModuleDecl_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConceptModuleDecl_5")) return false;
    RequiresClause(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ('constraints') MathAssertionExp ';'
  public static boolean ConstraintsClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConstraintsClause")) return false;
    if (!nextTokenIs(b, CONSTRAINTS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = ConstraintsClause_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, MathAssertionExp(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, CONSTRAINTS_CLAUSE, r, p, null);
    return r || p;
  }

  // ('constraints')
  private static boolean ConstraintsClause_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConstraintsClause_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CONSTRAINTS);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'conventions' MathAssertionExp (EntailsClause)? ';'
  public static boolean ConventionsClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConventionsClause")) return false;
    if (!nextTokenIs(b, CONVENTIONS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, CONVENTIONS);
    p = r; // pin = 1
    r = r && report_error_(b, MathAssertionExp(b, l + 1));
    r = p && report_error_(b, ConventionsClause_2(b, l + 1)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, CONVENTIONS_CLAUSE, r, p, null);
    return r || p;
  }

  // (EntailsClause)?
  private static boolean ConventionsClause_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConventionsClause_2")) return false;
    ConventionsClause_2_0(b, l + 1);
    return true;
  }

  // (EntailsClause)
  private static boolean ConventionsClause_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ConventionsClause_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = EntailsClause(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'correspondence' MathAssertionExp ';'
  public static boolean CorrespondenceClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CorrespondenceClause")) return false;
    if (!nextTokenIs(b, CORRESPONDENCE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, CORRESPONDENCE);
    p = r; // pin = 1
    r = r && report_error_(b, MathAssertionExp(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, CORRESPONDENCE_CLAUSE, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // 'decreasing' MathAssertionExp ';'
  public static boolean DecreasingClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DecreasingClause")) return false;
    if (!nextTokenIs(b, DECREASING)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, DECREASING);
    p = r; // pin = 1
    r = r && report_error_(b, MathAssertionExp(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, DECREASING_CLAUSE, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // 'else' Statements
  public static boolean ElseStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ElseStatement")) return false;
    if (!nextTokenIs(b, ELSE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, ELSE);
    p = r; // pin = 1
    r = r && Statements(b, l + 1);
    exit_section_(b, l, m, ELSE_STATEMENT, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // 'ensures' MathAssertionExp ';'
  public static boolean EnsuresClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnsuresClause")) return false;
    if (!nextTokenIs(b, ENSURES)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, ENSURES);
    p = r; // pin = 1
    r = r && report_error_(b, MathAssertionExp(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, ENSURES_CLAUSE, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // 'which_entails' MathExp (',' MathExp)*
  public static boolean EntailsClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EntailsClause")) return false;
    if (!nextTokenIs(b, WHICH_ENTAILS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, WHICH_ENTAILS);
    p = r; // pin = 1
    r = r && report_error_(b, MathExp(b, l + 1, -1));
    r = p && EntailsClause_2(b, l + 1) && r;
    exit_section_(b, l, m, ENTAILS_CLAUSE, r, p, null);
    return r || p;
  }

  // (',' MathExp)*
  private static boolean EntailsClause_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EntailsClause_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!EntailsClause_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "EntailsClause_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' MathExp
  private static boolean EntailsClause_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EntailsClause_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && MathExp(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'exemplar' identifier ';'
  public static boolean ExemplarDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExemplarDecl")) return false;
    if (!nextTokenIs(b, EXEMPLAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, EXEMPLAR);
    r = r && consumeToken(b, IDENTIFIER);
    p = r; // pin = 2
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, l, m, EXEMPLAR_DECL, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ExpWithRecover (',' ExpWithRecover)*
  static boolean ExpArgumentList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExpArgumentList")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = ExpWithRecover(b, l + 1);
    p = r; // pin = 1
    r = r && ExpArgumentList_1(b, l + 1);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  // (',' ExpWithRecover)*
  private static boolean ExpArgumentList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExpArgumentList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ExpArgumentList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ExpArgumentList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' ExpWithRecover
  private static boolean ExpArgumentList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExpArgumentList_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, COMMA);
    p = r; // pin = 1
    r = r && ExpWithRecover(b, l + 1);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // !('%'|'+'|'-'|'('|')'|','|'*'|'<'|'<='|'>'|'>='|'end'|'Operation'|'Type'|'Facility'|';')
  static boolean ExpListRecover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExpListRecover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !ExpListRecover_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // '%'|'+'|'-'|'('|')'|','|'*'|'<'|'<='|'>'|'>='|'end'|'Operation'|'Type'|'Facility'|';'
  private static boolean ExpListRecover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExpListRecover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MOD);
    if (!r) r = consumeToken(b, PLUS);
    if (!r) r = consumeToken(b, MINUS);
    if (!r) r = consumeToken(b, LPAREN);
    if (!r) r = consumeToken(b, RPAREN);
    if (!r) r = consumeToken(b, COMMA);
    if (!r) r = consumeToken(b, MUL);
    if (!r) r = consumeToken(b, LESS);
    if (!r) r = consumeToken(b, LESS_OR_EQUAL);
    if (!r) r = consumeToken(b, GREATER);
    if (!r) r = consumeToken(b, GREATER_OR_EQUAL);
    if (!r) r = consumeToken(b, END);
    if (!r) r = consumeToken(b, OPERATION);
    if (!r) r = consumeToken(b, FAMILY_TYPE);
    if (!r) r = consumeToken(b, FACILITY);
    if (!r) r = consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Exp
  static boolean ExpWithRecover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExpWithRecover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = Exp(b, l + 1, -1);
    exit_section_(b, l, m, null, r, false, ExpListRecover_parser_);
    return r;
  }

  /* ********************************************************** */
  // (ExtensionPairing)*
  static boolean ExtensionList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExtensionList")) return false;
    Marker m = enter_section_(b, l, _NONE_, null);
    int c = current_position_(b);
    while (true) {
      if (!ExtensionList_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ExtensionList", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, null, true, false, ExtensionListRec_parser_);
    return true;
  }

  // (ExtensionPairing)
  private static boolean ExtensionList_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExtensionList_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ExtensionPairing(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(';')
  static boolean ExtensionListRec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExtensionListRec")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !ExtensionListRec_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // (';')
  private static boolean ExtensionListRec_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExtensionListRec_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'extended' 'by' ModuleSpec ModuleArgList? ('externally')?
  // 'implemented' 'by' ModuleSpec ModuleArgList?
  public static boolean ExtensionPairing(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExtensionPairing")) return false;
    if (!nextTokenIs(b, EXTENDED)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, EXTENDED);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, BY));
    r = p && report_error_(b, ModuleSpec(b, l + 1)) && r;
    r = p && report_error_(b, ExtensionPairing_3(b, l + 1)) && r;
    r = p && report_error_(b, ExtensionPairing_4(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, IMPLEMENTED)) && r;
    r = p && report_error_(b, consumeToken(b, BY)) && r;
    r = p && report_error_(b, ModuleSpec(b, l + 1)) && r;
    r = p && ExtensionPairing_8(b, l + 1) && r;
    exit_section_(b, l, m, EXTENSION_PAIRING, r, p, null);
    return r || p;
  }

  // ModuleArgList?
  private static boolean ExtensionPairing_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExtensionPairing_3")) return false;
    ModuleArgList(b, l + 1);
    return true;
  }

  // ('externally')?
  private static boolean ExtensionPairing_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExtensionPairing_4")) return false;
    ExtensionPairing_4_0(b, l + 1);
    return true;
  }

  // ('externally')
  private static boolean ExtensionPairing_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExtensionPairing_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXTERNALLY);
    exit_section_(b, m, null, r);
    return r;
  }

  // ModuleArgList?
  private static boolean ExtensionPairing_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ExtensionPairing_8")) return false;
    ModuleArgList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // FacilityItem*
  public static boolean FacilityBlock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FacilityBlock")) return false;
    Marker m = enter_section_(b, l, _NONE_, "<facility block>");
    int c = current_position_(b);
    while (true) {
      if (!FacilityItem(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "FacilityBlock", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, FACILITY_BLOCK, true, false, ItemsRecover_parser_);
    return true;
  }

  /* ********************************************************** */
  // 'Facility' identifier 'is' ModuleSpec ModuleArgList?
  // ('externally')? 'implemented' 'by' ModuleSpec ModuleArgList?
  // ExtensionList? ';'
  public static boolean FacilityDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FacilityDecl")) return false;
    if (!nextTokenIs(b, FACILITY)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, FACILITY);
    r = r && consumeToken(b, IDENTIFIER);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, IS));
    r = p && report_error_(b, ModuleSpec(b, l + 1)) && r;
    r = p && report_error_(b, FacilityDecl_4(b, l + 1)) && r;
    r = p && report_error_(b, FacilityDecl_5(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, IMPLEMENTED)) && r;
    r = p && report_error_(b, consumeToken(b, BY)) && r;
    r = p && report_error_(b, ModuleSpec(b, l + 1)) && r;
    r = p && report_error_(b, FacilityDecl_9(b, l + 1)) && r;
    r = p && report_error_(b, FacilityDecl_10(b, l + 1)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, FACILITY_DECL, r, p, null);
    return r || p;
  }

  // ModuleArgList?
  private static boolean FacilityDecl_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FacilityDecl_4")) return false;
    ModuleArgList(b, l + 1);
    return true;
  }

  // ('externally')?
  private static boolean FacilityDecl_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FacilityDecl_5")) return false;
    FacilityDecl_5_0(b, l + 1);
    return true;
  }

  // ('externally')
  private static boolean FacilityDecl_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FacilityDecl_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXTERNALLY);
    exit_section_(b, m, null, r);
    return r;
  }

  // ModuleArgList?
  private static boolean FacilityDecl_9(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FacilityDecl_9")) return false;
    ModuleArgList(b, l + 1);
    return true;
  }

  // ExtensionList?
  private static boolean FacilityDecl_10(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FacilityDecl_10")) return false;
    ExtensionList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // FacilityDecl
  //     | OperationProcedureDecl
  //     | TypeReprDecl
  //     | MathStandardDefnDecl
  static boolean FacilityItem(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FacilityItem")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = FacilityDecl(b, l + 1);
    if (!r) r = OperationProcedureDecl(b, l + 1);
    if (!r) r = TypeReprDecl(b, l + 1);
    if (!r) r = MathStandardDefnDecl(b, l + 1);
    exit_section_(b, l, m, null, r, false, FacilityItemRecover_parser_);
    return r;
  }

  /* ********************************************************** */
  // !('Facility'|'Definition'|'Operation'|'Type'|'end')
  static boolean FacilityItemRecover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FacilityItemRecover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !FacilityItemRecover_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // 'Facility'|'Definition'|'Operation'|'Type'|'end'
  private static boolean FacilityItemRecover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FacilityItemRecover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FACILITY);
    if (!r) r = consumeToken(b, DEFINITION);
    if (!r) r = consumeToken(b, OPERATION);
    if (!r) r = consumeToken(b, FAMILY_TYPE);
    if (!r) r = consumeToken(b, END);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'Facility' identifier ';'
  // UsesItemList?
  // RequiresClause?
  // FacilityBlock
  // 'end' identifier ';'
  public static boolean FacilityModuleDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FacilityModuleDecl")) return false;
    if (!nextTokenIs(b, FACILITY)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, FACILITY);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, IDENTIFIER));
    r = p && report_error_(b, consumeToken(b, SEMICOLON)) && r;
    r = p && report_error_(b, FacilityModuleDecl_3(b, l + 1)) && r;
    r = p && report_error_(b, FacilityModuleDecl_4(b, l + 1)) && r;
    r = p && report_error_(b, FacilityBlock(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, END)) && r;
    r = p && report_error_(b, consumeToken(b, IDENTIFIER)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, FACILITY_MODULE_DECL, r, p, null);
    return r || p;
  }

  // UsesItemList?
  private static boolean FacilityModuleDecl_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FacilityModuleDecl_3")) return false;
    UsesItemList(b, l + 1);
    return true;
  }

  // RequiresClause?
  private static boolean FacilityModuleDecl_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FacilityModuleDecl_4")) return false;
    RequiresClause(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // identifier
  public static boolean FieldDef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldDef")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, FIELD_DEF, r);
    return r;
  }

  /* ********************************************************** */
  // FieldVarDefList ':' Type
  public static boolean FieldVarDeclGroup(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldVarDeclGroup")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = FieldVarDefList(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, COLON));
    r = p && Type(b, l + 1) && r;
    exit_section_(b, l, m, FIELD_VAR_DECL_GROUP, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // FieldDef &(!(';'))(',' FieldDef)*
  static boolean FieldVarDefList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldVarDefList")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = FieldDef(b, l + 1);
    p = r; // pin = 1
    r = r && FieldVarDefList_1(b, l + 1);
    r = r && FieldVarDefList_2(b, l + 1);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  // &(!(';'))
  private static boolean FieldVarDefList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldVarDefList_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_, null);
    r = FieldVarDefList_1_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // !(';')
  private static boolean FieldVarDefList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldVarDefList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !FieldVarDefList_1_0_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // (';')
  private static boolean FieldVarDefList_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldVarDefList_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' FieldDef)*
  private static boolean FieldVarDefList_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldVarDefList_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!FieldVarDefList_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "FieldVarDefList_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' FieldDef
  private static boolean FieldVarDefList_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldVarDefList_2_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, COMMA);
    p = r; // pin = 1
    r = r && FieldDef(b, l + 1);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // MathExp (',' MathExp)*
  static boolean GeneralizedApplyArgList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "GeneralizedApplyArgList")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = MathExp(b, l + 1, -1);
    p = r; // pin = 1
    r = r && GeneralizedApplyArgList_1(b, l + 1);
    exit_section_(b, l, m, null, r, p, GeneralizedApplyRecover_parser_);
    return r || p;
  }

  // (',' MathExp)*
  private static boolean GeneralizedApplyArgList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "GeneralizedApplyArgList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!GeneralizedApplyArgList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "GeneralizedApplyArgList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' MathExp
  private static boolean GeneralizedApplyArgList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "GeneralizedApplyArgList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && MathExp(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(']'|'⎤')
  static boolean GeneralizedApplyRecover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "GeneralizedApplyRecover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !GeneralizedApplyRecover_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // ']'|'⎤'
  private static boolean GeneralizedApplyRecover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "GeneralizedApplyRecover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RBRACK);
    if (!r) r = consumeToken(b, RCEIL);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'If' Exp 'then' Statements (ElseStatement)? 'end' ';'
  public static boolean IfStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IfStatement")) return false;
    if (!nextTokenIs(b, PROG_IF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, PROG_IF);
    p = r; // pin = 1
    r = r && report_error_(b, Exp(b, l + 1, -1));
    r = p && report_error_(b, consumeToken(b, THEN)) && r;
    r = p && report_error_(b, Statements(b, l + 1)) && r;
    r = p && report_error_(b, IfStatement_4(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, END)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, IF_STATEMENT, r, p, null);
    return r || p;
  }

  // (ElseStatement)?
  private static boolean IfStatement_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IfStatement_4")) return false;
    IfStatement_4_0(b, l + 1);
    return true;
  }

  // (ElseStatement)
  private static boolean IfStatement_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IfStatement_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ElseStatement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ImplItem*
  public static boolean ImplBlock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplBlock")) return false;
    Marker m = enter_section_(b, l, _NONE_, "<impl block>");
    int c = current_position_(b);
    while (true) {
      if (!ImplItem(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ImplBlock", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, IMPL_BLOCK, true, false, ItemsRecover_parser_);
    return true;
  }

  /* ********************************************************** */
  // TypeReprDecl
  //     | OperationProcedureDecl
  //     | FacilityDecl
  //     | ProcedureDecl
  //     | MathStandardDefnDecl
  static boolean ImplItem(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplItem")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = TypeReprDecl(b, l + 1);
    if (!r) r = OperationProcedureDecl(b, l + 1);
    if (!r) r = FacilityDecl(b, l + 1);
    if (!r) r = ProcedureDecl(b, l + 1);
    if (!r) r = MathStandardDefnDecl(b, l + 1);
    exit_section_(b, l, m, null, r, false, ImplItemRecover_parser_);
    return r;
  }

  /* ********************************************************** */
  // !('Type'|'Operation'|'Facility'|'Recursive'|'Definition'|'Procedure'|'end')
  static boolean ImplItemRecover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplItemRecover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !ImplItemRecover_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // 'Type'|'Operation'|'Facility'|'Recursive'|'Definition'|'Procedure'|'end'
  private static boolean ImplItemRecover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplItemRecover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, FAMILY_TYPE);
    if (!r) r = consumeToken(b, OPERATION);
    if (!r) r = consumeToken(b, FACILITY);
    if (!r) r = consumeToken(b, RECURSIVE);
    if (!r) r = consumeToken(b, DEFINITION);
    if (!r) r = consumeToken(b, PROCEDURE);
    if (!r) r = consumeToken(b, END);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'Implementation' identifier ImplModuleParameters?
  // 'for' ModuleSpec ('of' ModuleSpec)? ';'
  // UsesItemList?
  // RequiresClause?
  // ImplBlock
  // 'end' identifier ';'
  public static boolean ImplModuleDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplModuleDecl")) return false;
    if (!nextTokenIs(b, IMPLEMENTATION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, IMPLEMENTATION);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, IDENTIFIER));
    r = p && report_error_(b, ImplModuleDecl_2(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, FOR)) && r;
    r = p && report_error_(b, ModuleSpec(b, l + 1)) && r;
    r = p && report_error_(b, ImplModuleDecl_5(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, SEMICOLON)) && r;
    r = p && report_error_(b, ImplModuleDecl_7(b, l + 1)) && r;
    r = p && report_error_(b, ImplModuleDecl_8(b, l + 1)) && r;
    r = p && report_error_(b, ImplBlock(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, END)) && r;
    r = p && report_error_(b, consumeToken(b, IDENTIFIER)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, IMPL_MODULE_DECL, r, p, null);
    return r || p;
  }

  // ImplModuleParameters?
  private static boolean ImplModuleDecl_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplModuleDecl_2")) return false;
    ImplModuleParameters(b, l + 1);
    return true;
  }

  // ('of' ModuleSpec)?
  private static boolean ImplModuleDecl_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplModuleDecl_5")) return false;
    ImplModuleDecl_5_0(b, l + 1);
    return true;
  }

  // 'of' ModuleSpec
  private static boolean ImplModuleDecl_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplModuleDecl_5_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, OF);
    p = r; // pin = 1
    r = r && ModuleSpec(b, l + 1);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  // UsesItemList?
  private static boolean ImplModuleDecl_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplModuleDecl_7")) return false;
    UsesItemList(b, l + 1);
    return true;
  }

  // RequiresClause?
  private static boolean ImplModuleDecl_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplModuleDecl_8")) return false;
    RequiresClause(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ImplParamDecl  (';' ImplParamDecl)*
  static boolean ImplModuleParamList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplModuleParamList")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = ImplParamDecl(b, l + 1);
    p = r; // pin = 1
    r = r && ImplModuleParamList_1(b, l + 1);
    exit_section_(b, l, m, null, r, p, ParamListRec_parser_);
    return r || p;
  }

  // (';' ImplParamDecl)*
  private static boolean ImplModuleParamList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplModuleParamList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ImplModuleParamList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ImplModuleParamList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ';' ImplParamDecl
  private static boolean ImplModuleParamList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplModuleParamList_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, SEMICOLON);
    p = r; // pin = 1
    r = r && ImplParamDecl(b, l + 1);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '(' ImplModuleParamList ')'
  public static boolean ImplModuleParameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplModuleParameters")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, ImplModuleParamList(b, l + 1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, IMPL_MODULE_PARAMETERS, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ParamDecl|OperationDecl
  static boolean ImplParamDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ImplParamDecl")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ParamDecl(b, l + 1);
    if (!r) r = OperationDecl(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'initialization' (EnsuresClause)
  public static boolean IntializationClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IntializationClause")) return false;
    if (!nextTokenIs(b, INITIALIZATION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, INITIALIZATION);
    p = r; // pin = 1
    r = r && IntializationClause_1(b, l + 1);
    exit_section_(b, l, m, INTIALIZATION_CLAUSE, r, p, null);
    return r || p;
  }

  // (EnsuresClause)
  private static boolean IntializationClause_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IntializationClause_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = EnsuresClause(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !('end')
  static boolean ItemsRecover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ItemsRecover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !ItemsRecover_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // ('end')
  private static boolean ItemsRecover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ItemsRecover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, END);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'maintaining' MathAssertionExp ';'
  public static boolean MaintainingClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MaintainingClause")) return false;
    if (!nextTokenIs(b, MAINTAINING)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, MAINTAINING);
    p = r; // pin = 1
    r = r && report_error_(b, MathAssertionExp(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, MAINTAINING_CLAUSE, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // (MathReferenceExp '::')? ('+'|'-'|'~')
  static boolean MathAddOp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAddOp")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathAddOp_0(b, l + 1);
    r = r && MathAddOp_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (MathReferenceExp '::')?
  private static boolean MathAddOp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAddOp_0")) return false;
    MathAddOp_0_0(b, l + 1);
    return true;
  }

  // MathReferenceExp '::'
  private static boolean MathAddOp_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAddOp_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathReferenceExp(b, l + 1);
    r = r && consumeToken(b, COLONCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // '+'|'-'|'~'
  private static boolean MathAddOp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAddOp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PLUS);
    if (!r) r = consumeToken(b, MINUS);
    if (!r) r = consumeToken(b, TILDE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathExp ('if' MathExp ';' | 'otherwise' ';')
  public static boolean MathAlternativeItemExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAlternativeItemExp")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _COLLAPSE_, "<math alternative item exp>");
    r = MathExp(b, l + 1, -1);
    p = r; // pin = 1
    r = r && MathAlternativeItemExp_1(b, l + 1);
    exit_section_(b, l, m, MATH_ALTERNATIVE_ITEM_EXP, r, p, null);
    return r || p;
  }

  // 'if' MathExp ';' | 'otherwise' ';'
  private static boolean MathAlternativeItemExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAlternativeItemExp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathAlternativeItemExp_1_0(b, l + 1);
    if (!r) r = MathAlternativeItemExp_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'if' MathExp ';'
  private static boolean MathAlternativeItemExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAlternativeItemExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IF);
    r = r && MathExp(b, l + 1, -1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'otherwise' ';'
  private static boolean MathAlternativeItemExp_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAlternativeItemExp_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OTHERWISE);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathExp (',' MathExp)*
  static boolean MathArgList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathArgList")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = MathExp(b, l + 1, -1);
    p = r; // pin = 1
    r = r && MathArgList_1(b, l + 1);
    exit_section_(b, l, m, null, r, p, ParamListRec_parser_);
    return r || p;
  }

  // (',' MathExp)*
  private static boolean MathArgList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathArgList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!MathArgList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MathArgList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' MathExp
  private static boolean MathArgList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathArgList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && MathExp(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (MathReferenceExp '::')? ('->'|'⟶')
  static boolean MathArrowOp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathArrowOp")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathArrowOp_0(b, l + 1);
    r = r && MathArrowOp_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (MathReferenceExp '::')?
  private static boolean MathArrowOp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathArrowOp_0")) return false;
    MathArrowOp_0_0(b, l + 1);
    return true;
  }

  // MathReferenceExp '::'
  private static boolean MathArrowOp_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathArrowOp_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathReferenceExp(b, l + 1);
    r = r && consumeToken(b, COLONCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // '->'|'⟶'
  private static boolean MathArrowOp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathArrowOp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RARROW);
    if (!r) r = consumeToken(b, RARROW1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathQuantifiedExp|MathExp
  public static boolean MathAssertionExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAssertionExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, "<math assertion exp>");
    r = MathQuantifiedExp(b, l + 1);
    if (!r) r = MathExp(b, l + 1, -1);
    exit_section_(b, l, m, MATH_ASSERTION_EXP, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (MathReferenceExp '::')? ('iff'|'and'|'or')
  static boolean MathBooleanOp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathBooleanOp")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathBooleanOp_0(b, l + 1);
    r = r && MathBooleanOp_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (MathReferenceExp '::')?
  private static boolean MathBooleanOp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathBooleanOp_0")) return false;
    MathBooleanOp_0_0(b, l + 1);
    return true;
  }

  // MathReferenceExp '::'
  private static boolean MathBooleanOp_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathBooleanOp_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathReferenceExp(b, l + 1);
    r = r && consumeToken(b, COLONCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'iff'|'and'|'or'
  private static boolean MathBooleanOp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathBooleanOp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IFF);
    if (!r) r = consumeToken(b, AND);
    if (!r) r = consumeToken(b, OR);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'Categorical' 'Definition' 'for'
  // MathPrefixDefnSigs 'is' MathExp ';'
  public static boolean MathCategoricalDefnDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathCategoricalDefnDecl")) return false;
    if (!nextTokenIs(b, CATEGORICAL)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, CATEGORICAL);
    r = r && consumeToken(b, DEFINITION);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, FOR));
    r = p && report_error_(b, MathPrefixDefnSigs(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, IS)) && r;
    r = p && report_error_(b, MathExp(b, l + 1, -1)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, MATH_CATEGORICAL_DEFN_DECL, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '(' MathDefnParamList ')'
  static boolean MathDefinitionParams(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathDefinitionParams")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, MathDefnParamList(b, l + 1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // MathVarDeclGroup (',' MathVarDeclGroup)*
  static boolean MathDefnParamList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathDefnParamList")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = MathVarDeclGroup(b, l + 1);
    p = r; // pin = 1
    r = r && MathDefnParamList_1(b, l + 1);
    exit_section_(b, l, m, null, r, p, ParamListRec_parser_);
    return r || p;
  }

  // (',' MathVarDeclGroup)*
  private static boolean MathDefnParamList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathDefnParamList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!MathDefnParamList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MathDefnParamList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' MathVarDeclGroup
  private static boolean MathDefnParamList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathDefnParamList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && MathVarDeclGroup(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathPrefixDefnSig
  //         |   MathOutfixDefnSig
  //         |   MathInfixDefnSig
  //         |   MathPostfixDefnSig
  static boolean MathDefnSig(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathDefnSig")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathPrefixDefnSig(b, l + 1);
    if (!r) r = MathOutfixDefnSig(b, l + 1);
    if (!r) r = MathInfixDefnSig(b, l + 1);
    if (!r) r = MathPostfixDefnSig(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (MathReferenceExp '::')? ('='|'/='|'≠')
  static boolean MathEqualityOp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathEqualityOp")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathEqualityOp_0(b, l + 1);
    r = r && MathEqualityOp_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (MathReferenceExp '::')?
  private static boolean MathEqualityOp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathEqualityOp_0")) return false;
    MathEqualityOp_0_0(b, l + 1);
    return true;
  }

  // MathReferenceExp '::'
  private static boolean MathEqualityOp_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathEqualityOp_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathReferenceExp(b, l + 1);
    r = r && consumeToken(b, COLONCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // '='|'/='|'≠'
  private static boolean MathEqualityOp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathEqualityOp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EQUALS);
    if (!r) r = consumeToken(b, NEQUALS);
    if (!r) r = consumeToken(b, NEQUALS1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (MathReferenceExp '::')? ('implies')
  static boolean MathImpliesOp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathImpliesOp")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathImpliesOp_0(b, l + 1);
    r = r && MathImpliesOp_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (MathReferenceExp '::')?
  private static boolean MathImpliesOp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathImpliesOp_0")) return false;
    MathImpliesOp_0_0(b, l + 1);
    return true;
  }

  // MathReferenceExp '::'
  private static boolean MathImpliesOp_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathImpliesOp_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathReferenceExp(b, l + 1);
    r = r && consumeToken(b, COLONCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('implies')
  private static boolean MathImpliesOp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathImpliesOp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IMPLIES);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'Inductive' 'Definition'
  // MathDefnSig 'is'
  // '(i.)' MathAssertionExp ';'
  // '(ii.)' MathAssertionExp ';'
  public static boolean MathInductiveDefnDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathInductiveDefnDecl")) return false;
    if (!nextTokenIs(b, INDUCTIVE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, INDUCTIVE);
    r = r && consumeToken(b, DEFINITION);
    p = r; // pin = 2
    r = r && report_error_(b, MathDefnSig(b, l + 1));
    r = p && report_error_(b, consumeToken(b, IS)) && r;
    r = p && report_error_(b, consumeToken(b, IND_BASE)) && r;
    r = p && report_error_(b, MathAssertionExp(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, SEMICOLON)) && r;
    r = p && report_error_(b, consumeToken(b, IND_HYPO)) && r;
    r = p && report_error_(b, MathAssertionExp(b, l + 1)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, MATH_INDUCTIVE_DEFN_DECL, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '(' MathVarDecl ')' MathSymbolName
  //         '(' MathVarDecl ')' ':' MathExp
  public static boolean MathInfixDefnSig(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathInfixDefnSig")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, LPAREN);
    r = r && MathVarDecl(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && MathSymbolName(b, l + 1);
    p = r; // pin = 4
    r = r && report_error_(b, consumeToken(b, LPAREN));
    r = p && report_error_(b, MathVarDecl(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, RPAREN)) && r;
    r = p && report_error_(b, consumeToken(b, COLON)) && r;
    r = p && MathExp(b, l + 1, -1) && r;
    exit_section_(b, l, m, MATH_INFIX_DEFN_SIG, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // (MathReferenceExp '::')? ('o'|'union'|'∪'|'∪₊'|'intersect'|'∩'|'∩₊')
  static boolean MathJoiningOp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathJoiningOp")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathJoiningOp_0(b, l + 1);
    r = r && MathJoiningOp_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (MathReferenceExp '::')?
  private static boolean MathJoiningOp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathJoiningOp_0")) return false;
    MathJoiningOp_0_0(b, l + 1);
    return true;
  }

  // MathReferenceExp '::'
  private static boolean MathJoiningOp_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathJoiningOp_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathReferenceExp(b, l + 1);
    r = r && consumeToken(b, COLONCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'o'|'union'|'∪'|'∪₊'|'intersect'|'∩'|'∩₊'
  private static boolean MathJoiningOp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathJoiningOp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CAT);
    if (!r) r = consumeToken(b, UNION);
    if (!r) r = consumeToken(b, UNION1);
    if (!r) r = consumeToken(b, UNION_PLUS);
    if (!r) r = consumeToken(b, INTERSECT);
    if (!r) r = consumeToken(b, INTERSECT1);
    if (!r) r = consumeToken(b, INTERSECT_PLUS);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (MathReferenceExp '::')? ('*'|'/'|'%'|'⨯'|'ᴴ⨯')
  static boolean MathMultOp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathMultOp")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathMultOp_0(b, l + 1);
    r = r && MathMultOp_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (MathReferenceExp '::')?
  private static boolean MathMultOp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathMultOp_0")) return false;
    MathMultOp_0_0(b, l + 1);
    return true;
  }

  // MathReferenceExp '::'
  private static boolean MathMultOp_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathMultOp_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathReferenceExp(b, l + 1);
    r = r && consumeToken(b, COLONCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // '*'|'/'|'%'|'⨯'|'ᴴ⨯'
  private static boolean MathMultOp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathMultOp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MUL);
    if (!r) r = consumeToken(b, QUOTIENT);
    if (!r) r = consumeToken(b, MOD);
    if (!r) r = consumeToken(b, TIMES);
    if (!r) r = consumeToken(b, HTIMES);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ('|'|'||'|'<'|'⎝'|'⟨') MathVarDecl ('⟩'|'⎠'|'|'|'||'|'>') ':' MathExp
  public static boolean MathOutfixDefnSig(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathOutfixDefnSig")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<math outfix defn sig>");
    r = MathOutfixDefnSig_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, MathVarDecl(b, l + 1));
    r = p && report_error_(b, MathOutfixDefnSig_2(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COLON)) && r;
    r = p && MathExp(b, l + 1, -1) && r;
    exit_section_(b, l, m, MATH_OUTFIX_DEFN_SIG, r, p, null);
    return r || p;
  }

  // '|'|'||'|'<'|'⎝'|'⟨'
  private static boolean MathOutfixDefnSig_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathOutfixDefnSig_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BAR);
    if (!r) r = consumeToken(b, DBL_BAR);
    if (!r) r = consumeToken(b, LESS);
    if (!r) r = consumeToken(b, LCURVE);
    if (!r) r = consumeToken(b, LANGLE);
    exit_section_(b, m, null, r);
    return r;
  }

  // '⟩'|'⎠'|'|'|'||'|'>'
  private static boolean MathOutfixDefnSig_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathOutfixDefnSig_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RANGLE);
    if (!r) r = consumeToken(b, RCURVE);
    if (!r) r = consumeToken(b, BAR);
    if (!r) r = consumeToken(b, DBL_BAR);
    if (!r) r = consumeToken(b, GREATER);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathVarDecl '[' MathVarDecl ']' ':' MathExp
  public static boolean MathPostfixDefnSig(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPostfixDefnSig")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = MathVarDecl(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, LBRACK));
    r = p && report_error_(b, MathVarDecl(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, RBRACK)) && r;
    r = p && report_error_(b, consumeToken(b, COLON)) && r;
    r = p && MathExp(b, l + 1, -1) && r;
    exit_section_(b, l, m, MATH_POSTFIX_DEFN_SIG, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // MathPrefixNameList MathDefinitionParams? ':' MathExp
  public static boolean MathPrefixDefnSig(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixDefnSig")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<math prefix defn sig>");
    r = MathPrefixNameList(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, MathPrefixDefnSig_1(b, l + 1));
    r = p && report_error_(b, consumeToken(b, COLON)) && r;
    r = p && MathExp(b, l + 1, -1) && r;
    exit_section_(b, l, m, MATH_PREFIX_DEFN_SIG, r, p, null);
    return r || p;
  }

  // MathDefinitionParams?
  private static boolean MathPrefixDefnSig_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixDefnSig_1")) return false;
    MathDefinitionParams(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // MathPrefixDefnSig
  // (',' MathPrefixDefnSig)*
  static boolean MathPrefixDefnSigs(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixDefnSigs")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = MathPrefixDefnSig(b, l + 1);
    r = r && MathPrefixDefnSigs_1(b, l + 1);
    exit_section_(b, l, m, null, r, false, CategoricalSigListRec_parser_);
    return r;
  }

  // (',' MathPrefixDefnSig)*
  private static boolean MathPrefixDefnSigs_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixDefnSigs_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!MathPrefixDefnSigs_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MathPrefixDefnSigs_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' MathPrefixDefnSig
  private static boolean MathPrefixDefnSigs_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixDefnSigs_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && MathPrefixDefnSig(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(':'|';'|'(')
  static boolean MathPrefixListRec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixListRec")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !MathPrefixListRec_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // ':'|';'|'('
  private static boolean MathPrefixListRec_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixListRec_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    if (!r) r = consumeToken(b, SEMICOLON);
    if (!r) r = consumeToken(b, LPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathSymbolName (',' MathSymbolName)*
  static boolean MathPrefixNameList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixNameList")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = MathSymbolName(b, l + 1);
    p = r; // pin = 1
    r = r && MathPrefixNameList_1(b, l + 1);
    exit_section_(b, l, m, null, r, p, MathPrefixListRec_parser_);
    return r || p;
  }

  // (',' MathSymbolName)*
  private static boolean MathPrefixNameList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixNameList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!MathPrefixNameList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MathPrefixNameList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' MathSymbolName
  private static boolean MathPrefixNameList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixNameList_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, COMMA);
    p = r; // pin = 1
    r = r && MathSymbolName(b, l + 1);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '::' MathSymbolName
  public static boolean MathQualifiedReferenceExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathQualifiedReferenceExp")) return false;
    if (!nextTokenIs(b, COLONCOLON)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _LEFT_, null);
    r = consumeToken(b, COLONCOLON);
    r = r && MathSymbolName(b, l + 1);
    exit_section_(b, l, m, MATH_REFERENCE_EXP, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ('∃'|'∀'|'Exists'|'Forall') MathVarDeclGroup ',' MathAssertionExp
  public static boolean MathQuantifiedExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathQuantifiedExp")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _COLLAPSE_, "<math quantified exp>");
    r = MathQuantifiedExp_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, MathVarDeclGroup(b, l + 1));
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && MathAssertionExp(b, l + 1) && r;
    exit_section_(b, l, m, MATH_QUANTIFIED_EXP, r, p, null);
    return r || p;
  }

  // '∃'|'∀'|'Exists'|'Forall'
  private static boolean MathQuantifiedExp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathQuantifiedExp_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXISTS1);
    if (!r) r = consumeToken(b, FORALL1);
    if (!r) r = consumeToken(b, EXISTS);
    if (!r) r = consumeToken(b, FORALL);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathSymbolName
  public static boolean MathReferenceExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathReferenceExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<math reference exp>");
    r = MathSymbolName(b, l + 1);
    exit_section_(b, l, m, MATH_REFERENCE_EXP, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (MathReferenceExp '::')? ('<'|'>'|'<='|'≤'|'≤ᵤ'|'>='|'≥')
  static boolean MathRelationalOp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathRelationalOp")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathRelationalOp_0(b, l + 1);
    r = r && MathRelationalOp_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (MathReferenceExp '::')?
  private static boolean MathRelationalOp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathRelationalOp_0")) return false;
    MathRelationalOp_0_0(b, l + 1);
    return true;
  }

  // MathReferenceExp '::'
  private static boolean MathRelationalOp_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathRelationalOp_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathReferenceExp(b, l + 1);
    r = r && consumeToken(b, COLONCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // '<'|'>'|'<='|'≤'|'≤ᵤ'|'>='|'≥'
  private static boolean MathRelationalOp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathRelationalOp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LESS);
    if (!r) r = consumeToken(b, GREATER);
    if (!r) r = consumeToken(b, LESS_OR_EQUAL);
    if (!r) r = consumeToken(b, LESS_OR_EQUAL1);
    if (!r) r = consumeToken(b, LESS_OR_EQUAL_U);
    if (!r) r = consumeToken(b, GREATER_OR_EQUAL);
    if (!r) r = consumeToken(b, GREATER_OR_EQUAL1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (MathReferenceExp '::')? ('is_in'|'is_not_in'|'∈'|'∉')
  static boolean MathSetContainmentOp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSetContainmentOp")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathSetContainmentOp_0(b, l + 1);
    r = r && MathSetContainmentOp_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (MathReferenceExp '::')?
  private static boolean MathSetContainmentOp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSetContainmentOp_0")) return false;
    MathSetContainmentOp_0_0(b, l + 1);
    return true;
  }

  // MathReferenceExp '::'
  private static boolean MathSetContainmentOp_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSetContainmentOp_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathReferenceExp(b, l + 1);
    r = r && consumeToken(b, COLONCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'is_in'|'is_not_in'|'∈'|'∉'
  private static boolean MathSetContainmentOp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSetContainmentOp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IS_IN);
    if (!r) r = consumeToken(b, IS_NOT_IN);
    if (!r) r = consumeToken(b, IS_IN1);
    if (!r) r = consumeToken(b, IS_NOT_IN1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !('}')
  static boolean MathSetEleRecover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSetEleRecover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !MathSetEleRecover_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // ('}')
  private static boolean MathSetEleRecover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSetEleRecover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathExp (',' MathExp)*
  public static boolean MathSetElementsList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSetElementsList")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<math set elements list>");
    r = MathExp(b, l + 1, -1);
    p = r; // pin = 1
    r = r && MathSetElementsList_1(b, l + 1);
    exit_section_(b, l, m, MATH_SET_ELEMENTS_LIST, r, p, MathSetEleRecover_parser_);
    return r || p;
  }

  // (',' MathExp)*
  private static boolean MathSetElementsList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSetElementsList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!MathSetElementsList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MathSetElementsList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' MathExp
  private static boolean MathSetElementsList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSetElementsList_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, COMMA);
    p = r; // pin = 1
    r = r && MathExp(b, l + 1, -1);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ('Implicit')? 'Definition'
  //     MathDefnSig ('is' MathAssertionExp)? ';'
  public static boolean MathStandardDefnDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathStandardDefnDecl")) return false;
    if (!nextTokenIs(b, "<math standard defn decl>", DEFINITION, IMPLICIT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<math standard defn decl>");
    r = MathStandardDefnDecl_0(b, l + 1);
    r = r && consumeToken(b, DEFINITION);
    p = r; // pin = 2
    r = r && report_error_(b, MathDefnSig(b, l + 1));
    r = p && report_error_(b, MathStandardDefnDecl_3(b, l + 1)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, MATH_STANDARD_DEFN_DECL, r, p, null);
    return r || p;
  }

  // ('Implicit')?
  private static boolean MathStandardDefnDecl_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathStandardDefnDecl_0")) return false;
    MathStandardDefnDecl_0_0(b, l + 1);
    return true;
  }

  // ('Implicit')
  private static boolean MathStandardDefnDecl_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathStandardDefnDecl_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IMPLICIT);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('is' MathAssertionExp)?
  private static boolean MathStandardDefnDecl_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathStandardDefnDecl_3")) return false;
    MathStandardDefnDecl_3_0(b, l + 1);
    return true;
  }

  // 'is' MathAssertionExp
  private static boolean MathStandardDefnDecl_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathStandardDefnDecl_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IS);
    r = r && MathAssertionExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifier|
  // ('o'|'true'|'false'|int|'+'|'-'|'*'|'/'|'>'|'≤'|
  //  '<'|'<='|'>='|'≥'|'not'|'⌐'|'≼'|'ϒ'|'∪₊'|'≤ᵤ')
  public static boolean MathSymbolName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSymbolName")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<math symbol name>");
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = MathSymbolName_1(b, l + 1);
    exit_section_(b, l, m, MATH_SYMBOL_NAME, r, false, null);
    return r;
  }

  // 'o'|'true'|'false'|int|'+'|'-'|'*'|'/'|'>'|'≤'|
  //  '<'|'<='|'>='|'≥'|'not'|'⌐'|'≼'|'ϒ'|'∪₊'|'≤ᵤ'
  private static boolean MathSymbolName_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSymbolName_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CAT);
    if (!r) r = consumeToken(b, TRUE);
    if (!r) r = consumeToken(b, FALSE);
    if (!r) r = consumeToken(b, INT);
    if (!r) r = consumeToken(b, PLUS);
    if (!r) r = consumeToken(b, MINUS);
    if (!r) r = consumeToken(b, MUL);
    if (!r) r = consumeToken(b, QUOTIENT);
    if (!r) r = consumeToken(b, GREATER);
    if (!r) r = consumeToken(b, LESS_OR_EQUAL1);
    if (!r) r = consumeToken(b, LESS);
    if (!r) r = consumeToken(b, LESS_OR_EQUAL);
    if (!r) r = consumeToken(b, GREATER_OR_EQUAL);
    if (!r) r = consumeToken(b, GREATER_OR_EQUAL1);
    if (!r) r = consumeToken(b, NOT);
    if (!r) r = consumeToken(b, NEG);
    if (!r) r = consumeToken(b, PRECCURLYEQ);
    if (!r) r = consumeToken(b, VROD);
    if (!r) r = consumeToken(b, UNION_PLUS);
    if (!r) r = consumeToken(b, LESS_OR_EQUAL_U);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ('Corollary'|'Theorem') identifier ':'
  // MathAssertionExp ';'
  public static boolean MathTheoremDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathTheoremDecl")) return false;
    if (!nextTokenIs(b, "<math theorem decl>", COROLLARY, THEOREM)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<math theorem decl>");
    r = MathTheoremDecl_0(b, l + 1);
    r = r && consumeToken(b, IDENTIFIER);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, COLON));
    r = p && report_error_(b, MathAssertionExp(b, l + 1)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, MATH_THEOREM_DECL, r, p, null);
    return r || p;
  }

  // 'Corollary'|'Theorem'
  private static boolean MathTheoremDecl_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathTheoremDecl_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COROLLARY);
    if (!r) r = consumeToken(b, THEOREM);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathVarDef ':' MathExp
  public static boolean MathVarDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDecl")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = MathVarDef(b, l + 1);
    r = r && consumeToken(b, COLON);
    p = r; // pin = 2
    r = r && MathExp(b, l + 1, -1);
    exit_section_(b, l, m, MATH_VAR_DECL, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // MathVarDeclList ':' MathExp
  public static boolean MathVarDeclGroup(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDeclGroup")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = MathVarDeclList(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, COLON));
    r = p && MathExp(b, l + 1, -1) && r;
    exit_section_(b, l, m, MATH_VAR_DECL_GROUP, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // MathVarDef &(!(')')) (',' MathVarDef)*
  static boolean MathVarDeclList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDeclList")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = MathVarDef(b, l + 1);
    p = r; // pin = 1
    r = r && MathVarDeclList_1(b, l + 1);
    r = r && MathVarDeclList_2(b, l + 1);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  // &(!(')'))
  private static boolean MathVarDeclList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDeclList_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_, null);
    r = MathVarDeclList_1_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // !(')')
  private static boolean MathVarDeclList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDeclList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !MathVarDeclList_1_0_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // (')')
  private static boolean MathVarDeclList_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDeclList_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' MathVarDef)*
  private static boolean MathVarDeclList_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDeclList_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!MathVarDeclList_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MathVarDeclList_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' MathVarDef
  private static boolean MathVarDeclList_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDeclList_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && MathVarDef(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifier
  public static boolean MathVarDef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathVarDef")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, MATH_VAR_DEF, r);
    return r;
  }

  /* ********************************************************** */
  // '(' ExpArgumentList ')'
  public static boolean ModuleArgList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModuleArgList")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, ExpArgumentList(b, l + 1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, MODULE_ARG_LIST, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // PrecisModuleDecl
  //     | PrecisExtensionModuleDecl
  //     | ConceptExtensionModuleDecl
  //     | ConceptModuleDecl
  //     | ImplModuleDecl
  //     | FacilityModuleDecl
  static boolean ModuleDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModuleDecl")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = PrecisModuleDecl(b, l + 1);
    if (!r) r = PrecisExtensionModuleDecl(b, l + 1);
    if (!r) r = ConceptExtensionModuleDecl(b, l + 1);
    if (!r) r = ConceptModuleDecl(b, l + 1);
    if (!r) r = ImplModuleDecl(b, l + 1);
    if (!r) r = FacilityModuleDecl(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifier
  public static boolean ModuleSpec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModuleSpec")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, MODULE_SPEC, r);
    return r;
  }

  /* ********************************************************** */
  // TypeReferenceExp QualifiedTypeReferenceExp?
  static boolean NamedType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NamedType")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = TypeReferenceExp(b, l + 1);
    r = r && NamedType_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // QualifiedTypeReferenceExp?
  private static boolean NamedType_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NamedType_1")) return false;
    QualifiedTypeReferenceExp(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // VarDeclGroup*
  // Statements?
  public static boolean OpBlock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OpBlock")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<op block>");
    r = OpBlock_0(b, l + 1);
    r = r && OpBlock_1(b, l + 1);
    exit_section_(b, l, m, OP_BLOCK, r, false, null);
    return r;
  }

  // VarDeclGroup*
  private static boolean OpBlock_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OpBlock_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!VarDeclGroup(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "OpBlock_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // Statements?
  private static boolean OpBlock_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OpBlock_1")) return false;
    Statements(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ParamDecl (';' ParamDecl)*
  static boolean OpParamList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OpParamList")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = ParamDecl(b, l + 1);
    p = r; // pin = 1
    r = r && OpParamList_1(b, l + 1);
    exit_section_(b, l, m, null, r, p, ParamListRec_parser_);
    return r || p;
  }

  // (';' ParamDecl)*
  private static boolean OpParamList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OpParamList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!OpParamList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "OpParamList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ';' ParamDecl
  private static boolean OpParamList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OpParamList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SEMICOLON);
    r = r && ParamDecl(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'Operation' identifier OperationLikeParameters (':' Type)? ';'
  // RequiresClause? EnsuresClause?
  public static boolean OperationDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OperationDecl")) return false;
    if (!nextTokenIs(b, OPERATION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, OPERATION);
    r = r && consumeToken(b, IDENTIFIER);
    p = r; // pin = 2
    r = r && report_error_(b, OperationLikeParameters(b, l + 1));
    r = p && report_error_(b, OperationDecl_3(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, SEMICOLON)) && r;
    r = p && report_error_(b, OperationDecl_5(b, l + 1)) && r;
    r = p && OperationDecl_6(b, l + 1) && r;
    exit_section_(b, l, m, OPERATION_DECL, r, p, null);
    return r || p;
  }

  // (':' Type)?
  private static boolean OperationDecl_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OperationDecl_3")) return false;
    OperationDecl_3_0(b, l + 1);
    return true;
  }

  // ':' Type
  private static boolean OperationDecl_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OperationDecl_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    r = r && Type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // RequiresClause?
  private static boolean OperationDecl_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OperationDecl_5")) return false;
    RequiresClause(b, l + 1);
    return true;
  }

  // EnsuresClause?
  private static boolean OperationDecl_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OperationDecl_6")) return false;
    EnsuresClause(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '(' OpParamList? ')'
  static boolean OperationLikeParameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OperationLikeParameters")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, OperationLikeParameters_1(b, l + 1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  // OpParamList?
  private static boolean OperationLikeParameters_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OperationLikeParameters_1")) return false;
    OpParamList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'Operation' identifier OperationLikeParameters (':' Type)? ';'
  // RequiresClause? EnsuresClause?
  // ('Recursive')? 'Procedure'
  // OpBlock
  // 'end' CloseIdentifier ';'
  public static boolean OperationProcedureDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OperationProcedureDecl")) return false;
    if (!nextTokenIs(b, OPERATION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, OPERATION);
    r = r && consumeToken(b, IDENTIFIER);
    p = r; // pin = 2
    r = r && report_error_(b, OperationLikeParameters(b, l + 1));
    r = p && report_error_(b, OperationProcedureDecl_3(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, SEMICOLON)) && r;
    r = p && report_error_(b, OperationProcedureDecl_5(b, l + 1)) && r;
    r = p && report_error_(b, OperationProcedureDecl_6(b, l + 1)) && r;
    r = p && report_error_(b, OperationProcedureDecl_7(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, PROCEDURE)) && r;
    r = p && report_error_(b, OpBlock(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, END)) && r;
    r = p && report_error_(b, CloseIdentifier(b, l + 1)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, OPERATION_PROCEDURE_DECL, r, p, null);
    return r || p;
  }

  // (':' Type)?
  private static boolean OperationProcedureDecl_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OperationProcedureDecl_3")) return false;
    OperationProcedureDecl_3_0(b, l + 1);
    return true;
  }

  // ':' Type
  private static boolean OperationProcedureDecl_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OperationProcedureDecl_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    r = r && Type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // RequiresClause?
  private static boolean OperationProcedureDecl_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OperationProcedureDecl_5")) return false;
    RequiresClause(b, l + 1);
    return true;
  }

  // EnsuresClause?
  private static boolean OperationProcedureDecl_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OperationProcedureDecl_6")) return false;
    EnsuresClause(b, l + 1);
    return true;
  }

  // ('Recursive')?
  private static boolean OperationProcedureDecl_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OperationProcedureDecl_7")) return false;
    OperationProcedureDecl_7_0(b, l + 1);
    return true;
  }

  // ('Recursive')
  private static boolean OperationProcedureDecl_7_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OperationProcedureDecl_7_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RECURSIVE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ParameterMode ParamDefinitionListNoPin ':' Type
  public static boolean ParamDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamDecl")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<param decl>");
    r = ParameterMode(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, ParamDefinitionListNoPin(b, l + 1));
    r = p && report_error_(b, consumeToken(b, COLON)) && r;
    r = p && Type(b, l + 1) && r;
    exit_section_(b, l, m, PARAM_DECL, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // identifier
  public static boolean ParamDef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamDef")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, PARAM_DEF, r);
    return r;
  }

  /* ********************************************************** */
  // ParamDef &(!(';'|')')) (',' ParamDef)*
  static boolean ParamDefinitionListNoPin(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamDefinitionListNoPin")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = ParamDef(b, l + 1);
    p = r; // pin = 1
    r = r && ParamDefinitionListNoPin_1(b, l + 1);
    r = r && ParamDefinitionListNoPin_2(b, l + 1);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  // &(!(';'|')'))
  private static boolean ParamDefinitionListNoPin_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamDefinitionListNoPin_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_, null);
    r = ParamDefinitionListNoPin_1_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // !(';'|')')
  private static boolean ParamDefinitionListNoPin_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamDefinitionListNoPin_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !ParamDefinitionListNoPin_1_0_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // ';'|')'
  private static boolean ParamDefinitionListNoPin_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamDefinitionListNoPin_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SEMICOLON);
    if (!r) r = consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' ParamDef)*
  private static boolean ParamDefinitionListNoPin_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamDefinitionListNoPin_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ParamDefinitionListNoPin_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ParamDefinitionListNoPin_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' ParamDef
  private static boolean ParamDefinitionListNoPin_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamDefinitionListNoPin_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && ParamDef(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(')')
  static boolean ParamListRec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamListRec")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !ParamListRec_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // (')')
  private static boolean ParamListRec_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParamListRec_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'alters'
  //     | 'updates'
  //     | 'clears'
  //     | 'restores'
  //     | 'preserves'
  //     | 'replaces'
  //     | 'evaluates'
  //     | identifier
  public static boolean ParameterMode(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParameterMode")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<parameter mode>");
    r = consumeToken(b, ALTERS);
    if (!r) r = consumeToken(b, UPDATES);
    if (!r) r = consumeToken(b, CLEARS);
    if (!r) r = consumeToken(b, RESTORES);
    if (!r) r = consumeToken(b, PRESERVES);
    if (!r) r = consumeToken(b, REPLACES);
    if (!r) r = consumeToken(b, EVALUATES);
    if (!r) r = consumeToken(b, IDENTIFIER);
    exit_section_(b, l, m, PARAMETER_MODE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // PrecisItem*
  public static boolean PrecisBlock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisBlock")) return false;
    Marker m = enter_section_(b, l, _NONE_, "<precis block>");
    int c = current_position_(b);
    while (true) {
      if (!PrecisItem(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "PrecisBlock", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, PRECIS_BLOCK, true, false, ItemsRecover_parser_);
    return true;
  }

  /* ********************************************************** */
  // 'Precis' 'Extension' identifier 'for'
  // ModuleSpec ('with' ModuleSpec)? ';'
  // PrecisBlock
  // 'end' identifier ';'
  public static boolean PrecisExtensionModuleDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisExtensionModuleDecl")) return false;
    if (!nextTokenIs(b, PRECIS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, PRECIS);
    r = r && consumeToken(b, EXTENSION);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, IDENTIFIER));
    r = p && report_error_(b, consumeToken(b, FOR)) && r;
    r = p && report_error_(b, ModuleSpec(b, l + 1)) && r;
    r = p && report_error_(b, PrecisExtensionModuleDecl_5(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, SEMICOLON)) && r;
    r = p && report_error_(b, PrecisBlock(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, END)) && r;
    r = p && report_error_(b, consumeToken(b, IDENTIFIER)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, PRECIS_EXTENSION_MODULE_DECL, r, p, null);
    return r || p;
  }

  // ('with' ModuleSpec)?
  private static boolean PrecisExtensionModuleDecl_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisExtensionModuleDecl_5")) return false;
    PrecisExtensionModuleDecl_5_0(b, l + 1);
    return true;
  }

  // 'with' ModuleSpec
  private static boolean PrecisExtensionModuleDecl_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisExtensionModuleDecl_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, WITH);
    r = r && ModuleSpec(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathTheoremDecl
  //         | MathStandardDefnDecl
  //         | MathCategoricalDefnDecl
  //         | MathInductiveDefnDecl
  static boolean PrecisItem(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisItem")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = MathTheoremDecl(b, l + 1);
    if (!r) r = MathStandardDefnDecl(b, l + 1);
    if (!r) r = MathCategoricalDefnDecl(b, l + 1);
    if (!r) r = MathInductiveDefnDecl(b, l + 1);
    exit_section_(b, l, m, null, r, false, PrecisItemRecover_parser_);
    return r;
  }

  /* ********************************************************** */
  // !('Definition'|'Implicit'|'Theorem'|'Corollary'|'Categorical'|'Inductive'|'end')
  static boolean PrecisItemRecover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisItemRecover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !PrecisItemRecover_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // 'Definition'|'Implicit'|'Theorem'|'Corollary'|'Categorical'|'Inductive'|'end'
  private static boolean PrecisItemRecover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisItemRecover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DEFINITION);
    if (!r) r = consumeToken(b, IMPLICIT);
    if (!r) r = consumeToken(b, THEOREM);
    if (!r) r = consumeToken(b, COROLLARY);
    if (!r) r = consumeToken(b, CATEGORICAL);
    if (!r) r = consumeToken(b, INDUCTIVE);
    if (!r) r = consumeToken(b, END);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'Precis' identifier ';'
  // UsesItemList?
  // PrecisBlock
  // 'end' identifier ';'
  public static boolean PrecisModuleDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisModuleDecl")) return false;
    if (!nextTokenIs(b, PRECIS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, PRECIS);
    r = r && consumeToken(b, IDENTIFIER);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, SEMICOLON));
    r = p && report_error_(b, PrecisModuleDecl_3(b, l + 1)) && r;
    r = p && report_error_(b, PrecisBlock(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, END)) && r;
    r = p && report_error_(b, consumeToken(b, IDENTIFIER)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, PRECIS_MODULE_DECL, r, p, null);
    return r || p;
  }

  // UsesItemList?
  private static boolean PrecisModuleDecl_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrecisModuleDecl_3")) return false;
    UsesItemList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ('Recursive')? 'Procedure' identifier OperationLikeParameters (':' Type)? ';'
  // OpBlock
  // 'end' CloseIdentifier ';'
  public static boolean ProcedureDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ProcedureDecl")) return false;
    if (!nextTokenIs(b, "<procedure decl>", PROCEDURE, RECURSIVE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<procedure decl>");
    r = ProcedureDecl_0(b, l + 1);
    r = r && consumeToken(b, PROCEDURE);
    r = r && consumeToken(b, IDENTIFIER);
    p = r; // pin = 3
    r = r && report_error_(b, OperationLikeParameters(b, l + 1));
    r = p && report_error_(b, ProcedureDecl_4(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, SEMICOLON)) && r;
    r = p && report_error_(b, OpBlock(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, END)) && r;
    r = p && report_error_(b, CloseIdentifier(b, l + 1)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, PROCEDURE_DECL, r, p, null);
    return r || p;
  }

  // ('Recursive')?
  private static boolean ProcedureDecl_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ProcedureDecl_0")) return false;
    ProcedureDecl_0_0(b, l + 1);
    return true;
  }

  // ('Recursive')
  private static boolean ProcedureDecl_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ProcedureDecl_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RECURSIVE);
    exit_section_(b, m, null, r);
    return r;
  }

  // (':' Type)?
  private static boolean ProcedureDecl_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ProcedureDecl_4")) return false;
    ProcedureDecl_4_0(b, l + 1);
    return true;
  }

  // ':' Type
  private static boolean ProcedureDecl_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ProcedureDecl_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    r = r && Type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '::' identifier
  public static boolean QualifiedReferenceExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "QualifiedReferenceExp")) return false;
    if (!nextTokenIs(b, COLONCOLON)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _LEFT_, null);
    r = consumeToken(b, COLONCOLON);
    r = r && consumeToken(b, IDENTIFIER);
    exit_section_(b, l, m, REFERENCE_EXP, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '::' identifier
  public static boolean QualifiedTypeReferenceExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "QualifiedTypeReferenceExp")) return false;
    if (!nextTokenIs(b, COLONCOLON)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _LEFT_, null);
    r = consumeToken(b, COLONCOLON);
    r = r && consumeToken(b, IDENTIFIER);
    exit_section_(b, l, m, TYPE_REFERENCE_EXP, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'Record' RecordVarDeclGroup* 'end'
  public static boolean RecordType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RecordType")) return false;
    if (!nextTokenIs(b, RECORD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, RECORD);
    p = r; // pin = 1
    r = r && report_error_(b, RecordType_1(b, l + 1));
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, RECORD_TYPE, r, p, null);
    return r || p;
  }

  // RecordVarDeclGroup*
  private static boolean RecordType_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RecordType_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!RecordVarDeclGroup(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "RecordType_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // FieldVarDeclGroup ';'
  public static boolean RecordVarDeclGroup(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RecordVarDeclGroup")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = FieldVarDeclGroup(b, l + 1);
    p = r; // pin = 1
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, l, m, RECORD_VAR_DECL_GROUP, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // identifier
  public static boolean ReferenceExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ReferenceExp")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, REFERENCE_EXP, r);
    return r;
  }

  /* ********************************************************** */
  // 'requires' MathAssertionExp (EntailsClause)? ';'
  public static boolean RequiresClause(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RequiresClause")) return false;
    if (!nextTokenIs(b, REQUIRES)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, REQUIRES);
    p = r; // pin = 1
    r = r && report_error_(b, MathAssertionExp(b, l + 1));
    r = p && report_error_(b, RequiresClause_2(b, l + 1)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, REQUIRES_CLAUSE, r, p, null);
    return r || p;
  }

  // (EntailsClause)?
  private static boolean RequiresClause_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RequiresClause_2")) return false;
    RequiresClause_2_0(b, l + 1);
    return true;
  }

  // (EntailsClause)
  private static boolean RequiresClause_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RequiresClause_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = EntailsClause(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MathVarDeclGroup ';'
  static boolean ResMathCartVarGroup(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ResMathCartVarGroup")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = MathVarDeclGroup(b, l + 1);
    p = r; // pin = 1
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // Exp (AssignStatement|SwapStatement)? ';'
  public static boolean SimpleStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SimpleStatement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _COLLAPSE_, "<simple statement>");
    r = Exp(b, l + 1, -1);
    p = r; // pin = 1
    r = r && report_error_(b, SimpleStatement_1(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, SIMPLE_STATEMENT, r, p, null);
    return r || p;
  }

  // (AssignStatement|SwapStatement)?
  private static boolean SimpleStatement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SimpleStatement_1")) return false;
    SimpleStatement_1_0(b, l + 1);
    return true;
  }

  // AssignStatement|SwapStatement
  private static boolean SimpleStatement_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SimpleStatement_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = AssignStatement(b, l + 1);
    if (!r) r = SwapStatement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // SpecParamDecl  (';' SpecParamDecl)*
  static boolean SpecModuleParamList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SpecModuleParamList")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = SpecParamDecl(b, l + 1);
    p = r; // pin = 1
    r = r && SpecModuleParamList_1(b, l + 1);
    exit_section_(b, l, m, null, r, p, ParamListRec_parser_);
    return r || p;
  }

  // (';' SpecParamDecl)*
  private static boolean SpecModuleParamList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SpecModuleParamList_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!SpecModuleParamList_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "SpecModuleParamList_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ';' SpecParamDecl
  private static boolean SpecModuleParamList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SpecModuleParamList_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, SEMICOLON);
    p = r; // pin = 1
    r = r && SpecParamDecl(b, l + 1);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '(' SpecModuleParamList ')'
  public static boolean SpecModuleParameters(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SpecModuleParameters")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, SpecModuleParamList(b, l + 1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, SPEC_MODULE_PARAMETERS, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // TypeParamDecl | ParamDecl | MathStandardDefnDecl
  static boolean SpecParamDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SpecParamDecl")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = TypeParamDecl(b, l + 1);
    if (!r) r = ParamDecl(b, l + 1);
    if (!r) r = MathStandardDefnDecl(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // SimpleStatement
  //     | WhileStatement
  //     | IfStatement
  public static boolean Statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, "<statement>");
    r = SimpleStatement(b, l + 1);
    if (!r) r = WhileStatement(b, l + 1);
    if (!r) r = IfStatement(b, l + 1);
    exit_section_(b, l, m, STATEMENT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !('end'|':='|':=:'|'If'|'While'|'else'|identifier)
  static boolean StatementRecover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StatementRecover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !StatementRecover_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // 'end'|':='|':=:'|'If'|'While'|'else'|identifier
  private static boolean StatementRecover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StatementRecover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, END);
    if (!r) r = consumeToken(b, COLON_EQUALS);
    if (!r) r = consumeToken(b, COLON_EQUALS_COLON);
    if (!r) r = consumeToken(b, PROG_IF);
    if (!r) r = consumeToken(b, WHILE);
    if (!r) r = consumeToken(b, ELSE);
    if (!r) r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Statement*
  static boolean Statements(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Statements")) return false;
    Marker m = enter_section_(b, l, _NONE_, null);
    int c = current_position_(b);
    while (true) {
      if (!Statement(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Statements", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, null, true, false, StatementRecover_parser_);
    return true;
  }

  /* ********************************************************** */
  // string | raw_string
  public static boolean StringLiteral(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StringLiteral")) return false;
    if (!nextTokenIs(b, "<string literal>", RAW_STRING, STRING)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<string literal>");
    r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, RAW_STRING);
    exit_section_(b, l, m, STRING_LITERAL, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ':=:' Exp
  public static boolean SwapStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SwapStatement")) return false;
    if (!nextTokenIs(b, COLON_EQUALS_COLON)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _LEFT_, null);
    r = consumeToken(b, COLON_EQUALS_COLON);
    p = r; // pin = 1
    r = r && Exp(b, l + 1, -1);
    exit_section_(b, l, m, SWAP_STATEMENT, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // RecordType | NamedType
  public static boolean Type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Type")) return false;
    if (!nextTokenIs(b, "<type>", RECORD, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, "<type>");
    r = RecordType(b, l + 1);
    if (!r) r = NamedType(b, l + 1);
    exit_section_(b, l, m, TYPE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'initialization' (RequiresClause)? (EnsuresClause)?
  // OpBlock 'end' ';'
  public static boolean TypeImplInit(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeImplInit")) return false;
    if (!nextTokenIs(b, INITIALIZATION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, INITIALIZATION);
    r = r && TypeImplInit_1(b, l + 1);
    r = r && TypeImplInit_2(b, l + 1);
    r = r && OpBlock(b, l + 1);
    r = r && consumeToken(b, END);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, TYPE_IMPL_INIT, r);
    return r;
  }

  // (RequiresClause)?
  private static boolean TypeImplInit_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeImplInit_1")) return false;
    TypeImplInit_1_0(b, l + 1);
    return true;
  }

  // (RequiresClause)
  private static boolean TypeImplInit_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeImplInit_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = RequiresClause(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (EnsuresClause)?
  private static boolean TypeImplInit_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeImplInit_2")) return false;
    TypeImplInit_2_0(b, l + 1);
    return true;
  }

  // (EnsuresClause)
  private static boolean TypeImplInit_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeImplInit_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = EnsuresClause(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'Type' 'family' identifier TypeModelPortion ';'
  //     ExemplarDecl
  //     ConstraintsClause?
  //     IntializationClause?
  public static boolean TypeModelDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeModelDecl")) return false;
    if (!nextTokenIs(b, FAMILY_TYPE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, FAMILY_TYPE);
    r = r && consumeToken(b, FAMILY);
    r = r && consumeToken(b, IDENTIFIER);
    p = r; // pin = 3
    r = r && report_error_(b, TypeModelPortion(b, l + 1));
    r = p && report_error_(b, consumeToken(b, SEMICOLON)) && r;
    r = p && report_error_(b, ExemplarDecl(b, l + 1)) && r;
    r = p && report_error_(b, TypeModelDecl_6(b, l + 1)) && r;
    r = p && TypeModelDecl_7(b, l + 1) && r;
    exit_section_(b, l, m, TYPE_MODEL_DECL, r, p, null);
    return r || p;
  }

  // ConstraintsClause?
  private static boolean TypeModelDecl_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeModelDecl_6")) return false;
    ConstraintsClause(b, l + 1);
    return true;
  }

  // IntializationClause?
  private static boolean TypeModelDecl_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeModelDecl_7")) return false;
    IntializationClause(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'is' 'modeled' 'by' MathExp
  static boolean TypeModelPortion(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeModelPortion")) return false;
    if (!nextTokenIs(b, IS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IS);
    r = r && consumeToken(b, MODELED);
    r = r && consumeToken(b, BY);
    r = r && MathExp(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // 'type' identifier
  public static boolean TypeParamDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeParamDecl")) return false;
    if (!nextTokenIs(b, PARAM_TYPE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PARAM_TYPE);
    r = r && consumeToken(b, IDENTIFIER);
    exit_section_(b, m, TYPE_PARAM_DECL, r);
    return r;
  }

  /* ********************************************************** */
  // identifier
  public static boolean TypeReferenceExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeReferenceExp")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, TYPE_REFERENCE_EXP, r);
    return r;
  }

  /* ********************************************************** */
  // 'Type' identifier '=' (RecordType|Type) ';'
  // ConventionsClause?
  // CorrespondenceClause?
  // TypeImplInit?
  public static boolean TypeReprDecl(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeReprDecl")) return false;
    if (!nextTokenIs(b, FAMILY_TYPE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, FAMILY_TYPE);
    r = r && consumeToken(b, IDENTIFIER);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, EQUALS));
    r = p && report_error_(b, TypeReprDecl_3(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, SEMICOLON)) && r;
    r = p && report_error_(b, TypeReprDecl_5(b, l + 1)) && r;
    r = p && report_error_(b, TypeReprDecl_6(b, l + 1)) && r;
    r = p && TypeReprDecl_7(b, l + 1) && r;
    exit_section_(b, l, m, TYPE_REPR_DECL, r, p, null);
    return r || p;
  }

  // RecordType|Type
  private static boolean TypeReprDecl_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeReprDecl_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = RecordType(b, l + 1);
    if (!r) r = Type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ConventionsClause?
  private static boolean TypeReprDecl_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeReprDecl_5")) return false;
    ConventionsClause(b, l + 1);
    return true;
  }

  // CorrespondenceClause?
  private static boolean TypeReprDecl_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeReprDecl_6")) return false;
    CorrespondenceClause(b, l + 1);
    return true;
  }

  // TypeImplInit?
  private static boolean TypeReprDecl_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeReprDecl_7")) return false;
    TypeImplInit(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ModuleSpec
  public static boolean UsesItem(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UsesItem")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ModuleSpec(b, l + 1);
    exit_section_(b, m, USES_ITEM, r);
    return r;
  }

  /* ********************************************************** */
  // 'uses' UsesItem (',' UsesItem)* ';'
  public static boolean UsesItemList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UsesItemList")) return false;
    if (!nextTokenIs(b, USES)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, USES);
    p = r; // pin = 1
    r = r && report_error_(b, UsesItem(b, l + 1));
    r = p && report_error_(b, UsesItemList_2(b, l + 1)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, USES_ITEM_LIST, r, p, null);
    return r || p;
  }

  // (',' UsesItem)*
  private static boolean UsesItemList_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UsesItemList_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!UsesItemList_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "UsesItemList_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' UsesItem
  private static boolean UsesItemList_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UsesItemList_2_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, COMMA);
    p = r; // pin = 1
    r = r && UsesItem(b, l + 1);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // 'Var' VarSpec ';'
  public static boolean VarDeclGroup(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VarDeclGroup")) return false;
    if (!nextTokenIs(b, VAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, VAR);
    p = r; // pin = 1
    r = r && report_error_(b, VarSpec(b, l + 1));
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, VAR_DECL_GROUP, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // identifier
  public static boolean VarDef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VarDef")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, VAR_DEF, r);
    return r;
  }

  /* ********************************************************** */
  // VarDef &(!(';'))(',' VarDef)*
  static boolean VarDefList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VarDefList")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = VarDef(b, l + 1);
    p = r; // pin = 1
    r = r && VarDefList_1(b, l + 1);
    r = r && VarDefList_2(b, l + 1);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  // &(!(';'))
  private static boolean VarDefList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VarDefList_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_, null);
    r = VarDefList_1_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // !(';')
  private static boolean VarDefList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VarDefList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !VarDefList_1_0_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // (';')
  private static boolean VarDefList_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VarDefList_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' VarDef)*
  private static boolean VarDefList_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VarDefList_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!VarDefList_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "VarDefList_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // ',' VarDef
  private static boolean VarDefList_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VarDefList_2_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, COMMA);
    p = r; // pin = 1
    r = r && VarDef(b, l + 1);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // VarDefList ':' Type
  public static boolean VarSpec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "VarSpec")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = VarDefList(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, COLON));
    r = p && Type(b, l + 1) && r;
    exit_section_(b, l, m, VAR_SPEC, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // 'While' Exp
  // ChangingClause? MaintainingClause? DecreasingClause?
  // 'do' Statements 'end' ';'
  public static boolean WhileStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WhileStatement")) return false;
    if (!nextTokenIs(b, WHILE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, WHILE);
    p = r; // pin = 1
    r = r && report_error_(b, Exp(b, l + 1, -1));
    r = p && report_error_(b, WhileStatement_2(b, l + 1)) && r;
    r = p && report_error_(b, WhileStatement_3(b, l + 1)) && r;
    r = p && report_error_(b, WhileStatement_4(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, DO)) && r;
    r = p && report_error_(b, Statements(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, END)) && r;
    r = p && consumeToken(b, SEMICOLON) && r;
    exit_section_(b, l, m, WHILE_STATEMENT, r, p, null);
    return r || p;
  }

  // ChangingClause?
  private static boolean WhileStatement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WhileStatement_2")) return false;
    ChangingClause(b, l + 1);
    return true;
  }

  // MaintainingClause?
  private static boolean WhileStatement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WhileStatement_3")) return false;
    MaintainingClause(b, l + 1);
    return true;
  }

  // DecreasingClause?
  private static boolean WhileStatement_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WhileStatement_4")) return false;
    DecreasingClause(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // Expression root: Exp
  // Operator priority table:
  // 0: BINARY(OrInfixExp)
  // 1: BINARY(AndInfixExp)
  // 2: BINARY(RelationalInfixExp)
  // 3: BINARY(AddInfixExp)
  // 4: BINARY(MulInfixExp)
  // 5: PREFIX(UnaryExp)
  // 6: ATOM(NameExp) BINARY(SelectorExp) POSTFIX(CallExp) ATOM(LiteralExp)
  // 7: PREFIX(ParenExp)
  public static boolean Exp(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "Exp")) return false;
    addVariant(b, "<exp>");
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<exp>");
    r = UnaryExp(b, l + 1);
    if (!r) r = NameExp(b, l + 1);
    if (!r) r = LiteralExp(b, l + 1);
    if (!r) r = ParenExp(b, l + 1);
    p = r;
    r = r && Exp_0(b, l + 1, g);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  public static boolean Exp_0(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "Exp_0")) return false;
    boolean r = true;
    while (true) {
      Marker m = enter_section_(b, l, _LEFT_, null);
      if (g < 0 && consumeTokenSmart(b, OR)) {
        r = Exp(b, l, 0);
        exit_section_(b, l, m, OR_INFIX_EXP, r, true, null);
      }
      else if (g < 1 && consumeTokenSmart(b, AND)) {
        r = Exp(b, l, 1);
        exit_section_(b, l, m, AND_INFIX_EXP, r, true, null);
      }
      else if (g < 2 && RelationalInfixExp_0(b, l + 1)) {
        r = Exp(b, l, 2);
        exit_section_(b, l, m, RELATIONAL_INFIX_EXP, r, true, null);
      }
      else if (g < 3 && AddInfixExp_0(b, l + 1)) {
        r = Exp(b, l, 3);
        exit_section_(b, l, m, ADD_INFIX_EXP, r, true, null);
      }
      else if (g < 4 && MulInfixExp_0(b, l + 1)) {
        r = Exp(b, l, 4);
        exit_section_(b, l, m, MUL_INFIX_EXP, r, true, null);
      }
      else if (g < 6 && consumeTokenSmart(b, DOT)) {
        r = Exp(b, l, 6);
        exit_section_(b, l, m, SELECTOR_EXP, r, true, null);
      }
      else if (g < 6 && leftMarkerIs(b, REFERENCE_EXP) && ArgumentList(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, CALL_EXP, r, true, null);
      }
      else {
        exit_section_(b, l, m, null, false, false, null);
        break;
      }
    }
    return r;
  }

  // '='|'/='|'<'|'<='|'>'|'>='
  private static boolean RelationalInfixExp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RelationalInfixExp_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, EQUALS);
    if (!r) r = consumeTokenSmart(b, NEQUALS);
    if (!r) r = consumeTokenSmart(b, LESS);
    if (!r) r = consumeTokenSmart(b, LESS_OR_EQUAL);
    if (!r) r = consumeTokenSmart(b, GREATER);
    if (!r) r = consumeTokenSmart(b, GREATER_OR_EQUAL);
    exit_section_(b, m, null, r);
    return r;
  }

  // '+'|'-'
  private static boolean AddInfixExp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AddInfixExp_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, PLUS);
    if (!r) r = consumeTokenSmart(b, MINUS);
    exit_section_(b, m, null, r);
    return r;
  }

  // '*'|'/'|'%'
  private static boolean MulInfixExp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MulInfixExp_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, MUL);
    if (!r) r = consumeTokenSmart(b, QUOTIENT);
    if (!r) r = consumeTokenSmart(b, MOD);
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean UnaryExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UnaryExp")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = UnaryExp_0(b, l + 1);
    p = r;
    r = p && Exp(b, l, 5);
    exit_section_(b, l, m, UNARY_EXP, r, p, null);
    return r || p;
  }

  // '+'|'-'|'not'
  private static boolean UnaryExp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UnaryExp_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, PLUS);
    if (!r) r = consumeTokenSmart(b, MINUS);
    if (!r) r = consumeTokenSmart(b, NOT);
    exit_section_(b, m, null, r);
    return r;
  }

  // ReferenceExp QualifiedReferenceExp?
  public static boolean NameExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NameExp")) return false;
    if (!nextTokenIsFast(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, null);
    r = ReferenceExp(b, l + 1);
    r = r && NameExp_1(b, l + 1);
    exit_section_(b, l, m, REFERENCE_EXP, r, false, null);
    return r;
  }

  // QualifiedReferenceExp?
  private static boolean NameExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NameExp_1")) return false;
    QualifiedReferenceExp(b, l + 1);
    return true;
  }

  // int|StringLiteral|BooleanLiteral
  public static boolean LiteralExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LiteralExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<literal exp>");
    r = consumeTokenSmart(b, INT);
    if (!r) r = StringLiteral(b, l + 1);
    if (!r) r = BooleanLiteral(b, l + 1);
    exit_section_(b, l, m, LITERAL_EXP, r, false, null);
    return r;
  }

  public static boolean ParenExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ParenExp")) return false;
    if (!nextTokenIsFast(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, LPAREN);
    p = r;
    r = p && Exp(b, l, -1);
    r = p && report_error_(b, consumeToken(b, RPAREN)) && r;
    exit_section_(b, l, m, PAREN_EXP, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // Expression root: MathExp
  // Operator priority table:
  // 0: BINARY(MathBooleanInfixApplyExp)
  // 1: BINARY(MathImpliesInfixApplyExp)
  // 2: BINARY(MathEqualityInfixApplyExp)
  // 3: BINARY(MathRelationalInfixApplyExp)
  // 4: BINARY(MathApplicationInfixApplyExp)
  // 5: BINARY(MathSetInfixApplyExp)
  // 6: BINARY(MathAddInfixApplyExp)
  // 7: BINARY(MathMultInfixApplyExp)
  // 8: BINARY(MathIdentInfixApplyExp)
  // 9: PREFIX(MathBarOutfixApplyExp) PREFIX(MathAngleOutfixApplyExp) PREFIX(MathDblBarOutfixApplyExp) PREFIX(MathSqBrOutfixApplyExp) PREFIX(MathCupOutfixApplyExp) PREFIX(MathAngle1OutfixApplyExp)
  // 10: PREFIX(MathIncomingUnaryApplyExp)
  // 11: BINARY(MathJoiningInfixApplyExp)
  // 12: POSTFIX(MathPrefixApplyExp)
  // 13: POSTFIX(MathPrefixGeneralizedSqbrApplyExp)
  // 14: POSTFIX(MathPrefixGeneralizedCeilApplyExp)
  // 15: ATOM(MathNestedExp)
  // 16: ATOM(MathSymbolExp) BINARY(MathTypeAssertionExp) PREFIX(MathSetComprehensionExp) ATOM(MathSetExp) PREFIX(MathLambdaExp) BINARY(MathSelectorExp) ATOM(MathAlternativeExp) ATOM(MathCartProdExp) ATOM(MathLiteralExp)
  public static boolean MathExp(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "MathExp")) return false;
    addVariant(b, "<math exp>");
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<math exp>");
    r = MathBarOutfixApplyExp(b, l + 1);
    if (!r) r = MathAngleOutfixApplyExp(b, l + 1);
    if (!r) r = MathDblBarOutfixApplyExp(b, l + 1);
    if (!r) r = MathSqBrOutfixApplyExp(b, l + 1);
    if (!r) r = MathCupOutfixApplyExp(b, l + 1);
    if (!r) r = MathAngle1OutfixApplyExp(b, l + 1);
    if (!r) r = MathIncomingUnaryApplyExp(b, l + 1);
    if (!r) r = MathNestedExp(b, l + 1);
    if (!r) r = MathSymbolExp(b, l + 1);
    if (!r) r = MathSetComprehensionExp(b, l + 1);
    if (!r) r = MathSetExp(b, l + 1);
    if (!r) r = MathLambdaExp(b, l + 1);
    if (!r) r = MathAlternativeExp(b, l + 1);
    if (!r) r = MathCartProdExp(b, l + 1);
    if (!r) r = MathLiteralExp(b, l + 1);
    p = r;
    r = r && MathExp_0(b, l + 1, g);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  public static boolean MathExp_0(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "MathExp_0")) return false;
    boolean r = true;
    while (true) {
      Marker m = enter_section_(b, l, _LEFT_, null);
      if (g < 0 && MathBooleanOp(b, l + 1)) {
        r = MathExp(b, l, 0);
        exit_section_(b, l, m, MATH_BOOLEAN_INFIX_APPLY_EXP, r, true, null);
      }
      else if (g < 1 && MathImpliesOp(b, l + 1)) {
        r = MathExp(b, l, 1);
        exit_section_(b, l, m, MATH_IMPLIES_INFIX_APPLY_EXP, r, true, null);
      }
      else if (g < 2 && MathEqualityOp(b, l + 1)) {
        r = MathExp(b, l, 2);
        exit_section_(b, l, m, MATH_EQUALITY_INFIX_APPLY_EXP, r, true, null);
      }
      else if (g < 3 && MathRelationalOp(b, l + 1)) {
        r = MathExp(b, l, 3);
        exit_section_(b, l, m, MATH_RELATIONAL_INFIX_APPLY_EXP, r, true, null);
      }
      else if (g < 4 && MathArrowOp(b, l + 1)) {
        r = MathExp(b, l, 4);
        exit_section_(b, l, m, MATH_APPLICATION_INFIX_APPLY_EXP, r, true, null);
      }
      else if (g < 5 && MathSetContainmentOp(b, l + 1)) {
        r = MathExp(b, l, 5);
        exit_section_(b, l, m, MATH_SET_INFIX_APPLY_EXP, r, true, null);
      }
      else if (g < 6 && MathAddOp(b, l + 1)) {
        r = MathExp(b, l, 6);
        exit_section_(b, l, m, MATH_ADD_INFIX_APPLY_EXP, r, true, null);
      }
      else if (g < 7 && MathMultOp(b, l + 1)) {
        r = MathExp(b, l, 7);
        exit_section_(b, l, m, MATH_MULT_INFIX_APPLY_EXP, r, true, null);
      }
      else if (g < 8 && consumeTokenSmart(b, IDENTIFIER)) {
        r = MathExp(b, l, 8);
        exit_section_(b, l, m, MATH_IDENT_INFIX_APPLY_EXP, r, true, null);
      }
      else if (g < 11 && MathJoiningOp(b, l + 1)) {
        r = MathExp(b, l, 11);
        exit_section_(b, l, m, MATH_JOINING_INFIX_APPLY_EXP, r, true, null);
      }
      else if (g < 12 && MathPrefixApplyExp_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, MATH_PREFIX_APPLY_EXP, r, true, null);
      }
      else if (g < 13 && MathPrefixGeneralizedSqbrApplyExp_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, MATH_PREFIX_GENERALIZED_SQBR_APPLY_EXP, r, true, null);
      }
      else if (g < 14 && MathPrefixGeneralizedCeilApplyExp_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, MATH_PREFIX_GENERALIZED_CEIL_APPLY_EXP, r, true, null);
      }
      else if (g < 16 && consumeTokenSmart(b, COLON)) {
        r = MathExp(b, l, 16);
        exit_section_(b, l, m, MATH_TYPE_ASSERTION_EXP, r, true, null);
      }
      else if (g < 16 && consumeTokenSmart(b, DOT)) {
        r = MathExp(b, l, 16);
        exit_section_(b, l, m, MATH_SELECTOR_EXP, r, true, null);
      }
      else {
        exit_section_(b, l, m, null, false, false, null);
        break;
      }
    }
    return r;
  }

  public static boolean MathBarOutfixApplyExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathBarOutfixApplyExp")) return false;
    if (!nextTokenIsFast(b, BAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, BAR);
    p = r;
    r = p && MathExp(b, l, 9);
    r = p && report_error_(b, consumeToken(b, BAR)) && r;
    exit_section_(b, l, m, MATH_BAR_OUTFIX_APPLY_EXP, r, p, null);
    return r || p;
  }

  public static boolean MathAngleOutfixApplyExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAngleOutfixApplyExp")) return false;
    if (!nextTokenIsFast(b, LESS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, LESS);
    p = r;
    r = p && MathExp(b, l, 9);
    r = p && report_error_(b, consumeToken(b, GREATER)) && r;
    exit_section_(b, l, m, MATH_ANGLE_OUTFIX_APPLY_EXP, r, p, null);
    return r || p;
  }

  public static boolean MathDblBarOutfixApplyExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathDblBarOutfixApplyExp")) return false;
    if (!nextTokenIsFast(b, DBL_BAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, DBL_BAR);
    p = r;
    r = p && MathExp(b, l, 9);
    r = p && report_error_(b, consumeToken(b, DBL_BAR)) && r;
    exit_section_(b, l, m, MATH_DBL_BAR_OUTFIX_APPLY_EXP, r, p, null);
    return r || p;
  }

  public static boolean MathSqBrOutfixApplyExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSqBrOutfixApplyExp")) return false;
    if (!nextTokenIsFast(b, LBRACK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, LBRACK);
    p = r;
    r = p && MathExp(b, l, 9);
    r = p && report_error_(b, consumeToken(b, RBRACK)) && r;
    exit_section_(b, l, m, MATH_SQ_BR_OUTFIX_APPLY_EXP, r, p, null);
    return r || p;
  }

  public static boolean MathCupOutfixApplyExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathCupOutfixApplyExp")) return false;
    if (!nextTokenIsFast(b, LCURVE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, LCURVE);
    p = r;
    r = p && MathExp(b, l, 9);
    r = p && report_error_(b, consumeToken(b, RCURVE)) && r;
    exit_section_(b, l, m, MATH_CUP_OUTFIX_APPLY_EXP, r, p, null);
    return r || p;
  }

  public static boolean MathAngle1OutfixApplyExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAngle1OutfixApplyExp")) return false;
    if (!nextTokenIsFast(b, LANGLE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, LANGLE);
    p = r;
    r = p && MathExp(b, l, 9);
    r = p && report_error_(b, consumeToken(b, RANGLE)) && r;
    exit_section_(b, l, m, MATH_ANGLE_1_OUTFIX_APPLY_EXP, r, p, null);
    return r || p;
  }

  public static boolean MathIncomingUnaryApplyExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathIncomingUnaryApplyExp")) return false;
    if (!nextTokenIsFast(b, AT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, AT);
    p = r;
    r = p && MathExp(b, l, 10);
    exit_section_(b, l, m, MATH_INCOMING_UNARY_APPLY_EXP, r, p, null);
    return r || p;
  }

  // '(' MathArgList ')'
  private static boolean MathPrefixApplyExp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixApplyExp_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, LPAREN);
    r = r && MathArgList(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // '[' GeneralizedApplyArgList ']'
  private static boolean MathPrefixGeneralizedSqbrApplyExp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixGeneralizedSqbrApplyExp_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, LBRACK);
    r = r && GeneralizedApplyArgList(b, l + 1);
    r = r && consumeToken(b, RBRACK);
    exit_section_(b, m, null, r);
    return r;
  }

  // '⎡' GeneralizedApplyArgList '⎤'
  private static boolean MathPrefixGeneralizedCeilApplyExp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathPrefixGeneralizedCeilApplyExp_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, LCEIL);
    r = r && GeneralizedApplyArgList(b, l + 1);
    r = r && consumeToken(b, RCEIL);
    exit_section_(b, m, null, r);
    return r;
  }

  // '(' MathAssertionExp ')'
  public static boolean MathNestedExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathNestedExp")) return false;
    if (!nextTokenIsFast(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, MathAssertionExp(b, l + 1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, MATH_NESTED_EXP, r, p, null);
    return r || p;
  }

  // MathReferenceExp MathQualifiedReferenceExp?
  public static boolean MathSymbolExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSymbolExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, "<math symbol exp>");
    r = MathReferenceExp(b, l + 1);
    r = r && MathSymbolExp_1(b, l + 1);
    exit_section_(b, l, m, MATH_REFERENCE_EXP, r, false, null);
    return r;
  }

  // MathQualifiedReferenceExp?
  private static boolean MathSymbolExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSymbolExp_1")) return false;
    MathQualifiedReferenceExp(b, l + 1);
    return true;
  }

  public static boolean MathSetComprehensionExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSetComprehensionExp")) return false;
    if (!nextTokenIsFast(b, LBRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = MathSetComprehensionExp_0(b, l + 1);
    p = r;
    r = p && MathExp(b, l, -1);
    r = p && report_error_(b, consumeToken(b, RBRACE)) && r;
    exit_section_(b, l, m, MATH_SET_COMPREHENSION_EXP, r, p, null);
    return r || p;
  }

  // '{' MathVarDecl '|'
  private static boolean MathSetComprehensionExp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSetComprehensionExp_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, LBRACE);
    r = r && MathVarDecl(b, l + 1);
    r = r && consumeToken(b, BAR);
    exit_section_(b, m, null, r);
    return r;
  }

  // '{' MathSetElementsList? '}'
  public static boolean MathSetExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSetExp")) return false;
    if (!nextTokenIsFast(b, LBRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, LBRACE);
    r = r && MathSetExp_1(b, l + 1);
    p = r; // pin = 2
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, l, m, MATH_SET_EXP, r, p, null);
    return r || p;
  }

  // MathSetElementsList?
  private static boolean MathSetExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathSetExp_1")) return false;
    MathSetElementsList(b, l + 1);
    return true;
  }

  public static boolean MathLambdaExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathLambdaExp")) return false;
    if (!nextTokenIsFast(b, LAMBDA1, LAMBDA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = MathLambdaExp_0(b, l + 1);
    p = r;
    r = p && MathExp(b, l, -1);
    r = p && report_error_(b, consumeToken(b, RPAREN)) && r;
    exit_section_(b, l, m, MATH_LAMBDA_EXP, r, p, null);
    return r || p;
  }

  // (lambda|'λ') MathDefinitionParams '.' '('
  private static boolean MathLambdaExp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathLambdaExp_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathLambdaExp_0_0(b, l + 1);
    r = r && MathDefinitionParams(b, l + 1);
    r = r && consumeToken(b, DOT);
    r = r && consumeToken(b, LPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // lambda|'λ'
  private static boolean MathLambdaExp_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathLambdaExp_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, LAMBDA);
    if (!r) r = consumeTokenSmart(b, LAMBDA1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '{{' (MathAlternativeItemExp)+ '}}'
  public static boolean MathAlternativeExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAlternativeExp")) return false;
    if (!nextTokenIsFast(b, DBL_LBRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, DBL_LBRACE);
    r = r && MathAlternativeExp_1(b, l + 1);
    p = r; // pin = 2
    r = r && consumeToken(b, DBL_RBRACE);
    exit_section_(b, l, m, MATH_ALTERNATIVE_EXP, r, p, null);
    return r || p;
  }

  // (MathAlternativeItemExp)+
  private static boolean MathAlternativeExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAlternativeExp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathAlternativeExp_1_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!MathAlternativeExp_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MathAlternativeExp_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // (MathAlternativeItemExp)
  private static boolean MathAlternativeExp_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathAlternativeExp_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MathAlternativeItemExp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // 'Cart_Prod' ResMathCartVarGroup+  'end'
  public static boolean MathCartProdExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathCartProdExp")) return false;
    if (!nextTokenIsFast(b, CART_PROD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, CART_PROD);
    p = r; // pin = 1
    r = r && report_error_(b, MathCartProdExp_1(b, l + 1));
    r = p && consumeToken(b, END) && r;
    exit_section_(b, l, m, MATH_CART_PROD_EXP, r, p, null);
    return r || p;
  }

  // ResMathCartVarGroup+
  private static boolean MathCartProdExp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathCartProdExp_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ResMathCartVarGroup(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!ResMathCartVarGroup(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MathCartProdExp_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // int|'true'|'false'
  public static boolean MathLiteralExp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MathLiteralExp")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<math literal exp>");
    r = consumeTokenSmart(b, INT);
    if (!r) r = consumeTokenSmart(b, TRUE);
    if (!r) r = consumeTokenSmart(b, FALSE);
    exit_section_(b, l, m, MATH_LITERAL_EXP, r, false, null);
    return r;
  }

  final static Parser CategoricalSigListRec_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return CategoricalSigListRec(b, l + 1);
    }
  };
  final static Parser ConceptItemRecover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return ConceptItemRecover(b, l + 1);
    }
  };
  final static Parser ExpListRecover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return ExpListRecover(b, l + 1);
    }
  };
  final static Parser ExtensionListRec_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return ExtensionListRec(b, l + 1);
    }
  };
  final static Parser FacilityItemRecover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return FacilityItemRecover(b, l + 1);
    }
  };
  final static Parser GeneralizedApplyRecover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return GeneralizedApplyRecover(b, l + 1);
    }
  };
  final static Parser ImplItemRecover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return ImplItemRecover(b, l + 1);
    }
  };
  final static Parser ItemsRecover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return ItemsRecover(b, l + 1);
    }
  };
  final static Parser MathPrefixListRec_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return MathPrefixListRec(b, l + 1);
    }
  };
  final static Parser MathSetEleRecover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return MathSetEleRecover(b, l + 1);
    }
  };
  final static Parser ParamListRec_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return ParamListRec(b, l + 1);
    }
  };
  final static Parser PrecisItemRecover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return PrecisItemRecover(b, l + 1);
    }
  };
  final static Parser StatementRecover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return StatementRecover(b, l + 1);
    }
  };
}
