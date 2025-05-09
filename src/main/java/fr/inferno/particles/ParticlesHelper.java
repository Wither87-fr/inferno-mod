package fr.inferno.particles;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.options.WorldParticleOptions;

import java.awt.*;
import java.util.function.Supplier;

public class ParticlesHelper {
    private static final double VITESSE_ROTATION = 15000.0; // Plus grand = plus lent, plus petit = plus rapide


    public static void createFocusedEnergyRayon(Level level, Vec3 centerPos, Vec3 northPos, Vec3 southPos, Vec3 eastPos, Vec3 westPos, Color colorStart, Color colorEnd) {

        // Point central
        createDefaultParticle(LodestoneParticleRegistry.STAR_PARTICLE, centerPos, colorEnd, level);

        // Sources sur les poles
        createDefaultParticle(LodestoneParticleRegistry.WISP_PARTICLE, westPos, colorStart, level);
        createDefaultParticle(LodestoneParticleRegistry.WISP_PARTICLE, northPos, colorStart, level);
        createDefaultParticle(LodestoneParticleRegistry.WISP_PARTICLE, southPos, colorStart, level);
        createDefaultParticle(LodestoneParticleRegistry.WISP_PARTICLE, eastPos, colorStart, level);

        // Rayons vers le centre
        createRayonVersLeCentre(level, westPos, centerPos, colorStart, colorEnd);
        createRayonVersLeCentre(level, northPos, centerPos, colorStart, colorEnd);
        createRayonVersLeCentre(level, southPos, centerPos, colorStart, colorEnd);
        createRayonVersLeCentre(level, eastPos, centerPos, colorStart, colorEnd);

    }


    private static void createRayonVersLeCentre(Level level, Vec3 depart, Vec3 fin, Color colorStart, Color colorEnd) {
        int particlesParLigne = 15;
        double time = (System.currentTimeMillis() % 1000) / 1000.0; // Cycle sur 1 seconde

        for (int i = 0; i < particlesParLigne; i++) {
            double baseRatio = (double) i / (particlesParLigne - 1);
            // Ajout du décalage temporel pour créer le mouvement
            double ratio = (baseRatio + time) % 1.0;

            // Position interpolée
            double x = depart.x + (fin.x - depart.x) * ratio;
            double y = depart.y + (fin.y - depart.y) * ratio;
            double z = depart.z + (fin.z - depart.z) * ratio;
            Vec3 pos = new Vec3(x, y, z);

            // Interpolation de la couleur
            int r = (int) (colorStart.getRed() + (colorEnd.getRed() - colorStart.getRed()) * ratio);
            int g = (int) (colorStart.getGreen() + (colorEnd.getGreen() - colorStart.getGreen()) * ratio);
            int b = (int) (colorStart.getBlue() + (colorEnd.getBlue() - colorStart.getBlue()) * ratio);
            Color colorInterpolee = new Color(r, g, b);

            // Ajustement de la taille en fonction de la position
            float scale = 0.015f * (1 + (float)Math.sin(ratio * Math.PI) * 0.5f);

            createSimpleParticle(
                    LodestoneParticleRegistry.STAR_PARTICLE,
                    scale, 0,
                    0.8f, 0,
                    colorInterpolee,
                    3.0f,
                    Easing.SINE_IN_OUT,
                    1,
                    true,
                    level,
                    pos
            );
        }
    }





    private static void createDefaultParticle(Supplier<? extends ParticleType<WorldParticleOptions>> type, Vec3 pos, Color color, Level level) {
        createSimpleParticle(type, 0.1f, 0, 0.9f, 0, color, 5.5f, Easing.EXPO_IN_OUT, 1, true, level, pos);
    }
    private static void createSimpleParticle(Supplier<? extends ParticleType<WorldParticleOptions>> type,
                                              float startScaleValue, float endScaleValue,
                                              float startTransparencyValue, float endTransparencyValue,
                                              Color color, float colorCoefficient, Easing easing,
                                              int lifeTime,
                                              boolean noClip,
                                              Level level, Vec3 pos) {
        WorldParticleBuilder builder = WorldParticleBuilder.create(type)
                .setScaleData(GenericParticleData.create(startScaleValue, endScaleValue).build())
                .setTransparencyData(GenericParticleData.create(startTransparencyValue, endTransparencyValue).build())
                .setColorData(ColorParticleData.create(color, color)
                        .setCoefficient(colorCoefficient)
                        .setEasing(easing)
                        .build())
                .setLifetime(lifeTime);

        if(noClip) {
            builder.enableNoClip();
        }

        builder.spawn(level, pos.x, pos.y, pos.z);
    }

    public static void createRotatingPentacle(Level level, Vec3 pos, Color color) {
        double rotationAngle = (System.currentTimeMillis() % VITESSE_ROTATION) / VITESSE_ROTATION * Math.PI * 2;

        int nombrePointsEtoile = 5;
        int particlesParCercle = 90;
        double rayonCercle = 6.0;
        double rayonEtoileExt = 5.8;
        double rayonEtoileInt = 1.8;

        // Cercle extérieur
        for (int i = 0; i < particlesParCercle; i++) {
            double angle = Math.PI * 2 * i / particlesParCercle + rotationAngle;
            double x = pos.x + rayonCercle * Math.cos(angle);
            double z = pos.z + rayonCercle * Math.sin(angle);

            Vec3 pos2 = new Vec3(x, pos.y + 0.4, z);
            createSimpleParticle(LodestoneParticleRegistry.WISP_PARTICLE, 0.3f, 0, 0.9f, 0, color, 5.5f, Easing.EXPO_IN_OUT, 1, true, level, pos2);
        }

        // Étoile
        for (int i = 0; i < nombrePointsEtoile * 2; i++) {
            double angle = Math.PI * 2 * i / (nombrePointsEtoile * 2) + rotationAngle;
            double nextAngle = Math.PI * 2 * ((i + 1) % (nombrePointsEtoile * 2)) / (nombrePointsEtoile * 2) + rotationAngle;

            double rayon = (i % 2 == 0) ? rayonEtoileExt : rayonEtoileInt;
            double nextRayon = ((i + 1) % 2 == 0) ? rayonEtoileExt : rayonEtoileInt;

            double x = pos.x + rayon * Math.cos(angle);
            double z = pos.z + rayon * Math.sin(angle);
            double nextX = pos.x + nextRayon * Math.cos(nextAngle);
            double nextZ = pos.z + nextRayon * Math.sin(nextAngle);

            int particlesParLigne = 20;
            for (int j = 0; j < particlesParLigne; j++) {
                double ratio = (double) j / particlesParLigne;
                double lineX = x + (nextX - x) * ratio;
                double lineZ = z + (nextZ - z) * ratio;


                Vec3 posStar = new Vec3(lineX, pos.y + 0.4, lineZ);
                createSimpleParticle(LodestoneParticleRegistry.STAR_PARTICLE, 0.25f, 0, 0.8f, 0, color, 1.2f, Easing.SINE_IN_OUT, 1, true, level, posStar);
            }
        }
    }
}
