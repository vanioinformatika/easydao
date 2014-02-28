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
package hu.vanio.easydao;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 *
 * @author Gyula Szalai <gyula.szalai@vanio.hu>
 */
public class LocalisedMessages implements TemplateMethodModelEx {
    
    /** The resourceBundle that contains the localised messages */
    private final ResourceBundle messages;
    
    /**
     * Constructs a new instance
     * 
     * @param bundleResourceName The resourceBundle that contains the localised messages
     * @param locale The locale
     */
    public LocalisedMessages(String bundleResourceName, Locale locale) {
        if (locale != null) {
            this.messages = ResourceBundle.getBundle(bundleResourceName, locale);
        } else {
            this.messages = ResourceBundle.getBundle(bundleResourceName);
        }
    }
    
    /**
     * Gets a localised message with optional arguments
     * 
     * @param key The message key
     * @param args The arguments
     * @return The localised message with the arguments inserted
     */
    public String getMessage(String key, Object... args) {
        String message = this.messages.getString(key);
        return MessageFormat.format(message, args);
    }

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        
        if (arguments.size() < 1) {
            throw new TemplateModelException("Wrong number of arguments");
        }
        String key = ((SimpleScalar) arguments.get(0)).getAsString();
        if (key == null || key.isEmpty()) {
            throw new TemplateModelException("Invalid key value '" + key + "'");
        }
        
        Object[] argsAll = arguments.toArray();
        Object[] args = Arrays.copyOfRange(argsAll, 1, argsAll.length);
        
        return this.getMessage(key, args);
    }
    
}
