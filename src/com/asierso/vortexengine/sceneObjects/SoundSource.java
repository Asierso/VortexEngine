/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asierso.vortexengine.sceneObjects;

import com.asierso.vortexengine.miscellaneous.Startable;
import com.asierso.vortexengine.window.Window;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;

/**
 *
 * @author sobblaas
 */
public class SoundSource extends GameObject implements Startable {

    //Sound object
    private final Sound sound = new Sound();
    
    //Soundtrack list
    private final ArrayList<Soundtrack> soundtracks = new ArrayList<>();
    
    //Load modes
    public enum LoadModes {
        FILE, STREAM
    };

    public final float getPitch() {
        return sound.getPitch();
    }

    public final void setPitch(float pitch) {
        sound.setPitch(pitch);
    }

    public final boolean isLoop() {
        return sound.isLoop();
    }

    public final void setLoop(boolean isLoop) {
        sound.setLoop(isLoop);
    }

    public final float getVolume() {
        return sound.getVolume();
    }

    public final void setVolume(float volume) {
        sound.setVolume(volume);
    }

    @Override
    public void start() {
        sound.play();
    }

    @Override
    public void stop() {
        sound.stop();
    }

    public void addSoundtrack(String name,Object source,LoadModes mode) throws IOException {
        Soundtrack st = new Soundtrack(this,name);
        switch(mode){
            case FILE:
                st.loadFromFile((Path)source);
                break;
            case STREAM:
                st.loadFromStream((InputStream)source);
                break;
        }
        soundtracks.add(st);
    }
    
    public Soundtrack getSoundtrack(String name){
        return soundtracks.stream()
                .filter(obj->(obj.getName().equals(name)))
                .findFirst().get();
    }
    
    public Soundtrack getSoundtrack(int id){
        return soundtracks.get(id);
    }
    
    protected void select(Soundtrack target){
        sound.setBuffer(target);
    }
    
    public SoundSource(){
        sound.setVolume(50f);
        sound.setLoop(false);
        sound.setPitch(1.0f);
    }
    
    public class Soundtrack extends SoundBuffer{
        private String name = "";
        private SoundSource source = null;
        
        protected Soundtrack(SoundSource source, String name){
            this.source = source;
            this.name = name;
        }

        protected final String getName() {
            return name;
        }
        
        public final void select(){
            source.select(this);
        }
    }
}
