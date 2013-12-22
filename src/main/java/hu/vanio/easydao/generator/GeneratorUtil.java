/*
 * The MIT License
 *
 * Copyright 2013 Vanio Informatika Kft.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hu.vanio.easydao.generator;

/**
 * Helper function for generator
 * @author Istvan Pato <istvan.pato@vanio.hu>
 */
public class GeneratorUtil {
    
    /** There is no comment! */
    final public String EMPTY_COMMENT = "@FIXME: Warning: There is no comment in database!";

    /**
     * Create Java name from database's name
     * @param dbName database's name
     * @param firstCharToUpperCase set first result char to uppercase, i.e: class name
     * @param hasPrefix dbName has prefix
     * @param hasPostFix dbName has postfix
     * @return Java name based on java convention
     */
    public String createJavaName(String dbName,
            boolean firstCharToUpperCase,
            boolean hasPrefix,
            boolean hasPostFix) {
        String[] sArray = dbName.toLowerCase().split("_");
        String result = "";
        for (int i = 0; i < sArray.length; i++) {
            String s = sArray[i];
            if (hasPrefix && i == 0) {
                // skip first chunk
                continue;
            }
            if (hasPostFix && i == sArray.length - 1) {
                // skip last chunk
                continue;
            }
            if (i > 0) {
                // upper case chunks' first character
                String firstChar = s.substring(0, 1).toUpperCase();
                s = firstChar + s.substring(1, s.length());
            }
            result += s;
        }
        // handling first character
        if (firstCharToUpperCase) {
            // upper case
            String firstChar = result.substring(0, 1).toUpperCase();
            result = firstChar + result.substring(1, result.length());
        } else {
            // lower case
            String firstChar = result.substring(0, 1).toLowerCase();
            result = firstChar + result.substring(1, result.length());
        }
        return result;
    }
}
