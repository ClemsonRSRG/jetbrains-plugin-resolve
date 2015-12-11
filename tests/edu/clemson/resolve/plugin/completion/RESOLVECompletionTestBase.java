package edu.clemson.resolve.plugin.completion;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.UsefulTestCase;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class RESOLVECompletionTestBase
        extends
            LightPlatformCodeInsightFixtureTestCase {

    @Override protected void setUp() throws Exception {
        super.setUp();
    }

    @Override protected String getBasePath() {
        return "completion";
    }

    protected void doTestCompletion() {
        myFixture.testCompletion(getTestName(true) +
                ".resolve", getTestName(true) + "_after.resolve");
    }

    protected enum CheckType {EQUALS, INCLUDES, EXCLUDES}

    protected void doTestVariantsInner(CompletionType type, int count,
                                       CheckType checkType,
                                       String... variants) {
        myFixture.complete(type, count);
        List<String> stringList = myFixture.getLookupElementStrings();

        assertNotNull(
                "\nPossibly the single variant has been completed.\n" +
                        "File after:\n" +
                        myFixture.getFile().getText(),
                stringList);
        Collection<String> varList = new ArrayList<String>(Arrays.asList(variants));
        if (checkType == CheckType.EQUALS) {
            UsefulTestCase.assertSameElements(stringList, variants);
        }
        else if (checkType == CheckType.INCLUDES) {
            varList.removeAll(stringList);
            assertTrue("Missing variants: " + varList, varList.isEmpty());
        }
        else if (checkType == CheckType.EXCLUDES) {
            varList.retainAll(stringList);
            assertTrue("Unexpected variants: " + varList, varList.isEmpty());
        }
    }

    protected void doTestVariants(String txt, CompletionType type, int count,
                                  CheckType checkType, String... variants) {
        myFixture.configureByText("a.resolve", txt);
        doTestVariantsInner(type, count, checkType, variants);
    }

    protected void doTestEquals(String txt, String... variants) {
        doTestVariants(txt, CompletionType.BASIC, 1, CheckType.EQUALS, variants);
    }

    protected void doTestInclude(String txt, String... variants) {
        doTestVariants(txt, CompletionType.BASIC, 1, CheckType.INCLUDES, variants);
    }

}