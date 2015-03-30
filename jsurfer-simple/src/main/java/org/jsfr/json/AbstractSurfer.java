/*
 * The MIT License
 *
 * Copyright (c) 2015 WANG Lingsong
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

package org.jsfr.json;

import org.jsfr.json.SurfingContext.Builder;
import org.jsfr.json.path.JsonPath;

import java.io.Reader;
import java.util.Collection;

import static org.jsfr.json.SurfingContext.Builder.builder;


/**
 * Created by Leo on 2015/3/30.
 */
public abstract class AbstractSurfer implements JsonSurfer {

    protected JsonProvider jsonProvider;

    public AbstractSurfer(JsonProvider jsonProvider) {
        this.jsonProvider = jsonProvider;
    }

    @Override
    public <T> Collection<T> collect(Reader reader, JsonPath... paths) {
        CollectAllListener<T> listener = new CollectAllListener<T>();
        Builder builder = builder().withJsonProvider(jsonProvider);
        for (JsonPath jsonPath : paths) {
            builder.bind(jsonPath, listener);
        }
        surf(reader, builder.build());
        return listener.getCollection();
    }

    @Override
    public <T> T collectOne(Reader reader, JsonPath... paths) {
        CollectOneListener<T> listener = new CollectOneListener<T>();
        Builder builder = builder().withJsonProvider(jsonProvider);
        for (JsonPath jsonPath : paths) {
            builder.bind(jsonPath, listener);
        }
        surf(reader, builder.build());
        return listener.getValue();
    }

    protected void ensureJsonProvider(SurfingContext context) {
        if (context.getJsonProvider() == null) {
            context.setJsonProvider(jsonProvider);
        }
    }

}
