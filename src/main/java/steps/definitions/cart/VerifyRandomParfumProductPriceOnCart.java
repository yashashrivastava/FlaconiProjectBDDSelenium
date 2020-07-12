package steps.definitions.cart;

import context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import steps.model.Product;
import steps.parsers.PriceParser;


import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class VerifyRandomParfumProductPriceOnCart {

    private WebDriver myDriver;

    public VerifyRandomParfumProductPriceOnCart(TestContext context){
            myDriver = context.getDriver();
    }

    @Given("^user is already on Flaconi Home Page$")
    public void User_is_on_Flaconi_Page(){
        myDriver.manage().window().maximize();
        myDriver.get("https://www.flaconi.de/");
        myDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        myDriver.findElement(By.id("uc-btn-accept-banner")).click();
        myDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }



    @Then("^verify Price of Random Product From Parfum Category on the Cart$")
    public void Verify_Price_Of_Random_Product_From_Parfum_Category_on_the_Cart(){
        Actions act = new Actions(myDriver);

        WebElement parfmLink = myDriver.findElement(By.linkText("PARFUM"));
        act.moveToElement(parfmLink).perform();

        WebElement fregLink = myDriver.findElement(By.linkText("Düfte"));
        act.moveToElement(fregLink).click().perform();

        myDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        Product randomProd = selectRandomPerfumeProduct();  // select random product.

        myDriver.findElement(By.xpath("//button[@title='In den Warenkorb']")).click();
        myDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // if condition for popup exists then wait for "Zum Warenkorb" link click on "Zum Warenkorb" link of that page -->> and then verify on Cart page.
        // else directly find the price on cart and Verify it

        Boolean isPresentTrue = myDriver.findElements(By.id("add-to-cart-box")).size() != 0;
        if(isPresentTrue ){
            myDriver.findElement(By.linkText("Zum Warenkorb")).click();
        } else{
            myDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }


        WebElement priceOnCart = myDriver.findElement(By.xpath("//div[@class='columns small-4 medium-3']//span[@class='cart-total-price-value']"));
        String stringPriceOnCart = priceOnCart.getText();
        Double ActualPriceOnCart = PriceParser.extractPriceFromCartPage(stringPriceOnCart);

        System.out.println("Product Price on Overview Page: " + randomProd.getPrice());
        System.out.println("Product Price on Cart: "+ ActualPriceOnCart);

        if(Double.compare(randomProd.getPrice(),ActualPriceOnCart)==0){
            System.out.println("Test Case Passed");
        } else{
            System.out.println("Test Case Failed");
        }
    }


    public Product selectRandomPerfumeProduct(){
        List<WebElement> allPerfumeProductList = myDriver.findElements(By.xpath("//*[@class='product-item-box']"));    // Select all product item box elements.

        Random rand = new Random();
        int randProdNum = rand.nextInt(allPerfumeProductList.size());

        WebElement randProductElement = allPerfumeProductList.get(randProdNum);

        WebElement priceElement = randProductElement.findElement(By.xpath("div/span[@class='price']"));
        //System.out.println(priceElement.getText());
        String productPriceString = priceElement.getText();

        Double parsedPrice = PriceParser.extractPriceFromProductPage(productPriceString);

        WebElement productNameElement = randProductElement.findElement(By.xpath("div/a/strong"));
        String randomProductName = productNameElement.getText();

        productNameElement.click();
        myDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        return new Product(randomProductName, parsedPrice);
    }

    @And("^close the Browser$")
    public void closeTheBrowser(){
        myDriver.quit();
    }


}
