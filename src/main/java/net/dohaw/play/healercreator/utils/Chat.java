package net.dohaw.play.healercreator.utils;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class Chat {

    public static Text colorMsg(String s){
        return TextSerializers.FORMATTING_CODE.deserialize(TextSerializers.FORMATTING_CODE.serialize(Text.of(s)));
    }

}
