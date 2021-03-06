package integration;

import com.gb.Constants;
import com.gb.db.Database;
import com.gb.modelObject.Album;
import com.gb.modelObject.Genre;
import com.gb.modelObject.Group;
import com.gb.modelObject.Music;
import com.gb.restApp.Main;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class SeleniumTest {

    private static Database database;

    @BeforeClass
    public static void printName() {
        System.out.println("[Integration test] SeleniumTest");
        try {
            database = Database.getDatabase();

            assertNotNull(database);
            assertFalse(Database.getConnection().isClosed());
        } catch (Exception e) {
            System.out.println("-----------------------------------------------");
            e.printStackTrace();
            System.out.println("-----------------------------------------------");
        }
    }

    //@Ignore
    @Test
    public void browserFlow() {
        System.setProperty("webdriver.chrome.driver", Constants.USER_DIR+"\\src\\test\\resources\\driver\\chromedriver_84.exe");

        Main.main(new String[0]);

        WebDriver driver = new ChromeDriver();
        try {
            Database.getConnection().setAutoCommit(false);

            driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

            driver.get("localhost:8080/");


            // Inserimento di un gruppo
            WebElement groupButton = driver.findElement(By.id("dropdownGroup"));
            groupButton.click();
            WebElement insGroupButton = driver.findElement(By.id("insGroupButton"));
            insGroupButton.click();
            WebElement groupId = driver.findElement(By.id("groupid"));
            groupId.sendKeys(String.valueOf(Integer.MAX_VALUE));
            WebElement groupName = driver.findElement(By.id("name"));
            groupName.sendKeys("temporaryTestGroup");
            WebElement submitGroupButton = driver.findElement(By.id("insGroupFormButton"));
            submitGroupButton.click();

            List<Group> groupList = database.getGroupById(Integer.MAX_VALUE);
            assertFalse(groupList.isEmpty());
            assertEquals("temporaryTestGroup", groupList.get(0).getName());


            //Inserimento di un album
            WebElement albumButton = driver.findElement(By.id("dropdownAlbum"));
            albumButton.click();
            WebElement insAlbumButton = driver.findElement(By.id("insAlbumButton"));
            insAlbumButton.click();
            WebElement albumId = driver.findElement(By.id("albumid"));
            albumId.sendKeys(String.valueOf(Integer.MAX_VALUE));
            WebElement albumTitle = driver.findElement(By.id("title"));
            albumTitle.sendKeys("temporaryTestAlbum");
            WebElement albumYear = driver.findElement(By.id("year"));
            albumYear.sendKeys("2020");
            WebElement albumGroupId = driver.findElement(By.id("groupid"));
            albumGroupId.sendKeys(String.valueOf(Integer.MAX_VALUE));
            WebElement submitAlbumButton = driver.findElement(By.id("insAlbumFormButton"));
            submitAlbumButton.click();

            List<Album> albumList = database.getAlbumById(Integer.MAX_VALUE);
            assertFalse(albumList.isEmpty());
            assertEquals("temporaryTestAlbum", albumList.get(0).getTitle());


            //Inserimento di un genere
            WebElement genreButton = driver.findElement(By.id("dropdownGenre"));
            genreButton.click();
            WebElement insGenreButton = driver.findElement(By.id("insGenreButton"));
            insGenreButton.click();
            WebElement genreId = driver.findElement(By.id("genreid"));
            genreId.sendKeys(String.valueOf(Integer.MAX_VALUE));
            WebElement genreName = driver.findElement(By.id("name"));
            genreName.sendKeys("temporaryTestGenre");
            WebElement submitGenreButton = driver.findElement(By.id("insGenreFormButton"));
            submitGenreButton.click();

            List<Genre> genreList = database.getGenreById(Integer.MAX_VALUE);
            assertFalse(genreList.isEmpty());
            assertEquals("temporaryTestGenre", genreList.get(0).getName());


            //Inserimento di una canzone
            WebElement musicButton = driver.findElement(By.id("dropdownMusic"));
            musicButton.click();
            WebElement insMusicButton = driver.findElement(By.id("insMusicButton"));
            insMusicButton.click();
            WebElement musicId = driver.findElement(By.id("musicid"));
            musicId.sendKeys(String.valueOf(Integer.MAX_VALUE));
            WebElement musicTitle = driver.findElement(By.id("title"));
            musicTitle.sendKeys("temporaryTestMusic");
            WebElement musicAuthorId = driver.findElement(By.id("authorid"));
            musicAuthorId.sendKeys(String.valueOf(Integer.MAX_VALUE));
            WebElement musicAlbumId = driver.findElement(By.id("albumid"));
            musicAlbumId.sendKeys(String.valueOf(Integer.MAX_VALUE));
            WebElement musicYear = driver.findElement(By.id("year"));
            musicYear.sendKeys("2020");
            WebElement musicGenreId = driver.findElement(By.id("genreid"));
            musicGenreId.sendKeys(String.valueOf(Integer.MAX_VALUE));
            WebElement submitMusicButton = driver.findElement(By.id("insMusicFormButton"));
            submitMusicButton.click();

            List<Music> musicList = database.getMusicById(Integer.MAX_VALUE);
            assertFalse(musicList.isEmpty());
            assertEquals("temporaryTestMusic", musicList.get(0).getTitle());


            //Ricerca della canzone tramite la barra di ricerca
            WebElement searchForm = driver.findElement(By.id("searchFormText"));
            searchForm.sendKeys("temporaryTestMusic");
            WebElement searchButton = driver.findElement(By.id("searchButton"));
            searchButton.click();
            WebElement tbody = driver.findElement(By.tagName("tbody"));
            List<WebElement> td = tbody.findElement(By.tagName("tr")).findElements(By.tagName("td"));
            boolean found = false;
            for (WebElement campo : td) {
                if (campo.getText().equals("temporaryTestMusic")) {
                    found = true;
                }
            }
            assertTrue(found,
                    "La canzone appena inserita non è stata trovata tramite la funzione di ricerca.");

            //Eliminazione della canzone
            WebElement deleteButton = driver.findElement(By.id("del"+Integer.MAX_VALUE));
            deleteButton.click();
            WebElement reallyDelete = driver.findElement(By.ByClassName.className("btn-danger"));
            reallyDelete.click();

            //Nuova ricerca della canzone
            WebElement searchForm2 = driver.findElement(By.id("searchFormText"));
            searchForm2.sendKeys("temporaryTestMusic");
            WebElement searchButton2 = driver.findElement(By.id("searchButton"));
            searchButton2.click();
            try {
                //Se viene trovato qualcosa, si cerca nella tabella
                WebElement tbody2 = driver.findElement(By.tagName("tbody"));
                List<WebElement> td2 = tbody2.findElement(By.tagName("tr")).findElements(By.tagName("td"));
                boolean found2 = false;
                for (WebElement campo : td2) {
                    if (campo.getText().equals("temporaryTestMusic")) {
                        found2 = true;
                    }
                }
                assertFalse(found2,
                        "La canzone appena eliminata è stata trovata tramite la funzione di ricerca.");
            } catch (NoSuchElementException ex) {
                //Altrimenti si legge il messaggio mostrato all'utente
                WebElement main = driver.findElement(By.tagName("main"));
                WebElement message = main.findElement(By.tagName("h1"));
                if (!message.getText().contains("non trovata")) {
                    fail("La canzone appena eliminata è stata trovata tramite la funzione di ricerca.");
                }
            }
        } catch (Exception e) {
            System.out.println("-----------------------------------------------");
            e.printStackTrace();
            System.out.println("-----------------------------------------------");
            fail("Errore durante SeleniumTest");
        } finally {
            driver.quit();
            try {
                Database.getConnection().rollback();
                Database.getConnection().setAutoCommit(true);
            } catch (SQLException ey) {
                System.out.println(ey.getMessage());
            }
        }
    }

}
