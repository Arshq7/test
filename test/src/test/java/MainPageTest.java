import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.google.gson.internal.bind.util.ISO8601Utils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;


public class MainPageTest {

    @BeforeSuite
    public static void search() {
        com.codeborne.selenide.Configuration.baseUrl = "https://www.chitai-gorod.ru";
        open("/");
        $(By.name("q")).setValue("Тестирование").pressEnter();
        $(By.xpath("//h1[@class='color_blue']")).shouldHave(Condition.visible);
        ElementsCollection collection = $$(By.xpath("//span[contains(@class,'js__card_button_text')]"));
        collection.get(0).click();
        collection.get(1).click();
        collection.get(2).click();
    }

    @Test
    public void checkCountOfBooks() {
        String element = $(By.xpath("//span[contains(@class,'basket__item-count')]")).getText();
        int a = Integer.parseInt(element);
        Assert.assertEquals(a,3);
    }

    @Test
    public void basketInfo() {
        $(By.xpath("//a[@class='basket__logo']//img")).click();
        $(By.xpath("//li[contains(@class,'cart-nav__item')]")).shouldHave(Condition.visible);
        ElementsCollection collection = $$(By.xpath("//div[@class='basket-item__wrapper']"));
        Assert.assertEquals(collection.size(),3);
    }

    @Test
    public void checkPriceOfBooks() {
        $(By.xpath("//a[@class='basket__logo']//img")).click();
        $(By.xpath("//li[contains(@class,'cart-nav__item')]")).shouldHave(Condition.visible);
        ElementsCollection collection = $$(By.xpath("//span[@class='basket-item__price-total_discount js__total-price']"));
        String str1 = collection.get(0).getText();
        String str2 = collection.get(1).getText();
        String str3 = collection.get(2).getText();
        int priceBook1 = Integer.parseInt(str1.substring(0,4));
        int priceBook2 = Integer.parseInt(str2.substring(0,2));
        int priceBook3 = Integer.parseInt(str3.substring(0,4));
        Assert.assertEquals(priceBook1,2382);
        Assert.assertEquals(priceBook2,97);
        Assert.assertEquals(priceBook3,3207);
        int getPrice = priceBook1 + priceBook2 + priceBook3;
        String totalPrice = $(By.xpath("//span[@class='js__total_sum']")).getText();
        int a = Integer.parseInt(totalPrice);
        Assert.assertEquals(getPrice, a);
    }

    @Test
    public void deleteBook() {
        $(By.xpath("//a[@class='basket__logo']//img")).click();
        $(By.xpath("//div[contains(@class,'basket-item__control_delete')]/i")).click();
        $(By.xpath("//li[contains(@class,'cart-nav__item')]")).shouldHave(Condition.visible);
        String element = $(By.xpath("//span[@class='basket__item-count count js__basket_count']")).getText();
        int a = Integer.parseInt(element);
        Assert.assertEquals(a,2);
    }

    @Test
    public void checkPriceOfBooksAfterRemovingBook() {
        $(By.xpath("//div[contains(@class,'basket-item__control_delete')]/i")).click();
        $(By.xpath("//a[@class='basket__logo']//img")).click();
        $(By.xpath("//li[contains(@class,'cart-nav__item')]")).shouldHave(Condition.visible);
        ElementsCollection collection2 = $$(By.xpath("//span[@class='basket-item__price-total_discount js__total-price']"));
        String str1 = collection2.get(0).getText();
        String str2 = collection2.get(1).getText();
        int priceBook1 = Integer.parseInt(str1.substring(0,2));
        int priceBook2 = Integer.parseInt(str2.substring(0,4));
        int getPrice = priceBook1 + priceBook2;
        String totalPrice = $(By.xpath("//span[@class='js__total_sum']")).getText();
        int a = Integer.parseInt(totalPrice);
        Assert.assertEquals(getPrice, a);
    }

}
