package no.pepega.oc.common.component;

import com.google.common.hash.Hashing;
import net.minecraft.nbt.NbtCompound;
import no.pepega.oc.OCSettings;
import no.pepega.oc.api.Network;
import no.pepega.oc.api.driver.DeviceInfo;
import no.pepega.oc.api.machine.Arguments;
import no.pepega.oc.api.machine.Callback;
import no.pepega.oc.api.machine.Context;
import no.pepega.oc.api.network.Connector;
import no.pepega.oc.api.network.Node;
import no.pepega.oc.api.network.Visibility;
import no.pepega.oc.api.prefab.AbstractManagedEnvironment;

import java.util.*;

public class EEPROM extends AbstractManagedEnvironment implements DeviceInfo {
    @Override
    public Connector node() {
        return Network.newNode(this, Visibility.Neighbors)
                .withComponent("eeprom", Visibility.Neighbors)
                .withConnector()
                .create();
    }

    private byte[] codeData = new byte[OCSettings.eepromSize];

    private byte[] volatileData = new byte[OCSettings.eepromDataSize];

    private boolean readonly = false;

    private String label = "eeprom";

    public String checksum() {
        return Hashing.crc32().hashBytes(codeData).toString();
    }

    // ----------------------------------------------------------------------- //

    private Map<String, String> deviceInfo = null;

    @Override
    public Map<String, String> getDeviceInfo() {
        if (deviceInfo == null) {
            deviceInfo = new HashMap<>();
            deviceInfo.put(DeviceAttribute.Class, DeviceClass.Memory);
            deviceInfo.put(DeviceAttribute.Description, "EEPROM");
            deviceInfo.put(DeviceAttribute.Vendor, OCSettings.Constants.DeviceInfo.DefaultVendor);
            deviceInfo.put(DeviceAttribute.Product, "FlashStick2k");
            deviceInfo.put(DeviceAttribute.Capacity, String.valueOf(OCSettings.eepromSize));
            deviceInfo.put(DeviceAttribute.Size, String.valueOf(OCSettings.eepromSize));
        }
        return deviceInfo;
    }

    // ----------------------------------------------------------------------- //

    @Callback(direct = true, doc = "function():string -- Get the currently stored byte array.")
    public List<Object> get(Context context, Arguments args) {
        var list = new ArrayList<>();
        return Arrays.asList(codeData);
    }

    @Callback(doc = "function(data:string) -- Overwrite the currently stored byte array.")
    public List<Object> set(Context context, Arguments args) {
        if (readonly) {
            return Arrays.asList(null, "storage is readonly");
        }
        if (!node().tryChangeBuffer(-OCSettings.eepromWriteCost)) {
            return Arrays.asList(null, "not enough energy");
        }
        var newData = args.optByteArray(0, new byte[0]);
        if (newData.length > OCSettings.eepromSize) throw new IllegalArgumentException("not enough space");
        codeData = newData;
        context.pause(2); // deliberately slow to discourage use as normal storage medium
        return null;
    }

    @Callback(direct = true, doc = "function():string -- Get the label of the EEPROM.")
    public List<Object> getLabel(Context context, Arguments args) {
        return Collections.singletonList(label);
    }

    @Callback(doc = "function(data:string):string -- Set the label of the EEPROM.")
    public List<Object> setLabel(Context context, Arguments args) {
        if (readonly) {
            return Arrays.asList(null, "storage is readonly");
        }
        var tmp_label = args.optString(0, "EEPROM").trim();
        label = tmp_label.substring(0, Math.min(24, tmp_label.length()));
        if (label.isEmpty())
            label = "EEPROM";
        return Collections.singletonList(label);
    }

    @Callback(direct = true, doc = "function():number -- Get the storage capacity of this EEPROM.")
    public List<Object> getSize(Context context, Arguments args) {
        return Collections.singletonList(OCSettings.eepromSize);
    }

    @Callback(direct = true, doc = "function():string -- Get the checksum of the data on this EEPROM.")
    public List<Object> getChecksum(Context context, Arguments args) {
        return Collections.singletonList(checksum());
    }

    @Callback(direct = true, doc = "function(checksum:string):boolean -- Make this EEPROM readonly if it isn't already. This process cannot be reversed!")
    public List<Object> makeReadonly(Context context, Arguments args) {
        if (args.checkString(0) == checksum()) {
            readonly = true;
            return Collections.singletonList(true);
        }
        return Arrays.asList(null, "incorrect checksum");
    }

    @Callback(direct = true, doc = "function():number -- Get the storage capacity of this EEPROM.")
    public List<Object> getDataSize(Context context, Arguments args) {
        return Collections.singletonList(OCSettings.eepromDataSize);
    }

    @Callback(direct = true, doc = "function():string -- Get the currently stored byte array.")
    public List<Object> getData(Context context, Arguments args) {
        return Collections.singletonList(volatileData);
    }

    // ----------------------------------------------------------------------- //

    private final String EEPROMTag = OCSettings.namespace + "eeprom";
    private final String LabelTag = OCSettings.namespace + "label";
    private final String ReadonlyTag = OCSettings.namespace + "readonly";
    private final String UserdataTag = OCSettings.namespace + "userdata";

    @Override
    public void loadData(NbtCompound nbt) {
        super.loadData(nbt);
        codeData = nbt.getByteArray(EEPROMTag);
        if (nbt.contains(LabelTag)) {
            label = nbt.getString(LabelTag);
        }
        readonly = nbt.getBoolean(ReadonlyTag);
        volatileData = nbt.getByteArray(UserdataTag);
    }

    @Override
    public void saveData(NbtCompound nbt) {
        super.saveData(nbt);
        nbt.putByteArray(EEPROMTag, codeData);
        nbt.putString(LabelTag, label);
        nbt.putBoolean(ReadonlyTag, readonly);
        nbt.putByteArray(UserdataTag, volatileData);
    }
}
