package ru.airpaw.stepDefs;

import cucumber.api.java.ru.Дано;
import cucumber.api.java.ru.И;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import utils.ImageBin;
import utils.RawScreenshot;
import utils.RequestBuilder;
import org.json.JSONObject;


/**
 *
 * @author sbt-voronova-id
 */
public class StepDefinitions { 
    @Дано("^открывается страница с url \"(.*?)\"$")
    public void open_page(String pageLocalUrl) throws InterruptedException {
//        File pageLocal =  new File (pageLocalUrl);
        AppTest.driver.get("https://www.google.ru/webhp?hl=ru&ictx=2&sa=X&ved=0ahUKEwjK8OTz_NzSAhXE1iwKHUMZB8QQPQgD");
        AppTest.driver.manage().window().maximize();
        Thread.sleep(2000);
    }
    
    @И("^пользователь нажимает кнопку \"(.*?)\"$")
    public void clickButton(String buttonName) throws IOException, InterruptedException {
        BufferedImage img;
        img = AppTest.robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageBin curImage = new ImageBin(img);
        RequestBuilder req = new RequestBuilder();
        RawScreenshot screen = new RawScreenshot(img);
        JSONObject response = req.sendRequest(screen,"button",buttonName);
        int xCoord = (Integer)response.getJSONObject("point").get("x");
        int yCoord = (Integer)response.getJSONObject("point").get("y");
        AppTest.robot.mouseMove(xCoord, yCoord);
        Thread.sleep(1000);
        AppTest.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        AppTest.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(1000);
    }
    
    @И("^пользователь заполняет поле \"(.*?)\"$")
    public void inputField(String buttonName) throws IOException, InterruptedException {
        BufferedImage img;
        img = AppTest.robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageBin curImage = new ImageBin(img);
        RequestBuilder req = new RequestBuilder();
        RawScreenshot screen = new RawScreenshot(img);
        JSONObject response = req.sendRequest(screen,"input",buttonName);
        int xCoord = (Integer)response.getJSONObject("point").get("x");
        int yCoord = (Integer)response.getJSONObject("point").get("y");
        AppTest.robot.mouseMove(xCoord, yCoord);
        Thread.sleep(500);
        AppTest.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        AppTest.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(1000);
        AppTest.robot.keyPress(KeyEvent.VK_H);
        AppTest.robot.keyRelease(KeyEvent.VK_H);
        AppTest.robot.keyPress(KeyEvent.VK_H);
        AppTest.robot.keyRelease(KeyEvent.VK_H);
        AppTest.robot.keyPress(KeyEvent.VK_ENTER);
        AppTest.robot.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(1000);
    }
}
