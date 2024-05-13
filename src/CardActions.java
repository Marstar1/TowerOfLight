public class CardActions {
    private final Enemy enemy;
    private Hero hero;

    public CardActions(Enemy enemy,Hero hero) {

        this.enemy = enemy;
        this.hero = hero;
    }
        public int applyCardActionE (String cardName){
            int damage = 0;

            if (cardName.equals("C:/Users/Зяйка/Desktop/Курсач/Карта1.png")) {
                damage = 8;
            }
            if (cardName.equals("C:/Users/Зяйка/Desktop/Курсач/Карта2.png")) {
                damage = 11;
            }
            if (cardName.equals("C:/Users/Зяйка/Desktop/Курсач/Карта5.png")) {
                damage = 12;
            }
            if (cardName.equals("C:/Users/Зяйка/Desktop/Курсач/Карта7.png")) {
                damage = 6;
            }
            if (cardName.equals("C:/Users/Зяйка/Desktop/Курсач/Карта8.png")) {
                damage = 6;
            }
            if (cardName.equals("C:/Users/Зяйка/Desktop/Курсач/Карта9.png")) {
                damage = 6;
            }
            if (cardName.equals("C:/Users/Зяйка/Desktop/Курсач/Карта10.png")) {
                damage = 6;
            }

            return damage;
        }
        public int applyCardActionH(String cardName){
            int heal = 0;

            if (cardName.equals("C:/Users/Зяйка/Desktop/Курсач/Карта3.png")) {
                heal = 5;
            }
            return heal;

        }

        public int applyCardActionS(String cardName) {
            int shield = 0;

            if (cardName.equals("C:/Users/Зяйка/Desktop/Курсач/Карта4.png")) {
                shield = 8;
            }
            if (cardName.equals("C:/Users/Зяйка/Desktop/Курсач/Карта6.png")) {
                shield = 11;
            }
            if (cardName.equals("C:/Users/Зяйка/Desktop/Курсач/Карта11.png")) {
                shield = 8;
            }
            if (cardName.equals("C:/Users/Зяйка/Desktop/Курсач/Карта12.png")) {
                shield = 8;
            }
            if (cardName.equals("C:/Users/Зяйка/Desktop/Курсач/Карта13.png")) {
                shield = 8;
            }
            if (cardName.equals("C:/Users/Зяйка/Desktop/Курсач/Карта5.png")) {
                shield = 3;
            }

            return shield;
        }
    }
