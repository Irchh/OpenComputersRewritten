package no.pepega.oc.util;

import li.cil.repack.org.luaj.vm2.LuaValue;
import no.pepega.oc.OpenComputersRewritten;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class LuaJHelper {
    public static LuaValue toLuaValue(Object v) {
        return switch (v) {
            case null -> LuaValue.NIL;
            case java.lang.Boolean value -> LuaValue.valueOf(value);
            case java.lang.Byte value -> LuaValue.valueOf(value);
            case java.lang.Character value -> LuaValue.valueOf(String.valueOf(value));
            case java.lang.Short value -> LuaValue.valueOf(value);
            case java.lang.Integer value -> LuaValue.valueOf(value);
            case java.lang.Long value -> LuaValue.valueOf(value);
            case java.lang.Float value -> LuaValue.valueOf(value);
            case java.lang.Double value -> LuaValue.valueOf(value);
            case java.lang.String value -> LuaValue.valueOf(value);
            case List value -> {
                boolean isByteList = true;
                byte[] byteList = new byte[value.size()];
                int byteIndex = 0;
                for (Object obj: value) {
                    if (!(obj instanceof Byte)) {
                        isByteList = false;
                        break;
                    }
                    byteList[byteIndex] = (Byte) obj;
                }
                if (isByteList) {
                    yield LuaValue.valueOf(byteList);
                } else {
                    yield toLuaList(value);
                }
            }
            /*case value instanceof Value value -> {
                if (Settings.get.allowUserdata)
                    yield LuaValue.userdataOf(value);
                else
                    yield LuaValue.NIL;
            };*/
            //case value: Product -> toLuaList(v.productIterator.toIterable);
            //case value: Seq[_] -> toLuaList(value);
            case java.util.Map value -> toLuaTable(value);
            default -> {
                OpenComputersRewritten.log.warn("Tried to push an unsupported value of type to Lua: " + v.getClass().getName() + ".");
                yield LuaValue.NIL;
            }
        };
    }

    public static LuaValue toLuaList(Iterable<Object> value) {
        List<LuaValue> list = new ArrayList<>();
        value.forEach(o -> list.add(toLuaValue(o)));
        return LuaValue.listOf(list.toArray(LuaValue[]::new));
    }

    public static <K, V> LuaValue toLuaTable(Map<K, V> value) {
        List<LuaValue> list = new ArrayList<>();
        value.forEach((k, v) -> {
            list.add(toLuaValue(k));
            list.add(toLuaValue(v));
        });
        return LuaValue.tableOf(list.toArray(LuaValue[]::new));
    }
}
