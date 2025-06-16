package xyz.sculas.sfmthermalcompat.common.mapper;

import ca.teamdman.sfm.common.capabilityprovidermapper.CapabilityProviderMapper;
import ca.teamdman.sfm.common.util.NotStored;
import cofh.lib.api.StorageGroup;
import cofh.lib.common.inventory.ManagedItemInv;
import cofh.thermal.lib.common.block.entity.AugmentableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AugmentableBlockEntityCapabilityProviderMapper implements CapabilityProviderMapper {
    @Override
    public @Nullable ICapabilityProvider getProviderFor(LevelAccessor level, @NotStored BlockPos pos) {
        var baseBlockEntity = level.getBlockEntity(pos);
        if (!(baseBlockEntity instanceof AugmentableBlockEntity augmentableBlockEntity)) return null;
        return new AugmentableCapabilityProvider(augmentableBlockEntity);
    }

    private record AugmentableCapabilityProvider(AugmentableBlockEntity blockEntity) implements ICapabilityProvider {
        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            var inventory = (ManagedItemInv) blockEntity.getItemInv();
            if (cap == ForgeCapabilities.ITEM_HANDLER && inventory.hasAccessibleSlots()) {
                return LazyOptional.of(() -> inventory.getHandler(StorageGroup.ALL)).cast();
            } else {
                return blockEntity.getCapability(cap, side);
            }
        }
    }
}
