package ru.airpaw.stepDefs;

import cucumber.api.java.ru.Дано;
import cucumber.api.java.ru.И;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import ru.airpaw.AppTest;
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
    public void open_page(String pageLocalUrl) {
        File pageLocal =  new File (pageLocalUrl);
        AppTest.driver.get(pageLocal.getAbsolutePath());
        AppTest.driver.manage().window().maximize();
    }
    
    @И("^пользователь нажимает кнопку \"(.*?)\"$")
    public void clickButton(String buttonName) throws IOException, InterruptedException {
        BufferedImage img;
        img = AppTest.robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageBin curImage = new ImageBin(img);
        RequestBuilder req = new RequestBuilder();
        RawScreenshot screen = new RawScreenshot(curImage.adaptiveThresholdingChristian(img, 50, 50));
        JSONObject response = req.sendRequest(screen,"button",buttonName);
        int xCoord = Integer.parseInt(response.getString("x"));
        int yCoord = Integer.parseInt(response.getString("y"));
        AppTest.robot.mouseMove(xCoord, yCoord);
        AppTest.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        AppTest.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(500);
    }
}
