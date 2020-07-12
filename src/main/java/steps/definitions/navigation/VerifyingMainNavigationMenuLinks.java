package steps.definitions.navigation;


import context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class VerifyingMainNavigationMenuLinks {
    private WebDriver myDriver;

    public VerifyingMainNavigationMenuLinks(TestContext context){
        myDriver = context.getDriver();
    }


    @Then("^verify the main navigation menu links$")
    public void verifyMainNavigationMenuLinks(){
        HashMap<String, Set<String>> menuLinkMap = new HashMap<>();
        myDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        WebElement mainNavigation = myDriver.findElement(By.xpath("//ul[@class='nav-main']"));

        List<WebElement> mainNavigationLinks = mainNavigation.findElements(By.xpath("li"));
        System.out.println("Number of Main Navigation Links are: " + mainNavigationLinks.size());

        for (int i = 0; i < mainNavigationLinks.size(); i++) {
            List<WebElement> allLinks = mainNavigationLinks.get(i).findElements(By.tagName("a"));
            if(allLinks.size()>0){
                String menuName = allLinks.get(0).getAttribute("title");
                Set<String> links = new HashSet<String>();
                for (int j = 1; j < allLinks.size() ; j++) {
                    links.add(allLinks.get(j).getAttribute("href"));
                }
                menuLinkMap.put(menuName,links);
            }
        }

        for (String name : menuLinkMap.keySet()) {
            System.out.println(name +" :: "+ menuLinkMap.get(name).size());
            for (String link : menuLinkMap.get(name)) {
                if(!verifyLink(link)){
                    System.err.println(link);
                }
            }

        }
    }


    public boolean verifyLink(String urlLink){
        try{
            if(urlLink== null || urlLink.trim().equals("")){
                return false;
            }
            URL link = new URL(urlLink);
            HttpURLConnection httpConn = (HttpURLConnection)link.openConnection();
            httpConn.setConnectTimeout(2000);
            httpConn.connect();
            if(httpConn.getResponseCode()==200){
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
