package xyz.sculas.sfmthermalcompat.mixin;

import ca.teamdman.sfm.common.capabilityprovidermapper.CapabilityProviderMapper;
import ca.teamdman.sfm.common.registry.SFMCapabilityProviderMappers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.sculas.sfmthermalcompat.SfmThermalCompat;
import xyz.sculas.sfmthermalcompat.common.mapper.AugmentableBlockEntityCapabilityProviderMapper;

@Mixin(value = SFMCapabilityProviderMappers.class, remap = false)
public abstract class SfmCapabilityProviderMappersMixin {
    @Final
    @Shadow
    private static DeferredRegister<CapabilityProviderMapper> REGISTERER;

    @Inject(method = "register", at = @At("HEAD"), remap = false)
    private static void register(IEventBus bus, CallbackInfo ci) {
        SfmThermalCompat.LOGGER.info("Injecting capability provider mappers into SFM...");
        REGISTERER.register("thermal/augment", AugmentableBlockEntityCapabilityProviderMapper::new);
    }
}
