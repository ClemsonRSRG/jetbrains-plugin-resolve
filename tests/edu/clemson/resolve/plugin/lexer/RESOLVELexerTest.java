package edu.clemson.resolve.plugin.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.testFramework.LexerTestCase;

import java.io.File;
import java.io.IOException;

public class RESOLVELexerTest extends LexerTestCase {

    public void testHello() { doTest(); }
    public void testEscaped_Quotes() { doTest(); }

    private void doTest() {
        try {
            String text = FileUtil.loadFile(new File("./testData/lexer/"
                    + getTestName(false) + ".resolve"));
            String actual = printTokens(StringUtil
                    .convertLineSeparators(text.trim()), 0);
            assertSameLinesWithFile(new File("testData/lexer/"
                    + getTestName(false) + ".txt").getAbsolutePath(), actual);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override protected Lexer createLexer() { return new ResolveLexer(); }

    @Override protected String getDirPath() {
        return "../testData/lexer";
    }
}