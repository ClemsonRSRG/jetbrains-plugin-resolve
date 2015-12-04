package edu.clemson.resolve.plugin.completion;

import java.io.IOException;
import java.util.List;

public class RESOLVECompletionTest extends RESOLVECompletionTestBase {

    public void testSimpleUsesRef() throws IOException {
        myFixture.getTempDirFixture().createFile("A.resolve", "Precis A; Definition xss : V; end A;");
        myFixture.getTempDirFixture().createFile("foo/C.resolve", "");
        myFixture.configureByText("C.resolve", "Precis C; uses A; Definition foo : <caret>");
        myFixture.completeBasic();
        List<String> lookupElementStrings = myFixture.getLookupElementStrings();
        assertNotNull(lookupElementStrings);
        assertContainsElements(lookupElementStrings, "xss");
    }

    public void testImplicitImport() throws IOException {
        myFixture.getTempDirFixture().createFile("A.resolve", "Precis A; Definition xss : V; end A;");
        myFixture.getTempDirFixture().createFile("foo/Ext.resolve", "");
        myFixture.configureByText("Ext.resolve", "Precis Extension Ext for A; Definition foo : <caret>");
        myFixture.completeBasic();
        List<String> lookupElementStrings = myFixture.getLookupElementStrings();
        assertNotNull(lookupElementStrings);
        assertContainsElements(lookupElementStrings, "xss");
    }

    public void testSimpleLocalMathRef() {
        doTestInclude("Precis Foo; Definition T1(xs, ys : Z) : B is <caret>",
                "xs", "ys", "T1");
    }

    public void testSimpleMathSetRef() {
        doTestInclude("Precis Foo; Definition meh : V1 " +
                        "Definition T1 : B is {xs : T | <caret> is_in V}",
                "xs", "meh", "T1");
    }

    public void testQuantifierBoundVarRef() {
        doTestInclude("Precis Foo; Theorem T: " +
                "Forall xs, ys : Z, Exists pz : B, <caret>; " +
                "end Foo;", "xs", "ys", "pz");
    }

    public void testExclusivelyHardCodedMathRefs() {
        doTestEquals("Precis Foo; Corollary C1: <caret>; " +
                        "end Foo",
                "Powerset", "Cls", "SSet", "and", "or", "true", "false", "B",
                "implies", "not", "iff", "Entity", "El");
    }

}