package view;

import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;

public class GlobalLighting {

    public static Lighting get() {
        Light.Distant light = new Light.Distant();
        light.setElevation(45);
        light.setAzimuth(225);
        light.setColor(Color.WHITE);
        //light.setX(10);
        //light.setY(10);
        //light.setZ(45);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setDiffuseConstant(1.8); //0 < 1.0 < 2.0
        lighting.setSpecularConstant(0.8); // 0 < 0.3 < 2.0
        lighting.setSpecularExponent(20.0); // 0 < 20.0 < 40.0
        lighting.setSurfaceScale(5); // 0 < 1.5 < 10.0
        return lighting;
    }
}
