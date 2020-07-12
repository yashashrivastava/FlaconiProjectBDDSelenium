package steps.parsers;

public class PriceParser {

    public static Double extractPriceFromProductPage(String stringPrice){
        String arrPriceString[] = stringPrice.split(" ");
        String firstElement = arrPriceString[1];
        String finalFirstElement = firstElement.replace(',','.');

        Double extractedDouble = Double.parseDouble(finalFirstElement);
        return extractedDouble;
    }


    public static Double extractPriceFromCartPage(String stringPrice){
        /*String arrPriceString[] = stringPrice.split(" ");
        String finalIntString = arrPriceString[0].replace(',', '.');*/

        String arrPriceString[] = stringPrice.split(" ");
        String ZeroElement = arrPriceString[0];
        String finalIntString = ZeroElement.replace(',', '.');

        Double extractedDouble = Double.parseDouble(finalIntString);
        return extractedDouble;
    }

}
