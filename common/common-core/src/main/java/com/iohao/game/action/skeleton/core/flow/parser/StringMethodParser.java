/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iohao.game.action.skeleton.core.flow.parser;

import com.iohao.game.action.skeleton.core.ActionCommand;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.action.skeleton.protocol.wrapper.StringListPb;
import com.iohao.game.action.skeleton.protocol.wrapper.StringPb;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 渔民小镇
 * @date 2023-02-05
 */
final class StringMethodParser implements MethodParser {
    @Override
    public Class<?> getActualClazz(ActionCommand.MethodParamResultInfo methodParamResultInfo) {
        return methodParamResultInfo.isList() ? StringListPb.class : StringPb.class;
    }

    @Override
    public Object parseParam(byte[] data, ActionCommand.ParamInfo paramInfo) {

        if (paramInfo.isList()) {

            if (Objects.isNull(data)) {
                return new ArrayList<String>();
            }

            StringListPb stringListPb = DataCodecKit.decode(data, StringListPb.class);
            return stringListPb.stringValues;
        }

        if (Objects.isNull(data)) {
            return null;
        }

        StringPb stringPb = DataCodecKit.decode(data, StringPb.class);
        return stringPb.stringValue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object parseResult(ActionCommand.ActionMethodReturnInfo actionMethodReturnInfo, Object methodResult) {

        if (actionMethodReturnInfo.isList()) {
            StringListPb stringListPb = new StringListPb();
            stringListPb.stringValues = (List<String>) methodResult;
            return stringListPb;
        }

        StringPb stringPb = new StringPb();
        stringPb.stringValue = String.valueOf(methodResult);
        return stringPb;
    }

    private StringMethodParser() {

    }

    public static StringMethodParser me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final StringMethodParser ME = new StringMethodParser();
    }
}
