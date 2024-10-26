package org.reuac.commandwhitelist;

import java.util.List;

class WorldCommandSettings {
    private final List<String> whitelist;
    private final boolean enabledMessage;
    private final List<String> notAllowTip;

    public WorldCommandSettings(List<String> whitelist, boolean enabledMessage, List<String> notAllowTip) {
        this.whitelist = whitelist;
        this.enabledMessage = enabledMessage;
        this.notAllowTip = notAllowTip;
    }

    public List<String> getWhitelist() {
        return whitelist;
    }

    public boolean isEnabledMessage() {
        return enabledMessage;
    }

    public List<String> getNotAllowTip() {
        return notAllowTip;
    }
}