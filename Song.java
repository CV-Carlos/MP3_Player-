import com.sun.xml.internal.ws.encoding.policy.SelectOptimalEncodingFeatureConfigurator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Partially completed class to represent a song for the Song Database.
 * <P>
 * This file should be copied as Song.java and then modified to provide
 * the implementation.
 */
public class Song implements Comparable<Song>
{
  private String songTitle;
  private String songArtist;
  private String songGenre;
  private String songURL;
  private int timesPlayed = 0;

  public Song(String newSongData) throws SongException
  {
      String[] songInformation = newSongData.split("\\t");
      if (songInformation.length < 5)
          throw new SongException("The song information is missing some fields");
      songTitle = songInformation[0];
      songArtist = songInformation[1];
      songGenre = songInformation[2];
      songURL = songInformation[3];
      timesPlayed = Integer.parseInt(songInformation[4]);
  }

  /**
   * Sort: use title field<BR>
   * Match: use substring of title field
   */
  public static final int USE_TITLE = 0;
  /**
   * Sort: use artist<BR>
   * Match: use substring of artist field
   */
  public static final int USE_ARTIST = 1;
  /**
   * Sort: use genre field<BR>
   * Match: use substring of genre field
   */
  public static final int USE_GENRE = 2;
  /**
   * Sort: use times played field<BR>
   * Match: use substring of times played field
   */
  public static final int USE_TIMES_PLAYED = 3;
  /**
   * Sort: not used<BR>
   * Match: use exact match with title field
   */
  public static final int USE_EXACT_TITLE = 4;
  /**
   * Sort: not used<BR>
   * Match: use exact match with artist field
   */
  public static final int USE_EXACT_ARTIST = 5;
  /**
   * Sort: not used<BR>
   * Match: use exact match with genre field
   */
  public static final int USE_EXACT_GENRE = 6;
  
  /**
   * @return return title field
   */ 
  public String getTitle()
  {
    return songTitle;
  }

  /**
   * @return return artist field
   */  
  public String getArtist()
  {
    return songArtist;
  }

  /**
   * @return return genre field
   */
  public String getGenre()
  {
    return songGenre;
  }

  /**
   * @return return URL field
   */
  public String getURL()
  {
    return songURL;
  }
  
  public String getTimesPlayed()
  {
    return Integer.toString(timesPlayed);
  }

  public void incTimesPlayed()
  {
      timesPlayed++;
  }

  /** Mutator method to modified the songs */
  public void modifySongTitle(String newSongTitle)
  {
      songTitle = newSongTitle;
  }
  public void modifySongArtist(String newSongArtist)
  {
      songArtist = newSongArtist;
  }
  public void modifySongGenre(String newSongGenre)
  {
      songGenre = newSongGenre;
  }
  public void modifySongURL(String newSongURL)
  {
      songURL = newSongURL;
  }
  public void modifySongTimesPlayed(String newSongTimesPlayed)
  {
      songURL = newSongTimesPlayed;
  }

  /** Matches this song against a given
   *  title, artist or genre,
   *  depending on the given match mode, which may be as a substring or exact.
   *  (note: there is no match for url/timesPlayed)
   *  @param matchMode match mode to use
   *  @param matchValue match value to use (either exact or substring, depending on 
   *  matchMode)
   *  @return true if and only if song matches matchValue with respect to matchMode
   */
  public boolean matches(int matchMode, String matchValue)
  {
    Pattern p = Pattern.compile("[\\w+]?"+matchValue+"[\\w+]?");
    Matcher m;
    switch (matchMode) {
        case USE_TITLE:            
            m = p.matcher(songTitle);
            if (m.find())
              return true;
            break;
        case USE_ARTIST:
            m = p.matcher(songArtist);
            if (m.find())
              return true;
            break;
        case USE_GENRE:
            m = p.matcher(songGenre);
            if (m.find())
              return true;
            break;
        case USE_TIMES_PLAYED:
            if (timesPlayed == Integer.parseInt(matchValue))
              return true;
            break;
        case USE_EXACT_TITLE:
            if (songTitle.matches(matchValue))
              return true;
            break;
        case USE_EXACT_ARTIST:
            if (songArtist.matches(matchValue))
              return true;
            break;
        case USE_EXACT_GENRE:
            if (songGenre.matches(matchValue))
              return true;
            break;   
        default:
            break;
    }
    return false;
  }

  /**
   * Sets the sort mode to use in future comparisons.
   * This should be over-ridden in an implementation.
   * @param requiredSortMode required sort mode
   */
  public static int currentSortMode;
  public static void setSortMode(int requiredSortMode) 
  {
      currentSortMode = requiredSortMode;
  }

  /**
   * Modify to Implement {@link Comparable Comparable} interface, to give a total 
   * ordering on <CODE>Song</CODE> objects, but dependent upon the
   * current sort mode.
   * (note: consistent {@link #equals(Object) equals()} 
   * and {@link #hashCode() hashCode()} methods should be implemented.
   * @param other the object to be compared
   * @return -ve(<), 0(=), +ve(>)
   */
  private String songInfo;
  private String otherInfo;
  public int compareTo(Song other)
  { 
    switch (currentSortMode) {
        case USE_TITLE:
            songInfo = songTitle + songArtist + songGenre + songURL + Integer.toString(timesPlayed);
            otherInfo = other.songTitle + other.songArtist + other.songGenre + other.songURL + 
                        Integer.toString(other.timesPlayed);
            return songInfo.compareToIgnoreCase(otherInfo);
        case USE_ARTIST:
            songInfo = songArtist + songTitle + songGenre + songURL + Integer.toString(timesPlayed);
            otherInfo = other.songArtist + other.songTitle + other.songGenre + other.songURL + 
                        Integer.toString(other.timesPlayed);            
            return songInfo.compareToIgnoreCase(otherInfo);
        case USE_GENRE:
            songInfo =  songGenre + songTitle + songArtist +  songURL + Integer.toString(timesPlayed);
            otherInfo = other.songGenre + other.songTitle + other.songArtist + other.songURL + 
                        Integer.toString(other.timesPlayed);        
            return songInfo.compareToIgnoreCase(otherInfo);
        case USE_TIMES_PLAYED:
            songInfo = Integer.toString(timesPlayed) + songTitle + songArtist + songGenre + songURL;
            otherInfo = Integer.toString(other.timesPlayed) + other.songTitle + other.songArtist + other.songGenre 
                        + other.songURL;        
            return songInfo.compareToIgnoreCase(otherInfo);
        default:
            songInfo = songTitle + songArtist + songGenre + songURL + Integer.toString(timesPlayed);
            otherInfo = other.songTitle + other.songArtist + other.songGenre + other.songURL + 
                        Integer.toString(other.timesPlayed);        
            return songInfo.compareToIgnoreCase(otherInfo);
    }
  }

  public boolean equals(Song other) 
  {
    songInfo = songTitle + songArtist + songGenre + songURL + Integer.toString(timesPlayed);
    otherInfo = other.songTitle + other.songArtist + other.songGenre + other.songURL + 
                Integer.toString(other.timesPlayed);
    if (songInfo.compareToIgnoreCase(otherInfo) == 0)
      return true;
    else                    
      return false;
  }

  public int hashCode()
  {
      byte[] songInfoBytes;
      int songCode = 0;
      songInfo = songTitle + songArtist + songGenre + songURL + Integer.toString(timesPlayed);
      songInfoBytes = songInfo.getBytes();
      for (int index = 0; index < songInfoBytes.length; index++)
      {
          songCode = songCode + songInfoBytes[index];
      }
      return songCode;
  }

  public String toString()
  {
      return "<html><font size='5'color='white'>" + songTitle + "</font><br>" +
             "<font size='4'color='#6699ff'> Artist: " + songArtist + " Genre: " 
             + songGenre + " Played: " + timesPlayed + "</html>";
  }

  public String fileFormatSongInformation()
  {
      return "\n" +songTitle + "\t" + songArtist + "\t" + songGenre + "\t" + songURL + "\t" + 
             Integer.toString(timesPlayed);
  }  
}
