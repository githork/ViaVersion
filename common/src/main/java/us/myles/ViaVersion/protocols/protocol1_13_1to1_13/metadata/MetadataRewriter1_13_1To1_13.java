package us.myles.ViaVersion.protocols.protocol1_13_1to1_13.metadata;

import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.entities.Entity1_13Types;
import us.myles.ViaVersion.api.entities.Entity1_13Types.EntityType;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_13;
import us.myles.ViaVersion.api.rewriters.MetadataRewriter;
import us.myles.ViaVersion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import us.myles.ViaVersion.protocols.protocol1_13_1to1_13.packets.InventoryPackets;

import java.util.List;

public class MetadataRewriter1_13_1To1_13 extends MetadataRewriter<Entity1_13Types.EntityType> {

    @Override
    protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) {
        // 1.13 changed item to flat item (no data)
        if (metadata.getMetaType() == MetaType1_13.Slot) {
            InventoryPackets.toClient((Item) metadata.getValue());
        } else if (metadata.getMetaType() == MetaType1_13.BlockID) {
            // Convert to new block id
            int data = (int) metadata.getValue();
            metadata.setValue(Protocol1_13_1To1_13.getNewBlockStateId(data));
        }

        if (type == null) return;

        if (type.isOrHasParent(EntityType.MINECART_ABSTRACT) && metadata.getId() == 9) {
            // New block format
            int data = (int) metadata.getValue();
            metadata.setValue(Protocol1_13_1To1_13.getNewBlockStateId(data));
        }

        if (type.isOrHasParent(EntityType.ABSTRACT_ARROW) && metadata.getId() >= 7) {
            metadata.setId(metadata.getId() + 1); // New shooter UUID
        }
    }

}