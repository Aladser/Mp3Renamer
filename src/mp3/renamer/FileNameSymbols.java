package mp3.renamer;

/**
 *
 * Символы файлов ANSI -> Юникод
 */
public abstract class FileNameSymbols{
    /**
     * Получить символ из байт кода
     *
     * @param el байт код
     * @return char символ  
     */
    public static String get(byte el){
        switch(el){
            // Большие латинские буквы
            case 65: return "A";
            case 66: return "B";
            case 67: return "C";
            case 68: return "D";
            case 69: return "E";
            case 70: return "F";
            case 71: return "G";
            case 72: return "H";
            case 73: return "I";
            case 74: return "J";
            case 75: return "K";
            case 76: return "L";
            case 77: return "M";
            case 78: return "N";
            case 79: return "O";
            case 80: return "P";
            case 81: return "Q";
            case 82: return "R";
            case 83: return "S";
            case 84: return "T";
            case 85: return "U";
            case 86: return "V";
            case 87: return "W";
            case 88: return "X";
            case 89: return "Y";
            case 90: return "Z";
            // Маленькие латинские буквы    
            case 97: return "a";
            case 98: return "b";
            case 99: return "c";
            case 100: return "d";
            case 101: return "e";
            case 102: return "f";
            case 103: return "g";
            case 104: return "h";
            case 105: return "i";
            case 106: return "j";
            case 107: return "k";
            case 108: return "l";
            case 109: return "m";
            case 110: return "n";
            case 111: return "o";
            case 112: return "p";
            case 113: return "q";
            case 114: return "r";
            case 115: return "s";
            case 116: return "t";
            case 117: return "u";
            case 118: return "v";
            case 119: return "w";
            case 120: return "x";
            case 121: return "y";
            case 122: return "z";
            // Большие русские буквы
            case -64: return "А";
            case -63: return "Б";
            case -62: return "В";
            case -61: return "Г";
            case -60: return "Д";
            case -59: return "Е";
            case -88: return "Ё";
            case -58: return "Ж";
            case -57: return "З";
            case -56: return "И";
            case -55: return "Й";
            case -54: return "К";
            case -53: return "Л";
            case -52: return "М";
            case -51: return "Н";
            case -50: return "О";
            case -49: return "П";
            case -48: return "Р";
            case -47: return "С";
            case -46: return "Т";
            case -45: return "У";
            case -44: return "Ф";
            case -43: return "Х";
            case -42: return "Ц";
            case -41: return "Ч";
            case -40: return "Ш";
            case -39: return "Щ";
            case -38: return "Ъ";
            case -37: return "Ы";
            case -36: return "Ь";
            case -35: return "Э";
            case -34: return "Ю";
            case -33: return "Я";
            // Маленькие русские буквы
            case -32: return "а";
            case -31: return "б";
            case -30: return "в";
            case -29: return "г";
            case -28: return "д";
            case -27: return "е";
            case -72: return "ё";
            case -26: return "ж";
            case -25: return "з";
            case -24: return "и";
            case -23: return "й";
            case -22: return "к";
            case -21: return "л";
            case -20: return "м";
            case -19: return "н";
            case -18: return "о";
            case -17: return "п";
            case -16: return "р";
            case -15: return "с";
            case -14: return "т";
            case -13: return "у";
            case -12: return "ф";
            case -11: return "х";
            case -10: return "ц";
            case -9: return "ч";
            case -8: return "ш";
            case -7: return "щ";
            case -6: return "ъ";
            case -5: return "ы";
            case -4: return "ь";
            case -3: return "э";
            case -2: return "ю";
            case -1: return "я";
            // Спецсимволы
            case 32: return " ";
            case 39: return "'";
            case 40: return "(";
            case 41: return ")";
            case 46: return ".";
            case 45: return "-";
            // Цифры
            case 48: return "0";
            case 49: return "1";
            case 50: return "2";
            case 51: return "3";
            case 52: return "4";                
            case 53: return "5";
            case 54: return "6";
            case 55: return "7";
            case 56: return "8";
            case 57: return "9";
                
            default: return  "";
        }
    }
    
    /**
     * Получить строку из ANSI-байтового массива
     * @param arr байтовый ANSI-массив
     * @return  String строка
     */
    public static String getStringFromBytes(byte[] arr){
        String result = "";
        for(byte el : arr) result += get(el);
        return result;
    }
}
