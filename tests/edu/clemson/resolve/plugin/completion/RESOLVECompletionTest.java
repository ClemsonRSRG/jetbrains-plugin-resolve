package edu.clemson.resolve.plugin.completion;

public class RESOLVECompletionTest extends RESOLVECompletionTestBase {

    public void testSimpleLocalMathRef() {
        doTestEquals("Precis Foo; Definition T1(xs, ys : Z) : B is <caret>",
                "xs", "ys", "T1");
    }

   // public void testSimpleMathSetRef() {
   //     doTestEquals("Precis Foo; Definition meh : V1 " +
   //                     "Definition T1 : B is {xs : T | <caret> is_in V}",
   //             "xs", "meh", "T1");
   // }

   // public void testQuantifierBoundVarRef() {
   //     doTestEquals("Precis Foo; Theorem T: " +
   //             "Forall xs, ys : Z, Exists pz : B, <caret>; " +
   //             "end Foo;", "xs", "ys", "pz");
   // }
}