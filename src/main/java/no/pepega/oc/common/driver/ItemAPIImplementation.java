package no.pepega.oc.common.driver;

import net.minecraft.block.Block;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import no.pepega.oc.OCSettings;
import no.pepega.oc.OpenComputersRewritten;
import no.pepega.oc.api.Items;
import no.pepega.oc.api.detail.ItemAPI;
import no.pepega.oc.api.detail.ItemInfo;
import no.pepega.oc.api.fs.FileSystem;
import no.pepega.oc.common.block.util.ExtendedBlock;
import no.pepega.oc.common.block.util.ExtendedBlockItem;
import no.pepega.oc.common.init.OCDataComponentTypes;
import no.pepega.oc.common.item.util.ExtendedItem;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static net.minecraft.nbt.NbtElement.COMPOUND_TYPE;

public class ItemAPIImplementation implements ItemAPI {
    Map<String, ItemInfo> descriptors = new HashMap<>();
    Map<Object, String> names = new HashMap<>();

    Map<String, String> aliases = Map.of(
            "datacard", "datacard1",
            "wlancard", "wlancard2"
    );

    @Override
    public @Nullable ItemInfo get(String name) {
        return descriptors.get(name);
    }

    @Override
    public @Nullable ItemInfo get(ItemStack stack) {
        return names.get(getBlockOrItem(stack)) instanceof String s ? get(s) : null;
    }

    public Block registerBlockOnly(Block instance, String id) {
        if (!descriptors.containsKey(id)) {
            if (instance instanceof ExtendedBlock eb) {
                Registry.register(Registries.BLOCK, Identifier.of(OpenComputersRewritten.identifier, eb.registryName()), eb);
            }
            descriptors.put(id, new ItemInfo() {
                @Override
                public String name() {
                    return id;
                }

                @Override
                public Block block() {
                    return instance;
                }

                @Override
                public Item item() {
                    return null;
                }

                @Override
                public ItemStack createItemStack(int size) {
                    return ItemStack.EMPTY;
                }
            });
            names.put(instance, id);
        }
        return instance;
    }

    public Block registerBlock(ExtendedBlock instance) {
        return registerBlock(instance, instance.registryName());
    }

    public Block registerBlock(Block instance, String id) {
        if (!descriptors.containsKey(id)) {
            var itemInst = switch (instance) {
                case ExtendedBlock block -> {
                    Registry.register(Registries.BLOCK, Identifier.of(OpenComputersRewritten.identifier, block.registryName()), block);
                    yield Registry.register(Registries.ITEM, Identifier.of(OpenComputersRewritten.identifier, block.registryName()), new ExtendedBlockItem(block, new Item.Settings()));
                }
                default -> null;
            };
            descriptors.put(id, new ItemInfo() {
                @Override
                public String name() {
                    return id;
                }

                @Override
                public Block block() {
                    return instance;
                }

                @Override
                public Item item() {
                    return itemInst;
                }

                @Override
                public ItemStack createItemStack(int size) {
                    return new ItemStack(instance, size);
                }
            });
            names.put(instance, id);
        }
        return instance;
    }

    private Item registerItem(ExtendedItem instance) {
        return registerItem(instance, instance.registryName());
    }

    public Item registerItem(Item instance, String id) {
        if (!descriptors.containsKey(id)) {
            if (instance instanceof ExtendedItem ei) {
                Registry.register(Registries.ITEM, Identifier.of(OpenComputersRewritten.identifier, ei.registryName()), ei);
            }
            descriptors.put(id, new ItemInfo() {
                @Override
                public String name() {
                    return id;
                }

                @Override
                public Block block() {
                    return null;
                }

                @Override
                public Item item() {
                    return instance;
                }

                @Override
                public ItemStack createItemStack(int size) {
                    return new ItemStack(instance, size);
                }
            });
            names.put(instance, id);
        }
        return instance;
    }

    public ItemStack registerStack(ItemStack stack, String id) {
        var immutableStack = stack.copy();
        descriptors.put(id, new ItemInfo() {
            @Override
            public String name() {
                return id;
            }

            @Override
            public Block block() {
                return null;
            }

            @Override
            public Item item() {
                return immutableStack.getItem();
            }

            @Override
            public ItemStack createItemStack(int size) {
                var copy = immutableStack.copy();
                copy.setCount(size);
                return copy;
            }
        });
        return stack;
    }

    private Object getBlockOrItem(ItemStack stack) {
        if (stack.getItem() instanceof Item i) {
            if (i instanceof BlockItem bi)
                return bi.getBlock();
            else
                return i;
        }
        return null;
    }

    // ----------------------------------------------------------------------- //

    private final ArrayList<ItemStack> registeredItems = new ArrayList<>();

    @Override
    public ItemStack registerFloppy(String name, Identifier ident, DyeColor color, Callable<FileSystem> factory, boolean doRecipeCycling) {
        var data = new NbtCompound();
        data.putString(OCSettings.namespace + "fs.label", name);

        var stack = Items.get(OCSettings.Constants.ItemName.Floppy).createItemStack(1);
        var nbt = stack.getOrDefault(OCDataComponentTypes.LEGACY_NBT_COMPAT, NbtComponent.DEFAULT).copyNbt();
        nbt.put(OCSettings.namespace + "data", data);

        // Store this top level, so it won't get wiped on save.
        nbt.putString(OCSettings.namespace + "lootFactory", ident.toString());
        nbt.putInt(OCSettings.namespace + "color", color.getId());

        stack.set(OCDataComponentTypes.LEGACY_NBT_COMPAT, NbtComponent.of(nbt));

        registeredItems.add(stack);

        return stack.copy();
    }

    @Override
    public ItemStack registerEEPROM(String name, byte[] code, byte[] data, boolean readonly) {
        var stack = get(no.pepega.oc.common.init.Items.eeprom.registryName()).createItemStack(1);
        var itemData = stack.getOrDefault(OCDataComponentTypes.LEGACY_NBT_COMPAT, NbtComponent.DEFAULT).copyNbt();
        NbtCompound nbt;
        if (itemData.contains("data", COMPOUND_TYPE)) {
            nbt = itemData.getCompound("data");
        } else {
            nbt = new NbtCompound();
        }
        if (name != null) {
            nbt.putString(OCSettings.namespace + "label", name.trim().substring(0, Math.min(24, name.trim().length())));
        }
        if (code != null) {
            nbt.putByteArray(OCSettings.namespace + "eeprom", Arrays.copyOf(code, OCSettings.eepromSize));
        }
        if (data != null) {
            nbt.putByteArray(OCSettings.namespace + "userdata", Arrays.copyOf(data, OCSettings.eepromDataSize));
        }
        nbt.putBoolean(OCSettings.namespace + "readonly", readonly);

        itemData.put("data", nbt);

        stack.set(OCDataComponentTypes.LEGACY_NBT_COMPAT, NbtComponent.of(nbt));

        registeredItems.add(stack);

        return stack.copy();
    }

    public void init() {
        initMaterials();
        initTools();
        initComponents();
        initCards();
        initUpgrades();
        initStorage();
        initSpecial();

        aliases.forEach((k, v) -> {
            descriptors.putIfAbsent(k, descriptors.get(v));
        });
    }

    public void initMaterials() {

    }

    public void initTools() {

    }

    public void initComponents() {
        registerItem(no.pepega.oc.common.init.Items.cpu0);
        registerItem(no.pepega.oc.common.init.Items.cpu1);
        registerItem(no.pepega.oc.common.init.Items.cpu2);

        registerItem(no.pepega.oc.common.init.Items.memory0);
        registerItem(no.pepega.oc.common.init.Items.memory1);
        registerItem(no.pepega.oc.common.init.Items.memory2);
        registerItem(no.pepega.oc.common.init.Items.memory3);
        registerItem(no.pepega.oc.common.init.Items.memory4);
        registerItem(no.pepega.oc.common.init.Items.memory5);

        registerItem(no.pepega.oc.common.init.Items.apu0);
        registerItem(no.pepega.oc.common.init.Items.apu1);
        registerItem(no.pepega.oc.common.init.Items.apucreative);
    }

    public void initCards() {

    }

    public void initUpgrades() {

    }

    public void initStorage() {
        registerItem(no.pepega.oc.common.init.Items.eeprom);

        // Lua BIOS
        ItemStack luaBios;
        try {
            var code = new byte[4096];
            OpenComputersRewritten.class.getResourceAsStream(OCSettings.scriptPath + "bios.lua").read(code);
            luaBios = registerEEPROM("EEPROM (Lua BIOS)", code, null, false);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        registerStack(luaBios, OCSettings.Constants.ItemName.LuaBios);
    }

    public void initSpecial() {

    }

}
