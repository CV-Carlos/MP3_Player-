import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class SongDatabase
{
    private Set<Song> songSet;
    private Set<Song> copyOfSongSet;

    public SongDatabase()
    {
        songSet = new TreeSet<Song>();
    }

    public Song addNewSong(String newSongInfo) throws SongException
    {
        Song newSong = new Song(newSongInfo);        
        if (alreadyFilter)
        {
            if (newSong.matches(currentFilterMode, currentMatchFilter))
            {                     
                if(copyOfSongSet.add(newSong))
                    return newSong;
                else
                    return null;
            }
            else
            {
                return null;
            }
        }
        else
        {
            if (songSet.add(newSong))
                return newSong;
            else
                return null;      
        }
    }

    public Song[] eliminateSong(Song songToEliminate)
    {
        songSet.remove(songToEliminate);
        copyOfSongSet.remove(songToEliminate);
        if (alreadyFilter)
        {
            Song[] songArray = copyOfSongSet.toArray(new Song[0]);
            return songArray;
        }
        else
        {
            return noFilters();  
        }
    }

    private Boolean alreadyFilter = false;
    private int currentFilterMode;
    private String currentMatchFilter;
    public Song[] filterSong(int filterMode, String matchFilter)
    {
        if (filterMode <= 6 && filterMode >= 0 && matchFilter != null)
        {
            currentFilterMode = filterMode;
            currentMatchFilter = matchFilter;
            if (!alreadyFilter)
                copyOfSongSet = new TreeSet<Song>(songSet);
            alreadyFilter = true; 
            Iterator<Song> setIterator = copyOfSongSet.iterator();   
            while (setIterator.hasNext())
            {
                if (!(setIterator.next()).matches(filterMode, matchFilter))
                    setIterator.remove();
            }
            Song[] songArray = copyOfSongSet.toArray(new Song[0]);
            return songArray;   
        }
        else
        {
            return noFilters();
        }
             
    }
    
    public Song[] removeFilter()
    {
        alreadyFilter = false;
        return noFilters();
    }

    public Song[] noFilters()
    {
        Song[] songArray = songSet.toArray(new Song[0]);
        return songArray;
    }
    
    public String[] getGenres()
    {
        String[] arrayOfGenres;
        int indexArray = 0;
        if (alreadyFilter)
        {
            arrayOfGenres = new String[copyOfSongSet.size()];            
            for (Song songIterator : copyOfSongSet)
            {                
                arrayOfGenres[indexArray] = songIterator.getGenre();
                indexArray++;                
            }
        }
        else
        {
            arrayOfGenres = new String[songSet.size()];
            for (Song songIterator : songSet)
            {                
                arrayOfGenres[indexArray] = songIterator.getGenre();
                indexArray++;                 
            }
        }
        return arrayOfGenres;
    }

    public String[] getArtists()
    {
        String[] arrayOfArtists;
        int indexArray = 0;
        if (alreadyFilter)
        {
            arrayOfArtists = new String[copyOfSongSet.size()];            
            for (Song songIterator : copyOfSongSet)
            {                
                arrayOfArtists[indexArray] = songIterator.getArtist();
                indexArray++;                
            }
        }
        else
        {
            arrayOfArtists = new String[songSet.size()];
            for (Song songIterator : songSet)
            {                
                arrayOfArtists[indexArray] = songIterator.getArtist();
                indexArray++;                 
            }
        }
        return arrayOfArtists;
    }

    public String[] getTitles()
    {
        String[] arrayOfTitles;
        int indexArray = 0;
        if (alreadyFilter)
        {
            arrayOfTitles = new String[copyOfSongSet.size()];            
            for (Song songIterator : copyOfSongSet)
            {                
                arrayOfTitles[indexArray] = songIterator.getTitle();
                indexArray++;                
            }
        }
        else
        {
            arrayOfTitles = new String[songSet.size()];
            for (Song songIterator : songSet)
            {                
                arrayOfTitles[indexArray] = songIterator.getTitle();
                indexArray++;                 
            }
        }
        return arrayOfTitles;
    }

    public String[] getTimesPlayed()
    {
        String[] arrayOfTimesPlayed;
        int indexArray = 0;
        if (alreadyFilter)
        {
            arrayOfTimesPlayed = new String[copyOfSongSet.size()];            
            for (Song songIterator : copyOfSongSet)
            {                
                arrayOfTimesPlayed[indexArray] = songIterator.getTimesPlayed();
                indexArray++;                
            }
        }
        else
        {
            arrayOfTimesPlayed = new String[songSet.size()];
            for (Song songIterator : songSet)
            {                
                arrayOfTimesPlayed[indexArray] = songIterator.getTimesPlayed();
                indexArray++;                 
            }
        }
        return arrayOfTimesPlayed;        
    }

    public void playSong(MP3Player anMP3Player, Song songToPlay)         
    {
        try 
        {
            anMP3Player.play(songToPlay.getURL());
        } 
        catch (IOException exception)
        {
            System.out.println("There was an error during the playing of the song");
            System.out.println(exception.getMessage());
            
        }
                                
    }

    public void save(PrintWriter writer)
    {
        try 
        {
            Iterator<Song> setIterator = songSet.iterator();   
            while (setIterator.hasNext())
            {                
                writer.write((setIterator.next()).fileFormatSongInformation());
            }            
        } 
        catch (Exception exception) 
        {
            System.out.println("There was an error during the save of the file");
            System.out.println(exception.getMessage());
        }
        
    }

    public Song[] load(BufferedReader reader) throws SongException
    {
        String songInformation;
        Set<Song> addedSongs = new TreeSet<Song>();
        try
        {            
            while ((songInformation = reader.readLine()) != null )
            {
                Song newSong = this.addNewSong(songInformation);
                if (newSong != null)
                    addedSongs.add(newSong);
            }
            return addedSongs.toArray(new Song[0]);
        }
        catch(IOException exception)
        {
            System.out.println("There was an error during the load of the file");
            System.out.println(exception.getMessage());
        }
        return null;
    }   
}