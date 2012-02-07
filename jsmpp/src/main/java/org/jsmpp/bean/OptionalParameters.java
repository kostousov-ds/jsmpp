/*
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.jsmpp.bean;

import java.util.HashMap;
import java.util.Map;

import org.jsmpp.bean.OptionalParameter.Byte;
import org.jsmpp.bean.OptionalParameter.COctetString;
import org.jsmpp.bean.OptionalParameter.Int;
import org.jsmpp.bean.OptionalParameter.Null;
import org.jsmpp.bean.OptionalParameter.OctetString;
import org.jsmpp.bean.OptionalParameter.Short;
import org.jsmpp.bean.OptionalParameter.Tag;

/**
 * @author uudashr
 *
 */
public class OptionalParameters {
    
    private static interface Converter {
	OptionalParameter fromBytes(short tag, byte[] value);
    }
    
    private static Map<Class<? extends OptionalParameter>,Converter> map=new HashMap<Class<? extends OptionalParameter>, Converter>();
    static{
	map.put(Byte.class, new Converter() {
	    @Override
	    public OptionalParameter fromBytes(short tag, byte[] value) {
		return new Byte(tag, value);
	    }
	});
	map.put(COctetString.class, new Converter() {
	    @Override
	    public OptionalParameter fromBytes(short tag, byte[] value) {
		return new COctetString(tag, value);
	    }
	});
	map.put(Int.class, new Converter() {
	    @Override
	    public OptionalParameter fromBytes(short tag, byte[] value) {
		return new Int(tag, value);
	    }
	});
	map.put(Null.class, new Converter() {
	    @Override
	    public OptionalParameter fromBytes(short tag, byte[] value) {
		return new Null(tag);
	    }
	});
	map.put(OctetString.class, new Converter() {
	    @Override
	    public OptionalParameter fromBytes(short tag, byte[] value) {
		return new OctetString(tag, value);
	    }
	});
	map.put(Short.class, new Converter() {
	    @Override
	    public OptionalParameter fromBytes(short tag, byte[] value) {
		return new Short(tag, value);
	    }
	});  
    }
    
    /**
     * Create SAR_MESSAGE_REF_NUM TLV instance.
     * 
     * @param value is the value.
     * @return the optional parameter.
     */
    public static OptionalParameter.Short newSarMsgRefNum(short value) {
        return new OptionalParameter.Short(Tag.SAR_MSG_REF_NUM, value);
    }
    
    /**
     * Create SAR_MESSAGE_REF_NUM TLV instance.
     * The value will cast automatically into short type.
     * 
     * @param value is the value.
     * @return the optional parameter.
     */
    public static OptionalParameter.Short newSarMsgRefNum(int value) {
        return newSarMsgRefNum((byte)value);
    }
    
    /**
     * Create SAR_SEGMENT_SEQNUM TLV instance.
     * 
     * @param value is the value.
     * @return the optional parameter.
     */
    public static OptionalParameter.Byte newSarSegmentSeqnum(byte value) {
        return new OptionalParameter.Byte(Tag.SAR_SEGMENT_SEQNUM, value);
    }
    
    /**
     * Create SAR_SEGMENT_SEQNUM TLV instance.
     * The value will cast automatically into byte type.
     * 
     * @param value is the value.
     * @return the optional parameter.
     */
    public static OptionalParameter.Byte newSarSegmentSeqnum(int value) {
        return newSarSegmentSeqnum((byte)value);
    }
    
    /**
     * Create SAR_TOTAL_SEGMENTS TLV instance.
     * 
     * @param value is the value.
     * @return the optional parameter.
     */
    public static OptionalParameter.Byte newSarTotalSegments(byte value) {
        return new OptionalParameter.Byte(Tag.SAR_TOTAL_SEGMENTS, value);
    }
    
    /**
     * Create SAR_TOTAL_SEGMENTS TLV instance.
     * The value will cast automatically into byte type.
     * 
     * @param value is the value.
     * @return the optional parameter.
     */
    public static OptionalParameter.Byte newSarTotalSegments(int value) {
        return newSarTotalSegments((byte)value);
    }

    /**
     * Deserialize all recognized tag code to {@link OptionalParameter} object.
     * Unrecognized will be classified as {@link COctetString}.
     * 
     * @param tagCode is the tag code.
     * @param content is the content.
     * @return the OptionalParameter object.
     */
    public static OptionalParameter deserialize(short tagCode, byte[] content) {
        Tag tag;
        try {
            tag = Tag.valueOf(tagCode);
        } catch (IllegalArgumentException e) {
            return new COctetString(tagCode, content);
        }

	Converter converter = map.get(tag.type);
	if (converter == null) {
	    throw new IllegalArgumentException("Unsupported tag: " + tagCode);
	}
	return converter.fromBytes(tagCode, content);
    }
}
