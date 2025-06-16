package xyz.sculas.sfmthermalcompat.mixin;

import ca.teamdman.sfm.common.capabilityprovidermapper.CapabilityProviderMapper;
import ca.teamdman.sfm.common.registry.SFMCapabilityProviderMappers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.sculas.sfmthermalcompat.common.mapper.AugmentableBlockEntityCapabilityProviderMapper;

import java.util.List;

@Mixin(value = SFMCapabilityProviderMappers.class, remap = false)
public abstract class SfmCapabilityProviderMappersMixin {
    @Unique
    private static final List<CapabilityProviderMapper> sfmThermalCompat$MAPPERS = List.of(
            new AugmentableBlockEntityCapabilityProviderMapper()
    );

    @Inject(method = "discoverCapabilityProvider", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraftforge/registries/IForgeRegistry;getValues()Ljava/util/Collection;"), cancellable = true, remap = false)
    private static void discoverCapabilityProvider(Level level, BlockPos pos, CallbackInfoReturnable<ICapabilityProvider> cir) {
        for (var mapper : sfmThermalCompat$MAPPERS) {
            var provider = mapper.getProviderFor(level, pos);
            if (provider != null) {
                cir.setReturnValue(provider);
                return;
            }
        }
    }
}
