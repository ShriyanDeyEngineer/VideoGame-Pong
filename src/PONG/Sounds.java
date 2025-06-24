package PONG;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.util.Arrays;
import javax.sound.sampled.FloatControl;

public class Sounds
{
    Clip clip;
    URL[] SoundTrack_URLs = new URL[30];
    URL[] SFX_URLs = new URL[30];
    
    boolean canSwitchSongs = false;
    
    //String labels for the sound tracks which are used for displaying to the user what song is currently playing
    ArrayList<String> SoundTracks = new ArrayList<>(Arrays.asList("Shriyan Dey - Ascendance", "Shriyan Dey (Riffusion AI) - Finale", "Shriyan Dey (Riffusion AI) - Vivid Anthem", "Shriyan Dey (Riffusion AI) - Retro Recall", "Shriyan Dey (Riffusion AI) - Pulse"));
    
    public Sounds() 
    {
    	//Sound tracks
    	SoundTrack_URLs[0] = getClass().getResource("/PONG/Shriyan Dey - Ascendance.wav");
    	SoundTrack_URLs[1] = getClass().getResource("/PONG/Finale.wav");
    	SoundTrack_URLs[2] = getClass().getResource("/PONG/Vivid Anthem.wav");
    	SoundTrack_URLs[3] = getClass().getResource("/PONG/Retro Recall.wav");
    	SoundTrack_URLs[4] = getClass().getResource("/PONG/Pulse.wav");
        
        //Sound effects
    	SFX_URLs[0] = getClass().getResource("/PONG/Ball hits paddle SFX.wav");
    	SFX_URLs[1] = getClass().getResource("/PONG/Ball hits wall SFX.wav");
    	SFX_URLs[2] = getClass().getResource("/PONG/Ball goes out SFX.wav");
    }

    
    public void setSoundTrack_File(int i) 
    {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(SoundTrack_URLs[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            
            //Increases the volume of Ascendance.wav since its a bit too quiet
            if(i == 0)
            {
	            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	        	gainControl.setValue(gainControl.getMaximum());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setSFX_File(int i)
    {
    	try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(SFX_URLs[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void play() 
    {
        if (clip != null) 
        	clip.start();
    }

    public void loop() 
    {
        if (clip != null) 
        	clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() 
    {
        if (clip != null) 
        	clip.stop();
    }
    
    public boolean checkIfSoundTrackIsRunning()
    {
    	return clip.isRunning();
    }
    
    public void closeSoundTrack()
    {
    	clip.close();
    }
    
    public void PlaySoundTrack(int SoundTrack_ID)
    {
    	setSoundTrack_File(SoundTrack_ID);
		play();		
    }
}
