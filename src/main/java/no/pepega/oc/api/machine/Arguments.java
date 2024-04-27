package no.pepega.oc.api.machine;


import net.minecraft.item.ItemStack;

import java.util.Map;

/**
 * This interface provides access to arguments passed to a {@link Callback}.
 * <br>
 * It allows checking for the presence of arguments in a uniform manner, taking
 * care of proper type checking based on what can be passed along by Lua.
 * <br>
 * Note that integer values fetched this way are actually double values that
 * have been truncated. So if a Lua program passes <tt>1.9</tt> and you do a
 * <tt>checkInteger</tt> you'll get a <tt>1</tt>.
 * <br>
 * The indexes passed to the various functions start at zero, i.e. to get the
 * first argument you would use <tt>checkAny(0)</tt>. This is worth mentioning
 * because Lua starts its indexes at one.
 */
public interface Arguments extends Iterable<Object> {
    /**
     * The total number of arguments that were passed to the function.
     */
    int count();

    /**
     * Get whatever is at the specified index.
     * <br>
     * Throws an error if there are too few arguments.
     * <br>
     * The returned object will be one of the following, based on the conversion
     * performed internally:
     * <ul>
     * <li><tt>null</tt> if the Lua value was <tt>nil</tt>.</li>
     * <li><tt>java.lang.Boolean</tt> if the Lua value was a boolean.</li>
     * <li><tt>java.lang.Double</tt> if the Lua value was a number.</li>
     * <li><tt>byte[]</tt> if the Lua value was a string.</li>
     * </ul>
     *
     * @param index the index from which to get the argument.
     * @return the raw value at that index.
     * @throws IllegalArgumentException if there is no argument at that index.
     */
    Object checkAny(int index);

    /**
     * Try to get a boolean value at the specified index.
     * <br>
     * Throws an error if there are too few arguments.
     *
     * @param index the index from which to get the argument.
     * @return the boolean value at the specified index.
     * @throws IllegalArgumentException if there is no argument at that index,
     *                                  or if the argument is not a boolean.
     */
    boolean checkBoolean(int index);

    /**
     * Try to get an integer value at the specified index.
     * <br>
     * Throws an error if there are too few arguments.
     *
     * @param index the index from which to get the argument.
     * @return the integer value at the specified index.
     * @throws IllegalArgumentException if there is no argument at that index,
     *                                  or if the argument is not a number.
     */
    int checkInteger(int index);

    /**
     * Try to get a long value at the specified index.
     * <br>
     * Throws an error if there are too few arguments.
     *
     * @param index the index from which to get the argument.
     * @return the long value at the specified index.
     * @throws IllegalArgumentException if there is no argument at that index,
     *                                  or if the argument is not a number.
     * @since OpenComputers 1.8.0
     */
    long checkLong(int index);

    /**
     * Try to get a double value at the specified index.
     * <br>
     * Throws an error if there are too few arguments.
     *
     * @param index the index from which to get the argument.
     * @return the double value at the specified index.
     * @throws IllegalArgumentException if there is no argument at that index,
     *                                  or if the argument is not a number.
     */
    double checkDouble(int index);

    /**
     * Try to get a string value at the specified index.
     * <br>
     * Throws an error if there are too few arguments.
     * <br>
     * This will actually check for a byte array and convert it to a string
     * using UTF-8 encoding.
     *
     * @param index the index from which to get the argument.
     * @return the boolean value at the specified index.
     * @throws IllegalArgumentException if there is no argument at that index,
     *                                  or if the argument is not a string.
     */
    String checkString(int index);

    /**
     * Try to get a byte array at the specified index.
     * <br>
     * Throws an error if there are too few arguments.
     *
     * @param index the index from which to get the argument.
     * @return the byte array at the specified index.
     * @throws IllegalArgumentException if there is no argument at that index,
     *                                  or if the argument is not a byte array.
     */
    byte[] checkByteArray(int index);

    /**
     * Try to get a table at the specified index.
     * <br>
     * Throws an error if there are too few arguments.
     *
     * @param index the index from which to get the argument.
     * @return the table at the specified index.
     * @throws IllegalArgumentException if there is no argument at that index,
     *                                  or if the argument is not a table.
     */
    Map checkTable(int index);

    /**
     * Try to get an item stack representation at the specified index.
     * <br>
     * This is a utility method provided to convert tables to item stacks, with
     * the tables being of a compatible format to that of tables generated by
     * the built-in item stack converter. In particular, this takes care of
     * restoring NBT data attached to the item stack.
     * <br>
     * Throws an error if there are too few arguments.
     * <br>
     * <em>Important</em>: usually you will not want to be using this. Some
     * items require NBT information to fully describe them, and by default
     * this information is not returned to underlying architectures when
     * item stacks are returned from callbacks. This means the scripts can
     * usually not provide this full information, so the roundtrip callback&rarr;
     * script&rarr;callback will be incomplete.
     * <br>
     * Instead, please make use of the {@link no.pepega.oc.api.internal.Database}
     * component to get complete item stack descriptors.
     *
     * @param index the index from which to get the argument.
     * @return the item stack at the specified index.
     */
    ItemStack checkItemStack(int index);

    /**
     * Get whatever is at the specified index.
     * <br>
     * Return the specified default value if there is no such element, behaves
     * like {@link #checkAny(int)} otherwise.
     * <br>
     * The returned object will be one of the following, based on the conversion
     * performed internally:
     * <ul>
     * <li><tt>null</tt> if the Lua value was <tt>nil</tt>.</li>
     * <li><tt>java.lang.Boolean</tt> if the Lua value was a boolean.</li>
     * <li><tt>java.lang.Double</tt> if the Lua value was a number.</li>
     * <li><tt>byte[]</tt> if the Lua value was a string.</li>
     * </ul>
     *
     * @param index the index from which to get the argument.
     * @return the raw value at that index.
     */
    Object optAny(int index, Object def);

    /**
     * Try to get a boolean value at the specified index.
     * <br>
     * Return the specified default value if there is no such element, behaves
     * like {@link #checkBoolean(int)} otherwise.
     *
     * @param index the index from which to get the argument.
     * @return the boolean value at the specified index.
     * @throws IllegalArgumentException if the argument exists and is not a boolean.
     */
    boolean optBoolean(int index, boolean def);

    /**
     * Try to get an integer value at the specified index.
     * <br>
     * Return the specified default value if there is no such element, behaves
     * like {@link #checkInteger(int)} otherwise.
     *
     * @param index the index from which to get the argument.
     * @return the integer value at the specified index.
     * @throws IllegalArgumentException if the argument exists but is not a number.
     */
    int optInteger(int index, int def);

    /**
     * Try to get a long value at the specified index.
     * <br>
     * Return the specified default value if there is no such element, behaves
     * like {@link #checkLong(int)} otherwise.
     *
     * @param index the index from which to get the argument.
     * @return the long value at the specified index.
     * @throws IllegalArgumentException if the argument exists but is not a number.
     * @since OpenComputers 1.8.0
     */
    long optLong(int index, long def);

    /**
     * Try to get a double value at the specified index.
     * <br>
     * Return the specified default value if there is no such element, behaves
     * like {@link #checkDouble(int)} otherwise.
     *
     * @param index the index from which to get the argument.
     * @return the double value at the specified index.
     * @throws IllegalArgumentException if the argument exists and is not a number.
     */
    double optDouble(int index, double def);

    /**
     * Try to get a string value at the specified index.
     * <br>
     * Return the specified default value if there is no such element, behaves
     * like {@link #checkString(int)} otherwise.
     * <br>
     * This will actually check for a byte array and convert it to a string
     * using UTF-8 encoding.
     *
     * @param index the index from which to get the argument.
     * @return the boolean value at the specified index.
     * @throws IllegalArgumentException if the argument exists and is not a string.
     */
    String optString(int index, String def);

    /**
     * Try to get a byte array at the specified index.
     * <br>
     * Return the specified default value if there is no such element, behaves
     * like {@link #checkByteArray(int)} otherwise.
     *
     * @param index the index from which to get the argument.
     * @return the byte array at the specified index.
     * @throws IllegalArgumentException if the argument exists and is not a byte array.
     */
    byte[] optByteArray(int index, byte[] def);

    /**
     * Try to get a table at the specified index.
     * <br>
     * Return the specified default value if there is no such element, behaves
     * like {@link #checkTable(int)} otherwise.
     *
     * @param index the index from which to get the argument.
     * @return the table at the specified index.
     * @throws IllegalArgumentException if the argument exists and is not a table.
     */
    Map optTable(int index, Map def);

    /**
     * Try to get an item stack at the specified index.
     * <br>
     * Return the specified default value if there is no such element, behaves
     * like {@link #checkItemStack(int)} otherwise.
     *
     * @param index the index from which to get the argument.
     * @return the item stack at the specified index.
     * @throws IllegalArgumentException if the argument exists and is not an item stack.
     */
    ItemStack optItemStack(int index, ItemStack def);

    /**
     * Tests whether the argument at the specified index is a boolean value.
     * <br>
     * This will return false if there is <em>no</em> argument at the specified
     * index, i.e. if there are too few arguments.
     *
     * @param index the index to check.
     * @return true if the argument is a boolean; false otherwise.
     */
    boolean isBoolean(int index);

    /**
     * Tests whether the argument at the specified index is an integer value.
     * <br>
     * This will return false if there is <em>no</em> argument at the specified
     * index, i.e. if there are too few arguments.
     *
     * @param index the index to check.
     * @return true if the argument is an integer; false otherwise.
     */
    boolean isInteger(int index);

    /**
     * Tests whether the argument at the specified index is a long value.
     * <br>
     * This will return false if there is <em>no</em> argument at the specified
     * index, i.e. if there are too few arguments.
     *
     * @param index the index to check.
     * @return true if the argument is a long; false otherwise.
     */
    boolean isLong(int index);

    /**
     * Tests whether the argument at the specified index is a double value.
     * <br>
     * This will return false if there is <em>no</em> argument at the specified
     * index, i.e. if there are too few arguments.
     *
     * @param index the index to check.
     * @return true if the argument is a double; false otherwise.
     */
    boolean isDouble(int index);

    /**
     * Tests whether the argument at the specified index is a string value.
     * <br>
     * This will return false if there is <em>no</em> argument at the specified
     * index, i.e. if there are too few arguments.
     *
     * @param index the index to check.
     * @return true if the argument is a string; false otherwise.
     */
    boolean isString(int index);

    /**
     * Tests whether the argument at the specified index is a byte array.
     * <br>
     * This will return false if there is <em>no</em> argument at the specified
     * index, i.e. if there are too few arguments.
     *
     * @param index the index to check.
     * @return true if the argument is a byte array; false otherwise.
     */
    boolean isByteArray(int index);

    /**
     * Tests whether the argument at the specified index is a table.
     * <br>
     * This will return false if there is <em>no</em> argument at the specified
     * index, i.e. if there are too few arguments.
     *
     * @param index the index to check.
     * @return true if the argument is a table; false otherwise.
     */
    boolean isTable(int index);

    /**
     * Tests whether the argument at the specified index is an item stack.
     * <br>
     * This will return false if there is <em>no</em> argument at the specified
     * index, i.e. if there are too few arguments.
     *
     * @param index the index to check.
     * @return true if the argument is an item stack; false otherwise.
     */
    boolean isItemStack(int index);

    /**
     * Converts the argument list to a standard Java array, converting byte
     * arrays to strings automatically, since this is usually what others
     * want - if you need the actual raw byte arrays, don't use this method!
     *
     * @return an array containing all arguments.
     */
    Object[] toArray();
}
