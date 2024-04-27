package no.pepega.oc.common.component;

import no.pepega.oc.OCSettings;
import no.pepega.oc.api.Network;
import no.pepega.oc.api.driver.DeviceInfo;
import no.pepega.oc.api.network.Node;
import no.pepega.oc.api.network.Visibility;
import no.pepega.oc.api.prefab.AbstractManagedEnvironment;

import java.util.HashMap;
import java.util.Map;

public class Memory extends AbstractManagedEnvironment implements DeviceInfo {
    private final int tier;

    public Memory(int tier) {
        this.tier = tier;
    }

    @Override
    public Node node() {
        return Network.newNode(this, Visibility.Neighbors).create();
    }

    private Map<String, String> deviceInfo = null;

    @Override
    public Map<String, String> getDeviceInfo() {
        if (deviceInfo == null) {
            deviceInfo = new HashMap<>();
            deviceInfo.put(DeviceAttribute.Class, DeviceClass.Memory);
            deviceInfo.put(DeviceAttribute.Description, "Memory bank");
            deviceInfo.put(DeviceAttribute.Vendor, OCSettings.Constants.DeviceInfo.DefaultVendor);
            deviceInfo.put(DeviceAttribute.Product, "Multipurpose RAM Type");
            deviceInfo.put(DeviceAttribute.Clock, String.valueOf(((int) OCSettings.callBudgets[tier]) * 1000));
        }
        return deviceInfo;
    }
}
