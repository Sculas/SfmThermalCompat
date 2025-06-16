package xyz.sculas.sfmthermalcompat;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(SfmThermalCompat.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SfmThermalCompat {
    public static final String MODID = "sfmthermalcompat";
    public static final Logger LOGGER = LogUtils.getLogger();
}
