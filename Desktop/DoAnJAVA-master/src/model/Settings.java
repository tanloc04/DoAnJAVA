/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author ADMIN
 */
public class Settings implements Serializable{
    private GraphicsQuality graphicsQuality;
    private boolean isAudioEnabled;
    private int audioLevel;

    public Settings() {
        graphicsQuality = GraphicsQuality.HIGH;
        isAudioEnabled = true;
        audioLevel = 100;
    }

    public Settings(GraphicsQuality graphicsQuality, boolean isAudioEnabled, int audioLevel) {
        this.graphicsQuality = graphicsQuality;
        this.isAudioEnabled = isAudioEnabled;
        this.audioLevel = audioLevel;
    }

    public GraphicsQuality getGraphicsQuality() {
        return graphicsQuality;
    }

    public void setGraphicsQuality( GraphicsQuality graphicsQuality) {
        this.graphicsQuality = graphicsQuality;
    }

    public boolean isAudioEnabled() {
        return isAudioEnabled;
    }

    public void setAudioEnabled( boolean isAudioEnabled) {
        this.isAudioEnabled = isAudioEnabled;
    }

    public int getAudioLevel() {
        return audioLevel;
    }

    public void setAudioLevel( int audioLevel) {
        this.audioLevel = audioLevel;
    }
}
